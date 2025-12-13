FROM gradle:8.7-jdk21 AS build
WORKDIR /app
COPY build.gradle.kts ./
COPY --chown=gradle:gradle . .
RUN gradle dependencies --refresh-dependencies && gradle build -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]