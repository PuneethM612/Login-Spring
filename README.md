# Enhanced Login/Register Spring Boot Application

A Spring Boot application with user registration and login functionality.

## Features

- User registration with unique username validation
- User authentication
- REST API endpoints for Postman testing
- Web-based UI with HTML/CSS

## Prerequisites

- Java 8
- Maven
- PostgreSQL

## Setup

1. Make sure PostgreSQL is running on port 5432
2. Create a database named `lynxdb`
3. Update the database credentials in `src/main/resources/application.properties` if needed

## Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

## Web UI Usage

1. Access the application at `http://localhost:8080`
2. You can either register a new account or login with existing credentials
3. The app validates usernames to ensure they are unique
4. After successful login, you'll be redirected to a welcome page

## Testing with Postman

### Registration API

- Send a POST request to `http://localhost:8080/api/register`
- Set the Content-Type header to `application/json`
- Set the request body to:

```json
{
  "username": "john",
  "password": "1234"
}
```

Possible responses:

- 200 OK: `{"message":"Registration successful"}`
- 409 Conflict: `{"message":"Username already exists"}`

### Login API

- Send a POST request to `http://localhost:8080/api/login`
- Set the Content-Type header to `application/json`
- Set the request body to:

```json
{
  "username": "john",
  "password": "1234"
}
```

Possible responses:

- 200 OK: `{"message":"Successful login"}`
- 401 Unauthorized: `{"message":"Invalid username or password"}`

The credentials are stored in the `login_details` table in the PostgreSQL database.
