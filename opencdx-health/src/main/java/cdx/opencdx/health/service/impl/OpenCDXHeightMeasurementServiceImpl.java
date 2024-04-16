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
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.health.model.OpenCDXHeightMeasurementModel;
import cdx.opencdx.health.repository.OpenCDXHeightMeasurementRepository;
import cdx.opencdx.health.service.OpenCDXHeightMeasurementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Service for processing height measurement
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXHeightMeasurementServiceImpl implements OpenCDXHeightMeasurementService {

    private static final String DOMAIN = "OpenCDXHeightMeasurementServiceImpl";
    private static final String HEIGHT_MEASUREMENTS = "HEIGHT Measurements: ";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_HEIGHT_MEASUREMENTS = "Failed to convert Height Measurements";
    private static final String FAILED_TO_FIND_HEIGHT = "Failed to find height ";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXHeightMeasurementRepository openCDXHeightMeasurementRepository;

    /**
     * Constructor with OpenCDXHeightMeasurementService
     *
     * @param openCDXAuditService            user for recording PHI
     * @param openCDXCurrentUser             Current User Service
     * @param objectMapper                   ObjectMapper for converting to JSON for Audit system.
     * @param openCDXDocumentValidator       Validator for documents
     * @param openCDXHeightMeasurementRepository Mongo Repository for OpenCDXHeightMeasurement
     */
    public OpenCDXHeightMeasurementServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXHeightMeasurementRepository openCDXHeightMeasurementRepository) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXHeightMeasurementRepository = openCDXHeightMeasurementRepository;
    }
    /**
     * Method to create height measurement.
     *
     * @param request CreateHeightMeasurementRequest for measurement.
     * @return CreateHeightMeasurementResponse with measurement.
     */
    @Override
    public CreateHeightMeasurementResponse createHeightMeasurement(CreateHeightMeasurementRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles", new OpenCDXIdentifier(request.getHeightMeasurement().getPatientId()));
        OpenCDXHeightMeasurementModel openCDXHeightMeasurementModel = this.openCDXHeightMeasurementRepository.save(
                new OpenCDXHeightMeasurementModel(request.getHeightMeasurement()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Heights created",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    openCDXHeightMeasurementModel.getPatientId().toHexString(),
                    openCDXHeightMeasurementModel.getNationalHealthId(),
                    HEIGHT_MEASUREMENTS + openCDXHeightMeasurementModel.getId(),
                    this.objectMapper.writeValueAsString(openCDXHeightMeasurementModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_HEIGHT_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXHeightMeasurementModel.toString());
            throw openCDXNotAcceptable;
        }
        return CreateHeightMeasurementResponse.newBuilder()
                .setHeightMeasurement(openCDXHeightMeasurementModel.getProtobufMessage())
                .build();
    }

    /**
     * Method to get height measurement.
     *
     * @param request GetHeightMeasurementResponse for measurement.
     * @return GetHeightMeasurementRequest with measurement.
     */
    @Override
    public GetHeightMeasurementResponse getHeightMeasurement(GetHeightMeasurementRequest request) {
        OpenCDXHeightMeasurementModel model = this.openCDXHeightMeasurementRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, FAILED_TO_FIND_HEIGHT + request.getId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Heights Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getPatientId().toHexString(),
                    model.getNationalHealthId(),
                    HEIGHT_MEASUREMENTS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_HEIGHT_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return GetHeightMeasurementResponse.newBuilder()
                .setHeightMeasurement(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to update height measurement.
     *
     * @param request UpdateHeightMeasurementRequest for measurement.
     * @return UpdateHeightMeasurementResponse with measurement.
     */
    @Override
    public UpdateHeightMeasurementResponse updateHeightMeasurement(UpdateHeightMeasurementRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles", new OpenCDXIdentifier(request.getHeightMeasurement().getPatientId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "heights", new OpenCDXIdentifier(request.getHeightMeasurement().getId()));
        OpenCDXHeightMeasurementModel model = this.openCDXHeightMeasurementRepository.save(
                new OpenCDXHeightMeasurementModel(request.getHeightMeasurement()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Height Updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getPatientId().toHexString(),
                    model.getNationalHealthId(),
                    HEIGHT_MEASUREMENTS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_HEIGHT_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateHeightMeasurementResponse.newBuilder()
                .setHeightMeasurement(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to delete height measurement.
     *
     * @param request DeleteHeightMeasurementRequest for measurement.
     * @return SuccessResponse with measurement.
     */
    @Override
    public SuccessResponse deleteHeightMeasurement(DeleteHeightMeasurementRequest request) {
        OpenCDXHeightMeasurementModel model = this.openCDXHeightMeasurementRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, FAILED_TO_FIND_HEIGHT + request.getId()));

        this.openCDXHeightMeasurementRepository.deleteById(model.getId());
        log.info("Deleted Height: {}", request.getId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Height Deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getPatientId().toHexString(),
                    model.getNationalHealthId(),
                    HEIGHT_MEASUREMENTS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_HEIGHT_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    /**
     * Method to list height measurement.
     *
     * @param request ListHeightMeasurementsRequest for measurement.
     * @return ListHeightMeasurementsResponse with measurement.
     */
    @Override
    public ListHeightMeasurementsResponse listHeightMeasurements(ListHeightMeasurementsRequest request) {
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
        Page<OpenCDXHeightMeasurementModel> all = Page.empty();
        if (request.hasPatientId()) {
            all = this.openCDXHeightMeasurementRepository.findAllByPatientId(
                    new OpenCDXIdentifier(request.getPatientId()), pageable);
        } else if (request.hasNationalHealthId()) {
            all = this.openCDXHeightMeasurementRepository.findAllByNationalHealthId(
                    request.getNationalHealthId(), pageable);
        }
        log.trace("found database results");

        all.get().forEach(openCDXHeightMeasurementModel -> {
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        "height accessed",
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        openCDXHeightMeasurementModel.getPatientId().toHexString(),
                        openCDXHeightMeasurementModel.getNationalHealthId(),
                        HEIGHT_MEASUREMENTS + openCDXHeightMeasurementModel.getId(),
                        this.objectMapper.writeValueAsString(openCDXHeightMeasurementModel.getProtobufMessage()));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_HEIGHT_MEASUREMENTS, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXHeightMeasurementModel.toString());
                throw openCDXNotAcceptable;
            }
        });
        return ListHeightMeasurementsResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllHeightMeasurement(all.get()
                        .map(OpenCDXHeightMeasurementModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
