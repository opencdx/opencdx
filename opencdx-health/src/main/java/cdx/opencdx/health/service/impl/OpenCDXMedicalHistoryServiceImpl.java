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
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.AuditEntity;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.health.model.OpenCDXMedicalHistoryModel;
import cdx.opencdx.health.repository.OpenCDXMedicalHistoryRepository;
import cdx.opencdx.health.service.OpenCDXMedicalHistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service for processing MedicalHistory
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXMedicalHistoryServiceImpl implements OpenCDXMedicalHistoryService {
    private static final String DOMAIN = "OpenCDXMedicalHistoryServiceImpl";
    private static final String MEDICALHISTORY = "MedicalHistory : ";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_MEDICALHISTORY = "Failed to convert medical history";
    private static final String FAILED_TO_FIND_MEDICALHISTORY = "Failed to find medical history ";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXMedicalHistoryRepository openCDXMedicalHistoryRepository;

    /**
     * Constructor with OpenCDXMedicalHistoryService
     *
     * @param openCDXAuditService            user for recording PHI
     * @param openCDXCurrentUser             Current User Service
     * @param objectMapper                   ObjectMapper for converting to JSON for Audit system.
     * @param openCDXDocumentValidator       Validator for documents
     * @param openCDXMedicalHistoryRepository Mongo Repository for OpenCDXMedicalHistory
     */
    public OpenCDXMedicalHistoryServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXMedicalHistoryRepository openCDXMedicalHistoryRepository) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXMedicalHistoryRepository = openCDXMedicalHistoryRepository;
    }

    @Override
    public CreateMedicalHistoryResponse createMedicalHistory(CreateMedicalHistoryRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles", new OpenCDXIdentifier(request.getMedicalHistory().getPatientId()));
        OpenCDXMedicalHistoryModel model =
                this.openCDXMedicalHistoryRepository.save(new OpenCDXMedicalHistoryModel(request.getMedicalHistory()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "MedicalHistory created",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    MEDICALHISTORY + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_MEDICALHISTORY, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return CreateMedicalHistoryResponse.newBuilder()
                .setMedicalHistory(model.getProtobufMessage())
                .build();
    }

    @Override
    public GetMedicalHistoryResponse getMedicalHistory(GetMedicalHistoryRequest request) {
        OpenCDXMedicalHistoryModel model = this.openCDXMedicalHistoryRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, FAILED_TO_FIND_MEDICALHISTORY + request.getId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "MedicalHistory Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    MEDICALHISTORY + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_MEDICALHISTORY, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return GetMedicalHistoryResponse.newBuilder()
                .setMedicalHistory(model.getProtobufMessage())
                .build();
    }

    @Override
    public UpdateMedicalHistoryResponse updateMedicalHistory(UpdateMedicalHistoryRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles", new OpenCDXIdentifier(request.getMedicalHistory().getPatientId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "MedicalHistory", new OpenCDXIdentifier(request.getMedicalHistory().getId()));
        OpenCDXMedicalHistoryModel model =
                this.openCDXMedicalHistoryRepository.save(new OpenCDXMedicalHistoryModel(request.getMedicalHistory()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "MedicalHistory Updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    MEDICALHISTORY + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_MEDICALHISTORY, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateMedicalHistoryResponse.newBuilder()
                .setMedicalHistory(model.getProtobufMessage())
                .build();
    }

    @Override
    public SuccessResponse deleteMedicalHistory(DeleteMedicalHistoryRequest request) {
        OpenCDXMedicalHistoryModel model = this.openCDXMedicalHistoryRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, FAILED_TO_FIND_MEDICALHISTORY + request.getId()));

        this.openCDXMedicalHistoryRepository.deleteById(model.getId());
        log.info("Deleted MedicalHistory: {}", request.getId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "MedicalHistory Deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    MEDICALHISTORY + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_MEDICALHISTORY, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public ListMedicalHistoriesResponse listMedicalHistories(ListMedicalHistoriesRequest request) {
        log.trace("Searching Database");
        Pageable pageable;
        if (request.getPagination().hasSort()) {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize(),
                    request.getPagination().getSortAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                    request.getPagination().getSort());
        } else {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize());
        }
        Page<OpenCDXMedicalHistoryModel> all = Page.empty();
        if (request.hasPatientId()) {
            all = this.openCDXMedicalHistoryRepository.findAllByPatientId(
                    new OpenCDXIdentifier(request.getPatientId()), pageable);
        } else if (request.hasNationalHealthId()) {
            all = this.openCDXMedicalHistoryRepository.findAllByNationalHealthId(request.getNationalHealthId(), pageable);
        }
        log.trace("found database results");

        all.get().forEach(model -> {
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        "MedicalHistory accessed",
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        AuditEntity.newBuilder()
                                .setPatientId(model.getPatientId().toHexString())
                                .setNationalHealthId(model.getNationalHealthId())
                                .build(),
                        MEDICALHISTORY + model.getId(),
                        this.objectMapper.writeValueAsString(model.getProtobufMessage()));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_MEDICALHISTORY, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
                throw openCDXNotAcceptable;
            }
        });
        return ListMedicalHistoriesResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllMedicalHistory(
                        all.get().map(OpenCDXMedicalHistoryModel::getProtobufMessage).toList())
                .build();
    }
}
