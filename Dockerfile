FROM maven:3.8.4-openjdk-17 as build

RUN mkdir -p usr/local/app

COPY . usr/local/app

WORKDIR usr/local/app

RUN ["mvn","package"]

FROM openjdk:17

COPY --from=build usr/local/app/target/project-1.0-SNAPSHOT.jar usr/local/project/progetto.jar

WORKDIR /usr/local/project

CMD ["java","-jar","progetto.jar"]