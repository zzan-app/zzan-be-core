# Build stage
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copy gradle files first for better caching
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Copy all module build files
COPY zzan-app/build.gradle.kts ./zzan-app/
COPY zzan-core/build.gradle.kts ./zzan-core/
COPY zzan-user/build.gradle.kts ./zzan-user/
COPY zzan-feed/build.gradle.kts ./zzan-feed/
COPY zzan-liquor/build.gradle.kts ./zzan-liquor/
COPY zzan-place/build.gradle.kts ./zzan-place/
COPY zzan-infra/build.gradle.kts ./zzan-infra/

# Download dependencies
RUN gradle dependencies --no-daemon || true

# Copy all source code
COPY . .

# Build the application
RUN gradle :zzan-app:bootJar -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/zzan-app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
