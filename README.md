# Task-Manager-API
A secure RESTful backend API for managing personal tasks, built with Spring Boot, JWT authentication, and PostgreSQL.  Users can register, log in, and manage their own tasks through protected endpoints.

## Repository
https://github.com/Jose-bastardo/task-manager-api

## Tech Stack
Java 17+  
Spring Boot  
Spring Security  
JWT Authentication  
PostgreSQL  
Spring Data JPA / Hibernate  
Maven  

## Features
User registration  
User login with JWT authentication  
Secure password hashing (BCrypt)  
Create tasks  
Get all tasks (per user)  
Get task by ID  
Update tasks  
Delete tasks  
Stateless authentication  
Protected endpoints with Spring Security  
API Endpoints  
Auth  

### Register
```
POST /auth/register

{
  "username": "jose",
  "email": "jose@example.com",
  "password": "password123"
}
```
### Login
```
POST /auth/login

{
  "username": "jose@example.com",
  "password": "password123"
}

Response:

{
  "token": "jwt_token_here"
}
Tasks (Requires JWT)

Header:

Authorization: Bearer <token>
```

### Create Task
```
POST /tasks

{
  "title": "Finish backend",
  "description": "Complete Spring Boot API",
  "completed": false
}
```
### Get All Tasks
GET /tasks

### Get Task By ID
GET /tasks/{id}

### Update Task
PUT /tasks/{id}

### Delete Task
DELETE /tasks/{id}

## Project Structure
```
src/main/java/com/josebastardo/task_manager
│
├── Controller
├── Service
├── Repository
├── model
├── dto
├── security
└── Config
```
## Database Schema
Users  
id  
username  
email  
password  
role  
Tasks  
id  
title  
description  
completed  
user_id  

## Getting Started
1. Clone the repository  
git clone https://github.com/Jose-bastardo/task-manager.git  
cd task-manager  
2. Create PostgreSQL database  
CREATE DATABASE taskdb;  
3. Configure application.properties  
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb  
spring.datasource.username=postgres  
spring.datasource.password=postgres  
  
spring.jpa.hibernate.ddl-auto=update  
  
security.jwt.secret-key=your_secret_key  
security.jwt.expiration-time=86400000  
4. Run the application  
./mvnw spring-boot:run  
  
Application runs at:  
  
http://localhost:8080  
Authentication Flow  
Register a new user  
Login to receive JWT token  
Send token in Authorization header  
Access protected endpoints  

## Testing

Use Postman.  

Recommended order:  

Register  
Login  
Copy token  
Test /tasks endpoints  
Future Improvements  
Global exception handling  
Refresh tokens  
Pagination  
Swagger/OpenAPI docs  
Docker support  
Unit tests  
Role-based authorization  
Author  

# Jose Bastardo
https://github.com/Jose-bastardo
