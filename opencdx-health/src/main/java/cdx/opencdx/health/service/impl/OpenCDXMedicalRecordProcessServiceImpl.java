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
import cdx.opencdx.commons.model.OpenCDXClassificationModel;
import cdx.opencdx.commons.repository.OpenCDXClassificationRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXClassificationMessageService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.service.classification.ClassificationResponse;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import cdx.opencdx.health.model.*;
import cdx.opencdx.health.repository.*;
import cdx.opencdx.health.service.*;
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
    //private final OpenCDXMedicalRecordService openCDXMedicalRecordService;
    private final OpenCDXProfileRepository openCDXProfileRepository;
    private final OpenCDXIAMProfileService openCDXIAMProfileService;
    private final OpenCDXMedicationRepository openCDXMedicationRepository;
    private final OpenCDXMedicationService openCDXMedicationService;
    private final OpenCDXMedicationAdministrationRepository openCDXMedicationAdministrationRepository;
    private final OpenCDXMedicationAdministrationService openCDXMedicationAdministrationService;
    private final OpenCDXAllergyRepository openCDXAllergyRepository;
    private final OpenCDXAllergyService openCDXAllergyService;
    private final OpenCDXDoctorNotesRepository openCDXDoctorNotesRepository;
    private final OpenCDXDoctorNotesService openCDXDoctorNotesService;
    private final OpenCDXVaccineRepository openCDXVaccineRepository;
    private final OpenCDXVaccineService openCDXVaccineService;
    private final OpenCDXHeightMeasurementRepository openCDXHeightMeasurementRepository;
    private final OpenCDXHeightMeasurementService openCDXHeightMeasurementService;
    private final OpenCDXWeightMeasurementRepository openCDXWeightMeasurementRepository;
    private final OpenCDXWeightMeasurementService openCDXWeightMeasurementService;
    private final OpenCDXBPMRepository openCDXBPMRepository;
    private final OpenCDXBPMService openCDXBPMService;
    private final OpenCDXHeartRPMRepository openCDXHeartRPMRepository;
    private final OpenCDXHeartRPMService openCDXHeartRPMService;
    // private final OpenCDXQuestionnaireClient openCDXQuestionnaireClient;
    private final OpenCDXClassificationRepository openCDXClassificationRepository;
    private final OpenCDXClassificationMessageService openCDXClassificationMessageService;
    private final OpenCDXConnectedTestRepository openCDXConnectedTestRepository;
    private final OpenCDXConnectedTestService openCDXConnectedTestService;

    /**
     * Method to processing medical record.
     * @param openCDXCurrentUser current user.
     * @param openCDXProfileRepository profile repository.
     * @param openCDXIAMProfileService profile service.
     * @param openCDXClassificationRepository classification repository.
     * @param openCDXClassificationMessageService classification message service.
     * @param openCDXMedicationRepository medication repository.
     * @param openCDXMedicationService medication service.
     * @param openCDXMedicationAdministrationRepository medication administration repository.
     * @param openCDXMedicationAdministrationService medication administration service.
     * @param openCDXAllergyRepository allergy repository.
     * @param openCDXAllergyService allergy service.
     * @param openCDXDoctorNotesRepository doctor notes repository.
     * @param openCDXDoctorNotesService doctor notes service.
     * @param openCDXVaccineRepository vaccine repository.
     * @param openCDXVaccineService vaccine service.
     * @param openCDXHeightMeasurementRepository height measurement repository.
     * @param openCDXHeightMeasurementService height measurement service.
     * @param openCDXWeightMeasurementRepository weight measurement repository.
     * @param openCDXWeightMeasurementService weight measurement service.
     * @param openCDXBPMRepository bpm repository.
     * @param openCDXBPMService bpm service.
     * @param openCDXHeartRPMRepository heartRPM repository.
     * @param openCDXHeartRPMService heartRPM service.
     * @param openCDXMedicalRecordRepository medical record repository.
     * @param openCDXMedicalRecordService medical record service.
     * @param openCDXConnectedTestRepository connected test repository.
     * @param openCDXConnectedTestService connected test service.
     */
    public OpenCDXMedicalRecordProcessServiceImpl(
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXProfileRepository openCDXProfileRepository,
            OpenCDXIAMProfileService openCDXIAMProfileService,
            OpenCDXClassificationRepository openCDXClassificationRepository,
            OpenCDXClassificationMessageService openCDXClassificationMessageService,
            OpenCDXMedicationRepository openCDXMedicationRepository,
            OpenCDXMedicationService openCDXMedicationService,
            OpenCDXMedicationAdministrationRepository openCDXMedicationAdministrationRepository,
            OpenCDXMedicationAdministrationService openCDXMedicationAdministrationService,
            OpenCDXAllergyRepository openCDXAllergyRepository,
            OpenCDXAllergyService openCDXAllergyService,
            OpenCDXDoctorNotesRepository openCDXDoctorNotesRepository,
            OpenCDXDoctorNotesService openCDXDoctorNotesService,
            OpenCDXVaccineRepository openCDXVaccineRepository,
            OpenCDXVaccineService openCDXVaccineService,
            OpenCDXHeightMeasurementRepository openCDXHeightMeasurementRepository,
            OpenCDXHeightMeasurementService openCDXHeightMeasurementService,
            OpenCDXWeightMeasurementRepository openCDXWeightMeasurementRepository,
            OpenCDXWeightMeasurementService openCDXWeightMeasurementService,
            OpenCDXBPMRepository openCDXBPMRepository,
            OpenCDXBPMService openCDXBPMService,
            OpenCDXHeartRPMRepository openCDXHeartRPMRepository,
            OpenCDXHeartRPMService openCDXHeartRPMService,
            OpenCDXMedicalRecordRepository openCDXMedicalRecordRepository,
            OpenCDXMedicalRecordService openCDXMedicalRecordService,
            OpenCDXConnectedTestRepository openCDXConnectedTestRepository,
            OpenCDXConnectedTestService openCDXConnectedTestService) {
        // OpenCDXQuestionnaireClient openCDXQuestionnaireClient) {
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.openCDXIAMProfileService = openCDXIAMProfileService;
        this.openCDXClassificationRepository = openCDXClassificationRepository;
        this.openCDXClassificationMessageService = openCDXClassificationMessageService;
        this.openCDXMedicalRecordRepository = openCDXMedicalRecordRepository;
        this.openCDXMedicationRepository = openCDXMedicationRepository;
        this.openCDXMedicationService = openCDXMedicationService;
        this.openCDXMedicationAdministrationRepository = openCDXMedicationAdministrationRepository;
        this.openCDXMedicationAdministrationService = openCDXMedicationAdministrationService;
        this.openCDXAllergyRepository = openCDXAllergyRepository;
        this.openCDXAllergyService = openCDXAllergyService;
        this.openCDXDoctorNotesRepository = openCDXDoctorNotesRepository;
        this.openCDXDoctorNotesService = openCDXDoctorNotesService;
        this.openCDXVaccineRepository = openCDXVaccineRepository;
        this.openCDXVaccineService = openCDXVaccineService;
        this.openCDXHeightMeasurementRepository = openCDXHeightMeasurementRepository;
        this.openCDXHeightMeasurementService = openCDXHeightMeasurementService;
        this.openCDXWeightMeasurementRepository = openCDXWeightMeasurementRepository;
        this.openCDXWeightMeasurementService = openCDXWeightMeasurementService;
        this.openCDXBPMRepository = openCDXBPMRepository;
        this.openCDXBPMService = openCDXBPMService;
        this.openCDXHeartRPMRepository = openCDXHeartRPMRepository;
        this.openCDXHeartRPMService = openCDXHeartRPMService;
        this.openCDXConnectedTestRepository = openCDXConnectedTestRepository;
        this.openCDXConnectedTestService = openCDXConnectedTestService;
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
            if (openCDXProfileRepository.existsById(new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXIAMProfileService.updateUserProfile(UpdateUserProfileRequest.newBuilder()
                        .setUpdatedProfile(medicalRecordModel.getUserProfile())
                        .build());
            } else {
                openCDXIAMProfileService.createUserProfile(CreateUserProfileRequest
                        .newBuilder().setUserProfile(medicalRecordModel.getUserProfile()).build());
            }
            // Medication
            medicalRecordModel.getMedicationList().forEach(openCDXMedicationService::prescribing);
            // Medication Administration
            medicalRecordModel.getMedicationAdministrationList().forEach(medicationAdministration -> {
                if (openCDXMedicationAdministrationRepository.existsById(new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                    openCDXMedicationAdministrationService.trackMedicationAdministration(medicationAdministration);
                }
            })  ;
            medicalRecordModel.getKnownAllergyList().forEach(allergy -> {
                if (openCDXAllergyRepository.existsById(new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                    openCDXAllergyService.updateAllergy(UpdateAllergyRequest.newBuilder().setKnownAllergy(allergy).build());
                } else {
                    openCDXAllergyService.createAllergy(CreateAllergyRequest.newBuilder().setKnownAllergy(allergy).build());
                }
            });
            medicalRecordModel.getDoctorNotesList().forEach(doctorNotes -> {
                if (openCDXDoctorNotesRepository.existsById(new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                    openCDXDoctorNotesService.updateDoctorNotes(UpdateDoctorNotesRequest
                            .newBuilder().setDoctorNotes(doctorNotes).build());
                } else {
                    openCDXDoctorNotesService.createDoctorNotes(CreateDoctorNotesRequest.newBuilder().setDoctorNotes(doctorNotes)
                            .build());
                }
            });
            // Vaccine
            medicalRecordModel.getVaccineList().forEach(vaccine -> {
                if (openCDXVaccineRepository.existsById(new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                    openCDXVaccineService.updateVaccine(vaccine);
                } else {
                    openCDXVaccineService.trackVaccineAdministration(vaccine);
                }
            });
            medicalRecordModel.getHeightMeasurementList().forEach(heightMeasurement -> {
                if (openCDXHeightMeasurementRepository.existsById(new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                    openCDXHeightMeasurementService.createHeightMeasurement(CreateHeightMeasurementRequest.newBuilder()
                                    .setHeightMeasurement(heightMeasurement)
                            .build());
                } else {
                    openCDXHeightMeasurementService.createHeightMeasurement(CreateHeightMeasurementRequest.newBuilder()
                                    .setHeightMeasurement(heightMeasurement)
                            .build());
                }
            });
            medicalRecordModel.getWeightMeasurementList().forEach(weightMeasurement -> {
                if (openCDXWeightMeasurementRepository.existsById(new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                    openCDXWeightMeasurementService.updateWeightMeasurement(UpdateWeightMeasurementRequest.newBuilder()
                                    .setWeightMeasurement(weightMeasurement)
                            .build());
                } else {
                    openCDXWeightMeasurementService.createWeightMeasurement(CreateWeightMeasurementRequest.newBuilder()
                                    .setWeightMeasurement(weightMeasurement)
                            .build());
                }
            });
            medicalRecordModel.getBpmList().forEach(bpm -> {
                if (openCDXBPMRepository.existsById(new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                    openCDXBPMService.updateBPMMeasurement(UpdateBPMRequest.newBuilder().setBpmMeasurement(bpm).build());
                } else {
                    openCDXBPMService.createBPMMeasurement(CreateBPMRequest.newBuilder().setBpmMeasurement(bpm).build());
                }
            });
        medicalRecordModel.getHeartRPMList().forEach(heartRPM -> {
                if (openCDXHeartRPMRepository.existsById(new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                 openCDXHeartRPMService.updateHeartRPMMeasurement(UpdateHeartRPMRequest.newBuilder()
                         .setHeartRpmMeasurement(heartRPM).build());
                } else {
                    openCDXHeartRPMService.createHeartRPMMeasurement(CreateHeartRPMRequest.newBuilder()
                            .setHeartRpmMeasurement(heartRPM).build());
                }
        });
        //ConnectedTest
        medicalRecordModel.getConnectedTestList().forEach(openCDXConnectedTestService::submitTest);
        medicalRecordModel.getClassificationsList().forEach(classification -> openCDXClassificationRepository.save(OpenCDXClassificationModel.builder()
                .classificationResponse(ClassificationResponse.newBuilder().setClassification(classification)
                        .build())
                .build()));
        medicalRecordModel.setStatus(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE);
    }
}
