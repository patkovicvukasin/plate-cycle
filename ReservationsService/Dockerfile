# --------------------------------------------
# Dockerfile za ReservationsService
# --------------------------------------------
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY target/reservations-service-0.0.1-SNAPSHOT.jar reservations-service.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","/app/reservations-service.jar"]
