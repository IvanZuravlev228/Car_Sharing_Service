FROM openjdk:17-jdk-slim as builder
WORKDIR car-sharing-service
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} car-sharing-service.jar
RUN java -Djarmode=layertools -jar car-sharing-service.jar extract
