# --------------------------------------------
# Dockerfile za PickupService
# --------------------------------------------
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY target/pickup-service-0.0.1-SNAPSHOT.jar pickup-service.jar

EXPOSE 8084

ENTRYPOINT ["java","-jar","/app/pickup-service.jar"]
