FROM openjdk:20-slim

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

#ADD ./solar-ids-512.sa.tgz /data

COPY build/libs/opencdx-tinkar-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar "]