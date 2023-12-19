FROM openjdk:21-slim

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

COPY build/libs/opencdx-predictor-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar "]