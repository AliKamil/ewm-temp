FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY target/ewm-service-1.jar ewm-service.jar
ENTRYPOINT ["java", "-jar", "/ewm-service-1.jar"]