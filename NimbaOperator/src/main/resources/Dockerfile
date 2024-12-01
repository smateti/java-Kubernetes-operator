FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/java-operator.jar operator.jar

ENTRYPOINT ["java", "-jar", "operator.jar"]
