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
import cdx.opencdx.grpc.data.Vaccine;
import cdx.opencdx.grpc.service.health.GetVaccineByIdRequest;
import cdx.opencdx.grpc.service.health.ListVaccinesRequest;
import cdx.opencdx.grpc.service.health.ListVaccinesResponse;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.health.model.OpenCDXVaccineModel;
import cdx.opencdx.health.repository.OpenCDXVaccineRepository;
import cdx.opencdx.health.service.OpenCDXVaccineService;
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
 * Service for Vaccine
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXVaccineServiceImpl implements OpenCDXVaccineService {
    private static final String OBJECT = "OBJECT";
    private static final String DOMAIN = "OpenCDXVaccineServiceImpl";
    private static final String VACCINE = "Vaccine Administration: ";
    private static final String FAILED_TO_CONVERT_OPEN_CDX_VACCINE_MODEL = "failed to convert OpenCDXVaccineModel";
    private static final String PROFILES = "profiles";
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXVaccineRepository openCDXVaccineRepository;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor that takes a ObjectMapper, OpenCDXAuditService, OpenCDXCurrentUser, and OpenCDXDocumentValidator
     * @param objectMapper             Jackson Objectmapper to use
     * @param openCDXAuditService      Audit service to record events
     * @param openCDXCurrentUser Service to get Current user.
     * @param openCDXVaccineRepository repository for reading and writing vaccine data
     * @param openCDXDocumentValidator document validator
     */
    public OpenCDXVaccineServiceImpl(
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXVaccineRepository openCDXVaccineRepository,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXVaccineRepository = openCDXVaccineRepository;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    /**
     * Method to track when vaccine is given to a patient.
     *
     * @param request Request for the vaccine administration.
     * @return Response with the vaccine administration.
     */
    @Override
    public Vaccine trackVaccineAdministration(Vaccine request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(PROFILES, new OpenCDXIdentifier(request.getPatientId()));
        OpenCDXVaccineModel model = this.openCDXVaccineRepository.save(new OpenCDXVaccineModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Vaccine Prescribed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    VACCINE + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, FAILED_TO_CONVERT_OPEN_CDX_VACCINE_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return model.getProtobufMessage();
    }

    /**
     * Method to get vaccine information by ID.
     *
     * @param request Request for the vaccine by ID.
     * @return Response with the vaccine.
     */
    @Override
    public Vaccine getVaccineById(GetVaccineByIdRequest request) {
        OpenCDXVaccineModel model = this.openCDXVaccineRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, "Failed to find vaccine" + request.getId()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Vaccine Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    VACCINE + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, FAILED_TO_CONVERT_OPEN_CDX_VACCINE_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return model.getProtobufMessage();
    }

    @Override
    public Vaccine updateVaccine(Vaccine request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(PROFILES, new OpenCDXIdentifier(request.getPatientId()));
        OpenCDXVaccineModel vaccineModel = this.openCDXVaccineRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN,
                        3,
                        "FAILED_TO_FIND_VACCINE" + request.getId()));
        OpenCDXVaccineModel model = this.openCDXVaccineRepository.save(vaccineModel.update(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Vaccine Prescribed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    VACCINE + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, FAILED_TO_CONVERT_OPEN_CDX_VACCINE_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return model.getProtobufMessage();
    }

    /**
     * Method to get vaccine information by patient ID within a date range.
     *
     * @param request Request for the vaccine by patient ID.
     * @return Response with the list of vaccine.
     */
    @Override
    public ListVaccinesResponse listVaccines(ListVaccinesRequest request) {
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
        Page<OpenCDXVaccineModel> all = Page.empty();
        if (request.hasPatientId()) {
            all = this.openCDXVaccineRepository.findAllByPatientId(
                    new OpenCDXIdentifier(request.getPatientId()), pageable);
        } else if (request.hasNationalHealthId()) {
            all = this.openCDXVaccineRepository.findAllByNationalHealthId(request.getNationalHealthId(), pageable);
        }
        log.trace("found database results");
        this.recordAuditEvents(all);
        return ListVaccinesResponse.newBuilder()
                .addAllVaccine(all.map(OpenCDXVaccineModel::getProtobufMessage))
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .build();
    }

    private void recordAuditEvents(Page<OpenCDXVaccineModel> all) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();

        all.forEach(model -> {
            try {
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        "Vaccine Accessed",
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        AuditEntity.newBuilder()
                                .setPatientId(model.getId().toHexString())
                                .setNationalHealthId(model.getNationalHealthId())
                                .build(),
                        VACCINE + model.getId().toHexString(),
                        this.objectMapper.writeValueAsString(model));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                        this.getClass().getName(), 2, FAILED_TO_CONVERT_OPEN_CDX_VACCINE_MODEL, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
                throw openCDXNotAcceptable;
            }
        });
    }
}
