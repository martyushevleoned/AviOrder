FROM openjdk:22
ARG JAR_FILE=target/*.jar
COPY ./target/AviOrder-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]