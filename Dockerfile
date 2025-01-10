FROM openjdk:22
EXPOSE 8084
COPY target/auth-service.jar auth-service.jar
ENTRYPOINT ["java","-jar","/auth-service.jar"]