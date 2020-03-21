FROM java:8
LABEL maintainer="dev.ryulth@gmail.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/woom-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} woom-api.jar
ARG PROFILES
ENV SPRING_PROFILES_ACTIVE=${PROFILES}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/woom-api.jar"]
