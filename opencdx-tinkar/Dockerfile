FROM openjdk:20-slim
COPY build/libs/opencdx-tinkar-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=managed -jar /app.jar "]