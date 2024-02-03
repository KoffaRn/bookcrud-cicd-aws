FROM eclipse-temurin:17-jdk-alpine
COPY staging/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]