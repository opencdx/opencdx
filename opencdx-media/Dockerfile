ARG  CODE_VERSION=21-slim
FROM openjdk:${CODE_VERSION}

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

RUN apt-get clean

COPY build/libs/opencdx-media-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar "]