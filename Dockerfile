FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/staging/app.jar"]