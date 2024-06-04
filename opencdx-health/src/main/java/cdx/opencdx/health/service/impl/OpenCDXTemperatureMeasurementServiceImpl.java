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
import cdx.opencdx.health.model.OpenCDXTemperatureMeasurementModel;
import cdx.opencdx.health.repository.OpenCDXTemperatureMeasurementRepository;
import cdx.opencdx.health.service.OpenCDXTemperatureMeasurementService;
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

@Service
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXTemperatureMeasurementServiceImpl implements OpenCDXTemperatureMeasurementService {

    private static final String DOMAIN = "OpenCDXTemperatureMeasurementServiceImpl";
    private static final String TEMPERATURE_MEASUREMENTS = "TEMPERATURE Measurements: ";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_TEMPERATURE_MEASUREMENTS = "Failed to convert Temperature Measurements";
    private static final String FAILED_TO_FIND_TEMPERATURE = "Failed to find temperature ";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXTemperatureMeasurementRepository openCDXTemperatureMeasurementRepository;

    public OpenCDXTemperatureMeasurementServiceImpl(OpenCDXAuditService openCDXAuditService,
                                                    OpenCDXCurrentUser openCDXCurrentUser,
                                                    ObjectMapper objectMapper,
                                                    OpenCDXDocumentValidator openCDXDocumentValidator,
                                                    OpenCDXTemperatureMeasurementRepository openCDXTemperatureMeasurementRepository) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXTemperatureMeasurementRepository = openCDXTemperatureMeasurementRepository;
    }

    @Override
    public CreateTemperatureMeasurementResponse createTemperatureMeasurement(CreateTemperatureMeasurementRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles", new OpenCDXIdentifier(request.getTemperatureMeasurement().getPatientId()));
        OpenCDXTemperatureMeasurementModel model = this.openCDXTemperatureMeasurementRepository.save(
                new OpenCDXTemperatureMeasurementModel(request.getTemperatureMeasurement()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Temperatures created",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    TEMPERATURE_MEASUREMENTS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, FAILED_TO_CONVERT_TEMPERATURE_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return CreateTemperatureMeasurementResponse.newBuilder()
                .setTemperatureMeasurement(model.getProtobufMessage())
                .build();
    }

    @Override
    public GetTemperatureMeasurementResponse getTemperatureMeasurement(GetTemperatureMeasurementRequest request) {
        OpenCDXTemperatureMeasurementModel model = this.openCDXTemperatureMeasurementRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, FAILED_TO_FIND_TEMPERATURE + request.getId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Temperature Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    TEMPERATURE_MEASUREMENTS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_TEMPERATURE_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return GetTemperatureMeasurementResponse.newBuilder()
                .setTemperatureMeasurement(model.getProtobufMessage())
                .build();
    }

    @Override
    public UpdateTemperatureMeasurementResponse updateTemperatureMeasurement(UpdateTemperatureMeasurementRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles", new OpenCDXIdentifier(request.getTemperatureMeasurement().getPatientId()));
        OpenCDXTemperatureMeasurementModel model = this.openCDXTemperatureMeasurementRepository
                .findById(new OpenCDXIdentifier(request.getTemperatureMeasurement().getId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN,
                        1,
                        FAILED_TO_FIND_TEMPERATURE + request.getTemperatureMeasurement().getId()));
        model = this.openCDXTemperatureMeasurementRepository.save(model.update(request.getTemperatureMeasurement()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Temperature Updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    TEMPERATURE_MEASUREMENTS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_TEMPERATURE_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateTemperatureMeasurementResponse.newBuilder()
                .setTemperatureMeasurement(model.getProtobufMessage())
                .build();
    }

    @Override
    public SuccessResponse deleteTemperatureMeasurement(DeleteTemperatureMeasurementRequest request) {
        OpenCDXTemperatureMeasurementModel model = this.openCDXTemperatureMeasurementRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 2, FAILED_TO_FIND_TEMPERATURE + request.getId()));

        this.openCDXTemperatureMeasurementRepository.deleteById(model.getId());
        log.info("Deleted Temperature Measurement: {}", request.getId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Temperature Measurement Deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    TEMPERATURE_MEASUREMENTS + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_TEMPERATURE_MEASUREMENTS, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public ListTemperatureMeasurementsResponse listTemperatureMeasurements(ListTemperatureMeasurementsRequest request) {
        log.trace("Searching Database for Temperature Measurements");
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
        Page<OpenCDXTemperatureMeasurementModel> all = Page.empty();
        if (request.hasPatientId()) {
            all = this.openCDXTemperatureMeasurementRepository.findAllByPatientId(
                    new OpenCDXIdentifier(request.getPatientId()), pageable);
        } else if (request.hasNationalHealthId()) {
            all = this.openCDXTemperatureMeasurementRepository.findAllByNationalHealthId(
                    request.getNationalHealthId(), pageable);
        }
        log.trace("Found Temperature Measurements in database");

        all.get().forEach(model -> {
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        "Temperature Measurement accessed",
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        AuditEntity.newBuilder()
                                .setPatientId(model.getPatientId().toHexString())
                                .setNationalHealthId(model.getNationalHealthId())
                                .build(),
                        TEMPERATURE_MEASUREMENTS + model.getId(),
                        this.objectMapper.writeValueAsString(model.getProtobufMessage()));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 5, FAILED_TO_CONVERT_TEMPERATURE_MEASUREMENTS, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
                throw openCDXNotAcceptable;
            }
        });
        return ListTemperatureMeasurementsResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllTemperatureMeasurement(all.get()
                        .map(OpenCDXTemperatureMeasurementModel::getProtobufMessage)
                        .toList())
                .build();
    }
}

