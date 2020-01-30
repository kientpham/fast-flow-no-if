# NONE-IF Workflow for bulk data transactions to process quickly with stream parallel of the Java.

# Step to use the the framework

Step 1: Create Java or Spring Boot application
Step 2: Import the baseworkflow (code or lib) to your project
Step 3: Create your detail workflow
      a. Create Transaction Model object (for processing data)
      b. Create SharedDTO object to contain all objects or properties that share during processing
      c. Create TransactionManager extends from BaseTransactionManager to save the transaction when error or when process complete
      d. Create all Builders implement BaseBuilder to process for each transaction
      e. Create a Factory extend from AbstractFactory to include all the builders
      f. Create a Request implement from RequestHandlerBase to handle each request: read input list values and return list of transactions. 
      TEST and DONE!
