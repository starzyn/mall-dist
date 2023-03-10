FROM openjdk:8-slim

MAINTAINER zhan9yn

ADD target/mall-gateway-1.0-SNAPSHOT.jar /root/mall-gateway-1.0-SNAPSHOT.jar
EXPOSE 8201

ENTRYPOINT ["java","-jar","/root/mall-gateway-1.0-SNAPSHOT.jar"]