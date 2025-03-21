#!/bin/bash

# Function to check minimum OpenSSL version
check_openssl_version() {
    local min_version="3.0.2"
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

    # Create a Certificate Signing Request (CSR)
    if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "mingw" || "$OSTYPE" == "cygwin" ]]; then
        openssl req -new -nodes -out "${SERVICE_NAME}-cert.csr" -newkey rsa:4096 -keyout "${SERVICE_NAME}-key.pem" -subj "//C=US\ST=CA\L=SanDiego\O=SafeHealth\OU=OpenCDx\CN=${SERVICE_NAME}" -addext "subjectAltName = DNS:localhost"
    else
        openssl req -new -nodes -out "${SERVICE_NAME}-cert.csr" -newkey rsa:4096 -keyout "${SERVICE_NAME}-key.pem" -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=${SERVICE_NAME}" -addext "subjectAltName = DNS:localhost"
    fi

    # shellcheck disable=SC1073
    cat > "${SERVICE_NAME}.v3.ext" << EOF
    authorityKeyIdentifier=keyid,issuer
    basicConstraints=CA:FALSE
    keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
    subjectAltName = @alt_names

    [alt_names]
    DNS.1 = localhost
    DNS.2 = ${SERVICE_NAME}
EOF

    # Sign the CSR by the CA and generate the certificate
    openssl x509 -req -in "${SERVICE_NAME}-cert.csr" -days 3650 -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out "${SERVICE_NAME}-cert.pem" -sha256 -extfile "${SERVICE_NAME}.v3.ext" -passin pass:opencdx

    # Create an intermediate keystore with the certificate and the key
    openssl pkcs12 -export -in "${SERVICE_NAME}-cert.pem" -inkey "${SERVICE_NAME}-key.pem" -name "${SERVICE_NAME}" -out "${SERVICE_NAME}-keystore.p12" -passout pass:opencdx

    # Import the intermediate keystore into the application keystore
    keytool -importkeystore -noprompt -srckeystore "${SERVICE_NAME}-keystore.p12" -destkeystore opencdx-keystore.p12 -srcstorepass opencdx -deststorepass opencdx

    # Remove the csr, ext and intermediate keystore
    rm "${SERVICE_NAME}.v3.ext"
    rm "${SERVICE_NAME}-cert.csr"
    rm "${SERVICE_NAME}-keystore.p12"
}

generate_ca_certificate() {
    # Set variables
    SERVICE_NAME="ca"

    openssl genrsa -aes256 -out "${SERVICE_NAME}-key.pem" -passout pass:opencdx 4096

    if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "mingw" || "$OSTYPE" == "cygwin" ]]; then
        openssl req -x509 -new -nodes -key "${SERVICE_NAME}-key.pem" -sha256 -days 3650 -out "${SERVICE_NAME}-cert.pem" -subj "//C=US\ST=CA\L=SanDiego\O=SafeHealth\OU=OpenCDx\CN=${SERVICE_NAME}" -addext "subjectAltName = DNS:localhost" -passin pass:opencdx
    else
        openssl req -x509 -new -nodes -key "${SERVICE_NAME}-key.pem" -sha256 -days 3650 -out "${SERVICE_NAME}-cert.pem" -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=${SERVICE_NAME}" -addext "subjectAltName = DNS:localhost" -passin pass:opencdx
    fi

    echo "Certificate Authority (CA) certificate generated successfully."
    echo "CA Key: $CA_KEY_FILE"
    echo "CA Certificate: $CA_CERT_FILE"
}

check_ca_files_exist() {
    CA_KEY_FILE="ca-key.pem"
    CA_CERT_FILE="ca-cert.pem"

    if [ ! -f "$CA_KEY_FILE" ] || [ ! -f "$CA_CERT_FILE" ]; then
        rm -rf ./*.pem
        rm -rf ./*.p12

        echo "CA key or certificate file not found. Generating CA certificate..."
        generate_ca_certificate
    else
        echo "CA key and certificate files already exist."
    fi
}

# Check OpenSSL version
check_openssl_version

# Check if keytool is installed
check_keytool

# Array of service names
services=(  "audit"
            "communications"
            "health"
            "iam"
            "media"
            "tinkar"
            "config"
            "admin"
            "gateway"
            "discovery"
            "questionnaire"
            "mongodb"
            "nats"
            "classification"
            "prometheus"
            "grafana"
            "zipkin"
            "logistics"
        )

check_ca_files_exist

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
cat admin-cert.pem audit-cert.pem classification-cert.pem communications-cert.pem config-cert.pem health-cert.pem discovery-cert.pem config-cert.pem iam-cert.pem media-cert.pem prometheus-cert.pem questionnaire-cert.pem tinkar-cert.pem logistics-cert.pem zipkin-cert.pem > opencdx-clients.pem

#Regenerating the JKS Keystore will require re-generating the encrypted password/passcodes in the configuration.
#keytool -genkeypair -alias config-server-key -keyalg RSA -keysize 2048 -dname 'C=US,ST=CA,L=SanDiego,O=SafeHealth,OU=OpenCDx,CN=Config' -keypass opencdx -keystore config-server.jks -storepass opencdx