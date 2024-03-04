ARG  CODE_VERSION=21-slim
FROM openjdk:${CODE_VERSION}

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

COPY build/libs/opencdx-admin-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=managed -jar /app.jar "]