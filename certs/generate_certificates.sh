#!/bin/bash

if [ "$#" -ne 1 ]; then
  echo "Usage: $0 <ServiceName>"
  exit 1
fi

SERVICE_NAME=$1

openssl req -x509 -newkey rsa:4096 -keyout "${SERVICE_NAME}-key.pem" -out "${SERVICE_NAME}-cert.pem" -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=${SERVICE_NAME}"

openssl pkcs12 -export -in "${SERVICE_NAME}-cert.pem" -inkey "${SERVICE_NAME}-key.pem" -out "${SERVICE_NAME}-keystore.p12" -name "${SERVICE_NAME}" -passin pass:opencdx -passout pass:opencdx

keytool -importkeystore -srckeystore "${SERVICE_NAME}-keystore.p12" -destkeystore opencdx-keystore.p12 -srcstorepass opencdx -deststorepass opencdx

rm "${SERVICE_NAME}-keystore.p12"
