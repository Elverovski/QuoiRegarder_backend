# STAGE 1 - BUILD
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

# STAGE 2 - APPLICATION
FROM eclipse-temurin:21.0.6_7-jre-alpine-3.21 AS final
WORKDIR /opt/app
EXPOSE 8081

# Copie du jar généré correctement (nom exact du jar)
COPY --from=build /build/target/backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]