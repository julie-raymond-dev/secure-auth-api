# 🔐 Secure Auth API

[![CI](https://github.com/julie-raymond-dev/secure-auth-api/actions/workflows/ci.yml/badge.svg)](https://github.com/julie-raymond-dev/secure-auth-api/actions/workflows/ci.yml)
[![Deploy to Render](https://img.shields.io/badge/Render-Deploy-blue)](https://render.com/deploy?repo=https://github.com/julie-raymond-dev/secure-auth-api)
[![Codecov](https://codecov.io/gh/julie-raymond-dev/secure-auth-api/branch/main/graph/badge.svg)](https://codecov.io/gh/julie-raymond-dev/secure-auth-api)
[![Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen)](target/site/jacoco/index.html)

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

## 📑 API Documentation

![Swagger UI](docs/swagger.png)


* Swagger UI (running app): `http://localhost:8180/swagger-ui.html`
* OpenAPI spec: [`docs/openapi.yaml`](docs/openapi.yaml)

## 💻 Quick Examples (curl)
```bash
# Signup
curl -X POST http://localhost:8180/api/auth/register \
    -H "Content-Type: application/json" \
    -d '{"email":"test@example.com","password":"Passw0rd!"}'

# Login
curl -X POST http://localhost:8180/api/auth/authenticate \
    -H "Content-Type: application/json" \
    -d '{"email":"test@example.com","password":"Passw0rd!"}'

# Access a protected admin endpoint
curl -H "Authorization: Bearer <ACCESS_TOKEN>" http://localhost:8180/api/admin/resource
```

## 📈 Observability

The application exposes Spring Boot Actuator endpoints (health, metrics) and a Prometheus scrape endpoint.

- Health: `GET /actuator/health`
- Metrics: `GET /actuator/metrics`
- Prometheus format: `GET /actuator/prometheus`

Prometheus scrape sample:
```yaml
- job_name: secure-auth-api
  metrics_path: /actuator/prometheus
  static_configs:
    - targets: ['secure-auth-api:8180']
```

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
