FROM amazoncorretto:17-alpine

COPY build/libs/*.jar /app.jar

RUN apk update && apk upgrade && \
    # apk add --no-cache <necessary-packages> && \
    rm -rf /var/cache/apk/*

ENTRYPOINT ["java", "-jar", "app.jar"]