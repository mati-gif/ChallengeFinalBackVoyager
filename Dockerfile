FROM gradle:8.10.2-jdk17


COPY . .


RUN gradle build


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/VoyagerRestaurante-0.0.1-SNAPSHOT.jar"]