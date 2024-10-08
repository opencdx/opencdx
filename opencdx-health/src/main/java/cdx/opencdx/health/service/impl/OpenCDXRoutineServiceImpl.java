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
package cdx.opencdx.health.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.service.OpenCDXRoutineService;
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
public class OpenCDXRoutineServiceImpl implements OpenCDXRoutineService {
    private final OpenCDXDocumentValidator opencdxDocumentValidator;

    /**
     * Constructor for OpenCDXRoutineServiceImpl.
     *
     * @param opencdxDocumentValidator Document validator
     */
    @Autowired
    public OpenCDXRoutineServiceImpl(OpenCDXDocumentValidator opencdxDocumentValidator) {
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

    /**
     * Process the provided ClinicalProtocolExecutionRequest to create clinical protocol execution.
     * @param request The ClinicalProtocolExecutionRequest to be processed
     * @return Message indicating the completion of the clinical protocol execution creation
     */
    @Override
    public ClinicalProtocolExecutionResponse createClinicalProtocolExecution(ClinicalProtocolExecutionRequest request) {

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
                .map(OpenCDXIdentifier::new)
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
     * Trigger a lab result based on the provided LabResultRequest.
     * @param request The LabResultRequest to be processed
     * @return Message indicating the completion of the lab result triggering
     */
    @Override
    public LabResultResponse triggerLabResult(LabResultRequest request) {
        this.opencdxDocumentValidator.validateDocumentsOrThrow(request.getLabResult().getRelatedEntitiesList().stream()
                .map(OpenCDXIdentifier::new)
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
}
