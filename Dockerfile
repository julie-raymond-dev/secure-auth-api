# syntax=docker/dockerfile:1
# ------------------------------------------------------------
# Multi-stage build for Secure Auth API (Spring Boot + Java 17)
# ------------------------------------------------------------

# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom and download dependencies (this is cached unless pom.xml changes)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copy source code
COPY src ./src

# Package the application
RUN mvn -q package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the fat jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
