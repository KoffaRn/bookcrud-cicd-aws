FROM eclipse-temurin:17-jdk-alpine
ENV SPRING_PASSWORD=""
COPY staging/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]