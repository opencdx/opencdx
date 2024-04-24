# OpenCDx

## Repository Name: [opencdx/opencdx](https://github.com/opencdx/opencdx)

## Software Required
- Apache JMeter: 5.6.2 ℹ️
- Apache JMeter gRPC Request: 1.2.6 ℹ️
- Docker Desktop: 4.28.0 ℹ️  (Minimum recommended Docker resources: 24gb RAM, 6 CPU, 2gb Swap)
- Git: 2.39.3 ℹ️
- Java Development Kit (JDK): 21
- Keytool: 20.0.1
- Nodejs: 20.0.0
- OpenSSL: 3.1.2
- protoc-gen-doc: 1.5.1
- yq: 4:40.5 ℹ️

ℹ️ Specific version not required, but the latest version is recommended.

## Description

OpenCDX is a platform for the management of clinical data exchange. It is designed to be a secure, scalable, and interoperable platform for the exchange of clinical data. 

## Modules

- [OpenCdx Admin](opencdx-admin/README.md)
- [OpenCdx Anf](opencdx-anf/README.md)
- [OpenCdx Audit](opencdx-audit/README.md)
- [OpenCdx Classification](opencdx-classification/README.md)
- [OpenCdx Client](opencdx-client/README.md)
- [OpenCdx Commons](opencdx-commons/README.md)
- [OpenCDx Communications](opencdx-communications/README.md)
- [OpenCdx Config](opencdx-config/README.md)
- [OpenCdx Connected Lab](opencdx-connected-lab/README.md)
- [OpenCdx Connected Tests](opencdx-connected-test/README.md)
- [OpenCdx Discovery](opencdx-discovery/README.md)
- [OpenCdx Gateway](opencdx-gateway/README.md)
- [OpenCdx IAM](opencdx-iam/README.md)
- [OpenCdx Media](opencdx-media/README.md)
- [OpenCdx Predictor](opencdx-predictor/README.md)
- [OpenCDX Protector](opencdx-protector/README.md)
- [OpenCdx Proto](opencdx-proto/README.md)
- [openCdx Questionnaire](opencdx-questionnaire/README.md)
- [OpenCdx Routine](opencdx-routine/README.md)
- [OpenCdx Shipping](opencdx-shipping/README.md)
- [OpenCdx Tinkar](opencdx-tinkar/README.md)


## ZERO Tolerance
ZERO Tolerance means for any code to be accepted at in a Pull Request, it must meet the following requirements:
- 0 Sonarlint issues found
- O JavaDoc errors/warnings
- Protobuf fully documented
- Jacoco reporting 90+% code coverage.
- Spotless code formatter.

## Reports
_**Reports are part of the build not available from GitHub.**_

If a failure occurs during the build process, these reports will be generated showing the results collected. 

- [All Test Results](build/reports/allTests/index.html)
- [All Code Coverage](build/reports/jacoco/jacocoRootReport/html/index.html)
- [Dependency Check](build/reports/dependency-check-report.html)

## Required Software
Here's your updated markdown with descriptions for each software:
- [Git](https://git-scm.com/downloads): Git is a distributed version control system that enables developers to track changes in source code during software development. It allows for collaborative work, branching, and merging code efficiently.
- [Java Development Kit (JDK21)](https://jdk.java.net/21/): The Java Development Kit (JDK) is a software development kit used by Java developers to develop, compile, and run Java applications. JDK 21 is a specific version of the JDK that includes tools and libraries necessary for Java development.
- [OpenSSL Minimum Version 3.0.8](https://www.openssl.org/source/): OpenSSL is a robust, full-featured open-source toolkit for the Transport Layer Security (TLS) and Secure Sockets Layer (SSL) protocols. Version 3.0.8 is a specific release of OpenSSL that includes bug fixes, security updates, and new features.
- [Node.js](https://nodejs.org/en/download/): Node.js is an open-source, cross-platform JavaScript runtime environment that allows developers to run JavaScript code outside of a web browser. It is commonly used for building server-side applications and command-line tools.
- [Apache JMeter](https://jmeter.apache.org/download_jmeter.cgi): Apache JMeter is an open-source tool designed for load testing and performance measurement of web applications. It simulates heavy loads on a server, network, or object to test its strength or to analyze overall performance under different load types.
- [Apache JMeter gRPC Request](https://jmeter-plugins.org/?search=grpc): This is a plugin for Apache JMeter that extends its functionality to support gRPC (Google Remote Procedure Call), a high-performance, open-source RPC framework. With this plugin, you can send gRPC requests and analyze the performance of gRPC-based services.
- [Docker Desktop](https://www.docker.com/products/docker-desktop): Docker Desktop is an application for MacOS and Windows machines that enables developers to build and share containerized applications and microservices. It provides an easy-to-use interface for managing Docker containers, images, networks, and volumes.
- [protoc-gen-doc](https://github.com/pseudomuto/protoc-gen-doc/releases/tag/v1.5.1): protoc-gen-doc is a documentation generator plugin for the Google Protocol Buffers (protobuf) compiler (`protoc`). It generates HTML, Markdown, or DocBook documentation from comments in your `.proto` files, allowing you to document your protocol buffer definitions effectively.
- [yq](https://mikefarah.gitbook.io/yq/v/v3.x): yq is a lightweight and flexible command-line YAML processor. It allows you to manipulate YAML files easily using commands similar to `jq` for JSON. You can use yq to extract, modify, and filter YAML documents efficiently.
- [TINKAR](opencdx-tinkar/README.md#tinkar-db-settings): This specification is describing the requirements and characteristics of systems require to manage terminology produced by a variety of organization across a healthcare enterprise. This foundation must allow enterprise to extend terminology standards and implement extensions in a timely fashion.
## Deployment Procedures

1. Run the command "chmod 755 deploy.sh" in the root directory.
2. Run the command "./deploy.sh --deploy" in the root directory of the repository.
> This will run a full build, test, docker image generation and deployment of all services to docker desktop.
3. Once successfully deployed you will receive a menu like this:
> OpenCDX Deployment Menu:
> 
> 1. Build Docker Image                     
> 2. Start Docker (All Services)             
> 3. Start Docker (Custom)                   
> 4. Stop Docker                             
> 5. Open Admin Dashboard                    
> 6. Run JMeter Test Script                  
> 7. Open JMeter Test Script                 
> 8. Open Microservice Tracing Zipkin        
> 9. Open Test Report 
> 10. Publish Doc
> 11. Open JaCoCo Report
> 12. Check JavaDoc
> 13. Open Proto Doc
> 14. Container Status
> 15. Dependency Check
>
> Enter your choice (x to Exit):

4. At this time you can select any of the options to interact with the deployed services.  For example, to open the admin dashboard, select option 5 and press enter.

### Build Docker Image
This will build the docker image for all services, based on the current build of code in the repository.

### Start Docker (All Services / Custom)
This will allow you to select which images to build, and then start the docker containers for the selected services, plus any required services.

### Stop Docker
This will stop the OpenCDX Docker Container.

### Open Admin Dashboard
This will open the OpenCDX Admin Dashboard in your default web browser.
### Run JMeter Test Script
This will allow you to run a JMeter test script for OpenCDX.  It will prompt you with multiple tests that maybe run.  Each will have a description of the test.
### Open JMeter Test Script
This will open JMeter with the OpenCDX test script for running in the GUI, or for editing.
### Open Microservice Tracing Zipkin
This will open your default browser to the Zipkin tracing dashboard.  Allow for viewing of microservice tracing, and dependency graphs.
### Open Test Report
This will open the test report generated during the build process.
### Publish Doc
This will update the documentation for opencdx-admin to deploy for access
- JavaDoc
- ProtoDoc
- Dependency Check
- JaCoCo Report
### Open JaCoCo Report
This will open the JaCoCo report for viewing in your default web browser.
### Check JavaDoc
This will check the JavaDoc for all services and report any errors or warnings.
### Open Proto Doc
This will open the ProtoDoc for viewing in your default web browser.
### Container Status
This will show the status of the OpenCDX Docker Container.
### Dependency Check
This will run a dependency check on all services and report on vulnerabilities found.

### Deploy parameters
For the latest command line options please use:

`./deploy.sh --help`

Below are a few of the more common options to run:

### Build Only
To build the project without deploying, use the following command:

`./deploy.sh`
### Build and Deploy
To build and deploy the project, use the following command:

`./deploy.sh --deploy`
### Build / Deploy / Test
To build, deploy and test the project, use the following command:

`./deploy.sh --deploy --<test>>`
    
Where `<test>` is the name of the test to run.  For example:
 - smoke
 - performance
 - soak

### Skip Build jump to Menu
To skip the build and jump to the deployment menu, use the following command:

`./deploy.sh --skip`


## Monitoring and Observability
- Spring Admin is used for monitoring the microservices. 
- Zipkin is used microservice tracing.  This allows for the monitoring of the microservices and the dependencies between them.
- Prometheus is used for observing the microservices.
- Loki is used for logging the microservices.
- Grafana is used for displaying the combined data from Loki, Zipkin, and Prometheus.
### Accessing
1. Open OpenCDX Admin Dashboard: [http://localhost:8080](http://localhost:8861) or option 5 from the deployment menu.
2. The dashboard menu bar offers the following:
   1. A drop-down list of observability tools and their links.
   2. A drop-down list of documentation and their links.
   3. A drop-down list of microservices with their swagger links.
   4. Link to the Discovery Dashboard.
   5. Link to the NATS Dashboard.
#### Grafana
Grafana may require Username and Password, for sign-in on first use.  The default is: admin / admin (Grafana will require a password change on first use of the username/password).

## Logs
Logs can be viewed in Docker Desktop under the container logs. OpenCDX Admin Dashboard also provides access to the logs under each microservice under the "Loggins" section. Grafana does provide a dashboard for viewing specific logs across all microservices based on a TraceId, that is output to the logs. 

## JMeter Testing
1. Download the latest version of [Apache JMeter](https://jmeter.apache.org/download_jmeter.cgi)
2. Place Apache JMeter on your environment PATH
3. Install Apache JMeter plugin `JMeter gRPC Request`

## NVD API Key
The NVD API Key is optional and can be specified in your local environment by environment variable:

```export NVD_API_KEY=<your-api-key>```

## Support Team

[Avengers (cs@safehealth.me)](mailto:cs@safehealth.me)
