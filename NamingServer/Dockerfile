# --------------------------------------------
# Dockerfile za NamingServer (Eureka)
# --------------------------------------------
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY target/naming-server-0.0.1-SNAPSHOT.jar naming-server.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","/app/naming-server.jar"]
