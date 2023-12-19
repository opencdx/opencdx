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
     * Creates a delivery tracking record based on the provided DeliveryTrackingRequest.
     *
     * @param request The DeliveryTrackingRequest for creating a delivery tracking record.
     * @return A DeliveryTrackingResponse indicating the status of the delivery tracking creation.
     */
    DeliveryTrackingResponse createDeliveryTracking(DeliveryTrackingRequest request);

    /**
     * Retrieves information about a delivery tracking record based on the provided DeliveryTrackingRequest.
     *
     * @param request The DeliveryTrackingRequest for retrieving delivery tracking information.
     * @return A DeliveryTrackingResponse containing information about the requested delivery tracking record.
     */
    DeliveryTrackingResponse getDeliveryTracking(DeliveryTrackingRequest request);

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

    /**
     * Triggers a medication based on the provided MedicationRequest.
     *
     * @param request The MedicationRequest for triggering a medication.
     * @return A MedicationResponse indicating the status of the medication triggering.
     */
    MedicationResponse triggerMedication(MedicationRequest request);

    /**
     * Retrieves information about a medication based on the provided MedicationRequest.
     *
     * @param request The MedicationRequest for retrieving medication information.
     * @return A MedicationResponse containing information about the requested medication.
     */
    MedicationResponse getMedication(MedicationRequest request);
}
