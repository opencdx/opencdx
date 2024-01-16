# OpenCDx


## Technology Used

- Gradle > version 8.3
- Docker > ?version and yq dependency
- Spring 3.1.2
- MongoDb for Docker Deployment ?version
- JDK > openjdk 20.0.2
- node version ? list of dependencies such as [pnpm, ]

## External Dependencies   
- link to analysis report

## Repository Name: [opencdx/opencdx](https://github.com/opencdx/opencdx)

## Description

Template for creating Java Based REST/gRPC Microservices.

## Modules

- [OpenCdx Admin](opencdx-admin/README.md)
- [OpenCdx Audit](opencdx-audit/README.md)
- [OpenCdx Client](opencdx-client/README.md)
- [OpenCdx Commons](opencdx-commons/README.md)
- [OpenCDx Communications](opencdx-communications/README.md)
- [OpenCdx Config](opencdx-config/README.md)
- [OpenCdx Connected Tests](opencdx-connected-test/README.md)
- [OpenCdx Hello World](opencdx-helloworld/README.md)
- [OpenCdx Media](opencdx-media/README.md)
- [OpenCdx Proto](opencdx-proto/README.md)
- [OpenCdx Tinkar](opencdx-tinkar/README.md)
- [OpenCdx Protector](opencdx-protector/README.md)
- [OpenCdx Predictor](opencdx-predictor/README.md)
- [OpenCdx Questionnaire](opencdx-questionnaire/README.md)
- [OpenCdx Routine](opencdx-routine/README.md)
- [OpenCdx Classification](opencdx-classification/README.md)




## Build Results

## ZERO Tolerance
ZERO Tolerance means for any code to be accepted at in a Pull Request, it must meet the following requirements:
- 0 Sonarlint issues found
- O JavaDoc errors/warnings
- Protobuf fully documented
- Jacoco reporting 90% code coverage.
- Spotless code formatter.

## Reports
_**Reports are part of the build not available from GitHub.**_

If a failure occurs during the build process, these reports will be generated showing the results collected. 

- [All Test Results](build/reports/allTests/index.html)
- [All Code Coverage](build/reports/jacoco/jacocoRootReport/html/index.html)
- [Dependency Check](build/reports/dependency-check-report.html)

## Deployment Procedures

1. Run the command "chmod 755 deploy.sh" in the root directory.
2. Run the command "./deploy.sh"

### Deploy parameters
For the latest command line options please use:

`./deploy.sh --help`

Execute the following to deploy application

`./deploy.sh --deploy`

Once the system is up, the list of deployed applications is available at:

https://localhost:8861/admin/applications


## JMeter Testing
1. Download the latest version of [Apache JMeter](https://jmeter.apache.org/download_jmeter.cgi)
2. Place Apache JMeter on your environment PATH
3. Install Apache JMeter plugin `JMeter gRPC Request`

## Support Team

[Avengers (cs@safehealth.me)](mailto:cs@safehealth.me)
