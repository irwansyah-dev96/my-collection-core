# Stage 1: Build the application
# FROM eclipse-temurin:17-jdk-alpine AS builder
# WORKDIR /app
# COPY target/readcollection-core-0.0.1.jar myapp.jar

# Stage 2: Create the final image
# FROM eclipse-temurin:17-jre-jammy
# WORKDIR /app
# COPY --from=builder /app/myapp.jar myapp.jar
# EXPOSE 8080
# CMD ["java", "-jar", "myapp.jar"]

# RUN
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY target/readcollection-core-0.0.1.jar  myapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "myapp.jar"]

