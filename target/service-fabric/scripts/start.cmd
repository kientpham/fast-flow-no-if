@echo off


set timestamp=%DATE:/=-%_%TIME::=-%
set timestamp=%timestamp: =%

set OMS_LOG_PATH=C:\tbh\logs
mkdir %OMS_LOG_PATH%

set APP_HEAP_DUMP_PATH=C:\tbh\logs\memory\@project.artifactId@
mkdir %APP_HEAP_DUMP_PATH%
set APP_HEAP_FILE=%APP_HEAP_DUMP_PATH%\@project.artifactId@_%timestamp%.hprof


set OMS_LOG_FILE=%OMS_LOG_PATH%\@project.artifactId@-setup.log

set AI_AGENT=-javaagent:%cd%\applicationinsights-agent.jar
set HOSTNAME_AGENT=-javaagent:%cd%\noop-hostname-verifier-0.0.1.jar
set FAST_FAIL_PARAM=-Dspring.cloud.config.failFast=true
set LOGGING_PATH_PARAM=-DLOG_PATH=%OMS_LOG_PATH%
set SERVICE_NAME_PARAM=-DSERVICE_NAME=@project.artifactId@
set ENVIRONMENT_PARAM=-DENVIRONMENT=%ENVIRONMENT%
set LOG4J2_CONFIG_PARAM=-Dlogging.config=%Fabric_Folder_Application%/@project.artifactId@Pkg.Config.@project.version@/log4j2-system.xml
set XMX_PARAM=-Xmx%XMX%m
set XMS_PARAM=-Xms%XMS%m
set MAX_METASPACE_PARAM=-XX:MaxMetaspaceSize=%MAX_METASPACE_SIZE%m
set HEAP_DUMP_PATH=-XX:HeapDumpPath=%APP_HEAP_FILE%
set HEAP_DUMP_OUT_OF_MEM=-XX:+HeapDumpOnOutOfMemoryError
set NO_WINDOWS_DUMP=-XX:-CreateMinidumpOnCrash

powershell.exe -NonInteractive -ExecutionPolicy Bypass -Command ".\setup.ps1" >> %OMS_LOG_FILE%

if errorlevel 1 (
   echo setup.ps1 Failed with error code: %errorlevel% >> %OMS_LOG_FILE%
   exit /b %errorlevel%
)

IF "%ENCRYPTED%"=="true" (
	FOR /F "delims=" %%i IN ('powershell -command "& { . .\setup.ps1; DecryptSecret -encryptedSecret %KEYVAULT_APPSECRET% -thumbprint %THUMBPRINT% }"') DO SET KEYVAULT_APPSECRET=%%i
)
FOR /F "delims=" %%i IN ('powershell -command "& { . .\setup.ps1; GetSecretValueByUsernamePassword -secretKey sf-client-readonly-certificate-secret -clientId %KEYVAULT_APPID% -clientSecret %KEYVAULT_APPSECRET% }"') DO SET SF_CLIENT_PASSWORD=%%i

set EUREKA_DISCOVERY_OPTS=-Dtoolbox.eureka.auto.discovery.enabled=true -Dtoolbox.servicefabric.client.base-url=https://%CLUSTER_PRIMARY_LOADBALANCER%:19080 -Dtoolbox.servicefabric.client.keystore-path=file:///%TMP%/sf-client-keystore.jks -Dtoolbox.servicefabric.client.keystore-password=%SF_CLIENT_PASSWORD%

java %AI_AGENT% %HOSTNAME_AGENT% %FAST_FAIL_PARAM% %LOGGING_PATH_PARAM% %SERVICE_NAME_PARAM% %LOG4J2_CONFIG_PARAM% %ENVIRONMENT_PARAM% %XMX_PARAM% %XMS_PARAM% %MAX_METASPACE_PARAM% %HEAP_DUMP_PATH% %HEAP_DUMP_OUT_OF_MEM% %NO_WINDOWS_DUMP% %EUREKA_DISCOVERY_OPTS% %JVMOPTS% -jar @project.artifactId@-@project.version@.@project.packaging@
