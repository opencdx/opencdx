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
package cdx.opencdx.predictor.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.neural.predictor.PredictorOutput;
import cdx.opencdx.grpc.neural.predictor.PredictorRequest;
import cdx.opencdx.grpc.neural.predictor.PredictorResponse;
import cdx.opencdx.predictor.service.OpenCDXPredictorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing predictor requests.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXPredictorServiceImpl implements OpenCDXPredictorService {

    // Constants for error handling
    private static final String CONVERSION_ERROR = "Failed to convert Predictor Request";
    private static final String OBJECT = "OBJECT";

    // Dependencies injected via constructor
    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor for OpenCDXPredictorServiceImpl.
     *
     * @param openCDXAuditService Audit service for tracking FDA requirements
     * @param objectMapper        Object mapper for JSON processing
     * @param openCDXCurrentUser  Current User Service.
     * @param openCDXDocumentValidator Document Validator Service.
     */
    @Autowired
    public OpenCDXPredictorServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            ObjectMapper objectMapper,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    /**
     * Process the Predictor Data.
     *
     * @param request The request containing data for prediction.
     * @return A response message generated for the prediction request.
     */
    @Override
    public PredictorResponse predict(PredictorRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "connected-test", new ObjectId(request.getPredictorInput().getTestId()));
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Prediction request",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    "PREDICTOR: 131",
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        return PredictorResponse.newBuilder()
                .setPredictorOutput(PredictorOutput.newBuilder()
                        .setPredictedValue("OpenCDXPredictorServiceImpl [predict]")
                        .setEncounterId(request.getPredictorInput().getEncounterId()))
                .build();
    }
}
