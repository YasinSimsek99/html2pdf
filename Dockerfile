FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} htmltopdf-1.1.2-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/htmltopdf-1.1.2-SNAPSHOT.jar"]