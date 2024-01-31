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
package cdx.opencdx.classification.service.impl;

import cdx.opencdx.classification.model.OpenCDXClassificationModel;
import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXMediaClient;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.media.GetMediaRequest;
import cdx.opencdx.grpc.media.GetMediaResponse;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing Classification Requests
 */
@Service
@Observed(name = "opencdx")
public class OpenCDXClassificationServiceImpl implements OpenCDXClassificationService {

    // Constants for error handling
    private static final String CONVERSION_ERROR = "Failed to convert Protector Request";
    private static final String OBJECT = "OBJECT";
    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    private final OpenCDXMediaClient openCDXMediaClient;

    /**
     * Constructor for OpenCDXClassificationServiceImpl
     * @param openCDXAuditService service for auditing
     * @param objectMapper object mapper for converting objects
     * @param openCDXCurrentUser service for getting current user
     * @param openCDXDocumentValidator service for validating documents
     * @param openCDXMediaClient service for media client
     */
    @Autowired
    public OpenCDXClassificationServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            ObjectMapper objectMapper,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXMediaClient openCDXMediaClient) {
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXMediaClient = openCDXMediaClient;
    }

    /**
     * Process the ClassificationRequest
     * @param request request the process
     * @return Message generated for this request.
     */
    @Override
    public ClassificationResponse classify(ClassificationRequest request) {

        OpenCDXClassificationModel model = new OpenCDXClassificationModel();
        model.setUserAnswer(request.getUserAnswer());
        OpenCDXCallCredentials openCDXCallCredentials =
                new OpenCDXCallCredentials(this.openCDXCurrentUser.getCurrentUserAccessToken());

        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "users", new ObjectId(request.getUserAnswer().getUserId()));

        if (request.getUserAnswer().hasMediaId()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "media", new ObjectId(request.getUserAnswer().getMediaId()));

            GetMediaResponse response = this.openCDXMediaClient.getMedia(
                    GetMediaRequest.newBuilder()
                            .setId(request.getUserAnswer().getMediaId())
                            .build(),
                    openCDXCallCredentials);
            if (response.hasMedia()) {
                model.setMedia(response.getMedia());
            }
        }

        if (request.getUserAnswer().hasConnectedTestId()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "connected-test", new ObjectId(request.getUserAnswer().getConnectedTestId()));
        } else if (request.getUserAnswer().hasUserQuestionnaireId()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "questionnaire-user", new ObjectId(request.getUserAnswer().getUserQuestionnaireId()));
        } else {
            throw new OpenCDXNotFound(this.getClass().getName(), 2, "No Connected Test & No User Questionnaire ");
        }

        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Authorization Control record",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    "AUTHORIZATION_CONTROL: 145",
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 1, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return ClassificationResponse.newBuilder()
                .setMessage("Executed classify operation.")
                .build();
    }
}
