FROM eclipse-temurin:21-jre

WORKDIR /server
COPY build/libs/*-SNAPSHOT.jar ./server.jar

ENTRYPOINT ["java", "-jar", "./server.jar"]