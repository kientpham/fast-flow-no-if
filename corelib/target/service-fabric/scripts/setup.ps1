function GetAccessToken($client_id, $client_secret)
{
    $headers = @{"Content-Type"="application/x-www-form-urlencoded"}
    $encoded_client_secret = [System.Web.HttpUtility]::UrlEncode($client_secret)
    $body = ("grant_type=client_credentials&client_id=" + $client_id + "&client_secret=" + $encoded_client_secret +"&resource=https://vault.azure.net")
    $token_response = Invoke-RestMethod -Method Post -Uri ("https://login.windows.net/" + $subscription + "/oauth2/token") -Body $body -Headers $headers
    $access_token = $token_response."access_token"
    
    return $access_token
}

function GetSecretByAccessToken($secretKey, $access_token)
{
    #Retrieve  Secret
    $uri = ($keyvaulturl + "secrets/" + $secretKey + "?api-version=2016-10-01")
    $bearer = ("Bearer " + $access_token)
    $secret_response = Invoke-RestMethod -Method Get -Uri $uri -Headers @{"Authorization"=$bearer}
    
    return $secret_response
}

function GetSecretValueByUsernamePassword($secretKey, $clientId, $clientSecret)
{
	$access_token = GetAccessToken $clientId $clientSecret
	$response = GetSecretByAccessToken $secretKey $access_token
	
	return $response."value"
}

function ImportCertificateIntoKeyStore ($certificateKey, $secretKey, $keystore, $alias, $optional)
{

    Write-Output ("Importing certificate: " + $certificateKey + ", with secret: " + $secretKey + ", into keystore: " + $keystore) | info

    #Clean up existing files (necessary for rolling deployments)
    $pfxPath = ($Env:TMP + "\" + $certificateKey + ".pfx")

    If (Test-Path $pfxPath){
        Write-Output ( "Deleting existing pfx at: " + $pfxPath ) | info
        Remove-Item $pfxPath
    }

    $keystorePath = ($Env:TMP + "\" + $keystore + ".jks")

    If (Test-Path $keystorePath){
        Write-Output ( "Deleting existing keystore at: " + $keystorePath ) | info
        Remove-Item $keystorePath
    }

    try
    {
        #Retrieve Certificate
        Write-Output ("Retrieving certificate from: " + $uri) | info
        $cert_response = GetSecretByAccessToken $certificateKey $access_token
    }
    catch 
    {
        if ($optional -eq $True)
        {
            Write-Output ("Optional certificate with key: " + $certificateKey + " was not found in Azure Key Vault. Skipping...") | info
            return 
        }
        else
        {
            $errorMessage = ("Required certificate with key: " + $certificateKey + " not found in Azure Key Vault.")
            Write-Output $errorMessage | error
            throw $_
        }
    }

    $cert = $cert_response."value"

    try
    {
        #Retrieve Certificate Secret
        Write-Output ("Retrieving certificate secret from: " + $uri) | info
        $cert_secret_response = GetSecretByAccessToken $secretKey $access_token
    }
    catch
    {
        $errorMessage = ("Certificate secret with key: " + $secretKey + " not found in Azure Key Vault.")
        Write-Output $errorMessage | error
        throw $_
    }
    
    $cert_secret = $cert_secret_response."value"
    
    #Write Certificate to File
    Write-Output ("Writing certificate to file: " + $pfxPath) | info
    $certBytes = [System.Convert]::FromBase64String($cert)
    $certCollection = New-Object System.Security.Cryptography.X509Certificates.X509Certificate2Collection
    $certCollection.Import($certBytes,$null,[System.Security.Cryptography.X509Certificates.X509KeyStorageFlags]::Exportable)
    $protectedCertificateBytes = $certCollection.Export([System.Security.Cryptography.X509Certificates.X509ContentType]::Pkcs12, $cert_secret)

    [System.IO.File]::WriteAllBytes($pfxPath, $protectedCertificateBytes)

    Write-Output ("Creating Java KeyStore: " + $keystorePath) | info
    Write-Output ("  xmxarg: " + $xmxarg) | info
    #Write Certificates to JKS
    keytool $xmxarg -importkeystore -srckeystore $pfxPath -srcstoretype pkcs12 -destkeystore $keystorePath -deststoretype jks -deststorepass $cert_secret -destkeypass $cert_secret -srcstorepass $cert_secret -noprompt >$null 2>&1

    ChangeAlias $pfxPath $keystorePath $cert_secret $alias

    Write-Output ("Successfully imported certificate: " + $certificateKey + " into keystore: " + $keystorePath) | info

}

function ChangeAlias ($srcKeyStore, $destKeyStore, $keyStorePassword, $alias)
{
    $list=(keytool $xmxarg -list -keystore $srcKeyStore -storepass $keyStorePassword) | Out-String

    if ($null -eq $alias)
    {
        Write-Output "No alias change requested" | info
        return
    }

    if (-Not ($list.contains($alias)))
    {
        $start = $list.indexOf("{")
        $end = $list.indexOf("}") + 1
        $length = $end - $start
 
        $result = $list.substring($start, $length)
        Write-Output ("Changing alias from " + $result + " to " + $alias) | info

        keytool $xmxarg -changealias -alias $result -destalias $alias -keystore $destKeyStore -storepass $keyStorePassword
    }
    else
    {
        Write-Output ("No alias change needed") | info
    }
}

function DecryptSecret($encryptedSecret, $thumbprint)
{
    $store = New-Object System.Security.Cryptography.X509Certificates.X509Store("My", "LocalMachine")
    $store.Open([System.Security.Cryptography.X509Certificates.OpenFlags]::ReadOnly)
    $certificate = $store.Certificates | %{if($_.thumbprint -eq $thumbprint){$_}}

    $encryptedBytes = [System.Convert]::FromBase64String($encryptedSecret)

    $decryptedBytes = $certificate.PrivateKey.Decrypt($encryptedBytes, $false)
    return [System.Text.Encoding]::UTF8.GetString($decryptedBytes)
}

#######################################
# 
# MAIN
#
#######################################

Add-Type -AssemblyName System.Web

filter info {"$(Get-Date -UFormat "%Y-%m-%d %H:%M:%S") $ENV:ENVIRONMENT @project.artifactId@ INFO $pid 0 setup.ps1 $_"}
filter error {"$(Get-Date -UFormat "%Y-%m-%d %H:%M:%S") $ENV:ENVIRONMENT @project.artifactId@ ERROR $pid 0 setup.ps1 $_"}

#Get Environment Variables
$keyvaulturl = $Env:KEYVAULT_BASEURL
$client = $Env:KEYVAULT_APPID
$client_secret = $Env:KEYVAULT_APPSECRET
$subscription = $Env:SUBSCRIPTION_ID
$xmxarg = ("-J-Xmx{0}m" -f $Env:XMX)

Write-Output "Executing setup.ps1" | info

try
{
    if ($Env:ENCRYPTED -eq "true")
    {
        Write-Output "Decrypting secret" | info
        $client_secret = DecryptSecret $client_secret $ENV:THUMBPRINT
        Write-Output "Secret Decrypted" | info
    }
    else
    {
        Write-Output "Secrets are not encrypted." | info
    }

    Write-Output "Retrieving OAuth2 Token" | info
    #Retrieve OAuth2 Token
    $access_token = GetAccessToken $client $client_secret

    #Import the required SSL certificates for Tomcat
    ImportCertificateIntoKeyStore "certificate" "certificate-secret" "keystore" "tomcat" False

	#Import the sf-client-readonly-cert for applications to communicate with servicefabric
	ImportCertificateIntoKeyStore "sf-client-readonly-certificate" "sf-client-readonly-certificate-secret" "sf-client-keystore" $null False

    #For some services (access) a Boeing Gateway Certificate is needed for mutual authentication
    ImportCertificateIntoKeyStore "tlnh-access-eams-private-certificate-pfx" "tlnh-access-eams-private-certificate-pfx-secret" "boeing-gateway-keystore" $null True
    
    #For some services (access) a IFS Certificate is needed for mutual authentication
    ImportCertificateIntoKeyStore "tlnh-access-ifs-private-certificate-pfx" "tlnh-access-ifs-private-certificate-pfx-secret" "ifs-keystore" $null True

    Write-Output "setup.ps1 completed successfully" | info
}
catch
{
    Write-Output "Error occurred during the execution of setup.ps1" | info
    # Dig into the exception to get the Response details.
    # Note that value__ is not a typo.
    Write-Output ("StatusCode: " + $_.Exception.Response.StatusCode.value__) | error
    Write-Output ("StatusDescription: " + $_.Exception.Response.StatusDescription) | error
    Write-Output ("Error: " + $_) | error
    Exit 1
}
