plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
}

group = "com.zzan"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.5.4")
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-data-redis")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    api("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("io.github.microutils:kotlin-logging-jvm:3.0.5")
    api("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.github.f4b6a3:ulid-creator:5.2.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
