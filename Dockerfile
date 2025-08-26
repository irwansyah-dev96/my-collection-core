# RUN
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY target/readcollection-core-0.0.1.jar  myapp.jar
COPY config/application-core.properties application.properties
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "myapp.jar"]

