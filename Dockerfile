# Build stage
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copy gradle files first for better caching
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Copy all module build files
COPY app/build.gradle.kts ./app/
COPY common/build.gradle.kts ./common/
COPY user/build.gradle.kts ./user/
COPY feed/build.gradle.kts ./feed/
COPY liquor/build.gradle.kts ./liquor/
COPY place/build.gradle.kts ./place/
COPY infra/build.gradle.kts ./infra/

# Download dependencies
RUN gradle dependencies --no-daemon || true

# Copy all source code
COPY . .

# Build the application
RUN gradle :app:bootJar -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
