# OpenCDX

## Technology Used

- Gradle > version 8.3
- Docker
- Spring 3.1.2
- H2 Database for Local Instances
- PostgreSQL for Docker Deployment

## Repository Name: [ravi-safehealth/gRPCTemplate](https://github.com/ravi-safehealth/gRPCTemplate)

## Description

Template for creating Java Based REST/gRPC Microservices.

## Build Results

## ZERO Tolerance
ZERO Tolerance means for any code to be accepted at in a Pull Request, it must meet the following requirements:
- 0 Sonarlint issues found
- Jacoco reporting 90% code coverage.
- O Dependency Check issues.

## Reports

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