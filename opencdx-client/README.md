# OpenCDX Client Library

## Description
Spring Library with automatic configuration for Services to communicate using
gRPC to microservices. 

## Usage

### Helloworld Client

Below is an example of the applicaiton.yml contents that is required to 
instantiate the Helloworld Client. Address is subject to change.

```yaml
opencdx:
  client:
    helloworld: true

grpc:
  client:
    helloworld-server:
      address: 'static://helloworld:9090'
      enableKeepAlive: true
      keepAliveWithoutCalls: true
      negotiationType: plaintext
```