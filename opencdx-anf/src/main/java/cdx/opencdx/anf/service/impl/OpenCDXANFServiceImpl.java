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
package cdx.opencdx.anf.service.impl;

import cdx.opencdx.anf.model.OpenCDXANFStatementModel;
import cdx.opencdx.anf.repository.OpenCDXANFStatementRepository;
import cdx.opencdx.anf.service.OpenCDXANFService;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.anf.AnfStatement;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
@Observed(name = "opencdx")
public class OpenCDXANFServiceImpl implements OpenCDXANFService {
    private static final String DOMAIN = "OpenCDXANFServiceImpl";
    private static final String FAILED_TO_CONVERT_OPEN_CDXANF_STATEMENT_MODEL =
            "Failed to convert OpenCDXANFStatementModel";
    private static final String OBJECT = "OBJECT";
    private static final String ANF_STATEMENT = "ANF-STATEMENT: ";
    private static final String USERS = "users";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXANFStatementRepository openCDXANFStatementRepository;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor taking the a PersonRepository
     *
     * @param openCDXAuditService           Audit service for tracking FDA requirements
     * @param openCDXCurrentUser            Current User Service.
     * @param openCDXANFStatementRepository Repository for ANF Statements
     * @param objectMapper                Object Mapper for converting objects to JSON
     * @param openCDXDocumentValidator      Document Validator for validating documents
     */
    @Autowired
    public OpenCDXANFServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXANFStatementRepository openCDXANFStatementRepository,
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXANFStatementRepository = openCDXANFStatementRepository;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    @Override
    public AnfStatement.Identifier createANFStatement(AnfStatement.ANFStatement request) {

        if (!request.getAuthorList().isEmpty()) {
            this.openCDXDocumentValidator.validateDocumentsOrThrow(
                    USERS,
                    request.getAuthorList().stream()
                            .map(AnfStatement.Practitioner::getId)
                            .map(ObjectId::new)
                            .toList());
        }
        if (request.hasSubjectOfRecord()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    USERS, new ObjectId(request.getSubjectOfRecord().getId()));
        }
        OpenCDXANFStatementModel openCDXANFStatementModel =
                this.openCDXANFStatementRepository.save(new OpenCDXANFStatementModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating ANF Statement",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    UUID.randomUUID().toString(),
                    ANF_STATEMENT + openCDXANFStatementModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXANFStatementModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_OPEN_CDXANF_STATEMENT_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXANFStatementModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXANFStatementModel.getProtobufMessage().getId();
    }

    @Override
    public AnfStatement.ANFStatement getANFStatement(AnfStatement.Identifier request) {
        OpenCDXANFStatementModel openCDXANFStatementModel = this.openCDXANFStatementRepository
                .findById(new ObjectId(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, "Failed to find ANF Statement: " + request.getId()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Accessed ANF Statement",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    ANF_STATEMENT + UUID.randomUUID(),
                    openCDXANFStatementModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXANFStatementModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_OPEN_CDXANF_STATEMENT_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXANFStatementModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXANFStatementModel.getProtobufMessage();
    }

    @Override
    public AnfStatement.Identifier updateANFStatement(AnfStatement.ANFStatement request) {
        if (!request.getAuthorList().isEmpty()) {
            this.openCDXDocumentValidator.validateDocumentsOrThrow(
                    USERS,
                    request.getAuthorList().stream()
                            .map(AnfStatement.Practitioner::getId)
                            .map(ObjectId::new)
                            .toList());
        }
        if (request.hasSubjectOfRecord()) {
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    USERS, new ObjectId(request.getSubjectOfRecord().getId()));
        }
        OpenCDXANFStatementModel openCDXANFStatementModel =
                this.openCDXANFStatementRepository.save(new OpenCDXANFStatementModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating ANF Statement",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    UUID.randomUUID().toString(),
                    ANF_STATEMENT + openCDXANFStatementModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXANFStatementModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_OPEN_CDXANF_STATEMENT_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXANFStatementModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXANFStatementModel.getProtobufMessage().getId();
    }

    @Override
    public AnfStatement.Identifier deleteANFStatement(AnfStatement.Identifier request) {
        OpenCDXANFStatementModel openCDXANFStatementModel = this.openCDXANFStatementRepository
                .findById(new ObjectId(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, "Failed to find ANF Statement: " + request.getId()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Deleting ANF Statement",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    currentUser.getId().toHexString(),
                    UUID.randomUUID().toString(),
                    ANF_STATEMENT + openCDXANFStatementModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXANFStatementModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_OPEN_CDXANF_STATEMENT_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXANFStatementModel.toString());
            throw openCDXNotAcceptable;
        }
        return openCDXANFStatementModel.getProtobufMessage().getId();
    }
}
