FROM java:8

MAINTAINER zhan9yn

ADD ./target/mall-auth-1.0-SNAPSHOT.jar /root/mall-auth-1.0-SNAPSHOT.jar

ENTRYPOINT ["nohup","java","-jar","/root/mall-auth-1.0-SNAPSHOT.jar","&"]