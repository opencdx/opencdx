# Changelog

All notable changes to OpenCDx Backend Services will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.1] - 2025-10-11

### Added
- **GET `/audit/config/nats-status`**: Returns audit NATS configuration status
- **AuditConfigStatusResponse DTO**: Configuration status response object
- **opencdx.audit.nats.enabled flag**: Control NATS audit publishing (default: true, set to false in docker-compose for local dev)

### Fixed
- **Audit NATS failures**: Try-catch prevents 406 errors, falls back to local logging
- **.gitignore**: Added `bin/`, `logs/`, `.vscode/`

## [1.0.0] - 2025-10-09

### Added
- Initial open-source release of OpenCDx platform
- **Microservices Architecture**:
  - opencdx-gateway: API gateway with routing and SSL termination
  - opencdx-iam: Identity and Access Management service
  - opencdx-questionnaire: Form and questionnaire management
  - opencdx-audit: Centralized audit logging
  - opencdx-classification: Data classification and rules engine
  - opencdx-health: Health data management
  - opencdx-communications: Communication services
  - opencdx-logistics: Logistics and supply chain
  - opencdx-media: Media file management
  - opencdx-tinkar: Terminology knowledge representation
  - opencdx-config: Centralized configuration server
  - opencdx-discovery: Service discovery (Eureka)
  - opencdx-admin: Administrative dashboard

- **Core Infrastructure**:
  - MongoDB for data persistence
  - NATS JetStream for async messaging
  - Eureka for service discovery
  - Spring Cloud Config for centralized configuration
  - Zipkin for distributed tracing
  - Prometheus + Grafana for monitoring
  - Tempo for trace aggregation
  - Loki for log aggregation

- **Security**:
  - JWT-based authentication
  - TLS/SSL encryption for all services
  - Self-signed certificate generation scripts
  - Bearer token authorization
  - Spring Security integration

- **API Documentation**:
  - OpenAPI 3.0 specifications for all services
  - Swagger UI at `/api-docs` for each service
  - gRPC services with Protocol Buffers

- **Development Tools**:
  - Gradle build system with multi-module support
  - Docker Compose for local deployment
  - JMeter test scripts for performance testing
  - Comprehensive logging with SLF4J
  - Micrometer observability integration

### Testing
- JaCoCo code coverage reports
- JUnit 5 test suites
- Integration tests with Spring Boot Test
- Mockito for unit testing
- TestContainers for integration testing

### Documentation
- Module-specific README files
- INSTALL.md with detailed setup instructions
- SWAGGER.md for API documentation
- Deployment scripts with interactive menus

