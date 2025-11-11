# ---------- STAGE 1 : build ----------
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -B -q -DskipTests clean package

# ---------- STAGE 2 : runtime ----------
FROM eclipse-temurin:21-jre
# user non-root
RUN useradd -ms /bin/bash appuser
USER appuser
WORKDIR /app

# copie du jar (ajuste le wildcard si besoin)
COPY --from=builder /app/target/*SNAPSHOT*.jar /app/app.jar

ENV SPRING_PROFILES_ACTIVE=docker \
    JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseG1GC"

EXPOSE 8080
# Si tu n'as pas actuator, commente la ligne suivante.
# HEALTHCHECK --interval=30s --timeout=3s --retries=5 CMD curl -fsS http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
