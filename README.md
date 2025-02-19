# simple-application-api

Test project for simple-application-api.

## Requirements
For building and running the application you need:

- [JDK 17](https://www.azul.com/downloads/?version=java-17-lts&os=linux&package=jdk#zulu)
- [Maven 3](https://maven.apache.org)
- [Spring Boot 3.4.2](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.4-Release-Notes)


## Building a fat jar
```shell
cd ./simple-application-api
mvn clean package
```


## Testing the application

```shell
cd ./simple-application-api 
mvn clean test
```


## How to Run

```shell
cd ./simple-application-api
java -jar target/simple-application-api.jar
```


## Running the application locally

```shell
mvn spring-boot:run
```

## Implementation details
#### Initializer used:
[Spring Initializer](https://start.spring.io/)
#### Application Properties:

This section describes the configurable properties for the application. These properties can be set in the `application.properties` file or as environment variables.

##### General Configuration

| Property Name                   | Default Value                                  | Description                                                                                      |
|---------------------------------|------------------------------------------------|--------------------------------------------------------------------------------------------------|
| `spring.application.name`       | `simple-application-api` | The name of the Spring Boot application.                                                         |
| `server.port`                   | `8080`                                           | The port on which the application runs.                                                          |
| `routing-service.register.path` | `http://localhost:9191/register`                                         | Load balancer url used for registering instance.                        |
| `routing-service.deregister.path`              | `http://localhost:9191/deregister`                                         | Load balancer url used for de-registering instance.                                         |

