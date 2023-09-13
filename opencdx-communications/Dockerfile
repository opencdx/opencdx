FROM openjdk:20-slim

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

COPY build/libs/opencdx-communications-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar "]