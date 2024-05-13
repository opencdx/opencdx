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
import cdx.opencdx.health.model.OpenCDXHeartRPMModel;
import cdx.opencdx.health.repository.OpenCDXHeartRPMRepository;
import cdx.opencdx.health.service.OpenCDXHeartRPMService;
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
 * Service for processing heartRPM
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXHeartRPMServiceImpl implements OpenCDXHeartRPMService {
    private static final String DOMAIN = "OpenCDXHeartRPMServiceImpl";
    private static final String HEART_RPM = "HeartRPM : ";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_HEART_RPM = "Failed to convert HeartRPM";
    private static final String FAILED_TO_FIND_HEART_RPM = "Failed to find heartRPM ";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXHeartRPMRepository openCDXHeartRPMRepository;

    /**
     * Constructor with OpenCDXHeartRPMService
     *
     * @param openCDXAuditService            user for recording PHI
     * @param openCDXCurrentUser             Current User Service
     * @param objectMapper                   ObjectMapper for converting to JSON for Audit system.
     * @param openCDXDocumentValidator       Validator for documents
     * @param openCDXHeartRPMRepository Mongo Repository for OpenCDXHeartRPM
     */
    public OpenCDXHeartRPMServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXHeartRPMRepository openCDXHeartRPMRepository) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXHeartRPMRepository = openCDXHeartRPMRepository;
    }
    /**
     * Method to create heartRPM.
     *
     * @param request CreateHeartRPMRequest for measurement.
     * @return CreateHeartRPMResponse with measurement.
     */
    @Override
    public CreateHeartRPMResponse createHeartRPMMeasurement(CreateHeartRPMRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles",
                new OpenCDXIdentifier(request.getHeartRpmMeasurement().getPatientId()));
        OpenCDXHeartRPMModel model =
                this.openCDXHeartRPMRepository.save(new OpenCDXHeartRPMModel(request.getHeartRpmMeasurement()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "HeartRPM created",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    HEART_RPM + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_HEART_RPM, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return CreateHeartRPMResponse.newBuilder()
                .setHeartRpmMeasurement(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to get heartRPM.
     *
     * @param request GetHeartRPMResponse for measurement.
     * @return GetHeartRPMRequest with measurement.
     */
    @Override
    public GetHeartRPMResponse getHeartRPMMeasurement(GetHeartRPMRequest request) {
        OpenCDXHeartRPMModel model = this.openCDXHeartRPMRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, FAILED_TO_FIND_HEART_RPM + request.getId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "HeartRPM Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    HEART_RPM + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_HEART_RPM, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return GetHeartRPMResponse.newBuilder()
                .setHeartRpmMeasurement(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to update heartRPM.
     *
     * @param request UpdateHeartRPMRequest for measurement.
     * @return UpdateHeartRPMResponse with measurement.
     */
    @Override
    public UpdateHeartRPMResponse updateHeartRPMMeasurement(UpdateHeartRPMRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles",
                new OpenCDXIdentifier(request.getHeartRpmMeasurement().getPatientId()));
        OpenCDXHeartRPMModel model = this.openCDXHeartRPMRepository
                .findById(new OpenCDXIdentifier(request.getHeartRpmMeasurement().getId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN,
                        3,
                        FAILED_TO_FIND_HEART_RPM
                                + request.getHeartRpmMeasurement().getId()));
        model = this.openCDXHeartRPMRepository.save(model.update(request.getHeartRpmMeasurement()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "HeartRPM Updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    HEART_RPM + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_HEART_RPM, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateHeartRPMResponse.newBuilder()
                .setHeartRpmMeasurement(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to delete heartRPM.
     *
     * @param request DeleteHeartRPMRequest for measurement.
     * @return SuccessResponse with measurement.
     */
    @Override
    public SuccessResponse deleteHeartRPMMeasurement(DeleteHeartRPMRequest request) {
        OpenCDXHeartRPMModel model = this.openCDXHeartRPMRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, FAILED_TO_FIND_HEART_RPM + request.getId()));

        this.openCDXHeartRPMRepository.deleteById(model.getId());
        log.info("Deleted HeartRPM: {}", request.getId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "HeartRPM Deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    HEART_RPM + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_HEART_RPM, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    /**
     * Method to list heartRPM.
     *
     * @param request ListHeartRPMsRequest for measurement.
     * @return ListHeartRPMsResponse with measurement.
     */
    @Override
    public ListHeartRPMResponse listHeartRPMMeasurements(ListHeartRPMRequest request) {
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
        Page<OpenCDXHeartRPMModel> all = Page.empty();
        if (request.hasPatientId()) {
            all = this.openCDXHeartRPMRepository.findAllByPatientId(
                    new OpenCDXIdentifier(request.getPatientId()), pageable);
        } else if (request.hasNationalHealthId()) {
            all = this.openCDXHeartRPMRepository.findAllByNationalHealthId(request.getNationalHealthId(), pageable);
        }
        log.trace("found database results");

        all.get().forEach(model -> {
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        "heartRPM accessed",
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        AuditEntity.newBuilder()
                                .setPatientId(model.getPatientId().toHexString())
                                .setNationalHealthId(model.getNationalHealthId())
                                .build(),
                        HEART_RPM + model.getId(),
                        this.objectMapper.writeValueAsString(model.getProtobufMessage()));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_HEART_RPM, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
                throw openCDXNotAcceptable;
            }
        });
        return ListHeartRPMResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllHeartRpmMeasurement(
                        all.get().map(OpenCDXHeartRPMModel::getProtobufMessage).toList())
                .build();
    }
}
