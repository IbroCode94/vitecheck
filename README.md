# New Client Setup

1. Create an entry in the Client Table

# New Client Installation

1. Create the following records 
   - survey_installation
        - installation url - path to page on site where it's installed (used for email links)
        - set store type and optionally crm type 
   - survey_installation_setting 
     - set up results view settings
   - survey_installation_store_setting
     - set up store settings 
   - survey_installation_crm_setting (if exporting to CRM) 

            RUNNING THE APPLICATION

Running the Application with Docker and PostgreSQL
Navigate to the Project Folder:
Open your terminal and navigate to the root folder of your project.

Start Docker Containers:
Run the following command to start the Docker containers defined in your docker-compose.yml file:

docker-compose up -d
This will start the PostgreSQL container and any other services you've defined.

Connect to the PostgreSQL Database:
You can use a PostgreSQL client or a tool like psql to connect to the database. Run the following command:


 -Username: vitcheck_admin -d vitcheckDB password: V1tch3ck!
It will prompt you for the password, so enter V1tch3ck!.

Run the Application: