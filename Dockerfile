# syntax=docker/dockerfile:1

# Build stage
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

COPY . .

RUN --mount=type=cache,target=/home/gradle/.gradle/caches \
    --mount=type=cache,target=/home/gradle/.gradle/wrapper \
    gradle :zzan-app:bootJar -x test --no-daemon --parallel

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/zzan-app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
