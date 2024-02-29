FROM openjdk:22-ea

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

RUN apt-get clean

COPY build/libs/opencdx-connected-test-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar "]