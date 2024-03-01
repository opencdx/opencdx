ARG  CODE_VERSION=21-slim
FROM openjdk:${CODE_VERSION}

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

#ADD ./solar-ids-512.sa.tgz /data

RUN apt-get clean

COPY build/libs/opencdx-tinkar-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar "]