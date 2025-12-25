plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "hw-3-gateway"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    //implementation("org.springframework.boot:spring-boot-starter-web")
    //implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0")
    implementation("org.springdoc:springdoc-openapi-starter-common")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2025.0.0")
    }
}
