FROM eclipse-temurin:17-jdk-focal
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY Challenge.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]