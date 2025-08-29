# ---- Build stage ----
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package


# ---- Runtime stage (non-root) ----
FROM eclipse-temurin:17-jre
ENV APP_HOME=/app
WORKDIR ${APP_HOME}


# Tạo user không phải root
RUN useradd -ms /bin/bash appuser \
&& mkdir -p /app/logs \
&& chown -R appuser:appuser /app


COPY --from=build /workspace/target/*-SNAPSHOT.jar app.jar


USER appuser
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]