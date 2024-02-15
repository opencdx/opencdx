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
import cdx.opencdx.classification.repository.OpenCDXClassificationRepository;
import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.classification.service.OpenCDXClassifyProcessorService;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXConnectedTestClient;
import cdx.opencdx.client.service.OpenCDXMediaClient;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.connected.ConnectedTest;
import cdx.opencdx.grpc.connected.TestIdRequest;
import cdx.opencdx.grpc.media.GetMediaRequest;
import cdx.opencdx.grpc.media.GetMediaResponse;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.questionnaire.GetQuestionnaireRequest;
import cdx.opencdx.grpc.questionnaire.UserQuestionnaireData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing Classification Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXClassificationServiceImpl implements OpenCDXClassificationService {

    private static final String OBJECT = "OBJECT";
    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXMediaClient openCDXMediaClient;
    private final OpenCDXConnectedTestClient openCDXConnectedTestClient;
    private final OpenCDXQuestionnaireClient openCDXQuestionnaireClient;
    private final OpenCDXClassifyProcessorService openCDXClassifyProcessorService;
    private final OpenCDXClassificationRepository openCDXClassificationRepository;

    /**
     * Constructor for OpenCDXClassificationServiceImpl
     * @param openCDXAuditService service for auditing
     * @param objectMapper object mapper for converting objects
     * @param openCDXCurrentUser service for getting current user
     * @param openCDXDocumentValidator service for validating documents
     * @param openCDXMediaClient service for media client
     * @param openCDXClassifyProcessorService service for classification processor
     * @param openCDXConnectedTestClient service for connected test client
     * @param openCDXClassificationRepository repository for classification
     * @param openCDXQuestionnaireClient service for questionnaire client
     */
    @Autowired
    public OpenCDXClassificationServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            ObjectMapper objectMapper,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXMediaClient openCDXMediaClient,
            OpenCDXConnectedTestClient openCDXConnectedTestClient,
            OpenCDXQuestionnaireClient openCDXQuestionnaireClient,
            OpenCDXClassifyProcessorService openCDXClassifyProcessorService,
            OpenCDXClassificationRepository openCDXClassificationRepository) {
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXMediaClient = openCDXMediaClient;
        this.openCDXConnectedTestClient = openCDXConnectedTestClient;
        this.openCDXQuestionnaireClient = openCDXQuestionnaireClient;
        this.openCDXClassifyProcessorService = openCDXClassifyProcessorService;
        this.openCDXClassificationRepository = openCDXClassificationRepository;
    }

    /**
     * Process the ClassificationRequest
     * @param request request the process
     * @return Message generated for this request.
     */
    @Override
    public ClassificationResponse classify(ClassificationRequest request) {
        log.info("Processing ClassificationRequest");

        OpenCDXClassificationModel model = validateAndLoad(request);

        this.openCDXClassifyProcessorService.classify(model);

        model = this.openCDXClassificationRepository.save(model);

        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Classification Record and Results",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    "CLASSIFICATION: " + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    this.getClass().getName(), 1, "Failed to convert OpenCDXClassificationModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        log.info("Processed ClassificationRequest");
        return model.getClassificationResponse();
    }

    private OpenCDXClassificationModel validateAndLoad(ClassificationRequest request) {
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

        log.info("Validated ClassificationRequest");

        if (request.getUserAnswer().hasConnectedTestId()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "connected-test", new ObjectId(request.getUserAnswer().getConnectedTestId()));

            retrieveConnectedTest(request, openCDXCallCredentials, model);
        } else if (request.getUserAnswer().hasUserQuestionnaireId()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    "questionnaire-user", new ObjectId(request.getUserAnswer().getUserQuestionnaireId()));
            retrieveQuestionnaire(request, openCDXCallCredentials, model);
        }
        return model;
    }

    private void retrieveConnectedTest(
            ClassificationRequest request,
            OpenCDXCallCredentials openCDXCallCredentials,
            OpenCDXClassificationModel model) {
        log.info("Retrieving ConnectedTest: {}", request.getUserAnswer().getConnectedTestId());
        ConnectedTest testDetailsById = this.openCDXConnectedTestClient.getTestDetailsById(
                TestIdRequest.newBuilder()
                        .setTestId(request.getUserAnswer().getConnectedTestId())
                        .build(),
                openCDXCallCredentials);

        if (testDetailsById != null) {
            model.setConnectedTest(testDetailsById);
            if (testDetailsById.hasTestDetails()
                    && StringUtils.isNotEmpty(testDetailsById.getTestDetails().getMediaId())) {
                GetMediaResponse response = this.openCDXMediaClient.getMedia(
                        GetMediaRequest.newBuilder()
                                .setId(testDetailsById.getTestDetails().getMediaId())
                                .build(),
                        openCDXCallCredentials);
                if (response.hasMedia()) {
                    model.setTestDetailsMedia(response.getMedia());
                }
            }
        }
    }

    private void retrieveQuestionnaire(
            ClassificationRequest request,
            OpenCDXCallCredentials openCDXCallCredentials,
            OpenCDXClassificationModel model) {
        log.info("Retrieving UserQuestionnaireData: {}", request.getUserAnswer().getUserQuestionnaireId());
        UserQuestionnaireData userQuestionnaireData = this.openCDXQuestionnaireClient.getUserQuestionnaireData(
                GetQuestionnaireRequest.newBuilder()
                        .setId(request.getUserAnswer().getUserQuestionnaireId())
                        .build(),
                openCDXCallCredentials);

        if (userQuestionnaireData != null) {
            model.setUserQuestionnaireData(userQuestionnaireData);
        }
    }
}
