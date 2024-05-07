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
import cdx.opencdx.health.model.OpenCDXDoctorNotesModel;
import cdx.opencdx.health.repository.OpenCDXDoctorNotesRepository;
import cdx.opencdx.health.service.OpenCDXDoctorNotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;

/**
 * Service for processing doctor notes
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXDoctorNotesServiceImpl implements OpenCDXDoctorNotesService {
    private static final String DOMAIN = "OpenCDXDoctorNotesServiceImpl";
    private static final String DOCTOR_NOTES = "Doctor Notes : ";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_DOCTOR_NOTES = "Failed to convert doctor notes";
    private static final String FAILED_TO_FIND_DOCTOR_NOTES = "Failed to find doctor notes ";
    private static final String PROFILES = "profiles";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXDoctorNotesRepository openCDXDoctorNotesRepository;

    /**
     * Constructor with OpenCDXDoctorNotesService
     *
     * @param openCDXAuditService            user for recording PHI
     * @param openCDXCurrentUser             Current User Service
     * @param objectMapper                   ObjectMapper for converting to JSON for Audit system.
     * @param openCDXDocumentValidator       Validator for documents
     * @param openCDXDoctorNotesRepository Mongo Repository for OpenCDXDoctorNotes
     */
    public OpenCDXDoctorNotesServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXDoctorNotesRepository openCDXDoctorNotesRepository) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXDoctorNotesRepository = openCDXDoctorNotesRepository;
    }

    @Override
    public CreateDoctorNotesResponse createDoctorNotes(CreateDoctorNotesRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                PROFILES, new OpenCDXIdentifier(request.getDoctorNotes().getPatientId()));
        OpenCDXDoctorNotesModel openCDXDoctorNotesModel =
                this.openCDXDoctorNotesRepository.save(new OpenCDXDoctorNotesModel(request.getDoctorNotes()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Doctor Notes created",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(openCDXDoctorNotesModel.getPatientId().toHexString())
                            .setNationalHealthId(openCDXDoctorNotesModel.getNationalHealthId())
                            .build(),
                    DOCTOR_NOTES + openCDXDoctorNotesModel.getId(),
                    this.objectMapper.writeValueAsString(openCDXDoctorNotesModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_DOCTOR_NOTES, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXDoctorNotesModel.toString());
            throw openCDXNotAcceptable;
        }
        return CreateDoctorNotesResponse.newBuilder()
                .setDoctorNotes(openCDXDoctorNotesModel.getProtobufMessage())
                .build();
    }

    @Override
    public GetDoctorNotesResponse getDoctorNotes(GetDoctorNotesRequest request) {
        OpenCDXDoctorNotesModel model = this.openCDXDoctorNotesRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, FAILED_TO_FIND_DOCTOR_NOTES + request.getId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Doctor Notes Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    DOCTOR_NOTES + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_DOCTOR_NOTES, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return GetDoctorNotesResponse.newBuilder()
                .setDoctorNotes(model.getProtobufMessage())
                .build();
    }

    @Override
    public UpdateDoctorNotesResponse updateDoctorNotes(UpdateDoctorNotesRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                PROFILES, new OpenCDXIdentifier(request.getDoctorNotes().getPatientId()));
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "doctor-notes", new OpenCDXIdentifier(request.getDoctorNotes().getId()));
        OpenCDXDoctorNotesModel model =
                this.openCDXDoctorNotesRepository.save(new OpenCDXDoctorNotesModel(request.getDoctorNotes()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiUpdated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Doctor Notes Updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    DOCTOR_NOTES + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_DOCTOR_NOTES, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateDoctorNotesResponse.newBuilder()
                .setDoctorNotes(model.getProtobufMessage())
                .build();
    }

    @Override
    public SuccessResponse deleteDoctorNotes(DeleteDoctorNotesRequest request) {
        OpenCDXDoctorNotesModel model = this.openCDXDoctorNotesRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 3, FAILED_TO_FIND_DOCTOR_NOTES + request.getId()));

        this.openCDXDoctorNotesRepository.deleteById(model.getId());
        log.info("Deleted Doctor Notes: {}", request.getId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Doctor Notes Deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getPatientId().toHexString())
                            .setNationalHealthId(model.getNationalHealthId())
                            .build(),
                    DOCTOR_NOTES + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_DOCTOR_NOTES, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public ListDoctorNotesResponse listAllByPatientId(ListDoctorNotesRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(PROFILES, new OpenCDXIdentifier(request.getPatientId()));
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

        Page<OpenCDXDoctorNotesModel> all;

        if (!request.getTags().isEmpty() && request.hasStartDate() && request.hasEndDate()) {

            Instant start = Instant.ofEpochSecond(
                    request.getStartDate().getSeconds(), request.getStartDate().getNanos());

            Instant end = Instant.ofEpochSecond(
                    request.getEndDate().getSeconds(), request.getEndDate().getNanos());

            all = this.openCDXDoctorNotesRepository.findAllByPatientIdAndTagsContainingAndNoteDatetimeBetween(
                    new OpenCDXIdentifier(request.getPatientId()), request.getTags(), start, end, pageable);
        } else if (!request.getTags().isEmpty()) {
            all = this.openCDXDoctorNotesRepository.findAllByPatientIdAndTags(
                    new OpenCDXIdentifier(request.getPatientId()), request.getTags(), pageable);
        } else if (request.hasStartDate() && request.hasEndDate()) {
            Instant start = Instant.ofEpochSecond(
                    request.getStartDate().getSeconds(), request.getStartDate().getNanos());

            Instant end = Instant.ofEpochSecond(
                    request.getEndDate().getSeconds(), request.getEndDate().getNanos());

            all = this.openCDXDoctorNotesRepository.findAllByPatientIdAndNoteDatetimeBetween(
                    new OpenCDXIdentifier(request.getPatientId()), start, end, pageable);
        } else {
            all = this.openCDXDoctorNotesRepository.findAllByPatientId(
                    new OpenCDXIdentifier(request.getPatientId()), pageable);
        }
        log.trace("found database results");

        all.get().forEach(openCDXDoctorNotesModel -> {
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.phiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        "Doctor Notes accessed",
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        AuditEntity.newBuilder()
                                .setPatientId(
                                        openCDXDoctorNotesModel.getPatientId().toHexString())
                                .setNationalHealthId(openCDXDoctorNotesModel.getNationalHealthId())
                                .build(),
                        DOCTOR_NOTES + openCDXDoctorNotesModel.getId(),
                        this.objectMapper.writeValueAsString(openCDXDoctorNotesModel.getProtobufMessage()));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_DOCTOR_NOTES, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, openCDXDoctorNotesModel.toString());
                throw openCDXNotAcceptable;
            }
        });
        return ListDoctorNotesResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllDoctorNotes(all.get()
                        .map(OpenCDXDoctorNotesModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
