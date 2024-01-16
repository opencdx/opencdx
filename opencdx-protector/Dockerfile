FROM openjdk:21-slim

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

RUN apt-get clean

COPY build/libs/opencdx-protector-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar "]