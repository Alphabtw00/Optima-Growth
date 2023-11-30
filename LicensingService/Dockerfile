#construction image from base image provided by openjdk with version 17-jre
FROM openjdk:17-jre
#gets the jar from target folder and assigns it as  the value to variable JAR_FILE
ARG JAR_FILE=target/*.jar
#copy that variable to the directory named app.jar in the root of the image
COPY ${JAR_FILE} app.jar
#defining entrypoint (command that is executed when the container starts)(runs our app through jar)
ENTRYPOINT ["java","-jar","app.jar"]
