FROM amazoncorretto:19.0.2 AS build

ENV ORG_GRADLE_PROJECT_isProduction=true

COPY . /otc
WORKDIR /otc
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon
RUN ./gradlew install --no-daemon

FROM amazoncorretto:19.0.2-alpine

COPY --from=build /otc/build/install/otc /otc
WORKDIR /otc/bin

EXPOSE 8080

CMD ["./otc"]