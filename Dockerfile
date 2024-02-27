FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim-buster
COPY --from=build /target/asn1-0.0.1-SNAPSHOT.jar asn1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "asn1.jar"]