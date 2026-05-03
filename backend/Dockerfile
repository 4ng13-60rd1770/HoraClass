# syntax=docker/dockerfile:1
# Imágenes Debian (no Alpine): manifiesto multi-arquitectura (linux/amd64 y linux/arm64, p. ej. Apple Silicon).
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests -B

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

RUN groupadd --system spring && useradd --system --gid spring spring
COPY --from=build --chown=spring:spring /build/target/horaclass-*.jar /app/app.jar
USER spring:spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
