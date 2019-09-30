# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine
# Add Maintainer Info
LABEL maintainer="Kishore.kaipa@gmail.com"
# Make port 8085 available to the world outside this container
EXPOSE 8085
# The application's jar file
ARG JAR_FILE=target/project-management-app-0.0.1-SNAPSHOT.jar
# Add the application's jar to the container
ADD ${JAR_FILE} project-management-app-0.0.1-SNAPSHOT.jar
# Run the JAR file
ENTRYPOINT ["java","-jar","/project-management-app-0.0.1-SNAPSHOT.jar"]