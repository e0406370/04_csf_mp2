#################################################################

# STAGE 1 CONTAINER: FRONTEND -> BUILD THE SOURCE CODE IMAGE FOR ANGULAR

FROM node:21 AS ng-builder

RUN npm i -g @angular/cli

WORKDIR /ngapp

COPY frontend/*.json .
COPY frontend/src src

RUN npm ci && ng build

#################################################################

# STAGE 2 CONTAINER: BACKEND -> BUILD THE SOURCE CODE IMAGE FOR SPRING BOOT

FROM maven:3-eclipse-temurin-21 AS sb-builder

WORKDIR /sbapp

COPY backend/mvnw .
COPY backend/mvnw.cmd .
COPY backend/pom.xml .
COPY backend/.mvn .mvn
COPY backend/src src
COPY --from=ng-builder /ngapp/dist/frontend/browser/ /sbapp/src/main/resources/static/

RUN mvn package -Dmaven.test.skip=true

#################################################################

# STAGE 3 CONTAINER: BUILD THE FINAL APPLICATION AS THE .jar FILE

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=sb-builder /sbapp/target/backend-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=3050

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar

#################################################################