# OpenCDX Audit Microservice
> This module implements the OpenCDX Audit service. It receives audit
> messages over the messaging system and then processes them.

## Docker Image
opencdx/audit

## Docker Port
- RestAPI > Host 8180 : Container 8180
- Grpc > Host 9190 : Container 9190

## Links
- [All Test Results](build/reports/tests/test/index.html)
- [All Code Coverage](build/reports/jacoco/test/html/index.html)
- [Sonarlint Report](build/reports/sonarlint/sonarlintMain.html)

## Interfaces
**OpenCDXAuditMessageHandler** - Handler for receiving messages from the OpenCDXMessageService, then processes them into an AuditEvent object. Currently this Interface outputs ALL AuditEvents to the log. A production implementation would want to implement a complete solution here to properly store and process the AuditEvent records.