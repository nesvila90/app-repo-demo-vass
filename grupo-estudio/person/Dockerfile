FROM openjdk:8-jdk-alpine
RUN mkdir workspace
MAINTAINER nestorvl2011@hotmail.com
VOLUME /tmp
COPY target/*.jar app.jar
WORKDIR workspace
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar