FROM openjdk:8-jdk-alpine
MAINTAINER cloud-java
RUN echo "Asia/Shanghai" > /etc/timezone
VOLUME ["/tmp", "/app", "/app-lib/all"]
EXPOSE 10100
ARG JAR_FILE
COPY target/all-lib /app-lib/all
COPY target/${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-Dloader=/app-lib/all","-jar","/app/app.jar"]
