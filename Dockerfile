FROM gradle:jdk21-alpine AS GRADLE_BUILD

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src
COPY . .
RUN gradle build -x test

FROM openjdk

ENV APP_HOME=/usr/app/

COPY --from=GRADLE_BUILD $APP_HOME/build/libs/PocketFMScrapping-0.0.1-SNAPSHOT.jar /app/

EXPOSE 80
ENTRYPOINT ["java", "-Dserver.port=80", "-jar", "/app/PocketFMScrapping-0.0.1-SNAPSHOT.jar"]
