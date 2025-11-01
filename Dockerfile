## ---- Build stage ----
#FROM maven:3.9.8-eclipse-temurin-17 AS build
#WORKDIR /workspace
#COPY pom.xml .
#RUN mvn -q -e -DskipTests dependency:go-offline
#COPY src ./src
#RUN mvn -q -DskipTests package
#
#
## ---- Runtime stage (non-root) ----
#FROM eclipse-temurin:17-jre
#ENV APP_HOME=/app
#WORKDIR ${APP_HOME}
#
#
## Tạo user không phải root
#RUN useradd -ms /bin/bash appuser \
#&& mkdir -p /app/logs \
#&& chown -R appuser:appuser /app
#
#
#COPY --from=build /workspace/target/*-SNAPSHOT.jar app.jar
#
#
#USER appuser
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/app/app.jar"]

# Base stage (reused by test and dev stages)
FROM eclipse-temurin:17-jdk-jammy AS base

WORKDIR /build

COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/

# Test stage
FROM base AS test

WORKDIR /build

COPY ./src src/
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw test

# Intermediate stage
FROM base AS deps

WORKDIR /build

RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw dependency:go-offline -DskipTests

# Intermediate stage
FROM deps AS package

WORKDIR /build

COPY ./src src/
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

# Build stage
FROM package AS extract

WORKDIR /build

RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

# Development stage
FROM extract AS development

WORKDIR /build

RUN cp -r /build/target/extracted/dependencies/. ./
RUN cp -r /build/target/extracted/spring-boot-loader/. ./
RUN cp -r /build/target/extracted/snapshot-dependencies/. ./
RUN cp -r /build/target/extracted/application/. ./

ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000"

CMD [ "java", "-Dspring.profiles.active=postgres", "org.springframework.boot.loader.launch.JarLauncher" ]

# Runtime stage
FROM eclipse-temurin:17-jre-jammy AS runtime

ARG UID=10001

RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser

COPY --from=extract build/target/extracted/dependencies/ ./
COPY --from=extract build/target/extracted/spring-boot-loader/ ./
COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract build/target/extracted/application/ ./

EXPOSE 8080
#ENTRYPOINT [ "java", "-Dspring.profiles.active=postgres", "org.springframework.boot.loader.launch.JarLauncher" ]
ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]
