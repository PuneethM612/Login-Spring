# Simple Login Spring Boot Application

A minimal Spring Boot application that accepts login credentials and stores them in PostgreSQL.

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

## Testing with Postman

1. Send a POST request to `http://localhost:8080/login`
2. Set the Content-Type header to `application/json`
3. Set the request body to:

```json
{
  "username": "john",
  "password": "1234"
}
```

You should receive a response:

```json
{
  "message": "Successful login"
}
```

The credentials will be stored in the `login_details` table in the PostgreSQL database.
