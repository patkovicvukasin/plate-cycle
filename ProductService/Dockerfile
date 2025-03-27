# --------------------------------------------
# Dockerfile za ProductService
# --------------------------------------------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Proveri kako se zaista zove jar fajl (u target)
COPY target/product-service-0.0.1-SNAPSHOT.jar product-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/product-service.jar"]
