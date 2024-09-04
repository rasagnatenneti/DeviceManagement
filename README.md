# Device Management REST API

## Prerequisites
Java 17 or later
Maven (for building the project)
MySQL

## Setup Instructions
1. Clone the Repository
2. Configure the Database
Update the application.properties file in src/main/resources/ with your database credentials.
3. Build the Project
mvn clean install
4. Run the Application
mvn spring-boot:run
5. Access the API
The API will be available at http://localhost:8080

## Docker Integration
If you prefer running the application and the database using Docker:

- Build the Docker Image
docker build -t device-management.
-Run with Docker Compose
docker-compose up

## Description

This Spring Boot project provides a RESTful service for managing a device database. The service supports basic CRUD operations, along with search functionality. It also includes security features using JWT tokens and integrates with an SQL database.

### Entities Represented

1. **Device**
   - **Name:** The name of the device.
   - **Brand:** The brand of the device.
   - **Creation Time:** The timestamp of when the device was added to the database.

## Security

This application uses JWT (JSON Web Token) for securing the endpoints. Each request to the secured endpoints requires a valid JWT token in the `Authorization` header.

### Authentication

- **Endpoint:** `POST /authenticate (http://localhost:8080/authenticate)`
- **Description:** Authenticates a user and returns a JWT token.
- **Request Body:** `JwtRequest` containing username and password.
- Example: {
    "username": "user",
    "password": "password"
}
  
### Supported Operations

The REST service supports the following operations:

1. **Add Device**
   - **Endpoint:** `[POST /devices](http://localhost:8080/devices)`
   - **Description:** Adds a new device to the database.
   - **Request Body:** `DeviceDTO` containing device name and brand
   - example: {
    "name": "Device688",
    "brand": "Brand6"
}.

2. **Get a Device by Identifier**
   - **Endpoint:** `[GET /devices/{id}](http://localhost:8080/devices/7)`
   - **Description:** Retrieves a specific device by its unique identifier.

3. **List All Devices**
   - **Endpoint:** `[GET /devices](http://localhost:8080/devices)`
   - **Description:** Lists all devices stored in the database.

4. **Update Device**
   - **Endpoint (Full Update and Partial Update):** `[PUT /devices/{id}](http://localhost:8080/devices/3)`
   - **Description:** Updates a specific device's details.
   - **Request Body:** `DeviceDTO` containing updated device details.
   - example:{
    "brand": "Brand6"
}.

5. **Delete Device**
   - **Endpoint:** `[DELETE /devices/{id}](http://localhost:8080/devices/2)`
   - **Description:** Deletes a device from the database by its identifier.

6. **Search Device by Brand**
   - **Endpoint:** `[GET /devices/search?brand={brand}](http://localhost:8080/devices/search?brand=Brand3)`
   - **Description:** Searches for devices by their brand name.


## Database Integration

The application is integrated with an SQL database for storing and managing devices. The database connection is configured via the `application.properties` file.

### Database Configuration in application.properties File

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/devicedb
spring.datasource.username=deviceuser
spring.datasource.password=devicepassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
