# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the project JAR file into the container at /app
COPY target/device-management.jar /app/device-management.jar

# Expose the port on which the service runs
EXPOSE 8080

# Set environment variables for MySQL configuration
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/devicedb?useSSL=false&serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=deviceuser
ENV SPRING_DATASOURCE_PASSWORD=devicepassword

# Run the JAR file
ENTRYPOINT ["java", "-jar", "device-management.jar"]