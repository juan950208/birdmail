# birdmail

Authentication and email management system with Java and Spring boot

## Technologies

- Java 21
- Spring Boot
- JWT
- PostgreSQL

## Requirements

- Java 21
- Maven
- PostgreSQL

## So far
- User registration (implemented with frontend)
- Login (implemented with frontend)
- Send email (through API client: Postman, Insomnia, etc)
- Load inbox (through API client)
- Load sent emails (through API client)

## Set-up

1. Create databse birdmail.
2. Configure credentials on application.properties:

spring.datasource.url=...
spring.datasource.username=...
spring.datasource.password=...

## Run

1. Clone the repository.
2. Open the project on IntelliJ IDEA.
3. Run the class BirdmailApplication.
4. Access through http://localhost:8080

## Frontend run

1. Open the project birdmail_frontend on VSCode.
2. Run using live server.
3. Access through http://localhost:5500
