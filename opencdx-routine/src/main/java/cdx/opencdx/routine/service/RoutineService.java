/*
 * Copyright 2023 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.routine.service;

import cdx.opencdx.grpc.routine.ClinicalProtocolExecutionRequest;
import cdx.opencdx.grpc.routine.ClinicalProtocolExecutionResponse;
import cdx.opencdx.grpc.routine.DeliveryTrackingRequest;
import cdx.opencdx.grpc.routine.DeliveryTrackingResponse;
import cdx.opencdx.grpc.routine.DiagnosisRequest;
import cdx.opencdx.grpc.routine.DiagnosisResponse;
import cdx.opencdx.grpc.routine.LabOrderRequest;
import cdx.opencdx.grpc.routine.LabOrderResponse;
import cdx.opencdx.grpc.routine.LabResultRequest;
import cdx.opencdx.grpc.routine.LabResultResponse;
import cdx.opencdx.grpc.routine.MedicationRequest;
import cdx.opencdx.grpc.routine.MedicationResponse;
import cdx.opencdx.grpc.routine.RoutineRequest;
import cdx.opencdx.grpc.routine.RoutineResponse;
import cdx.opencdx.grpc.routine.SuspectedDiagnosisRequest;
import cdx.opencdx.grpc.routine.SuspectedDiagnosisResponse;

/**
 * Interface for the RoutineService
 */
public interface RoutineService {
    /**
     * Process the RoutineRequest
     * @param request request the process
     * @return Message generated for this request.
     */
    RoutineResponse createRoutine(RoutineRequest request);
    RoutineResponse getRoutine(RoutineRequest request);

    DeliveryTrackingResponse createDeliveryTracking(DeliveryTrackingRequest request);
    DeliveryTrackingResponse getDeliveryTracking(DeliveryTrackingRequest request);

    ClinicalProtocolExecutionResponse createClinicalProtocolExecution(ClinicalProtocolExecutionRequest request);
    ClinicalProtocolExecutionResponse getClinicalProtocolExecution(ClinicalProtocolExecutionRequest request);

    LabOrderResponse triggerLabOrder(LabOrderRequest request);
    LabOrderResponse getLabOrder(LabOrderRequest request);

    DiagnosisResponse triggerDiagnosis(DiagnosisRequest request);
    DiagnosisResponse getDiagnosis(DiagnosisRequest request);

    SuspectedDiagnosisResponse triggerSuspectedDiagnosis(SuspectedDiagnosisRequest request);
    SuspectedDiagnosisResponse getSuspectedDiagnosis(SuspectedDiagnosisRequest request);

    LabResultResponse triggerLabResult(LabResultRequest request);
    LabResultResponse getLabResult(LabResultRequest request);

    MedicationResponse triggerMedication(MedicationRequest request);
    MedicationResponse getMedication(MedicationRequest request);
}
