# project-management-app
Project Management Tasks Spring Boot Application

----------------------------
Steps to Build the Code
-------------------------
1) Clone the App code from Git Repo https://github.com/KishoreKaipa/project-management-app.git
2) Replace the db password for property spring.datasource.password under {projectDir}/src/main/resources/application.properties to {yourMySQLDBPassword}
3) Perform mvn clean install -U
   Note: If you are using Eclipse / STS / IntelliJ IDE's make sure to install Lombok on your IDE (Instructions for Lombok installation can be found via google search)


------------------------
Steps to Run the Code 
---------------------
1) cd ${project-management-app}
2) mvn spring-boot:run
3) Application is configured to run in 8085 port, hence URL for Users will be http://localhost:8085/projectmanagement/users etc.
   Note: Please refer to Sample Postman requests available in GitHub under https://github.com/KishoreKaipa/project-management-app_Postman_API_Collections.git
4) Jacoco Code Coverage details will be available under /target/site/jacocoReports/index.html

-----------------
Software Required
-----------------
1) JDK 8
2) Maven 3.6.0
3) Eclipse / IntelliJ IDE
4) MySQL DB
5) Postman REST Client

---------------
MySQL DB Dump
-------------
MySQL DB Dump is available at https://github.com/KishoreKaipa/project-management-app_DB_Artifacts.git
