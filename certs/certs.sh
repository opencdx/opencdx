#!/bin/bash

generate_certificate() {
    local SERVICE_NAME=$1

    openssl req -x509 -newkey rsa:4096 -keyout "${SERVICE_NAME}-key.pem" -out "${SERVICE_NAME}-cert.pem" -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=${SERVICE_NAME}"

    openssl pkcs12 -export -in "${SERVICE_NAME}-cert.pem" -inkey "${SERVICE_NAME}-key.pem" -out "${SERVICE_NAME}-keystore.p12" -name "${SERVICE_NAME}" -passin pass:opencdx -passout pass:opencdx

    keytool -importkeystore -noprompt -srckeystore "${SERVICE_NAME}-keystore.p12" -destkeystore opencdx-keystore.p12 -srcstorepass opencdx -deststorepass opencdx

    rm "${SERVICE_NAME}-keystore.p12"
}

# Generate certificates for individual services
generate_certificate "Audit"
generate_certificate "HelloWorld"
generate_certificate "Communications"
generate_certificate "Connected-Test"
generate_certificate "IAM"
generate_certificate "Media"
generate_certificate "Tinkar"
generate_certificate "Config"
generate_certificate "Admin"
generate_certificate "Gateway"
generate_certificate "Discovery"
generate_certificate "Routine"

# Generate certificates for MongoDB
generate_certificate "mongodb"

# Generate certificates for NATS
generate_certificate "nats"

# Setup keys and certificate for Mongodb
cat mongodb-key.pem mongodb-cert.pem > mongodb.pem

# Concatenate client certs into a client truststore
cat Audit-cert.pem Communications-cert.pem HelloWorld-cert.pem Media-cert.pem Admin-cert.pem > opencdx-clients.pem
