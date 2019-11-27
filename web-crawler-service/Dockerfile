FROM adoptopenjdk/openjdk11-openj9:alpine-jre

ADD ./target/web-crawler-service-0.0.1-SNAPSHOT.jar /usr/src/web-crawler-service-0.0.1-SNAPSHOT.jar

WORKDIR usr/src

ENTRYPOINT ["java","-jar", "web-crawler-service-0.0.1-SNAPSHOT.jar"]