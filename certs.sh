cd certs

# Key pair Generation commands and exporting them to PEM files
# -------------------------------------------------------------------

openssl req -x509 -newkey rsa:4096 -keyout Audit-key.pem -out Audit-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Audit"

openssl req -x509 -newkey rsa:4096 -keyout HelloWorld-key.pem -out HelloWorld-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=HelloWorld"

openssl req -x509 -newkey rsa:4096 -keyout Communications-key.pem -out Communications-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Communications"

openssl req -x509 -newkey rsa:4096 -keyout Connected-Test-key.pem -out Connected-Test-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Connected-Test"

openssl req -x509 -newkey rsa:4096 -keyout IAM-key.pem -out IAM-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=IAM"

openssl req -x509 -newkey rsa:4096 -keyout Media-key.pem -out Media-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Media"

openssl req -x509 -newkey rsa:4096 -keyout Tinkar-key.pem -out Tinkar-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Tinkar"

openssl req -x509 -newkey rsa:4096 -keyout Config-key.pem -out Config-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Config"

openssl req -x509 -newkey rsa:4096 -keyout Admin-key.pem -out Admin-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Admin"

openssl req -x509 -newkey rsa:4096 -keyout Gateway-key.pem -out Gateway-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Gateway"

openssl req -x509 -newkey rsa:4096 -keyout Discovery-key.pem -out Discovery-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Discovery"

openssl req -x509 -newkey rsa:4096 -keyout Routine-key.pem -out Routine-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=Routine"


# Export Certificates and keys into individual keystores
# -------------------------------------------------------

openssl pkcs12 -export -in Audit-cert.pem -inkey Audit-key.pem -out Audit-keystore.p12 -name Audit -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in HelloWorld-cert.pem -inkey HelloWorld-key.pem -out HelloWorld-keystore.p12 -name HelloWorld -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in Communications-cert.pem -inkey Communications-key.pem -out Communications-keystore.p12 -name Communications -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in Connected-Test-cert.pem -inkey Connected-Test-key.pem -out Connected-Test-keystore.p12 -name Connected-Test -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in IAM-cert.pem -inkey IAM-key.pem -out IAM-keystore.p12 -name IAM -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in Media-cert.pem -inkey Media-key.pem -out Media-keystore.p12 -name Media -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in Tinkar-cert.pem -inkey Tinkar-key.pem -out Tinkar-keystore.p12 -name Tinkar -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in Config-cert.pem -inkey Config-key.pem -out Config-keystore.p12 -name Config -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in Admin-cert.pem -inkey Admin-key.pem -out Admin-keystore.p12 -name Admin -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in Gateway-cert.pem -inkey Gateway-key.pem -out Gateway-keystore.p12 -name Gateway -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in Discovery-cert.pem -inkey Discovery-key.pem -out Discovery-keystore.p12 -name Discovery -passin pass:opencdx -passout pass:opencdx

openssl pkcs12 -export -in Routine-cert.pem -inkey Routine-key.pem -out Routine-keystore.p12 -name Routine -passin pass:opencdx -passout pass:opencdx


# Import service specific keystores into a single keystore
# --------------------------------------------------------

keytool -importkeystore -noprompt -srckeystore Audit-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore HelloWorld-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore Communications-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore Connected-Test-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore IAM-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore Media-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore Tinkar-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore Config-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore Admin-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore Gateway-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore Discovery-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

keytool -importkeystore -noprompt -srckeystore Routine-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx


# Setup keys and certificate for Mongodb
# --------------------------------------

openssl req -x509 -newkey rsa:4096 -keyout mongodb-key.pem -out mongodb-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=localhost"

openssl pkcs12 -export -in mongodb-cert.pem -inkey mongodb-key.pem -out mongodb-keystore.p12 -name mongodb -passin pass:opencdx -passout pass:opencdx

keytool -importkeystore -noprompt -srckeystore mongodb-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx

cat mongodb-key.pem mongodb-cert.pem > mongodb.pem


# Setup keys and certificate for NATS
# --------------------------------------

openssl req -x509 -newkey rsa:4096 -keyout nats-key.pem -out nats-cert.pem -days 3650 -nodes -passout pass:opencdx -subj "/C=US/ST=CA/L=SanDiego/O=SafeHealth/OU=OpenCDx/CN=nats"

openssl pkcs12 -export -in nats-cert.pem -inkey nats-key.pem -out nats-keystore.p12 -name nats -passin pass:opencdx -passout pass:opencdx

keytool -importkeystore -noprompt -srckeystore nats-keystore.p12 -destkeystore opencdx-keystore.p12  -srcstorepass opencdx -deststorepass opencdx


# Delete service specific keystores
# ---------------------------------

rm $(ls -1 *.p12 | grep -v '^opencdx-keystore.p12$')


# Concatenate client certs into a client truststore
# --------------------------------------------------

cat Audit-cert.pem Communications-cert.pem HelloWorld-cert.pem Media-cert.pem Admin-cert.pem > opencdx-clients.pem
