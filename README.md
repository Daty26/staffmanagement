# Staff Time Management

Staff Time Management is a Spring Boot application for managing employees, working hours, shifts, leave requests, notifications, and attendance logs. It includes a REST API secured with JWT authentication and a static frontend served from Spring Boot.

## Features

- User authentication with access and refresh tokens
- Role-based access for `MANAGER` and `EMPLOYEE`
- Employee user management
- Personal information management
- Clock-in and clock-out tracking
- Shift assignment management
- Leave request creation, filtering, approval, and rejection
- Notification creation, listing, and deletion
- Weekly employee attendance logs
- Swagger/OpenAPI documentation
- Seed data for local development

## Tech Stack

- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT with `jjwt`
- MapStruct
- Lombok
- Springdoc OpenAPI
- Maven

## Requirements

- Java 17 or newer
- Maven, or the included Maven wrapper
- PostgreSQL

## Getting Started

### 1. Clone the repository

```bash
git clone https://gitlab.hof-university.de/Daty/staff-time-management.git
cd staff-time-management
```

### 2. Create a PostgreSQL database

Create a local database, for example:

```sql
CREATE DATABASE staffmanagement;
```

### 3. Configure application properties

Create `src/main/resources/application.properties` if it is not already present:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/staffmanagement
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

server.port=8080
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application runs at:

```text
http://localhost:8080
```

The static frontend is served from:

```text
http://localhost:8080/
```

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

## Default Seed Data

When the database is empty, the application creates default users and sample clock entries.

| Username | Password | Role |
| --- | --- | --- |
| `ktsa1` | `1234` | `MANAGER` |
| `ktsa2` - `ktsa11` | `1234` | `EMPLOYEE` |

The initializer also creates personal information, one shift assignment for employees, and 15 previous clock entries per user.

## Authentication

Login endpoint:

```http
POST /api/v1/login
```

Example request:

```json
{
  "username": "ktsa1",
  "password": "1234"
}
```

Example response:

```json
{
  "access_token": "jwt-access-token",
  "refresh_token": "refresh-token"
}
```

Use the access token in protected requests:

```http
Authorization: Bearer jwt-access-token
```

Refresh token endpoint:

```http
POST /api/v1/refreshToken
```

## API Overview

| Module | Endpoint | Description |
| --- | --- | --- |
| Authentication | `POST /api/v1/login` | Login and receive tokens |
| Authentication | `POST /api/v1/refreshToken` | Refresh access token |
| Users | `POST /api/v1/users` | Create a user |
| Users | `GET /api/v1/users` | Get all users |
| Users | `GET /api/v1/users/{id}` | Get user by ID |
| Users | `PUT /api/v1/users/{id}` | Update user data |
| Clock | `POST /api/v1/clock/in` | Clock in |
| Clock | `POST /api/v1/clock/out` | Clock out |
| Clock | `GET /api/v1/clock/user` | Get current user's clock entries |
| Clock | `GET /api/v1/clock` | Get all clock entries |
| Leave | `POST /api/v1/leaves` | Create leave request |
| Leave | `GET /api/v1/leaves` | Get all leave requests |
| Leave | `GET /api/v1/leaves/user` | Get current user's leave requests |
| Leave | `GET /api/v1/leaves/{id}` | Get leave request by ID |
| Leave | `GET /api/v1/leaves/status/{status}` | Filter leave requests by status |
| Leave | `PUT /api/v1/leaves/{id}/status` | Update leave request status |
| Shifts | `POST /api/shift` | Create shift assignment |
| Shifts | `GET /api/shift` | Get all shift assignments |
| Shifts | `GET /api/shift/user` | Get current user's shifts |
| Personal Info | `PUT /api/personalInfo/updatePersonalInfo` | Update personal information |
| Personal Info | `GET /api/personalInfo/user` | Get current user's personal information |
| Personal Info | `GET /api/personalInfo` | Get all personal information |
| Notifications | `POST /api/notifications` | Send notification |
| Notifications | `GET /api/notifications` | Get all notifications |
| Notifications | `GET /api/notifications/user` | Get current user's notifications |
| Notifications | `DELETE /api/notifications/{id}` | Delete notification |
| Logs | `GET /api/v1/logs?userId={id}&weekStart={date}` | Get weekly employee attendance logs |

## Roles

### Manager

Managers can manage users, view employee logs, send notifications, review clock entries, and update leave request statuses.

### Employee

Employees can clock in and out, view their own data, manage personal information, view shifts and notifications, and submit leave requests.

## Project Structure

```text
src/main/java/de/university/staffmanagement
|-- config          Security, CORS, Swagger, JWT filter, and seed data
|-- controller      REST controllers
|-- dto             Request and response DTOs
|-- entity          JPA entities
|-- enums           Roles, statuses, leave types, and schedule types
|-- exception       Global exception handling
|-- mapper          MapStruct mappers
|-- repository      Spring Data JPA repositories
`-- service         Business logic interfaces and implementations
```

## Testing

Run all tests:

```bash
./mvnw test
```

## Generate Javadocs

The project is configured to generate Javadocs into the `docs` directory.

```bash
./mvnw javadoc:javadoc
```

Open the generated documentation at:

```text
docs/apidocs/index.html
```

## Notes

- JWT access tokens are valid for 20 minutes.
- Most API responses are wrapped in `ResponseWrapper`.
- Swagger endpoints and login/refresh endpoints are public.
- Protected endpoints require a valid `Authorization: Bearer <token>` header.
