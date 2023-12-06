FROM openjdk:21-slim

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl

USER root
COPY src/main/resources/zipkin-cert.pem  $JAVA_HOME/lib/security/
RUN \
    cd $JAVA_HOME/lib/security  && \
    keytool -importcert -keystore cacerts -storepass changeit -file /$JAVA_HOME/lib/security/zipkin-cert.pem -alias "zipkin"  -noprompt

COPY build/libs/opencdx-iam-*.jar app.jar

ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]
