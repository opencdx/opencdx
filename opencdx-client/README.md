# OpenCDX Client Library
> Spring Library with automatic configuration for Services to communicate using gRPC to microservices. 

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

## Links
_**Links are part of the build not available from GitHub.**_
- [All Test Results](build/reports/tests/test/index.html)
- [All Code Coverage](build/reports/jacoco/test/html/index.html)
- [Sonarlint Report](build/reports/sonarlint/sonarlintMain.html)