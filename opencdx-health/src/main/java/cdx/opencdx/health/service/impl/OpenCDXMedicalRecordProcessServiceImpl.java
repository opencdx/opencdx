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
import cdx.opencdx.commons.model.OpenCDXConnectedTestModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import cdx.opencdx.health.model.OpenCDXMedicalRecordModel;
import cdx.opencdx.health.model.OpenCDXMedicationModel;
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
@SuppressWarnings({"java:S3776", "java:S1117", "java:S3923"})
public class OpenCDXMedicalRecordProcessServiceImpl implements OpenCDXMedicalRecordProcessService {

    private final OpenCDXMedicalRecordRepository openCDXMedicalRecordRepository;
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
    private final OpenCDXConnectedTestRepository openCDXConnectedTestRepository;
    private final OpenCDXConnectedTestService openCDXConnectedTestService;
    private final OpenCDXMedicalConditionsRepository openCDXMedicalConditionsRepository;
    private final OpenCDXMedicalConditionsService openCDXMedicalConditionsService;
    private final OpenCDXTemperatureMeasurementRepository openCDXTemperatureMeasurementRepository;
    private final OpenCDXTemperatureMeasurementService openCDXTemperatureMeasurementService;
    private final OpenCDXMedicalHistoryService openCDXMedicalHistoryService;
    private final OpenCDXMedicalHistoryRepository openCDXMedicalHistoryRepository;

    /**
     * Method to processing medical record.
     *
     * @param openCDXProfileRepository                  profile repository.
     * @param openCDXIAMProfileService                  profile service.
     * @param openCDXMedicationRepository               medication repository.
     * @param openCDXMedicationService                  medication service.
     * @param openCDXMedicationAdministrationRepository medication administration repository.
     * @param openCDXMedicationAdministrationService    medication administration service.
     * @param openCDXAllergyRepository                  allergy repository.
     * @param openCDXAllergyService                     allergy service.
     * @param openCDXDoctorNotesRepository              doctor notes repository.
     * @param openCDXDoctorNotesService                 doctor notes service.
     * @param openCDXVaccineRepository                  vaccine repository.
     * @param openCDXVaccineService                     vaccine service.
     * @param openCDXHeightMeasurementRepository        height measurement repository.
     * @param openCDXHeightMeasurementService           height measurement service.
     * @param openCDXWeightMeasurementRepository        weight measurement repository.
     * @param openCDXWeightMeasurementService           weight measurement service.
     * @param openCDXBPMRepository                      bpm repository.
     * @param openCDXBPMService                         bpm service.
     * @param openCDXHeartRPMRepository                 heartRPM repository.
     * @param openCDXHeartRPMService                    heartRPM service.
     * @param openCDXMedicalRecordRepository            medical record repository.
     * @param openCDXMedicalRecordService               medical record service.
     * @param openCDXConnectedTestRepository            connected test repository.
     * @param openCDXConnectedTestService               connected test service.
     * @param openCDXMedicalConditionsRepository        medical condition repository.
     * @param openCDXMedicalConditionsService           medical condition service.
     * @param openCDXTemperatureMeasurementRepository   temperature measurement repository.
     * @param openCDXTemperatureMeasurementService      temperature measurement service.
     * @param openCDXMedicalHistoryService              medical history service.
     * @param openCDXMedicalHistoryRepository           medical history repository.
     */
    public OpenCDXMedicalRecordProcessServiceImpl(
            OpenCDXProfileRepository openCDXProfileRepository,
            OpenCDXIAMProfileService openCDXIAMProfileService,
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
            OpenCDXConnectedTestService openCDXConnectedTestService,
            OpenCDXMedicalConditionsRepository openCDXMedicalConditionsRepository,
            OpenCDXMedicalConditionsService openCDXMedicalConditionsService,
            OpenCDXTemperatureMeasurementRepository openCDXTemperatureMeasurementRepository,
            OpenCDXTemperatureMeasurementService openCDXTemperatureMeasurementService,
            OpenCDXMedicalHistoryService openCDXMedicalHistoryService,
            OpenCDXMedicalHistoryRepository openCDXMedicalHistoryRepository) {
        this.openCDXProfileRepository = openCDXProfileRepository;
        this.openCDXIAMProfileService = openCDXIAMProfileService;
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
        this.openCDXMedicalConditionsRepository = openCDXMedicalConditionsRepository;
        this.openCDXMedicalConditionsService = openCDXMedicalConditionsService;
        this.openCDXTemperatureMeasurementRepository = openCDXTemperatureMeasurementRepository;
        this.openCDXTemperatureMeasurementService = openCDXTemperatureMeasurementService;
        this.openCDXMedicalHistoryService = openCDXMedicalHistoryService;
        this.openCDXMedicalHistoryRepository = openCDXMedicalHistoryRepository;
    }

    @Override
    public void processMedicalRecord(OpenCDXIdentifier medicalRecordId) {
        Optional<OpenCDXMedicalRecordModel> model = openCDXMedicalRecordRepository.findById(medicalRecordId);
        if (model.isEmpty()) {
            log.info("Medical Record not found");
            return;
        }
        OpenCDXMedicalRecordModel medicalRecordModel = model.get();
        if (medicalRecordModel.getStatus() == MedicalRecordStatus.MEDICAL_RECORD_STATUS_EXPORT) {
            log.info("Processing Export");
            processExport(medicalRecordModel);
        } else if (medicalRecordModel.getStatus() == MedicalRecordStatus.MEDICAL_RECORD_STATUS_IMPORT) {
            log.info("Processing Import");
            processImport(medicalRecordModel);
        }
    }

    private void processExport(OpenCDXMedicalRecordModel medicalRecordModel) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        medicalRecordModel.setMedicationList(openCDXMedicationRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setMedicationAdministrationList(openCDXMedicationAdministrationRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setKnownAllergyList(openCDXAllergyRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setDoctorNotesList(openCDXDoctorNotesRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setVaccineList(openCDXVaccineRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setHeightMeasurementList(openCDXHeightMeasurementRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setWeightMeasurementList(openCDXWeightMeasurementRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setBpmList(openCDXBPMRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setHeartRPMList(openCDXHeartRPMRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setConnectedTestList(openCDXConnectedTestRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setMedicalConditions(openCDXMedicalConditionsRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());

        medicalRecordModel.setTemperatureMeasurementList(openCDXTemperatureMeasurementRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());
        medicalRecordModel.setMedicalHistoryList(openCDXMedicalHistoryRepository
                .findAllByPatientId(
                        new OpenCDXIdentifier(
                                medicalRecordModel.getUserProfile().getId()),
                        pageable)
                .toList());

        medicalRecordModel.setStatus(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE);

        openCDXMedicalRecordRepository.save(medicalRecordModel);
    }

    private void processImport(OpenCDXMedicalRecordModel medicalRecordModel) {
        if (openCDXProfileRepository.existsById(
                new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
            openCDXIAMProfileService.updateUserProfile(UpdateUserProfileRequest.newBuilder()
                    .setUpdatedProfile(medicalRecordModel.getUserProfile())
                    .build());
        } else {
            openCDXIAMProfileService.createUserProfile(CreateUserProfileRequest.newBuilder()
                    .setUserProfile(medicalRecordModel.getUserProfile())
                    .build());
        }
        // Medication
        medicalRecordModel.getMedicationList().stream()
                .map(OpenCDXMedicationModel::getProtobufMessage)
                .forEach(openCDXMedicationService::prescribing);
        // Medication Administration
        medicalRecordModel.getMedicationAdministrationList().forEach(medicationAdministration -> {
            if (openCDXMedicationAdministrationRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXMedicationAdministrationService.trackMedicationAdministration(
                        medicationAdministration.getProtobufMessage());
            }
        });
        medicalRecordModel.getKnownAllergyList().forEach(allergy -> {
            if (openCDXAllergyRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXAllergyService.updateAllergy(UpdateAllergyRequest.newBuilder()
                        .setKnownAllergy(allergy.getProtobufMessage())
                        .build());
            } else {
                openCDXAllergyService.createAllergy(CreateAllergyRequest.newBuilder()
                        .setKnownAllergy(allergy.getProtobufMessage())
                        .build());
            }
        });
        medicalRecordModel.getDoctorNotesList().forEach(doctorNotes -> {
            if (openCDXDoctorNotesRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXDoctorNotesService.updateDoctorNotes(UpdateDoctorNotesRequest.newBuilder()
                        .setDoctorNotes(doctorNotes.getProtobufMessage())
                        .build());
            } else {
                openCDXDoctorNotesService.createDoctorNotes(CreateDoctorNotesRequest.newBuilder()
                        .setDoctorNotes(doctorNotes.getProtobufMessage())
                        .build());
            }
        });
        // Vaccine
        medicalRecordModel.getVaccineList().forEach(vaccine -> {
            if (openCDXVaccineRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXVaccineService.updateVaccine(vaccine.getProtobufMessage());
            } else {
                openCDXVaccineService.trackVaccineAdministration(vaccine.getProtobufMessage());
            }
        });
        medicalRecordModel.getHeightMeasurementList().forEach(heightMeasurement -> {
            if (openCDXHeightMeasurementRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXHeightMeasurementService.createHeightMeasurement(CreateHeightMeasurementRequest.newBuilder()
                        .setHeightMeasurement(heightMeasurement.getProtobufMessage())
                        .build());
            } else {
                openCDXHeightMeasurementService.createHeightMeasurement(CreateHeightMeasurementRequest.newBuilder()
                        .setHeightMeasurement(heightMeasurement.getProtobufMessage())
                        .build());
            }
        });
        medicalRecordModel.getWeightMeasurementList().forEach(weightMeasurement -> {
            if (openCDXWeightMeasurementRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXWeightMeasurementService.updateWeightMeasurement(UpdateWeightMeasurementRequest.newBuilder()
                        .setWeightMeasurement(weightMeasurement.getProtobufMessage())
                        .build());
            } else {
                openCDXWeightMeasurementService.createWeightMeasurement(CreateWeightMeasurementRequest.newBuilder()
                        .setWeightMeasurement(weightMeasurement.getProtobufMessage())
                        .build());
            }
        });
        medicalRecordModel.getBpmList().forEach(bpm -> {
            if (openCDXBPMRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXBPMService.updateBPMMeasurement(UpdateBPMRequest.newBuilder()
                        .setBpmMeasurement(bpm.getProtobufMessage())
                        .build());
            } else {
                openCDXBPMService.createBPMMeasurement(CreateBPMRequest.newBuilder()
                        .setBpmMeasurement(bpm.getProtobufMessage())
                        .build());
            }
        });
        medicalRecordModel.getHeartRPMList().forEach(heartRPM -> {
            if (openCDXHeartRPMRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXHeartRPMService.updateHeartRPMMeasurement(UpdateHeartRPMRequest.newBuilder()
                        .setHeartRpmMeasurement(heartRPM.getProtobufMessage())
                        .build());
            } else {
                openCDXHeartRPMService.createHeartRPMMeasurement(CreateHeartRPMRequest.newBuilder()
                        .setHeartRpmMeasurement(heartRPM.getProtobufMessage())
                        .build());
            }
        });
        // ConnectedTest
        medicalRecordModel.getConnectedTestList().stream()
                .map(OpenCDXConnectedTestModel::getProtobufMessage)
                .forEach(openCDXConnectedTestService::submitTest);

        medicalRecordModel.getMedicalConditions().forEach(medicalConditions -> {
            if (openCDXMedicalConditionsRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXMedicalConditionsService.updateDiagnosis(DiagnosisRequest.newBuilder()
                        .setDiagnosis(medicalConditions.getProtobufMessage())
                        .build());
            } else {
                openCDXMedicalConditionsService.createDiagnosis(DiagnosisRequest.newBuilder()
                        .setDiagnosis(medicalConditions.getProtobufMessage())
                        .build());
            }
        });

        medicalRecordModel.getTemperatureMeasurementList().forEach(temperatureMeasurement -> {
            if (openCDXTemperatureMeasurementRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXTemperatureMeasurementService.updateTemperatureMeasurement(
                        UpdateTemperatureMeasurementRequest.newBuilder()
                                .setTemperatureMeasurement(temperatureMeasurement.getProtobufMessage())
                                .build());
            } else {
                openCDXTemperatureMeasurementService.createTemperatureMeasurement(
                        CreateTemperatureMeasurementRequest.newBuilder()
                                .setTemperatureMeasurement(temperatureMeasurement.getProtobufMessage())
                                .build());
            }
        });

        medicalRecordModel.getMedicalHistoryList().forEach(medicalHistory -> {
            if (openCDXMedicalHistoryRepository.existsById(
                    new OpenCDXIdentifier(medicalRecordModel.getUserProfile().getId()))) {
                openCDXMedicalHistoryService.updateMedicalHistory(UpdateMedicalHistoryRequest.newBuilder()
                        .setMedicalHistory(medicalHistory.getProtobufMessage())
                        .build());
            } else {
                openCDXMedicalHistoryService.createMedicalHistory(CreateMedicalHistoryRequest.newBuilder()
                        .setMedicalHistory(medicalHistory.getProtobufMessage())
                        .build());
            }
        });
        medicalRecordModel.setStatus(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE);

        openCDXMedicalRecordRepository.save(medicalRecordModel);
    }
}
