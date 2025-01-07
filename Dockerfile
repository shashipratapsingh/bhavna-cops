FROM openjdk:17
EXPOSE 8761
COPY target/eureka-server.jar eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar"]

#for making docker image
# docker build --tag=eureka-server:latest .

#for docker compase
# docke-compose up -d

