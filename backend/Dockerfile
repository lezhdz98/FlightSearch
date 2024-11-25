# Use an official Eclipse Temurin runtime as a parent image
FROM eclipse-temurin:22-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file to the container
COPY build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 9090

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]



