# Base image for NATS server
FROM nats:alpine3.18

# Copy NATS server configuration file
COPY ./nats-server.conf /nats-server.conf

# Set the command to run NATS server with configuration file
CMD ["-c", "/nats-server.conf"]

# Set environment variable for NATS stream maximum age
ENV NATS_STREAM_MAX_AGE=86400s
