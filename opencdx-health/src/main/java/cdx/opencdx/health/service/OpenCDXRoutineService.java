/*
 * Copyright 2024 Safe Health Systems, Inc.
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
package cdx.opencdx.health.service;

import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for the OpenCDXRoutineService, which defines methods for processing routine-related requests.
 */
public interface OpenCDXRoutineService {

    /**
     * Processes the RoutineRequest and generates a corresponding RoutineResponse.
     *
     * @param request The RoutineRequest to be processed.
     * @return A RoutineResponse generated for the provided request.
     */
    RoutineResponse createRoutine(RoutineRequest request);

    /**
     * Retrieves information about a routine based on the provided RoutineRequest.
     *
     * @param request The RoutineRequest for retrieving routine information.
     * @return A RoutineResponse containing information about the requested routine.
     */
    RoutineResponse getRoutine(RoutineRequest request);

    /**
     * Creates a clinical protocol execution based on the provided ClinicalProtocolExecutionRequest.
     *
     * @param request The ClinicalProtocolExecutionRequest for creating a clinical protocol execution.
     * @return A ClinicalProtocolExecutionResponse indicating the status of the clinical protocol execution creation.
     */
    ClinicalProtocolExecutionResponse createClinicalProtocolExecution(ClinicalProtocolExecutionRequest request);

    /**
     * Retrieves information about a clinical protocol execution based on the provided ClinicalProtocolExecutionRequest.
     *
     * @param request The ClinicalProtocolExecutionRequest for retrieving clinical protocol execution information.
     * @return A ClinicalProtocolExecutionResponse containing information about the requested clinical protocol execution.
     */
    ClinicalProtocolExecutionResponse getClinicalProtocolExecution(ClinicalProtocolExecutionRequest request);

    /**
     * Triggers a lab order based on the provided LabOrderRequest.
     *
     * @param request The LabOrderRequest for triggering a lab order.
     * @return A LabOrderResponse indicating the status of the lab order triggering.
     */
    LabOrderResponse triggerLabOrder(LabOrderRequest request);

    /**
     * Retrieves information about a lab order based on the provided LabOrderRequest.
     *
     * @param request The LabOrderRequest for retrieving lab order information.
     * @return A LabOrderResponse containing information about the requested lab order.
     */
    LabOrderResponse getLabOrder(LabOrderRequest request);

    /**
     * Triggers a diagnosis based on the provided DiagnosisRequest.
     *
     * @param request The DiagnosisRequest for triggering a diagnosis.
     * @return A DiagnosisResponse indicating the status of the diagnosis triggering.
     */
    DiagnosisResponse triggerDiagnosis(DiagnosisRequest request);

    /**
     * Retrieves information about a diagnosis based on the provided DiagnosisRequest.
     *
     * @param request The DiagnosisRequest for retrieving diagnosis information.
     * @return A DiagnosisResponse containing information about the requested diagnosis.
     */
    DiagnosisResponse getDiagnosis(DiagnosisRequest request);

    /**
     * Triggers a suspected diagnosis based on the provided SuspectedDiagnosisRequest.
     *
     * @param request The SuspectedDiagnosisRequest for triggering a suspected diagnosis.
     * @return A SuspectedDiagnosisResponse indicating the status of the suspected diagnosis triggering.
     */
    SuspectedDiagnosisResponse triggerSuspectedDiagnosis(SuspectedDiagnosisRequest request);

    /**
     * Retrieves information about a suspected diagnosis based on the provided SuspectedDiagnosisRequest.
     *
     * @param request The SuspectedDiagnosisRequest for retrieving suspected diagnosis information.
     * @return A SuspectedDiagnosisResponse containing information about the requested suspected diagnosis.
     */
    SuspectedDiagnosisResponse getSuspectedDiagnosis(SuspectedDiagnosisRequest request);

    /**
     * Triggers a lab result based on the provided LabResultRequest.
     *
     * @param request The LabResultRequest for triggering a lab result.
     * @return A LabResultResponse indicating the status of the lab result triggering.
     */
    LabResultResponse triggerLabResult(LabResultRequest request);

    /**
     * Retrieves information about a lab result based on the provided LabResultRequest.
     *
     * @param request The LabResultRequest for retrieving lab result information.
     * @return A LabResultResponse containing information about the requested lab result.
     */
    LabResultResponse getLabResult(LabResultRequest request);
}
