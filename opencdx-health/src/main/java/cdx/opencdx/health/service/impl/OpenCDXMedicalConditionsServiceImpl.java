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
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.AuditEntity;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.health.model.OpenCDXMedicalConditionsModel;
import cdx.opencdx.health.repository.OpenCDXMedicalConditionsRepository;
import cdx.opencdx.health.service.OpenCDXApiFDA;
import cdx.opencdx.health.service.OpenCDXMedicalConditionsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * Service for Medical Conditions
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXMedicalConditionsServiceImpl implements OpenCDXMedicalConditionsService {
    private static final String OBJECT = "OBJECT";
    private static final String MEDICALCONDITIONS = "MedicalConditions: ";
    private static final String DOMAIN = "OpenCDXMedicalConditionsServiceImpl";
    private static final String FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_CONDITIONS_MODEL =
            "failed to convert OpenCDXMedicalConditionsModel";
    private static final String FAILED_TO_FIND_MEDICAL_CONDITIONS = "Failed to find medical condition ";
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXMedicalConditionsRepository openCDXMedicalConditionsRepository;
    private final OpenCDXProfileRepository openCDXProfileRepository;
    private final OpenCDXApiFDA openCDXApiFDA;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor that takes a ObjectMapper, OpenCDXAuditService, OpenCDXCurrentUser, and OpenCDXDocumentValidator
     * @param objectMapper             Jackson Objectmapper to use
     * @param openCDXAuditService      Audit service to record events
     * @param openCDXCurrentUser Service to get Current user.
     * @param openCDXMedicalConditionsRepository repository for reading and writing medical conditions data
     * @param openCDXProfileRepository repository for reading and writing profile data
     * @param openCDXApiFDA OpenFDA API service
     */
    public OpenCDXMedicalConditionsServiceImpl(
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXMedicalConditionsRepository openCDXMedicalConditionsRepository,
            OpenCDXProfileRepository openCDXProfileRepository,
            OpenCDXApiFDA openCDXApiFDA) {
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXMedicalConditionsRepository = openCDXMedicalConditionsRepository;
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.openCDXApiFDA = openCDXApiFDA;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    /**
     * Triggers a diagnosis based on the provided DiagnosisRequest.
     *
     * @param request The DiagnosisRequest for creating a diagnosis.
     * @return A DiagnosisResponse indicating the status of the diagnosis creation.
     */
    @Override
    public DiagnosisResponse createDiagnosis(DiagnosisRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "medical-conditions",
                new OpenCDXIdentifier(request.getDiagnosis().getPatientId()));
        OpenCDXMedicalConditionsModel model =
                this.openCDXMedicalConditionsRepository.save(new OpenCDXMedicalConditionsModel(request.getDiagnosis()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Medical Condition created",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    MEDICALCONDITIONS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_CONDITIONS_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return DiagnosisResponse.newBuilder()
                .setDiagnosis(model.getProtobufMessage())
                .build();
    }

    /**
     * Retrieves information about a diagnosis based on the provided DiagnosisRequest.
     *
     * @param request The DiagnosisRequest for retrieving diagnosis information.
     * @return A DiagnosisResponse containing information about the requested diagnosis.
     */
    @Override
    public DiagnosisResponse getDiagnosis(DiagnosisRequest request) {
        OpenCDXMedicalConditionsModel model = this.openCDXMedicalConditionsRepository
                .findById(new OpenCDXIdentifier(request.getDiagnosis().getDiagnosisId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN,
                        3,
                        FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_CONDITIONS_MODEL
                                + request.getDiagnosis().getDiagnosisId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "MedicalCondition Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    MEDICALCONDITIONS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_CONDITIONS_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return DiagnosisResponse.newBuilder()
                .setDiagnosis(model.getProtobufMessage())
                .build();
    }

    /**
     * Updates information about a diagnosis based on the provided DiagnosisRequest.
     *
     * @param request The DiagnosisRequest for updating  diagnosis information.
     * @return A DiagnosisResponse containing information about the requested diagnosis.
     */
    @Override
    public DiagnosisResponse updateDiagnosis(DiagnosisRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "medical-conditions",
                new OpenCDXIdentifier(request.getDiagnosis().getPatientId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "medical-conditions",
                new OpenCDXIdentifier(request.getDiagnosis().getDiagnosisId()));
        OpenCDXMedicalConditionsModel model =
                this.openCDXMedicalConditionsRepository.save(new OpenCDXMedicalConditionsModel(request.getDiagnosis()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Medical Condition updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    MEDICALCONDITIONS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_CONDITIONS_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return DiagnosisResponse.newBuilder()
                .setDiagnosis(model.getProtobufMessage())
                .build();
    }

    /**
     * Deletes information about a diagnosis based on the provided DiagnosisRequest.
     *
     * @param request The DiagnosisRequest for deleting the diagnosis information.
     * @return A DiagnosisResponse containing information about the requested diagnosis.
     */
    @Override
    public DiagnosisResponse deleteDiagnosis(DiagnosisRequest request) {
        OpenCDXMedicalConditionsModel model = this.openCDXMedicalConditionsRepository
                .findById(new OpenCDXIdentifier(request.getDiagnosis().getDiagnosisId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN,
                        3,
                        FAILED_TO_FIND_MEDICAL_CONDITIONS
                                + request.getDiagnosis().getDiagnosisId()));

        this.openCDXMedicalConditionsRepository.deleteById(model.getId());
        log.info("Deleted MedicalCondition: {}", request.getDiagnosis().getDiagnosisId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "MedicalCondition Deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    MEDICALCONDITIONS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_CONDITIONS_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return DiagnosisResponse.newBuilder()
                .setDiagnosis(model.getProtobufMessage())
                .build();
    }

    /**
     * Retrieves information about a diagnosis based on the provided ListDiagnosisRequest.
     *
     * @param request The ListDiagnosisRequest for deleting the diagnosis information.
     * @return A ListDiagnosisResponse containing information about the requested diagnosis.
     */
    @Override
    public ListDiagnosisResponse listDiagnosis(ListDiagnosisRequest request) {
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
        Page<OpenCDXMedicalConditionsModel> all = Page.empty();
        if (request.hasPatientId()) {
            all = this.openCDXMedicalConditionsRepository.findAllByPatientId(
                    new OpenCDXIdentifier(request.getPatientId()), pageable);
        } else if (request.hasNationalHealthId()) {
            all = this.openCDXMedicalConditionsRepository.findAllByNationalHealthId(
                    request.getNationalHealthId(), pageable);
        }
        log.trace("found database results");

        all.get().forEach(model -> {
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        "MedicalConditions accessed",
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        AuditEntity.newBuilder()
                                .setPatientId(model.getPatientId().toHexString())
                                .setNationalHealthId(model.getNationalHealthId())
                                .build(),
                        MEDICALCONDITIONS + model.getId(),
                        this.objectMapper.writeValueAsString(model.getProtobufMessage()));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_CONDITIONS_MODEL, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
                throw openCDXNotAcceptable;
            }
        });
        return ListDiagnosisResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllDiagnosis(all.get()
                        .map(OpenCDXMedicalConditionsModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
