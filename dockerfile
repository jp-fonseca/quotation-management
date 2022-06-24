FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} quotationmanagement.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","quotationmanagement.jar"]
