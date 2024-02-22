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
package cdx.opencdx.routine.service.impl;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.routine.*;
import cdx.opencdx.routine.service.OpenCDXRoutineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing Routine Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXRoutineServiceImpl implements OpenCDXRoutineService {

    private static final String CONVERSION_ERROR = "Failed to convert Routine Request";
    private static final String OBJECT = "OBJECT";

    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXDocumentValidator opencdxDocumentValidator;

    /**
     * Constructor for OpenCDXRoutineServiceImpl.
     *
     * @param openCDXAuditService Audit Service for recording
     * @param objectMapper        Object mapper for JSON processing
     * @param openCDXCurrentUser  Current user system
     * @param opencdxDocumentValidator Document validator
     */
    @Autowired
    public OpenCDXRoutineServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            ObjectMapper objectMapper,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXDocumentValidator opencdxDocumentValidator) {
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.opencdxDocumentValidator = opencdxDocumentValidator;
    }

    /**
     * Retrieve routine information by ID from the provided RoutineRequest.
     * @param request The RoutineRequest containing the ID for retrieval
     * @return Message containing details of the requested routine
     */
    @Override
    public RoutineResponse createRoutine(RoutineRequest request) {

        return RoutineResponse.newBuilder().setRoutine(request.getRoutine()).build();
    }

    /**
     * Retrieve routine information by ID from the provided RoutineRequest.
     * @param request The RoutineRequest containing the ID for retrieval
     * @return Message containing details of the requested routine
     */
    @Override
    public RoutineResponse getRoutine(RoutineRequest request) {

        return RoutineResponse.newBuilder().setRoutine(request.getRoutine()).build();
    }

    @Override
    public DeliveryTrackingResponse createDeliveryTracking(DeliveryTrackingRequest request) {

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
        this.opencdxDocumentValidator.validateDocumentsOrThrow(request.getLabOrder().getRelatedEntitiesList().stream()
                .map(ObjectId::new)
                .toList());

        return LabOrderResponse.newBuilder().setLabOrder(request.getLabOrder()).build();
    }

    /**
     * Retrieve lab order information from the provided LabOrderRequest.
     * @param request The LabOrderRequest for retrieving lab order details
     * @return Message containing details of the requested lab order
     */
    @Override
    public LabOrderResponse getLabOrder(LabOrderRequest request) {

        return LabOrderResponse.newBuilder().setLabOrder(request.getLabOrder()).build();
    }

    /**
     * Trigger a diagnosis based on the provided DiagnosisRequest.
     * @param request The DiagnosisRequest to be processed
     * @return Message indicating the completion of the diagnosis triggering
     */
    @Override
    public DiagnosisResponse triggerDiagnosis(DiagnosisRequest request) {
        this.opencdxDocumentValidator.validateDocumentsOrThrow(request.getDiagnosis().getRelatedEntitiesList().stream()
                .map(ObjectId::new)
                .toList());

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
        this.opencdxDocumentValidator.validateDocumentsOrThrow(
                request.getSuspectedDiagnosis().getRelatedEntitiesList().stream()
                        .map(ObjectId::new)
                        .toList());

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
        this.opencdxDocumentValidator.validateDocumentsOrThrow(request.getLabResult().getRelatedEntitiesList().stream()
                .map(ObjectId::new)
                .toList());

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
        this.opencdxDocumentValidator.validateDocumentsOrThrow(request.getMedication().getRelatedEntitiesList().stream()
                .map(ObjectId::new)
                .toList());

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
        return MedicationResponse.newBuilder()
                .setMedication(request.getMedication())
                .build();
    }
}
