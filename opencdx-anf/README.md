# ANFService Overview

The ANFService is a clinical statement service designed to manage ANFStatements and associated circumstances. It provides CRUD operations for ANFStatements and associated entities.

## Components

### 1. Identifier

- Represents a unique identifier for an ANFStatement.

### 2. Measure

- Represents a measurable element, such as time or test results.

### 3. LogicalExpression

- Represents a logical expression used in defining measures.

### 4. Participant

- Represents a participant, typically a patient.

### 5. Timing

- Represents timing information associated with a clinical statement.

### 6. Practitioner

- Represents a practitioner or authoring entity.

### 7. AssociatedStatement

- Represents associated statements like preconditions or interpretations.

### 8. Repetition

- Represents repetition details for a RequestCircumstance.

### 9. Circumstance

- Represents the circumstances associated with a clinical statement.

### 10. RequestCircumstance

- Represents circumstances related to a request.

### 11. PerformanceCircumstance

- Represents circumstances related to the performance of an action.

### 12. NarrativeCircumstance

- Represents circumstances related to a narrative description.

### 13. Reference

- Represents a reference to other entities like patient records or health practitioners.

### 14. ANFStatement

- Main representation of an ANFStatement encapsulating various details.

## Service

### ANFService

- Provides CRUD operations for ANFStatements and associated entities.

## Message Formats

### 1. PredictorInput

- Provides input data for making predictions, including encounter ID, test ID, and features extracted from the test data.

### 2. PredictorOutput

- Represents the output of the prediction model, including the encounter ID and the predicted outcome or parameter.

### 3. PredictorRequest

- A request message format for the Neural Predictor, encapsulating PredictorInput for prediction.

### 4. PredictorResponse

- A response message format for the Neural Predictor, encapsulating PredictorOutput as the result of the prediction.

## Docker

- Docker Image: opencdx/helloworld
- Docker Port:
  - RestAPI: Host 8080 : Container 8080
  - Grpc: Host 9090 : Container 9090

## Links
_**Links are part of the build not available from GitHub.**_
- [All Test Results](build/reports/tests/test/index.html)
- [All Code Coverage](build/reports/jacoco/test/html/index.html)
- [Sonarlint Report](build/reports/sonarlint/sonarlintMain.html)
- [API-DOCS](http://localhost:8080/api-docs)
- [API-DOCS YAML](http://localhost:8080/api-docs.yaml)

## Interfaces
