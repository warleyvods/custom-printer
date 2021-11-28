FROM openjdk:8

EXPOSE 8005
WORKDIR /app
COPY target/custom-printer.jar /app/custom-printer.jar

ENTRYPOINT ["java", "-jar", "custom-printer.jar"]
