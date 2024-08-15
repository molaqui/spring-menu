# Use the JDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Add a volume pointing to /tmp
VOLUME /tmp

# Copy the JAR file to the container
COPY target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-XX:+EnableDynamicAgentLoading", "-jar", "/app.jar"]
