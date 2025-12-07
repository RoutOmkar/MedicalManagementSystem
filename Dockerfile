# ========== Build stage ==========
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven config and source
COPY pom.xml .
COPY src ./src

# Build the Spring Boot jar (skip tests to speed up)
RUN mvn -q -DskipTests package

# ========== Run stage ==========
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy jar from build stage (whatever name it has)
COPY --from=build /app/target/*.jar app.jar

# Your app listens on 8080 (or ${PORT:8080} in application.properties)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
