# OpenCDx Key Generation and Management

## Introduction

This repository contains the source code for the OpenCDx Routine project, a part of the SafeHealth initiative. The project involves the generation and management of cryptographic key pairs and certificates using OpenSSL and Keytool.

## Getting Started

Follow the instructions below to generate the necessary key pairs, certificates, and keystore for the Routine microservice project.

### Prerequisites

Make sure you have OpenSSL and Keytool installed on your system.

### Instructions

1. Generate a self-signed X.509 certificate and RSA key pair:

   ```bash
   openssl req -x509 -newkey rsa:4096
           -keyout Routine-key.pem
           -out Routine-cert.pem
           -days 3650 -nodes
           -passout pass:*******
           -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Routine"

2. Create a PKCS#12 keystore:
   ```bash
   openssl pkcs12 -export
           -in Routine-cert.pem
           -inkey Routine-key.pem
           -out Routine-keystore.p12
           -name Routine
           -passin pass:*******
           -passout pass:*******

3. Import the PKCS#12 keystore into a Java keystore:
   ```bash
   keytool -importkeystore
           -srckeystore Routine-keystore.p12 
           -destkeystore opencdx-keystore.p12
           -srcstorepass *******
           -deststorepass *******

4. Clean up the intermediate PKCS#12 keystore:
   ```bash
   rm Routine-keystore.p12


## Usage
The generated opencdx-keystore.p12 file can be used in your application for secure communication.

## License
This project is licensed under the [Apache License 2.0](LICENSE).

## Support Team

[Avengers (cs@safehealth.me)](mailto:cs@safehealth.me)
