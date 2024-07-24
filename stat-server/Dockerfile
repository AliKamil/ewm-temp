FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY target/stat-server-1.jar stat-server.jar
ENTRYPOINT ["java", "-jar", "/stat-server-1.jar"]