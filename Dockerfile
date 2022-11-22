FROM amazoncorretto:17
LABEL maintainer="francescobruno97"
ADD build/libs/client-1.0-SNAPSHOT.jar client.jar
ENTRYPOINT ["java","-jar","client.jar"]