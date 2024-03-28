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
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.health.*;
import cdx.opencdx.health.model.OpenCDXWeightMeasurementModel;
import cdx.opencdx.health.repository.OpenCDXWeightMeasurementRepository;
import cdx.opencdx.health.service.OpenCDXWeightMeasurementService;
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
 * Service for processing weight measurement
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXWeightMeasurementServiceImpl implements OpenCDXWeightMeasurementService {

    private static final String DOMAIN = "OpenCDXWeightMeasurementServiceImpl";
    private static final String WEIGHT_MEASUREMENTS = "WEIGHT Measurements: ";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_WEIGHT_MEASUREMENTS = "Failed to convert Weight Measurements";
    private static final String FAILED_TO_FIND_WEIGHT = "Failed to find weight ";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXWeightMeasurementRepository openCDXWeightMeasurementRepository;

    /**
     * Constructor with OpenCDXWeightMeasurementService
     *
     * @param openCDXAuditService            user for recording PHI
     * @param openCDXCurrentUser             Current User Service
     * @param objectMapper                   ObjectMapper for converting to JSON for Audit system.
     * @param openCDXDocumentValidator       Validator for documents
     * @param openCDXWeightMeasurementRepository Mongo Repository for OpenCDXWeightMeasurement
     */
    public OpenCDXWeightMeasurementServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXWeightMeasurementRepository openCDXWeightMeasurementRepository) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXWeightMeasurementRepository = openCDXWeightMeasurementRepository;
    }
    /**
     * Method to create weight measurement.
     *
     * @param request CreateWeightMeasurementRequest for measurement.
     * @return CreateWeightMeasurementResponse with measurement.
     */
    @Override
    public CreateWeightMeasurementResponse createWeightMeasurement(CreateWeightMeasurementRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles", new OpenCDXIdentifier(request.getWeightMeasurement().getPatientId()));
        OpenCDXWeightMeasurementModel openCDXWeightMeasurementModel = this.openCDXWeightMeasurementRepository.save(
                new OpenCDXWeightMeasurementModel(request.getWeightMeasurement()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Weights created",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    openCDXWeightMeasurementModel.getPatientId().toHexString(),
                    openCDXWeightMeasurementModel.getNationalHealthId(),
                    WEIGHT_MEASUREMENTS + openCDXWeightMeasurementModel.getId(),
                    this.objectMapper.writeValueAsString(openCDXWeightMeasurementModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, FAILED_TO_CONVERT_WEIGHT_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXWeightMeasurementModel.toString());
            throw openCDXNotAcceptable;
        }
        return CreateWeightMeasurementResponse.newBuilder()
                .setWeightMeasurement(openCDXWeightMeasurementModel.getProtobufMessage())
                .build();
    }

    /**
     * Method to get weight measurement.
     *
     * @param request GetWeightMeasurementResponse for measurement.
     * @return GetWeightMeasurementRequest with measurement.
     */
    @Override
    public GetWeightMeasurementResponse getWeightMeasurement(GetWeightMeasurementRequest request) {
        OpenCDXWeightMeasurementModel model = this.openCDXWeightMeasurementRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, FAILED_TO_FIND_WEIGHT + request.getId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Weights Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getPatientId().toHexString(),
                    model.getNationalHealthId(),
                    WEIGHT_MEASUREMENTS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_WEIGHT_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return GetWeightMeasurementResponse.newBuilder()
                .setWeightMeasurement(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to update weight measurement.
     *
     * @param request UpdateWeightMeasurementRequest for measurement.
     * @return UpdateWeightMeasurementResponse with measurement.
     */
    @Override
    public UpdateWeightMeasurementResponse updateWeightMeasurement(UpdateWeightMeasurementRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles", new OpenCDXIdentifier(request.getWeightMeasurement().getPatientId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "weights", new OpenCDXIdentifier(request.getWeightMeasurement().getId()));
        OpenCDXWeightMeasurementModel model = this.openCDXWeightMeasurementRepository.save(
                new OpenCDXWeightMeasurementModel(request.getWeightMeasurement()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Weight Updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getPatientId().toHexString(),
                    model.getNationalHealthId(),
                    WEIGHT_MEASUREMENTS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_WEIGHT_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateWeightMeasurementResponse.newBuilder()
                .setWeightMeasurement(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to delete weight measurement.
     *
     * @param request DeleteWeightMeasurementRequest for measurement.
     * @return SuccessResponse with measurement.
     */
    @Override
    public SuccessResponse deleteWeightMeasurement(DeleteWeightMeasurementRequest request) {
        OpenCDXWeightMeasurementModel model = this.openCDXWeightMeasurementRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 2, FAILED_TO_FIND_WEIGHT + request.getId()));

        this.openCDXWeightMeasurementRepository.deleteById(model.getId());
        log.info("Deleted Weight: {}", request.getId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Weight Deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getPatientId().toHexString(),
                    model.getNationalHealthId(),
                    WEIGHT_MEASUREMENTS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_WEIGHT_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    /**
     * Method to list weight measurement.
     *
     * @param request ListWeightMeasurementsRequest for measurement.
     * @return ListWeightMeasurementsResponse with measurement.
     */
    @Override
    public ListWeightMeasurementsResponse listWeightMeasurements(ListWeightMeasurementsRequest request) {
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
        Page<OpenCDXWeightMeasurementModel> all = Page.empty();
        if (request.hasPatientId()) {
            all = this.openCDXWeightMeasurementRepository.findAllByPatientId(
                    new OpenCDXIdentifier(request.getPatientId()), pageable);
        } else if (request.hasNationalHealthId()) {
            all = this.openCDXWeightMeasurementRepository.findAllByNationalHealthId(
                    request.getNationalHealthId(), pageable);
        }
        log.trace("found database results");

        all.get().forEach(openCDXWeightMeasurementModel -> {
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        "weight accessed",
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        openCDXWeightMeasurementModel.getPatientId().toHexString(),
                        openCDXWeightMeasurementModel.getNationalHealthId(),
                        WEIGHT_MEASUREMENTS + openCDXWeightMeasurementModel.getId(),
                        this.objectMapper.writeValueAsString(openCDXWeightMeasurementModel.getProtobufMessage()));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 5, FAILED_TO_CONVERT_WEIGHT_MEASUREMENTS, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXWeightMeasurementModel.toString());
                throw openCDXNotAcceptable;
            }
        });
        return ListWeightMeasurementsResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllWeightMeasurement(all.get()
                        .map(OpenCDXWeightMeasurementModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
