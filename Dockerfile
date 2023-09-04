FROM openjdk:17-jdk-alpine

MAINTAINER mediaflow-api

EXPOSE 8081
COPY build/libs/mediaflow-api-1.0.0.jar mediaflow-api-1.0.0.jar

ENTRYPOINT ["java","-jar","/mediaflow-api-1.0.0.jar"]