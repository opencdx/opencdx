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

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.health.medication.*;
import cdx.opencdx.health.model.OpenCDXMedicationModel;
import cdx.opencdx.health.repository.OpenCDXMedicationRepository;
import cdx.opencdx.health.service.OpenCDXApiFDA;
import cdx.opencdx.health.service.OpenCDXMedicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * Service for Medication
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXMedicationServiceImpl implements OpenCDXMedicationService {
    public static final String OBJECT = "OBJECT";
    public static final String MEDICATION = "Medication: ";
    public static final String FAILED_TO_CONVERT_OPEN_CDX_MEDICATION_MODEL = "failed to convert OpenCDXMedicationModel";
    private final ObjectMapper objectMapper;
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXMedicationRepository openCDXMedicationRepository;
    private final OpenCDXProfileRepository openCDXProfileRepository;
    private final OpenCDXApiFDA openCDXApiFDA;

    /**
     * Constructor that takes a ObjectMapper, OpenCDXAuditService, OpenCDXCurrentUser, and OpenCDXDocumentValidator
     * @param objectMapper             Jackson Objectmapper to use
     * @param openCDXAuditService      Audit service to record events
     * @param openCDXCurrentUser Service to get Current user.
     * @param openCDXMedicationRepository repository for reading and writing medication data
     * @param openCDXProfileRepository repository for reading and writing profile data
     * @param openCDXApiFDA OpenFDA API service
     */
    public OpenCDXMedicationServiceImpl(
            ObjectMapper objectMapper,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXMedicationRepository openCDXMedicationRepository,
            OpenCDXProfileRepository openCDXProfileRepository,
            OpenCDXApiFDA openCDXApiFDA) {
        this.objectMapper = objectMapper;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXMedicationRepository = openCDXMedicationRepository;
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.openCDXApiFDA = openCDXApiFDA;
    }

    @Override
    public Medication prescribing(Medication request) {
        OpenCDXProfileModel patient = this.openCDXProfileRepository
                .findById(new ObjectId(request.getPatientId()))
                .orElseThrow(() -> new OpenCDXNotAcceptable(this.getClass().getName(), 1, "Patient not found"));

        OpenCDXMedicationModel model = this.openCDXMedicationRepository.save(new OpenCDXMedicationModel(request));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Medication Prescribed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    patient.getId().toHexString(),
                    patient.getNationalHealthId(),
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

    @Override
    public Medication ending(EndMedicationRequest request) {
        OpenCDXMedicationModel model = this.openCDXMedicationRepository
                .findById(new ObjectId(request.getMedicationId()))
                .orElseThrow(() -> new OpenCDXNotAcceptable(this.getClass().getName(), 1, "Medication not found"));
        OpenCDXProfileModel patient = this.openCDXProfileRepository
                .findById(model.getPatientId())
                .orElseThrow(() -> new OpenCDXNotAcceptable(this.getClass().getName(), 1, "Patient not found"));

        model.setEndDate(Instant.ofEpochSecond(
                request.getEndDate().getSeconds(), request.getEndDate().getNanos()));

        model = this.openCDXMedicationRepository.save(model);

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Medication Ended",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    patient.getId().toHexString(),
                    patient.getNationalHealthId(),
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

    @Override
    public ListMedicationsResponse listAllMedications(ListMedicationsRequest request) {
        Pageable pageable = getPageable(request.getPagination());
        Page<OpenCDXMedicationModel> all = Page.empty();
        if (request.hasPatientId()) {
            all = this.openCDXMedicationRepository.findAllByPatientId(new ObjectId(request.getPatientId()), pageable);

        } else if (request.hasNationalHealthId()) {
            all = this.openCDXMedicationRepository.findAllByNationalHealthId(request.getNationalHealthId(), pageable);
        }
        this.recordAuditEvents(all);
        return ListMedicationsResponse.newBuilder()
                .addAllMedications(all.map(OpenCDXMedicationModel::getProtobufMessage))
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .build();
    }

    @Override
    public ListMedicationsResponse listCurrentMedications(ListMedicationsRequest request) {
        Pageable pageable = getPageable(request.getPagination());
        Page<OpenCDXMedicationModel> all = Page.empty();
        if (request.hasPatientId()) {
            all = this.openCDXMedicationRepository.findAllByPatientIdAndEndDateIsNull(
                    new ObjectId(request.getPatientId()), pageable);

        } else if (request.hasNationalHealthId()) {
            all = this.openCDXMedicationRepository.findAllByNationalHealthIdAndEndDateIsNull(
                    request.getNationalHealthId(), pageable);
        }
        this.recordAuditEvents(all);
        return ListMedicationsResponse.newBuilder()
                .addAllMedications(all.map(OpenCDXMedicationModel::getProtobufMessage))
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .build();
    }

    @Override
    public ListMedicationsResponse searchMedications(SearchMedicationsRequest request) {

        List<OpenCDXMedicationModel> medications = this.openCDXApiFDA.getMedicationsByBrandName(request.getBrandName());

        Page<OpenCDXMedicationModel> all = this.getMedicaitonPage(
                request.getPagination().getPageNumber(), request.getPagination().getPageSize(), medications);
        return ListMedicationsResponse.newBuilder()
                .addAllMedications(all.map(OpenCDXMedicationModel::getProtobufMessage))
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .build();
    }

    private Page<OpenCDXMedicationModel> getMedicaitonPage(int page, int size, List<OpenCDXMedicationModel> all) {

        Pageable pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), all.size());

        if (start > end) {
            return Page.empty();
        }

        List<OpenCDXMedicationModel> pageContent = all.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, all.size());
    }

    private void recordAuditEvents(Page<OpenCDXMedicationModel> all) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();

        all.forEach(model -> {
            try {
                this.openCDXAuditService.phiCreated(
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

    private Pageable getPageable(Pagination pagination) {
        Pageable pageable;
        if (pagination.hasSort()) {
            pageable = PageRequest.of(
                    pagination.getPageNumber(),
                    pagination.getPageSize(),
                    pagination.getSortAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                    pagination.getSort());
        } else {
            pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize());
        }

        return pageable;
    }
}
