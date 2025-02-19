# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file into the container
COPY /target/simple-application-api.jar /app/simple-application-api.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-Drouting-service.register.path=http://host.docker.internal:9191/register", "-Drouting-service.deregister.path=http://host.docker.internal:9191/deregister", "-Dserver.address=0.0.0.0", "-Xmx8192M", "-jar", "simple-application-api.jar"]