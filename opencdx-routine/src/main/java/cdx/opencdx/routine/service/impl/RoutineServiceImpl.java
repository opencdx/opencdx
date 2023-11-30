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
package cdx.opencdx.routine.service.impl;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.routine.*;
import cdx.opencdx.routine.service.RoutineService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing Routine Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class RoutineServiceImpl implements RoutineService {
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Process the provided DeliveryTrackingRequest to create delivery tracking.
     * @param request The DeliveryTrackingRequest to be processed
     * @return Message indicating the completion of the delivery tracking creation
     */
    @Autowired
    public RoutineServiceImpl(OpenCDXAuditService openCDXAuditService, OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
    }

    /**
     * Retrieve routine information by ID from the provided RoutineRequest.
     * @param request The RoutineRequest containing the ID for retrieval
     * @return Message containing details of the requested routine
     */
    @Override
    public RoutineResponse createRoutine(RoutineRequest request) {

        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Routine record create",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getRoutine().getRoutineId(),
                "ROUTINE: 145",
                request.toString());

        return RoutineResponse.newBuilder().setRoutine(request.getRoutine()).build();
    }

    /**
     * Retrieve routine information by ID from the provided RoutineRequest.
     * @param request The RoutineRequest containing the ID for retrieval
     * @return Message containing details of the requested routine
     */
    @Override
    public RoutineResponse getRoutine(RoutineRequest request) {

        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Routine record requested",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getRoutine().getRoutineId(),
                "ROUTINE: 145",
                request.toString());

        return RoutineResponse.newBuilder().setRoutine(request.getRoutine()).build();
    }

    @Override
    public DeliveryTrackingResponse createDeliveryTracking(DeliveryTrackingRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Delivery Tracking record create",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getDeliveryTracking().getDeliveryId(),
                "DELIVERY_TRACKING: 145",
                request.toString());

        return DeliveryTrackingResponse.newBuilder()
                .setDeliveryTracking(request.getDeliveryTracking())
                .build();
    }

    /**
     * Retrieve delivery tracking information by ID from the provided DeliveryTrackingRequest.
     * @param request The DeliveryTrackingRequest containing the ID for retrieval
     * @return Message containing details of the requested delivery tracking
     */
    @Override
    public DeliveryTrackingResponse getDeliveryTracking(DeliveryTrackingRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Delivery Tracking record requested",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getDeliveryTracking().getDeliveryId(),
                "DELIVERY_TRACKING: 145",
                request.toString());

        return DeliveryTrackingResponse.newBuilder()
                .setDeliveryTracking(request.getDeliveryTracking())
                .build();
    }

    /**
     * Process the provided ClinicalProtocolExecutionRequest to create clinical protocol execution.
     * @param request The ClinicalProtocolExecutionRequest to be processed
     * @return Message indicating the completion of the clinical protocol execution creation
     */
    @Override
    public ClinicalProtocolExecutionResponse createClinicalProtocolExecution(ClinicalProtocolExecutionRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Clinical Protocol Execution record create",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getClinicalProtocolExecution().getExecutionId(),
                "CLINICAL_PROTOCOL_EXECUTION: 145",
                request.toString());

        return ClinicalProtocolExecutionResponse.newBuilder()
                .setClinicalProtocolExecution(request.getClinicalProtocolExecution())
                .build();
    }

    /**
     * Retrieve clinical protocol execution information by ID from the provided ClinicalProtocolExecutionRequest.
     * @param request The ClinicalProtocolExecutionRequest containing the ID for retrieval
     * @return Message containing details of the requested clinical protocol execution
     */
    @Override
    public ClinicalProtocolExecutionResponse getClinicalProtocolExecution(ClinicalProtocolExecutionRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Clinical Protocol Execution record requested",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getClinicalProtocolExecution().getExecutionId(),
                "CLINICAL_PROTOCOL_EXECUTION: 145",
                request.toString());

        return ClinicalProtocolExecutionResponse.newBuilder()
                .setClinicalProtocolExecution(request.getClinicalProtocolExecution())
                .build();
    }

    /**
     * Trigger a lab order based on the provided LabOrderRequest.
     * @param request The LabOrderRequest to be processed
     * @return Message indicating the completion of the lab order triggering
     */
    @Override
    public LabOrderResponse triggerLabOrder(LabOrderRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Lab Order record trigger",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getLabOrder().getLabOrderId(),
                "LAB_ORDER: 145",
                request.toString());

        return LabOrderResponse.newBuilder().setLabOrder(request.getLabOrder()).build();
    }

    /**
     * Retrieve lab order information from the provided LabOrderRequest.
     * @param request The LabOrderRequest for retrieving lab order details
     * @return Message containing details of the requested lab order
     */
    @Override
    public LabOrderResponse getLabOrder(LabOrderRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Lab Order record requested",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getLabOrder().getLabOrderId(),
                "LAB_ORDER: 145",
                request.toString());

        return LabOrderResponse.newBuilder().setLabOrder(request.getLabOrder()).build();
    }

    /**
     * Trigger a diagnosis based on the provided DiagnosisRequest.
     * @param request The DiagnosisRequest to be processed
     * @return Message indicating the completion of the diagnosis triggering
     */
    @Override
    public DiagnosisResponse triggerDiagnosis(DiagnosisRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Diagnosis record trigger",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getDiagnosis().getDiagnosisId(),
                "DIAGNOSIS: 145",
                request.toString());

        return DiagnosisResponse.newBuilder()
                .setDiagnosis(request.getDiagnosis())
                .build();
    }

    /**
     * Retrieve diagnosis information from the provided DiagnosisRequest.
     * @param request The DiagnosisRequest for retrieving diagnosis details
     * @return Message containing details of the requested diagnosis
     */
    @Override
    public DiagnosisResponse getDiagnosis(DiagnosisRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Diagnosis record requested",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getDiagnosis().getDiagnosisId(),
                "DIAGNOSIS: 145",
                request.toString());

        return DiagnosisResponse.newBuilder()
                .setDiagnosis(request.getDiagnosis())
                .build();
    }

    /**
     * Trigger a suspected diagnosis based on the provided SuspectedDiagnosisRequest.
     * @param request The SuspectedDiagnosisRequest to be processed
     * @return Message indicating the completion of the suspected diagnosis triggering
     */
    @Override
    public SuspectedDiagnosisResponse triggerSuspectedDiagnosis(SuspectedDiagnosisRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Suspected Diagnosis record trigger",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getSuspectedDiagnosis().getSuspectedDiagnosisId(),
                "SUSPECTED_DIAGNOSIS: 145",
                request.toString());

        return SuspectedDiagnosisResponse.newBuilder()
                .setSuspectedDiagnosis(request.getSuspectedDiagnosis())
                .build();
    }

    /**
     * Retrieve suspected diagnosis information from the provided SuspectedDiagnosisRequest.
     * @param request The SuspectedDiagnosisRequest for retrieving suspected diagnosis details
     * @return Message containing details of the requested suspected diagnosis
     */
    @Override
    public SuspectedDiagnosisResponse getSuspectedDiagnosis(SuspectedDiagnosisRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Suspected Diagnosis record requested",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getSuspectedDiagnosis().getSuspectedDiagnosisId(),
                "SUSPECTED_DIAGNOSIS: 145",
                request.toString());

        return SuspectedDiagnosisResponse.newBuilder()
                .setSuspectedDiagnosis(request.getSuspectedDiagnosis())
                .build();
    }

    /**
     * Trigger a lab result based on the provided LabResultRequest.
     * @param request The LabResultRequest to be processed
     * @return Message indicating the completion of the lab result triggering
     */
    @Override
    public LabResultResponse triggerLabResult(LabResultRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Lab Result record trigger",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getLabResult().getResultId(),
                "LAB_RESULT: 145",
                request.toString());

        return LabResultResponse.newBuilder()
                .setLabResult(request.getLabResult())
                .build();
    }

    /**
     * Retrieve lab result information from the provided LabResultRequest.
     * @param request The LabResultRequest for retrieving lab result details
     * @return Message containing details of the requested lab result
     */
    @Override
    public LabResultResponse getLabResult(LabResultRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Lab Result record requested",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getLabResult().getResultId(),
                "LAB_RESULT: 145",
                request.toString());

        return LabResultResponse.newBuilder()
                .setLabResult(request.getLabResult())
                .build();
    }

    /**
     * Trigger a medication based on the provided MedicationRequest.
     * @param request The MedicationRequest to be processed
     * @return Message indicating the completion of the medication triggering
     */
    @Override
    public MedicationResponse triggerMedication(MedicationRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Medication record trigger",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getMedication().getMedicationId(),
                "MEDICATION: 145",
                request.toString());

        return MedicationResponse.newBuilder()
                .setMedication(request.getMedication())
                .build();
    }

    /**
     * Retrieve medication information from the provided MedicationRequest.
     * @param request The MedicationRequest for retrieving medication details
     * @return Message containing details of the requested medication
     */
    @Override
    public MedicationResponse getMedication(MedicationRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        this.openCDXAuditService.phiCreated(
                currentUser.getId().toHexString(),
                currentUser.getAgentType(),
                "Medication record requested",
                SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                request.getMedication().getMedicationId(),
                "MEDICATION: 145",
                request.toString());

        return MedicationResponse.newBuilder()
                .setMedication(request.getMedication())
                .build();
    }
}
