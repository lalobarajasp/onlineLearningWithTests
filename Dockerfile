#running
#./mvnw clean package
#will first clean the target directory, then compile the code and package it into a JAR file
#(or other format), which will be placed in the target directory of your project. This command is
#often used before deploying an application, as it ensures that the latest changes have been compiled
#and packaged properly.

# Fetching latest version of Java
FROM openjdk:17-jdk-alpine

# Setting up work directory
#WORKDIR /app

# Copy the jar file into our app
COPY "./target/onlineLearning-0.0.1-SNAPSHOT.jar" "online-learning.jar"

# Exposing port 8080
EXPOSE 8080

# Starting the application
ENTRYPOINT ["java", "-jar", "online-learning.jar"]