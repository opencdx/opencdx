# OpenCDX Communications Microservice
> Implements the OpenCDX Communications system for Email/SMS.

## Docker Image
opencdx/communications

## Docker Port
- RestAPI > Host 8380 : Container 8380
- Grpc > Host 9390 : Container 9390

## Links
_**Links are part of the build not available from GitHub.**_
- [All Test Results](build/reports/tests/test/index.html)
- [All Code Coverage](build/reports/jacoco/test/html/index.html)
- [Sonarlint Report](build/reports/sonarlint/sonarlintMain.html)
- [API-DOCS](http://localhost:8380/api-docs)
- [API-DOCS YAML](http://localhost:8380/api-docs.yaml)
## Interfaces
**OpenCDXCommunicationService** - Interface for implementation of the Communcations service.  Full implementation has been made.

**OpenCDXEmailService** - Interface for interacting with an email system for sending and receiving emails. Current implementation is to allow logging of the emails, without sending the emails.

**OpenCDXHTMLProcessor** - Thymeleaf based implementation to process an HTML template with the associated variables for generating HTML.

**OpenCDXSMSService** - Interface for interacting with an SMS system for sending and receiving SMS messages.  Current implementation is to allow logging of messages without sending.

## Tasks
**High Priority Notification Sending/Retry** - Responsible for sending High Priority Notification Events, also will retry any failed Immediate Priority Notification Events in the case of failure. Schedule is based on a cron configuration `queue.priority.high`, default is attempt every minute.

**Medium Priority Notification Sending/Retry** - Responsible for sending Medium Priority Notification Events. Schedule is based on a cron configuration `queue.priority.medium`, default is attempt every hour.

**Low Priority Notification Sending/Retry** - Responsible for sending Low Priority Notification Events. Schedule is based on a cron configuration `queue.priority.low`, default is attempt every 3 hours.
