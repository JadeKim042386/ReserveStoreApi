FROM amazoncorretto:17
RUN mkdir -p deploy
WORKDIR /deploy
COPY ./build/libs/ReserveStoreApi-0.0.2.jar app.jar
ENTRYPOINT ["java", "-jar", "/deploy/app.jar", "-DSpring.prifiles.active=local"]
