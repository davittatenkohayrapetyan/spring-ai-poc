FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn -B -DskipTests clean package

# Use a lightweight, multi-arch runtime (Ubuntu Jammy)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Install curl for healthcheck
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

# Copy the built jar
COPY --from=builder /app/target/*.jar /app/app.jar

# Create a non-root user (Debian/Ubuntu base)
RUN groupadd --system spring \
    && useradd --system --gid spring --create-home spring
USER spring:spring

# Expose the application port
EXPOSE 8080

# Set the entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
