FROM debian:latest

RUN apt-get update && apt-get install openjdk-17-jre-headless -y

# The application's jar file
ARG JAR_FILE=build/libs/PocketFMScrapping-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} PocketFMScrapping-0.0.1-SNAPSHOT.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/PocketFMScrapping-0.0.1-SNAPSHOT.jar"]
