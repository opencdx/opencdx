FROM openjdk:20-slim

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

COPY build/libs/opencdx-config-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=native -jar /app.jar "]