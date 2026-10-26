FROM eclipse-temurin:27-jre

RUN useradd --system --uid 1001 app
WORKDIR /app
COPY target/*.jar app.jar
USER app

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
