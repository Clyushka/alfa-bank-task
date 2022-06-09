FROM openjdk:11
ADD ./build/libs/alfa-1.jar /app/
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "alfa-1.jar"]