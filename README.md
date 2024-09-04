# Device Management REST API

## Description

This Spring Boot project provides a RESTful service for managing a device database. The service supports basic CRUD operations, along with search functionality. It also includes security features using JWT tokens and integrates with an SQL database.

### Entities Represented

1. **Device**
   - **Name:** The name of the device.
   - **Brand:** The brand of the device.
   - **Creation Time:** The timestamp of when the device was added to the database.

### Supported Operations

The REST service supports the following operations:

1. **Add Device**
   - **Endpoint:** `POST /devices`
   - **Description:** Adds a new device to the database.
   - **Request Body:** `DeviceDTO` containing device name and brand.

2. **Get Device by Identifier**
   - **Endpoint:** `GET /devices/{id}`
   - **Description:** Retrieves a specific device by its unique identifier.

3. **List All Devices**
   - **Endpoint:** `GET /devices`
   - **Description:** Lists all devices stored in the database.

4. **Update Device**
   - **Endpoint (Full Update and Partial Update):** `PUT /devices/{id}`
   - **Description:** Updates a specific device's details.
   - **Request Body:** `DeviceDTO` containing updated device details.

5. **Delete Device**
   - **Endpoint:** `DELETE /devices/{id}`
   - **Description:** Deletes a device from the database by its identifier.

6. **Search Device by Brand**
   - **Endpoint:** `GET /devices/search?brand={brand}`
   - **Description:** Searches for devices by their brand name.

## Security

This application uses JWT (JSON Web Token) for securing the endpoints. Each request to the secured endpoints requires a valid JWT token in the `Authorization` header.

### Authentication

- **Endpoint:** `POST /authenticate`
- **Description:** Authenticates a user and returns a JWT token.
- **Request Body:** `JwtRequest` containing username and password.

## Database Integration

The application is integrated with an SQL database for storing and managing devices. The database connection is configured via the `application.properties` file.

### Database Configuration Example

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/devicedb
spring.datasource.username=root
spring.datasource.password=rootpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
