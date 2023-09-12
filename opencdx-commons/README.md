# OpenCDX Commons Library
> This module enables the autoconfiguration of various OpenCDX commons components. 

## Links
- [All Test Results](build/reports/tests/test/index.html)
- [All Code Coverage](build/reports/jacoco/test/html/index.html)
- [Sonarlint Report](build/reports/sonarlint/sonarlintMain.html)

## Interfaces
**OpenCDXMessageService** - Interface for messaging services.  Provides a consistent messaging service across all OpenCDX modules. Currently two instances of this exist. 
 1. **NoOpOpenCDXMessageServiceImpl** - A no operation instances of OpenCDXMessageService.  This is loaded if not other OpenCDXMessageService is loaded.
2. **NatsOpenCDXMessageServiceImpl** - A NATS based instance of OpenCDXMessageService.  This is loaded if nats.spring.server property is set.

**OpenCDXAuditService** - Interface for submitting Audit messages for record keeping.  Implements an instances that uses the OpenCDXMessageService to submit audit messages to the [OpenCDX-Audit](../opencdx-audit/README.md) microservice.

**OpenCDXMessageHandler** - Interface for implementing subscribers to the OpenCDXMessageService.