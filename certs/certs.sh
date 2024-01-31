#!/bin/bash

# Function to check minimum OpenSSL version
check_openssl_version() {
    local min_version="3.1.2"
    if ! command -v openssl &> /dev/null; then
        echo "Error: OpenSSL is not installed. Please install OpenSSL and try again."
        exit 1
    fi

    local openssl_version=$(openssl version | awk '{print $2}')
    if [ "$(printf '%s\n' "$min_version" "$openssl_version" | sort -V | head -n1)" != "$min_version" ]; then
        echo "Error: OpenSSL version $min_version or higher is required."
        exit 1
    fi
}

# Function to check minimum Keytool version
check_keytool() {
    local min_version="20.0.1"
    if ! command -v keytool &> /dev/null; then
        echo "Error: keytool is not installed. Please install keytool and try again."
        exit 1
    fi

    local keytool_version=$(keytool -version | awk '{print $2}')
    if [ "$(printf '%s\n' "$min_version" "$keytool_version" | sort -V | head -n1)" != "$min_version" ]; then
        echo "Error: keytool version $min_version or higher is required."
        exit 1
    fi
}

generate_certificate() {
    local SERVICE_NAME=$1

    if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "mingw" || "$OSTYPE" == "cygwin" ]]; then
        openssl req -x509 -newkey rsa:4096 -keyout "${SERVICE_NAME}-key.pem" -out "${SERVICE_NAME}-cert.pem" -days 3650 -nodes -passout pass:opencdx -subj "//C=US\ST=CA\L=SanDiego\O=SafeHealth\OU=OpenCDx\CN=${SERVICE_NAME}"
    else
        openssl req -x509 -newkey rsa:4096 -keyout "${SERVICE_NAME}-key.pem" -out "${SERVICE_NAME}-cert.pem" -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=${SERVICE_NAME}"
    fi

    openssl pkcs12 -export -in "${SERVICE_NAME}-cert.pem" -inkey "${SERVICE_NAME}-key.pem" -out "${SERVICE_NAME}-keystore.p12" -name "${SERVICE_NAME}" -passin pass:opencdx -passout pass:opencdx

    keytool -importkeystore -noprompt -srckeystore "${SERVICE_NAME}-keystore.p12" -destkeystore opencdx-keystore.p12 -srcstorepass opencdx -deststorepass opencdx

    rm "${SERVICE_NAME}-keystore.p12"
}

# Check OpenSSL version
check_openssl_version

# Check if keytool is installed
check_keytool

# Array of service names
services=(  "Audit"
            "HelloWorld"
            "Communications"
            "Connected-Test"
            "IAM"
            "Media"
            "Tinkar"
            "Config"
            "Admin"
            "Gateway"
            "Discovery"
            "Routine"
            "Predictor"
            "Protector"
            "Questionnaire"
            "ANF"
            "mongodb"
            "nats"
            "classification"
            "Dashboard"
            "GraphQl"
            "Zipkin"
        )

# Generate certificates for services
for service in "${services[@]}"; do
    pem_file="${service}-cert.pem"
    if [ ! -e "$pem_file" ]; then
        echo "Generating certificate for $service..."
        generate_certificate "$service"
    fi
done

# Setup keys and certificate for Mongodb
cat mongodb-key.pem mongodb-cert.pem > mongodb.pem

# Concatenate client certs into a client truststore
cat Audit-cert.pem Communications-cert.pem HelloWorld-cert.pem Media-cert.pem Admin-cert.pem > opencdx-clients.pem


#Regenerating the JKS Keystore will require re-generating the encrypted password/passcodes in the configuration.
#keytool -genkeypair -alias config-server-key -keyalg RSA -keysize 2048 -dname 'C=US,ST=CA,L=SanDiego,O=SafeHealth,OU=OpenCDx,CN=Config' -keypass opencdx -keystore config-server.jks -storepass opencdx