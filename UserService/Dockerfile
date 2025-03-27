# --------------------------------------------
# Dockerfile primer za UserService
# --------------------------------------------

# 1) Baza: Java 17 JRE (umesto Java 21)
FROM eclipse-temurin:17-jre

# 2) Radni direktorijum u kontejneru
WORKDIR /app

# 3) Kopiramo JAR fajl iz "target" foldera u /app folder
#    Proveri kako se zove fajl u target (npr. user-service-0.0.1-SNAPSHOT.jar)
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar

# 4) Expose port (dokumentaciono) - ako je u application.properties 8081
EXPOSE 8081

# 5) Komanda za start aplikacije
ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]
