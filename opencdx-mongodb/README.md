## MongoDB Data encryption

The following URL details options for encrypting MongoDB data.
https://www.mongodb.com/products/capabilities/security/encryption

### Encryption in transit

- Mongodb is configured with TLS to ensure that the data in transit is encrypted. 
- OpenCDx reference implementation mongodb is encrypted with self-signed certificate.
- For commercial implementation of OpenCDx, this can easily be replaced with a CA certificate.

### Encryption at rest

- Mongodb encryption at rest is an enterprise feature.
- Providers such as ATLAS provide encryption at rest as a enterprise feature. For more information on this please refer to the following URL.
- https://www.mongodb.com/docs/manual/core/security-encryption-at-rest/
- OpenCDx reference implementation does not encrypt local mongodb instance at rest.