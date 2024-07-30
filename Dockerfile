FROM openjdk:17-jdk-slim
WORKDIR /app
COPY Challenge.jar app.jar
EXPOSE 10000
CMD ["java", "-jar", "app.jar"]