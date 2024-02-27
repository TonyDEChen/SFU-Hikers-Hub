FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim-buster
COPY --from=build /target/sfu_hikers_hub-0.0.1-SNAPSHOT.jar sfu_hikers_hub.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sfu_hikers_hub.jar"]
