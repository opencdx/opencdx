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
    private static final String AUTHORIZATION_CONTROL = "AUTHORIZATION_CONTROL: 131";
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

    // Submiited Questionnaire
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
                    "User Form Data Submission",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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

    /**
     * Process the GetQuestionnaireRequest
     * @param request request the process
     * @return Message generated for this request.
     */
    @Override
    public Questionnaire getSubmittedQuestionnaire(GetQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "User Form Data Access Request",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        return Questionnaire.newBuilder()
                .setDescription("User Submitted Questionnaire Description")
                .build();
    }

    /**
     * Process the GetQuestionnaireRequest
     * @param request request the process
     * @return Message generated for this request.
     */
    @Override
    public Questionnaires getSubmittedQuestionnaireList(GetQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Questionnaire Access Request",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        return Questionnaires.newBuilder()
                .addQuestionnaires(Questionnaire.newBuilder()
                        .setDescription("User Submitted Questionnaire one Description")
                        .build())
                .addQuestionnaires(Questionnaire.newBuilder()
                        .setDescription("User Submitted Questionnaire two Description")
                        .build())
                .build();
    }

    /**
     * Process the QuestionnaireRequest
     * @param request request the process
     * @return Message generated for this request.
     */
    @Override
    public SubmissionResponse deleteSubmittedQuestionnaire(DeleteQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "User Form Data Deletion Request",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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
                .setMessage("deleteSubmittedQuestionnaire Executed")
                .build();
    }

    // System Level Questionnaire
    /**
     * Process the Create QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the CreateQuestionnaireData request.
     */
    @Override
    public SubmissionResponse createQuestionnaireData(QuestionnaireDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "CreateQuestionnaireData [System Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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
                .setMessage("createQuestionnaireData Executed")
                .build();
    }

    /**
     * Process the Update QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the UpdateQuestionnaireData request.
     */
    @Override
    public SubmissionResponse updateQuestionnaireData(QuestionnaireDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "UpdateQuestionnaireData [System Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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
                .setMessage("updateQuestionnaireData Executed")
                .build();
    }

    /**
     * Process the Get QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the GetQuestionnaireData request.
     */
    @Override
    public Questionnaire getQuestionnaireData(GetQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "GetQuestionnaireData [System Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return Questionnaire.newBuilder()
                .setDescription("System Level Questionnaire Description")
                .build();
    }

    /**
     * Process the Get QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the GetQuestionnaireData request.
     */
    @Override
    public Questionnaires getQuestionnaireDataList(GetQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "GetQuestionnaireDataList [System Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        return Questionnaires.newBuilder()
                .addQuestionnaires(Questionnaire.newBuilder()
                        .setDescription("System Level Questionnaire one Description")
                        .build())
                .addQuestionnaires(Questionnaire.newBuilder()
                        .setDescription("System Level Questionnaire two Description")
                        .build())
                .build();
    }

    /**
     * Process the Delete QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the DeleteQuestionnaireData request.
     */
    @Override
    public SubmissionResponse deleteQuestionnaireData(DeleteQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "DeleteQuestionnaireData [System Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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
                .setMessage("deleteQuestionnaireData Executed")
                .build();
    }

    // Client Level Questionnaire
    /**
     * Process the Create Client QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the CreateClientQuestionnaireData request.
     */
    @Override
    public SubmissionResponse createClientQuestionnaireData(ClientQuestionnaireDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "CreateClientQuestionnaireData [Organization | Workspace Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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
                .setMessage("createClientQuestionnaireData Executed")
                .build();
    }

    /**
     * Process the Update Client QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the UpdateClientQuestionnaireData request.
     */
    @Override
    public SubmissionResponse updateClientQuestionnaireData(ClientQuestionnaireDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "UpdateClientQuestionnaireData [Organization | Workspace Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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
                .setMessage("updateClientQuestionnaireData Executed")
                .build();
    }

    /**
     * Process the Get Client QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the GetClientQuestionnaireData request.
     */
    @Override
    public Questionnaire getClientQuestionnaireData(GetQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "GetClientQuestionnaireData [Organization | Workspace Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return Questionnaire.newBuilder()
                .setDescription("Client Level Questionnaire Description")
                .build();
    }

    /**
     * Process the Get Client QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the GetClientQuestionnaireData request.
     */
    @Override
    public Questionnaires getClientQuestionnaireDataList(GetQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "GetClientQuestionnaireDataList [Organization | Workspace Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        return Questionnaires.newBuilder()
                .addQuestionnaires(Questionnaire.newBuilder()
                        .setDescription("Client Level Questionnaire one")
                        .build())
                .addQuestionnaires(Questionnaire.newBuilder()
                        .setDescription("Client Level Questionnaire one")
                        .build())
                .build();
    }

    /**
     * Process the Delete Client QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the DeleteClientQuestionnaireData request.
     */
    @Override
    public SubmissionResponse deleteClientQuestionnaireData(DeleteQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "DeleteClientQuestionnaireData [Organization | Workspace Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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
                .setMessage("deleteClientQuestionnaireData Executed")
                .build();
    }

    // User Level Questionnaire
    /**
     * Process the Create User QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the CreateUserQuestionnaireData request.
     */
    @Override
    public SubmissionResponse createUserQuestionnaireData(UserQuestionnaireDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "CreateUserQuestionnaireData [User Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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
                .setMessage("createUserQuestionnaireData Executed")
                .build();
    }

    /**
     * Process the Update User QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the UpdateUserQuestionnaireData request.
     */
    @Override
    public SubmissionResponse updateUserQuestionnaireData(UserQuestionnaireDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "UpdateUserQuestionnaireData [User Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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
                .setMessage("updateUserQuestionnaireData Executed")
                .build();
    }

    /**
     * Process the Get User QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the GetUserQuestionnaireData request.
     */
    @Override
    public Questionnaire getUserQuestionnaireData(GetQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "GetUserQuestionnaireData [User Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return Questionnaire.newBuilder()
                .setDescription("User Level Questionnaire Description")
                .build();
    }

    /**
     * Process the Get User QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the GetUserQuestionnaireData request.
     */
    @Override
    public Questionnaires getUserQuestionnaireDataList(GetQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "GetUserQuestionnaireDataList [User Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        return Questionnaires.newBuilder()
                .addQuestionnaires(Questionnaire.newBuilder()
                        .setDescription("User Level Questionnaire one")
                        .build())
                .addQuestionnaires(Questionnaire.newBuilder()
                        .setDescription("User Level Questionnaire two")
                        .build())
                .build();
    }

    /**
     * Process the Delete User QuestionnaireRequest Data
     * @param request request the process
     * @return Message generated for the DeleteUserQuestionnaireData request.
     */
    @Override
    public SubmissionResponse deleteUserQuestionnaireData(DeleteQuestionnaireRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "DeleteUserQuestionnaireData [User Level]",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    currentUser.getNationalHealthId(),
                    AUTHORIZATION_CONTROL,
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
                .setMessage("deleteUserQuestionnaireData Executed")
                .build();
    }
}
