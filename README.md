# GeneralStore Backend

This is the back-end repository for the GeneralStore project, a store management system designed to handle inventory, sales, and customer data. 

## Tools and Technologies Used

This project leverages the following tools and technologies:

## Built With
- **Java 17** - Core language for the backend.
- **Spring Boot** - For creating RESTful APIs and managing the application lifecycle.
- **Maven** - For project dependency management and building.
- **MySQL** - Relational database for storing store, product, and customer information.
- **Git** - Version control to track changes in the project.
  
## Features
- Manage product inventory
- Track sales transactions
- Handle customer records
- Centralized error handling via Global Exception Handler

## Getting Started
1. Clone the repository.
2. Configure the database settings in `application.properties`.
3. Run the project using Maven.

##API Documentation with Swagger
I have included **Swagger** in this project to facilitate the documentation and testing of the REST Api. Swagger provides a visual interface that allows easy interaction with the API endpoints.
###How to Acces the Documentation
Once the server is up and runnin, you can acces the API documentation through the follow url:
http://localhost:8080/swagger-ui.html
Note: Be sure to replace '8080' with the port on wich your application is running, if it es diferent.

###Requirements
Ensure that your project includes the following dependenies in the 'pom.xml' file (if you are using Maven):
<dependency>
	<groupId>org.springdoc</groupId>
	<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
	<version>2.1.0</version>
</dependency>

