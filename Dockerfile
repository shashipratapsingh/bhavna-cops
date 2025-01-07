FROM openjdk:17
EXPOSE 8082
COPY target/consumer-service.jar consumer-service.jar
ENTRYPOINT ["java","-jar","/consumer-service.jar"]