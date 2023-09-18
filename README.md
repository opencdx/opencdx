# OpenCDX

## Technology Used

- Gradle > version 8.3
- Docker
- Spring 3.1.2
- MongoDb for Docker Deployment

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
- [OpenCdx Hello World](opencdx-helloworld/README.md)
- [OpenCdx Proto](opencdx-proto/README.md)
- [OpenCdx Tinkar](opencdx-tinkar/README.md)

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
Usage: ./deploy.sh [--skip] [--clean] [--no_menu] [--all] [--help

--skip     Skip the build process and directly open reports/documentation.

--clean    Clean the project before building.

--no_menu  Skip the interactive menu and perform actions directly.

--all      Skip the interactive menu and open all available reports/documentation.

--help     Show this help message.

### Option available running deploy.sh
Select an option:
1. Open Test Report
2. Open Dependency Check Report
3. Open JaCoCo Report
4. Open JavaDoc
5. Open Proto Doc
6. Build Docker Image
7. Start Docker
8. Stop Docker

Enter your choice (x to Exit):

## Support Team

[Avengers Common Services (cs@safehealth.me)](mailto:cs@safehealth.me)