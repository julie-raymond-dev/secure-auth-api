# 🔐 Secure Auth API

A secure authentication API built with **Java**, **Spring Boot**, and **JWT**, following **OWASP** best practices.  
It includes user registration, login, password hashing, role-based access control, and token refresh.

## 🔧 Tech Stack
- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- Hibernate & MySQL
- OWASP security headers & best practices
- Docker & Docker Compose

## ✅ Features
- Signup / Login
- Password hashing (BCrypt)
- JWT-based authentication
- Role-based access control (RBAC)
- Token refresh endpoint
- RESTful error handling
- CORS & security headers

## 🚀 Getting Started
```bash
docker-compose up --build

📂 Structure
auth: JWT configuration

controller: API endpoints

service: Business logic

repository: Database access

config: Spring security setup

🔐 Security
CSRF protection disabled for stateless API

OWASP Top 10 mitigation (e.g., XSS, injection, etc.)

Input validation & structured error responses

Built with ❤️ for clean, secure APIs.
