FROM openjdk:11

EXPOSE 9100

ADD build/libs/ringleader-0.0.1-SNAPSHOT.jar ringleader-0.0.1.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar", "/app.jar"]