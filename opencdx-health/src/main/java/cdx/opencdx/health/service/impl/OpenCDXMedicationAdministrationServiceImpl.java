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
import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.grpc.data.MedicationAdministration;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.GetMedicationByIdRequest;
import cdx.opencdx.grpc.service.health.ListMedicationsRequest;
import cdx.opencdx.grpc.service.health.ListMedicationsResponse;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.health.model.OpenCDXMedicationAdministrationModel;
import cdx.opencdx.health.model.OpenCDXMedicationModel;
import cdx.opencdx.health.repository.OpenCDXMedicationAdministrationRepository;
import cdx.opencdx.health.repository.OpenCDXMedicationRepository;
import cdx.opencdx.health.service.OpenCDXMedicationAdministrationService;
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
 * Service for Medication Administration
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXMedicationAdministrationServiceImpl implements OpenCDXMedicationAdministrationService {
    private static final String OBJECT = "OBJECT";
    private static final String DOMAIN = "OpenCDXMedicationAdministrationServiceImpl";
    private static final String MEDICATION = "Medication Administration: ";
    private static final String FAILED_TO_CONVERT_OPEN_CDX_MEDICATION_MODEL =
            "failed to convert OpenCDXMedicationModel";
    private static final String PROFILES = "profiles";
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXMedicationAdministrationRepository openCDXMedicationAdministrationRepository;
    private final OpenCDXMedicationRepository openCDXMedicationRepository;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;

    /**
     * Constructor that takes a ObjectMapper, OpenCDXAuditService, OpenCDXCurrentUser, and OpenCDXDocumentValidator
     * @param objectMapper             Jackson Objectmapper to use
     * @param openCDXAuditService      Audit service to record events
     * @param openCDXCurrentUser Service to get Current user.
     * @param openCDXMedicationAdministrationRepository repository for reading and writing medication admins data
     * @param openCDXMedicationRepository repository for reading and writing medication data
     * @param openCDXDocumentValidator document validator
     */
    public OpenCDXMedicationAdministrationServiceImpl(
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXMedicationAdministrationRepository openCDXMedicationAdministrationRepository,
            OpenCDXMedicationRepository openCDXMedicationRepository,
            OpenCDXDocumentValidator openCDXDocumentValidator) {
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXMedicationAdministrationRepository = openCDXMedicationAdministrationRepository;
        this.openCDXMedicationRepository = openCDXMedicationRepository;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
    }

    /**
     * Method to track when medication is given to a patient.
     *
     * @param request Request for the medication administration.
     * @return Response with the medication administration.
     */
    @Override
    public MedicationAdministration trackMedicationAdministration(MedicationAdministration request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(PROFILES, new OpenCDXIdentifier(request.getPatientId()));
        OpenCDXMedicationAdministrationModel model =
                this.openCDXMedicationAdministrationRepository.save(new OpenCDXMedicationAdministrationModel(request));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Medication Prescribed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getId().toHexString(),
                    model.getNationalHealthId(),
                    MEDICATION + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    this.getClass().getName(), 2, FAILED_TO_CONVERT_OPEN_CDX_MEDICATION_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        return model.getProtobufMessage();
    }

    /**
     * Method to get medication information by ID.
     *
     * @param request Request for the medication by ID.
     * @return Response with the medication.
     */
    @Override
    public Medication getMedicationById(GetMedicationByIdRequest request) {
        OpenCDXMedicationAdministrationModel model = this.openCDXMedicationAdministrationRepository
                .findById(new OpenCDXIdentifier(request.getMedicationId()))
                .orElseThrow(
                        () -> new OpenCDXNotFound(DOMAIN, 3, "Failed to find medication" + request.getMedicationId()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Medication Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getId().toHexString(),
                    model.getNationalHealthId(),
                    MEDICATION + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    this.getClass().getName(), 2, FAILED_TO_CONVERT_OPEN_CDX_MEDICATION_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        OpenCDXMedicationModel medication = this.openCDXMedicationRepository
                .findById(new OpenCDXIdentifier(request.getMedicationId()))
                .orElseThrow(() -> new OpenCDXNotAcceptable(this.getClass().getName(), 1, "Medication not found"));
        return medication.getProtobufMessage();
    }

    /**
     * Method to get medication information by patient ID within a date range.
     *
     * @param request Request for the medication by patient ID.
     * @return Response with the list of medication.
     */
    @Override
    public ListMedicationsResponse listMedications(ListMedicationsRequest request) {
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
        Page<OpenCDXMedicationModel> all = Page.empty();
        if (request.hasPatientId()) {
            all = this.openCDXMedicationRepository.findAllByPatientId(
                    new OpenCDXIdentifier(request.getPatientId()), pageable);
        } else if (request.hasNationalHealthId()) {
            all = this.openCDXMedicationRepository.findAllByNationalHealthId(request.getNationalHealthId(), pageable);
        }
        log.trace("found database results");
        this.recordAuditEvents(all);
        return ListMedicationsResponse.newBuilder()
                .addAllMedications(all.map(OpenCDXMedicationModel::getProtobufMessage))
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .build();
    }

    private void recordAuditEvents(Page<OpenCDXMedicationModel> all) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();

        all.forEach(model -> {
            try {
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        "Medication Accessed",
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        model.getPatientId().toHexString(),
                        model.getNationalHealthId(),
                        MEDICATION + model.getId().toHexString(),
                        this.objectMapper.writeValueAsString(model));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                        this.getClass().getName(), 2, FAILED_TO_CONVERT_OPEN_CDX_MEDICATION_MODEL, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
                throw openCDXNotAcceptable;
            }
        });
    }
}
