FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} quotation-management.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","quotation-management.jar"]
