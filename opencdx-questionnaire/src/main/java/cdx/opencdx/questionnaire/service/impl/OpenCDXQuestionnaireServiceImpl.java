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
package cdx.opencdx.questionnaire.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.questionnaire.*;
import cdx.opencdx.questionnaire.service.OpenCDXQuestionnaireService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing Questionnaire Requests
 */
@Service
@Observed(name = "opencdx")
public class OpenCDXQuestionnaireServiceImpl implements OpenCDXQuestionnaireService {

    // Constants for error handling
    private static final String CONVERSION_ERROR = "Failed to convert Protector Request";
    private static final String OBJECT = "OBJECT";
    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor taking the a PersonRepository
     *
     * @param objectMapper2    repository for interacting with the database.
     * @param objectMapper2 Audit service for tracking FDA requirements
     * @param openCDXCurrentUser Current User Service.
     */
    @Autowired
    public OpenCDXQuestionnaireServiceImpl(
            OpenCDXAuditService openCDXAuditService, ObjectMapper objectMapper, OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXCurrentUser = openCDXCurrentUser;
    }

    /**
     * Process the QuestionnaireRequest
     * @param request request the process
     * @return Message generated for this request.
     */
    @Override
    public SubmissionResponse submitQuestionnaire(QuestionnaireRequest request) {

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
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return SubmissionResponse.newBuilder()
                .setSuccess(true)
                .setMessage("submitQuestionnaire Executed")
                .build();
    }
}
