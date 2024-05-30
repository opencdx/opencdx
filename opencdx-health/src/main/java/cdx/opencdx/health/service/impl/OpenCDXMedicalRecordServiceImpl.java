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
import cdx.opencdx.commons.exceptions.OpenCDXForbidden;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXMedicalRecordMessageService;
import cdx.opencdx.grpc.data.AuditEntity;
import cdx.opencdx.grpc.data.MedicalRecord;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.health.model.OpenCDXMedicalRecordModel;
import cdx.opencdx.health.repository.OpenCDXMedicalRecordRepository;
import cdx.opencdx.health.service.OpenCDXMedicalRecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Service for processing Medical Record
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXMedicalRecordServiceImpl implements OpenCDXMedicalRecordService {

    private static final String OBJECT = "OBJECT";
    private static final String MEDICAL_RECORD = "Medical Record: ";
    private static final String FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_RECORD_MODEL =
            "failed to convert OpenCDXMedicalRecordModel";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXMedicalRecordRepository openCDXMedicalRecordRepository;
    private final OpenCDXProfileRepository openCDXProfileRepository;
    private final OpenCDXMedicalRecordMessageService openCDXMedicalRecordMessageService;

    /**
     * Constructor with OpenCDXAllergyService
     *
     * @param openCDXAuditService            user for recording PHI
     * @param openCDXCurrentUser             Current User Service
     * @param objectMapper                   ObjectMapper for converting to JSON for Audit system.
     * @param openCDXDocumentValidator       Validator for documents
     * @param openCDXMedicalRecordRepository Mongo Repository for OpenCDXMedicalRecord
     * @param openCDXProfileRepository       Mongo Repository for OpenCDXProfile
     * @param openCDXMedicalRecordMessageService Service for sending messages
     */
    public OpenCDXMedicalRecordServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXMedicalRecordRepository openCDXMedicalRecordRepository,
            OpenCDXProfileRepository openCDXProfileRepository,
            OpenCDXMedicalRecordMessageService openCDXMedicalRecordMessageService) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXMedicalRecordRepository = openCDXMedicalRecordRepository;
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.openCDXMedicalRecordMessageService = openCDXMedicalRecordMessageService;
    }

    @Override
    public GetMedicalRecordResponse requestMedicalRecord(GetMedicalRecordRequest request) {
        OpenCDXProfileModel openCDXProfileModel = null;
        if (request.hasPatientId()) {
            openCDXProfileModel = this.openCDXProfileRepository
                    .findById(new OpenCDXIdentifier(request.getPatientId()))
                    .orElseThrow(() -> new OpenCDXNotFound(this.getClass().getName(), 1, "Patient ID not found"));
        } else if (request.hasNationalHealthId()) {
            openCDXProfileModel = openCDXProfileRepository
                    .findByNationalHealthId(request.getNationalHealthId())
                    .orElseThrow(() -> new OpenCDXNotFound(this.getClass().getName(), 2, "NHID not found"));
        }
        OpenCDXMedicalRecordModel model =
                this.openCDXMedicalRecordRepository.save(new OpenCDXMedicalRecordModel(openCDXProfileModel));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Medical Record Requested",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getUserProfile().getId())
                            .setNationalHealthId(model.getUserProfile().getNationalHealthId())
                            .build(),
                    MEDICAL_RECORD + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    this.getClass().getName(), 1, FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_RECORD_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        openCDXMedicalRecordMessageService.sendMedicalRecord(model.getProtobufMessage());

        return GetMedicalRecordResponse.newBuilder()
                .setMedicalRecordId(model.getId().toString())
                .build();
    }

    @Override
    public SuccessResponse getMedicalRecordStatus(MedicalRecordByIdRequest request) {
        OpenCDXMedicalRecordModel model = this.openCDXMedicalRecordRepository
                .findById(new OpenCDXIdentifier(request.getMedicalRecordId()))
                .orElseThrow(() -> new OpenCDXNotFound(this.getClass().getName(), 3, "Medical Record ID not found"));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Medical Record Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getUserProfile().getId())
                            .setNationalHealthId(model.getUserProfile().getNationalHealthId())
                            .build(),
                    MEDICAL_RECORD + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    this.getClass().getName(), 2, FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_RECORD_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        if (model.getStatus().equals(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE)) {
            return SuccessResponse.newBuilder().setSuccess(true).build();
        }
        return SuccessResponse.newBuilder().setSuccess(false).build();
    }

    @Override
    public MedicalRecordByIdResponse getMedicalRecordById(MedicalRecordByIdRequest request) {
        OpenCDXMedicalRecordModel model = this.openCDXMedicalRecordRepository
                .findById(new OpenCDXIdentifier(request.getMedicalRecordId()))
                .orElseThrow(() -> new OpenCDXNotFound(this.getClass().getName(), 3, "Medical Record ID not found"));
        if (!model.getStatus().equals(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE)) {
            throw new OpenCDXForbidden(this.getClass().getName(), 1, "Medical Record not complete");
        }
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Medical Record Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(model.getUserProfile().getId())
                            .setNationalHealthId(model.getUserProfile().getNationalHealthId())
                            .build(),
                    MEDICAL_RECORD + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    this.getClass().getName(), 3, FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_RECORD_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return MedicalRecordByIdResponse.newBuilder()
                .setMedicalRecord(model.getProtobufMessage())
                .build();
    }

    @Override
    public CreateMedicalRecordResponse createMedicalRecord(CreateMedicalRecordRequest request) {
        this.openCDXDocumentValidator.validateDocumentOrThrow(
                "profiles",
                new OpenCDXIdentifier(
                        request.getMedicalRecord().getUserProfile().getId()));
        MedicalRecord medicalRecord = request.getMedicalRecord();
        OpenCDXMedicalRecordModel medicalRecordModel = OpenCDXMedicalRecordModel.builder()
                .id(new OpenCDXIdentifier(medicalRecord.getId()))
                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_IMPORT)
                .userProfile(medicalRecord.getUserProfile())
                .classificationsList(medicalRecord.getClassificationList())
                .medicationList(medicalRecord.getMedicationList())
                .medicationAdministrationList(medicalRecord.getMedicationAdministrationList())
                .knownAllergyList(medicalRecord.getKnownAllergyList())
                .doctorNotesList(medicalRecord.getDoctorNotesList())
                .vaccineList(medicalRecord.getVaccineList())
                .heightMeasurementList(medicalRecord.getHeightMeasurementList())
                .weightMeasurementList(medicalRecord.getWeightMeasurementList())
                .bpmList(medicalRecord.getBpmList())
                .heartRPMList(medicalRecord.getHeartRPMList())
                .userQuestionnaireDataList(medicalRecord.getUserQuestionnaireDataList())
                .build();
        medicalRecordModel = openCDXMedicalRecordRepository.save(medicalRecordModel);
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Medical Record Created",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    AuditEntity.newBuilder()
                            .setPatientId(medicalRecordModel.getUserProfile().getId())
                            .setNationalHealthId(
                                    medicalRecordModel.getUserProfile().getNationalHealthId())
                            .build(),
                    MEDICAL_RECORD + medicalRecordModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(medicalRecordModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    this.getClass().getName(), 4, FAILED_TO_CONVERT_OPEN_CDX_MEDICAL_RECORD_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        openCDXMedicalRecordMessageService.sendMedicalRecord(medicalRecordModel.getProtobufMessage());
        return CreateMedicalRecordResponse.newBuilder()
                .setMedicalRecordId(medicalRecordModel.getId().toString())
                .build();
    }
}
