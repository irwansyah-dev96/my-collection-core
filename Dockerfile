# RUN
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY target/readcollection-core-0.0.1.jar  myapp.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "myapp.jar"]

