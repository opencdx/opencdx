# NeuralPredictorService Overview

The **NeuralPredictorService** is a predictive modeling system designed to make accurate predictions based on input data. It offers the following key components:

## 1. PredictorInput
Provides input data for making predictions, including encounter ID, test ID, and features extracted from the test data.

## 2. PredictorOutput
Represents the output of the prediction model, including the encounter ID and the predicted outcome or parameter.

## 3. PredictorRequest
A request message format for the Neural Predictor, encapsulating PredictorInput for prediction.

## 4. PredictorResponse
A response message format for the Neural Predictor, encapsulating PredictorOutput as the result of the prediction.

## 5. NeuralPredictorService
The service itself, providing the "Predict" operation to predict a value based on the input data.

## Docker Image
opencdx/helloworld

## Docker Port
- RestAPI > Host 8080 : Container 8080
- Grpc > Host 9090 : Container 9090

## Links
_**Links are part of the build not available from GitHub.**_
- [All Test Results](build/reports/tests/test/index.html)
- [All Code Coverage](build/reports/jacoco/test/html/index.html)
- [Sonarlint Report](build/reports/sonarlint/sonarlintMain.html)
- [API-DOCS](http://localhost:8080/api-docs)
- [API-DOCS YAML](http://localhost:8080/api-docs.yaml)
## Interfaces
