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
import cdx.opencdx.commons.repository.OpenCDXClassificationRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import cdx.opencdx.health.model.*;
import cdx.opencdx.health.repository.*;
import cdx.opencdx.health.service.OpenCDXMedicalRecordProcessService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Interface for the OpenCDXMedicalRecordProcessService
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXMedicalRecordProcessServiceImpl implements OpenCDXMedicalRecordProcessService {

    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXMedicalRecordRepository openCDXMedicalRecordRepository;
    private final OpenCDXProfileRepository openCDXProfileRepository;
    private final OpenCDXMedicationRepository openCDXMedicationRepository;
    private final OpenCDXMedicationAdministrationRepository openCDXMedicationAdministrationRepository;
    private final OpenCDXAllergyRepository openCDXAllergyRepository;
    private final OpenCDXDoctorNotesRepository openCDXDoctorNotesRepository;
    private final OpenCDXVaccineRepository openCDXVaccineRepository;
    private final OpenCDXHeightMeasurementRepository openCDXHeightMeasurementRepository;
    private final OpenCDXWeightMeasurementRepository openCDXWeightMeasurementRepository;
    private final OpenCDXBPMRepository openCDXBPMRepository;
    private final OpenCDXHeartRPMRepository openCDXHeartRPMRepository;
    // private final OpenCDXQuestionnaireClient openCDXQuestionnaireClient;
    private final OpenCDXClassificationRepository openCDXClassificationRepository;
    private final OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    /**
     * Method to processing medical record.
     */
    public OpenCDXMedicalRecordProcessServiceImpl(
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXProfileRepository openCDXProfileRepository,
            OpenCDXClassificationRepository openCDXClassificationRepository,
            OpenCDXMedicationRepository openCDXMedicationRepository,
            OpenCDXMedicationAdministrationRepository openCDXMedicationAdministrationRepository,
            OpenCDXAllergyRepository openCDXAllergyRepository,
            OpenCDXDoctorNotesRepository openCDXDoctorNotesRepository,
            OpenCDXVaccineRepository openCDXVaccineRepository,
            OpenCDXHeightMeasurementRepository openCDXHeightMeasurementRepository,
            OpenCDXWeightMeasurementRepository openCDXWeightMeasurementRepository,
            OpenCDXBPMRepository openCDXBPMRepository,
            OpenCDXHeartRPMRepository openCDXHeartRPMRepository,
            OpenCDXMedicalRecordRepository openCDXMedicalRecordRepository,
            OpenCDXConnectedTestRepository openCDXConnectedTestRepository) {
        // OpenCDXQuestionnaireClient openCDXQuestionnaireClient) {
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.openCDXClassificationRepository = openCDXClassificationRepository;
        this.openCDXMedicalRecordRepository = openCDXMedicalRecordRepository;
        this.openCDXMedicationRepository = openCDXMedicationRepository;
        this.openCDXMedicationAdministrationRepository = openCDXMedicationAdministrationRepository;
        this.openCDXAllergyRepository = openCDXAllergyRepository;
        this.openCDXDoctorNotesRepository = openCDXDoctorNotesRepository;
        this.openCDXVaccineRepository = openCDXVaccineRepository;
        this.openCDXHeightMeasurementRepository = openCDXHeightMeasurementRepository;
        this.openCDXWeightMeasurementRepository = openCDXWeightMeasurementRepository;
        this.openCDXBPMRepository = openCDXBPMRepository;
        this.openCDXHeartRPMRepository = openCDXHeartRPMRepository;
        this.openCDXConnectedTestRepository = openCDXConnectedTestRepository;
        // this.openCDXQuestionnaireClient = openCDXQuestionnaireClient;
    }

    @Override
    public void processMedicalRecord(OpenCDXIdentifier medicalRecordId) {
        Optional<OpenCDXMedicalRecordModel> model = openCDXMedicalRecordRepository.findById(medicalRecordId);
        if (model.isEmpty()) {
            return;
        }
        OpenCDXMedicalRecordModel medicalRecordModel = model.get();
        if (medicalRecordModel.getStatus() == MedicalRecordStatus.MEDICAL_RECORD_STATUS_EXPORT) {
            processExport(medicalRecordModel);
        } else if (medicalRecordModel.getStatus() == MedicalRecordStatus.MEDICAL_RECORD_STATUS_IMPORT) {
            processImport(medicalRecordModel);
        }
    }

    private void processExport(OpenCDXMedicalRecordModel medicalRecordModel) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        //        OpenCDXCallCredentials openCDXCallCredentials =
        //                new OpenCDXCallCredentials(this.openCDXCurrentUser.getCurrentUserAccessToken());

        //        medicalRecordModel.setUserQuestionnaireDataList(
        //
        // openCDXQuestionnaireClient.getUserQuestionnaireDataList(GetQuestionnaireListRequest.newBuilder()
        //                        .setId(medicalRecordModel.getUserProfile().getId()).build(),
        // openCDXCallCredentials).getListList());
        //        medicalRecordModel.setClassificationsList(
        openCDXClassificationRepository.findAllByPatientId(new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()));


        medicalRecordModel.setMedicationList(
                openCDXMedicationRepository
                        .findAllByPatientId(
                                new OpenCDXIdentifier(
                                        medicalRecordModel.getUserProfile().getId()),
                                pageable)
                        .stream()
                        .map(OpenCDXMedicationModel::getProtobufMessage)
                        .toList());
        medicalRecordModel.setMedicationAdministrationList(
                openCDXMedicationAdministrationRepository
                        .findAllByPatientId(
                                new OpenCDXIdentifier(
                                        medicalRecordModel.getUserProfile().getId()),
                                pageable)
                        .stream()
                        .map(OpenCDXMedicationAdministrationModel::getProtobufMessage)
                        .toList());
        medicalRecordModel.setKnownAllergyList(
                openCDXAllergyRepository
                        .findAllByPatientId(
                                new OpenCDXIdentifier(
                                        medicalRecordModel.getUserProfile().getId()),
                                pageable)
                        .stream()
                        .map(OpenCDXAllergyModel::getProtobufMessage)
                        .toList());
        medicalRecordModel.setDoctorNotesList(
                openCDXDoctorNotesRepository
                        .findAllByPatientId(
                                new OpenCDXIdentifier(
                                        medicalRecordModel.getUserProfile().getId()),
                                pageable)
                        .stream()
                        .map(OpenCDXDoctorNotesModel::getProtobufMessage)
                        .toList());
        medicalRecordModel.setVaccineList(
                openCDXVaccineRepository
                        .findAllByPatientId(
                                new OpenCDXIdentifier(
                                        medicalRecordModel.getUserProfile().getId()),
                                pageable)
                        .stream()
                        .map(OpenCDXVaccineModel::getProtobufMessage)
                        .toList());
        medicalRecordModel.setHeightMeasurementList(
                openCDXHeightMeasurementRepository
                        .findAllByPatientId(
                                new OpenCDXIdentifier(
                                        medicalRecordModel.getUserProfile().getId()),
                                pageable)
                        .stream()
                        .map(OpenCDXHeightMeasurementModel::getProtobufMessage)
                        .toList());
        medicalRecordModel.setWeightMeasurementList(
                openCDXWeightMeasurementRepository
                        .findAllByPatientId(
                                new OpenCDXIdentifier(
                                        medicalRecordModel.getUserProfile().getId()),
                                pageable)
                        .stream()
                        .map(OpenCDXWeightMeasurementModel::getProtobufMessage)
                        .toList());
        medicalRecordModel.setBpmList(
                openCDXBPMRepository
                        .findAllByPatientId(
                                new OpenCDXIdentifier(
                                        medicalRecordModel.getUserProfile().getId()),
                                pageable)
                        .stream()
                        .map(OpenCDXBPMModel::getProtobufMessage)
                        .toList());
        medicalRecordModel.setHeartRPMList(
                openCDXHeartRPMRepository
                        .findAllByPatientId(
                                new OpenCDXIdentifier(
                                        medicalRecordModel.getUserProfile().getId()),
                                pageable)
                        .stream()
                        .map(OpenCDXHeartRPMModel::getProtobufMessage)
                        .toList());
        medicalRecordModel.setConnectedTestList(
                openCDXConnectedTestRepository
                        .findAllByPatientId(
                                new OpenCDXIdentifier(
                                        medicalRecordModel.getUserProfile().getId()),
                                pageable)
                        .stream()
                        .map(OpenCDXConnectedTestModel::getProtobufMessage)
                        .toList());

        medicalRecordModel.setStatus(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE);
    }

    private void processImport(OpenCDXMedicalRecordModel medicalRecordModel) {
        medicalRecordModel.setConnectedTestList(medicalRecordModel.getConnectedTestList());
        medicalRecordModel.setStatus(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE);
        // openCDXMedicalRecordRepository.save(medicalRecordModel);
    }
}
