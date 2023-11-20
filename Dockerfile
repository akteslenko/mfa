FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package


FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/nexo-mfa.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]