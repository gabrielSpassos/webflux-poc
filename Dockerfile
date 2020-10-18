FROM openjdk:11
ADD /build/libs/webflux-poc-*.jar /webflux-poc-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/webflux-poc-service.jar"]