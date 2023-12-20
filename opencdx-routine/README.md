# RoutineSystemService Overview

The **RoutineSystemService** is a comprehensive system designed to manage various entities related to routine processes, clinical protocols, delivery tracking, and healthcare data. The system encompasses entities such as Routine, DeliveryTracking, ClinicalProtocolExecution, LabOrder, Diagnosis, SuspectedDiagnosis, LabResult, and Medication. Each entity serves a specific purpose within the healthcare domain.

## 1. Routine
Represents routine processes with details such as routine ID, name, description, status, creation and last update timestamps, assigned user or team, and associated clinical protocols.

## 2. DeliveryTracking
Manages the tracking of deliveries, including delivery ID, order ID, status, start and end timestamps, current location, recipient information, and details of the delivered items.

## 3. ClinicalProtocolExecution
Handles the execution of clinical protocols, capturing execution ID, associated routine and protocol IDs, status, start and end timestamps, results, assigned medical staff, and individual protocol steps.

## 4. LabOrder
Facilitates the creation and retrieval of lab orders with information such as lab order ID, test name, order timestamp, matched value set, and related entities.

## 5. Diagnosis
Deals with diagnosis data, storing details like diagnosis ID, diagnosis code, diagnosis timestamp, matched value set, and related entities.

## 6. SuspectedDiagnosis
Manages suspected diagnosis data, including suspected diagnosis ID, diagnosis code, diagnosis timestamp, matched value set, and related entities.

## 7. LabResult
Handles lab result data, storing result ID, result value, result timestamp, matched value set, and related entities.

## 8. Medication
Manages medication data, including medication ID, medication name, administration timestamp, matched value set, and related entities.

## Docker Image
opencdx/routine

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
