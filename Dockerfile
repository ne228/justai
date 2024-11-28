# Stage 1: Use Maven image to build the project
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Build the project to create the WAR file
RUN mvn clean install

# Stage 2: Use a smaller image to run the WAR file
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the WAR file from the build stage to the runtime container
COPY --from=build /app/target/task-0.0.1-SNAPSHOT.war /opt/app/task.war

# Expose the port the app will run on
EXPOSE 9999

# Command to run the application
CMD ["java", "-jar", "/opt/app/task.war"]
