# Spring Boot Learning Roadmap

## Recommended Project: Field Kit Exchange

Build a community platform for lending niche equipment such as cameras, camping gear, musical instruments, and tools.

### Core features

- Users register and list equipment.
- Other users reserve items for date ranges.
- Owners approve or reject reservation requests.
- The application prevents overlapping reservations.
- Users track pickup, return, damage, and maintenance states.
- Waitlists promote the next user after a cancellation.
- Reliability scores reflect completed loans and late returns.
- Users search by category, availability, location, and condition.
- Owners upload equipment photos, and users attach pickup, return, and damage photos.
- Users receive email notifications for approvals, waitlist promotions, and overdue returns, sent in the background.

## What to Learn Next

### 1. Database fundamentals

- PostgreSQL
- SQL joins, indexes, constraints, and transactions
- Spring Data JPA relationships such as `@OneToMany`
- Date-overlap queries for reservation conflicts

Resources:

- [SQLBolt](https://sqlbolt.com/)
- [PostgreSQL tutorial](https://www.postgresql.org/docs/current/tutorial.html)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### 2. Production-quality REST APIs

- DTOs instead of exposing database entities
- Request validation
- Global exception handling with `@RestControllerAdvice`
- Pagination, filtering, and sorting
- Correct HTTP methods and status codes

Resources:

- [Building REST Services with Spring](https://spring.io/guides/tutorials/rest/)
- [Spring validation guide](https://spring.io/guides/gs/validating-form-input/)

### 3. Testing

- JUnit 5
- Mockito
- Controller and service tests
- Integration tests with Testcontainers
- Tests for conflicting and concurrent reservations

Resources:

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://site.mockito.org/)
- [Testing a Spring Boot REST API with Testcontainers](https://testcontainers.com/guides/testing-spring-boot-rest-api-using-testcontainers/)

### 4. Spring Security

- Authentication and authorization
- Password hashing
- Session-based authentication first, then JWT
- Owner, borrower, and administrator roles
- Endpoint and resource-level permissions

Resources:

- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)

### 5. Application structure

- Controller, service, and repository layers
- Dependency injection
- Configuration and environment profiles
- Database migrations with Flyway
- Transaction boundaries for reservation operations

Resources:

- [Spring Boot Reference](https://docs.spring.io/spring-boot/reference/)
- [Flyway documentation](https://documentation.red-gate.com/flyway)

### 6. File upload and cloud storage

- Multipart file uploads with `MultipartFile`
- File validation: size limits, allowed content types, and safe file names
- Storing file metadata (name, type, size, storage key, owner) in PostgreSQL
- Local disk storage first, then S3-compatible object storage (AWS S3, or MinIO for local development)
- Pre-signed URLs for secure downloads and direct uploads
- Authorization checks on uploads and downloads
- Image thumbnails and cleanup of orphaned files

Resources:

- [Uploading Files](https://spring.io/guides/gs/uploading-files/)
- [AWS SDK for Java v2](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html)
- [Spring Cloud AWS](https://docs.awspring.io/spring-cloud-aws/docs/current/reference/html/index.html)
- [MinIO Java SDK](https://min.io/docs/minio/linux/developers/java/minio-java.html)

### 7. Background jobs and message queues

- Scheduled tasks with `@Scheduled` (overdue reminders, status updates)
- Asynchronous work with `@Async` and thread pools
- Message queues with RabbitMQ, including producers, consumers, and acknowledgements
- Retries, dead-letter queues, and idempotent consumers
- Sending notification emails from a queue instead of the request thread
- Running scheduled jobs safely with several app instances (ShedLock)
- Apache Kafka only after RabbitMQ, if you need event streaming

Resources:

- [Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks/)
- [Creating Asynchronous Methods](https://spring.io/guides/gs/async-method/)
- [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
- [ShedLock](https://github.com/lukas-krecan/ShedLock)
- [Spring for Apache Kafka](https://spring.io/projects/spring-kafka)

### 8. Deployment and operations

- Docker and Docker Compose
- Environment variables and secrets
- PostgreSQL, RabbitMQ, and MinIO containers
- Cloud deployment
- Logging
- Spring Boot Actuator

Resources:

- [Docker Get Started](https://docs.docker.com/get-started/)
- [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)

## Suggested Build Order

1. Build user and equipment CRUD.
2. Add PostgreSQL and Flyway migrations.
3. Implement the reservation workflow.
4. Add validation and global exception handling.
5. Add authentication and permissions.
6. Implement search, filtering, and pagination.
7. Add unit and integration tests.
8. Add equipment photo upload and storage (local disk first, then S3 or MinIO).
9. Add waitlists and scheduled overdue reminders with `@Scheduled`.
10. Move notifications to background processing with `@Async`, then RabbitMQ.
11. Containerize and deploy the application.

## General Resources

- [Learn Java](https://dev.java/learn/)
- [Spring Guides](https://spring.io/guides)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/reference/)
- Book: *Spring Start Here* by Laurentiu Spilca

## Learning Advice

Build each feature when you reach the related topic instead of completing many disconnected tutorials. Start with one well-structured Spring Boot application. Leave microservices and reactive programming until you are comfortable building, testing, securing, and deploying the complete application.
