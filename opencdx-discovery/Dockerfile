FROM openjdk:21-slim

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

COPY build/libs/opencdx-discovery-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=managed -jar /app.jar "]