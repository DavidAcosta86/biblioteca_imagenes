FROM eclipse-temurin:11-jdk-alpine

WORKDIR /app

COPY target/biblioteca-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
