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
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.anf.AnfStatement;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing ANF Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXANFServiceImpl implements OpenCDXANFService {
    private static final String DOMAIN = "OpenCDXANFServiceImpl";
    private static final String FAILED_TO_CONVERT_OPEN_CDXANF_STATEMENT_MODEL =
            "Failed to convert OpenCDXANFStatementModel";
    private static final String OBJECT = "OBJECT";
    private static final String ANF_STATEMENT = "ANF-STATEMENT: ";
    private static final String PROFILES = "profiles";
    private static final String FAILED_TO_FIND_PATIENT = "Failed to find patient: ";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXANFStatementRepository openCDXANFStatementRepository;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXProfileRepository openCDXProfileRepository;

    /**
     * Constructor taking the a PersonRepository
     *
     * @param openCDXAuditService           Audit service for tracking FDA requirements
     * @param openCDXCurrentUser            Current User Service.
     * @param openCDXANFStatementRepository Repository for ANF Statements
     * @param objectMapper                Object Mapper for converting objects to JSON
     * @param openCDXDocumentValidator      Document Validator for validating documents
     * @param openCDXProfileRepository       Repository for OpenCDX Profile collection
     */
    @Autowired
    public OpenCDXANFServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXANFStatementRepository openCDXANFStatementRepository,
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXProfileRepository openCDXProfileRepository) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXANFStatementRepository = openCDXANFStatementRepository;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXProfileRepository = openCDXProfileRepository;
        log.trace("OpenCDXANFServiceImpl created");
    }

    @Override
    public AnfStatement.Identifier createANFStatement(AnfStatement.ANFStatement request) {
        log.trace("Creating ANF Statement");
        if (!request.getAuthorsList().isEmpty()) {
            log.trace("Validating authors");
            this.openCDXDocumentValidator.validateDocumentsOrThrow(
                    "provider",
                    request.getAuthorsList().stream()
                            .filter(AnfStatement.Practitioner::hasId)
                            .map(AnfStatement.Practitioner::getId)
                            .map(OpenCDXIdentifier::new)
                            .toList());
        }
        if (request.hasSubjectOfRecord()) {
            log.trace("Validating subject of record");
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    PROFILES, new OpenCDXIdentifier(request.getSubjectOfRecord().getId()));
        }
        OpenCDXANFStatementModel openCDXANFStatementModel =
                this.openCDXANFStatementRepository.save(new OpenCDXANFStatementModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            OpenCDXProfileModel patient = this.openCDXProfileRepository
                    .findById(new OpenCDXIdentifier(request.getSubjectOfRecord().getId()))
                    .orElseThrow(() -> new OpenCDXNotFound(
                            DOMAIN,
                            1,
                            FAILED_TO_FIND_PATIENT
                                    + request.getSubjectOfRecord().getId()));

            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating ANF Statement",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    patient.getId().toHexString(),
                    patient.getNationalHealthId(),
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
        log.trace("Getting ANF Statement");
        OpenCDXANFStatementModel openCDXANFStatementModel = this.openCDXANFStatementRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, "Failed to find ANF Statement: " + request.getId()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            OpenCDXProfileModel patient = this.openCDXProfileRepository
                    .findById(new OpenCDXIdentifier(
                            openCDXANFStatementModel.getSubjectOfRecord().getId()))
                    .orElseThrow(() -> new OpenCDXNotFound(
                            DOMAIN,
                            1,
                            FAILED_TO_FIND_PATIENT
                                    + openCDXANFStatementModel
                                            .getSubjectOfRecord()
                                            .getId()));
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Accessed ANF Statement",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    patient.getId().toHexString(),
                    patient.getNationalHealthId(),
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
        log.trace("Updating ANF Statement");
        if (!request.getAuthorsList().isEmpty()) {
            log.trace("Validating authors");
            this.openCDXDocumentValidator.validateDocumentsOrThrow(
                    "provider",
                    request.getAuthorsList().stream()
                            .filter(AnfStatement.Practitioner::hasId)
                            .map(AnfStatement.Practitioner::getId)
                            .map(OpenCDXIdentifier::new)
                            .toList());
        }
        if (request.hasSubjectOfRecord()) {
            log.trace("Validating subject of record");
            this.openCDXDocumentValidator.validateDocumentOrThrow(
                    PROFILES, new OpenCDXIdentifier(request.getSubjectOfRecord().getId()));
        }
        OpenCDXANFStatementModel openCDXANFStatementModel =
                this.openCDXANFStatementRepository.save(new OpenCDXANFStatementModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            OpenCDXProfileModel patient = this.openCDXProfileRepository
                    .findById(new OpenCDXIdentifier(request.getSubjectOfRecord().getId()))
                    .orElseThrow(() -> new OpenCDXNotFound(
                            DOMAIN,
                            1,
                            FAILED_TO_FIND_PATIENT
                                    + request.getSubjectOfRecord().getId()));
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating ANF Statement",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    patient.getId().toHexString(),
                    patient.getNationalHealthId(),
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
        log.trace("Deleting ANF Statement");
        OpenCDXANFStatementModel openCDXANFStatementModel = this.openCDXANFStatementRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, "Failed to find ANF Statement: " + request.getId()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            OpenCDXProfileModel patient = this.openCDXProfileRepository
                    .findById(new OpenCDXIdentifier(
                            openCDXANFStatementModel.getSubjectOfRecord().getId()))
                    .orElseThrow(() -> new OpenCDXNotFound(
                            DOMAIN,
                            1,
                            FAILED_TO_FIND_PATIENT
                                    + openCDXANFStatementModel
                                            .getSubjectOfRecord()
                                            .getId()));
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Deleting ANF Statement",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    patient.getId().toHexString(),
                    patient.getNationalHealthId(),
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
