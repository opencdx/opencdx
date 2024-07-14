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
package cdx.opencdx.health.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXConnectedTestModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.model.OpenCDXUserQuestionnaireModel;
import cdx.opencdx.grpc.data.MedicalRecord;
import cdx.opencdx.grpc.data.UserProfile;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Medical Record in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("medical-record")
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXMedicalRecordModel {
    @Id
    private OpenCDXIdentifier id;

    private MedicalRecordStatus status;
    private UserProfile userProfile;
    List<OpenCDXMedicationModel> medicationList;
    List<OpenCDXMedicationAdministrationModel> medicationAdministrationList;
    List<OpenCDXAllergyModel> knownAllergyList;
    List<OpenCDXDoctorNotesModel> doctorNotesList;
    List<OpenCDXVaccineModel> vaccineList;
    List<OpenCDXHeightMeasurementModel> heightMeasurementList;
    List<OpenCDXWeightMeasurementModel> weightMeasurementList;
    List<OpenCDXBPMModel> bpmList;
    List<OpenCDXHeartRPMModel> heartRPMList;
    List<OpenCDXUserQuestionnaireModel> userQuestionnaireDataList;
    List<OpenCDXConnectedTestModel> connectedTestList;
    List<OpenCDXMedicalConditionsModel> medicalConditions;
    List<OpenCDXMedicalHistoryModel> medicalHistoryList;
    List<OpenCDXTemperatureMeasurementModel> temperatureMeasurementList;
    private Instant created;
    private Instant modified;
    private String creator;
    private String modifier;

    /**
     * Constructor from protobuf message medicalRecord
     * @param medicalRecord Protobuf message to generate from
     */
    public OpenCDXMedicalRecordModel(MedicalRecord medicalRecord) {
        this.id = new OpenCDXIdentifier(medicalRecord.getId());
        this.status = medicalRecord.getStatus();
        this.userProfile = medicalRecord.getUserProfile();
        this.medicationList = medicalRecord.getMedicationList().stream()
                .map(OpenCDXMedicationModel::new)
                .toList();
        this.medicationAdministrationList = medicalRecord.getMedicationAdministrationList().stream()
                .map(OpenCDXMedicationAdministrationModel::new)
                .toList();
        this.knownAllergyList = medicalRecord.getKnownAllergyList().stream()
                .map(OpenCDXAllergyModel::new)
                .toList();
        this.doctorNotesList = medicalRecord.getDoctorNotesList().stream()
                .map(OpenCDXDoctorNotesModel::new)
                .toList();
        this.vaccineList = medicalRecord.getVaccineList().stream()
                .map(OpenCDXVaccineModel::new)
                .toList();
        this.heightMeasurementList = medicalRecord.getHeightMeasurementList().stream()
                .map(OpenCDXHeightMeasurementModel::new)
                .toList();
        this.weightMeasurementList = medicalRecord.getWeightMeasurementList().stream()
                .map(OpenCDXWeightMeasurementModel::new)
                .toList();
        this.bpmList =
                medicalRecord.getBpmList().stream().map(OpenCDXBPMModel::new).toList();
        this.heartRPMList = medicalRecord.getHeartRPMList().stream()
                .map(OpenCDXHeartRPMModel::new)
                .toList();
        this.userQuestionnaireDataList = medicalRecord.getUserQuestionnaireDataList().stream()
                .map(OpenCDXUserQuestionnaireModel::new)
                .toList();
        this.connectedTestList = medicalRecord.getConnectedTestList().stream()
                .map(OpenCDXConnectedTestModel::new)
                .toList();
        this.medicalConditions = medicalRecord.getMedicalConditionsList().stream()
                .map(OpenCDXMedicalConditionsModel::new)
                .toList();
        this.temperatureMeasurementList = medicalRecord.getTemperatureMeasurementList().stream()
                .map(OpenCDXTemperatureMeasurementModel::new)
                .toList();
        this.medicalHistoryList = medicalRecord.getMedicalHistoryList().stream()
                .map(OpenCDXMedicalHistoryModel::new)
                .toList();

        if (medicalRecord.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    medicalRecord.getCreated().getSeconds(),
                    medicalRecord.getCreated().getNanos());
        }
        if (medicalRecord.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    medicalRecord.getModified().getSeconds(),
                    medicalRecord.getModified().getNanos());
        }
        if (medicalRecord.hasCreator()) {
            this.creator = medicalRecord.getCreator();
        }
        if (medicalRecord.hasModifier()) {
            this.modifier = medicalRecord.getModifier();
        }
    }

    /**
     * Constructor from protobuf message medicalRecord
     * @param profileModel Protobuf message to generate from
     */
    public OpenCDXMedicalRecordModel(OpenCDXProfileModel profileModel) {
        this.id = new OpenCDXIdentifier();
        this.userProfile = profileModel.getUserProfileProtobufMessage();
        this.status = MedicalRecordStatus.MEDICAL_RECORD_STATUS_EXPORT;
    }

    /**
     * Method to get the protobuf Known Allergy object
     * @return protobuf bpm object
     */
    public MedicalRecord getProtobufMessage() {
        MedicalRecord.Builder builder = MedicalRecord.newBuilder();
        builder.setId(this.id.toHexString());
        builder.setStatus(this.status);
        builder.setUserProfile(this.userProfile);
        if (this.medicationList != null) {
            builder.addAllMedication(this.medicationList.stream()
                    .map(OpenCDXMedicationModel::getProtobufMessage)
                    .toList());
        }
        if (this.medicationAdministrationList != null) {
            builder.addAllMedicationAdministration(this.medicationAdministrationList.stream()
                    .map(OpenCDXMedicationAdministrationModel::getProtobufMessage)
                    .toList());
        }
        if (this.knownAllergyList != null) {
            builder.addAllKnownAllergy(this.knownAllergyList.stream()
                    .map(OpenCDXAllergyModel::getProtobufMessage)
                    .toList());
        }
        if (this.doctorNotesList != null) {
            builder.addAllDoctorNotes(this.doctorNotesList.stream()
                    .map(OpenCDXDoctorNotesModel::getProtobufMessage)
                    .toList());
        }
        if (this.vaccineList != null) {
            builder.addAllVaccine(this.vaccineList.stream()
                    .map(OpenCDXVaccineModel::getProtobufMessage)
                    .toList());
        }
        if (this.heightMeasurementList != null) {
            builder.addAllHeightMeasurement(this.heightMeasurementList.stream()
                    .map(OpenCDXHeightMeasurementModel::getProtobufMessage)
                    .toList());
        }
        if (this.weightMeasurementList != null) {
            builder.addAllWeightMeasurement(this.weightMeasurementList.stream()
                    .map(OpenCDXWeightMeasurementModel::getProtobufMessage)
                    .toList());
        }
        if (this.bpmList != null) {
            builder.addAllBpm(this.bpmList.stream()
                    .map(OpenCDXBPMModel::getProtobufMessage)
                    .toList());
        }
        if (this.heartRPMList != null) {
            builder.addAllHeartRPM(this.heartRPMList.stream()
                    .map(OpenCDXHeartRPMModel::getProtobufMessage)
                    .toList());
        }
        if (this.userQuestionnaireDataList != null) {
            builder.addAllUserQuestionnaireData(this.userQuestionnaireDataList.stream()
                    .map(OpenCDXUserQuestionnaireModel::getProtobufMessage)
                    .toList());
        }
        if (this.connectedTestList != null) {
            builder.addAllConnectedTest(this.connectedTestList.stream()
                    .map(OpenCDXConnectedTestModel::getProtobufMessage)
                    .toList());
        }
        if (this.medicalConditions != null) {
            builder.addAllMedicalConditions(this.medicalConditions.stream()
                    .map(OpenCDXMedicalConditionsModel::getProtobufMessage)
                    .toList());
        }

        if (this.temperatureMeasurementList != null) {
            builder.addAllTemperatureMeasurement(this.temperatureMeasurementList.stream()
                    .map(OpenCDXTemperatureMeasurementModel::getProtobufMessage)
                    .toList());
        }
        if (this.medicalHistoryList != null) {
            builder.addAllMedicalHistory(this.medicalHistoryList.stream()
                    .map(OpenCDXMedicalHistoryModel::getProtobufMessage)
                    .toList());
        }
        if (this.created != null) {
            builder.setCreated(Timestamp.newBuilder()
                    .setSeconds(this.created.getEpochSecond())
                    .setNanos(this.created.getNano())
                    .build());
        }
        if (this.modified != null) {
            builder.setModified(Timestamp.newBuilder()
                    .setSeconds(this.modified.getEpochSecond())
                    .setNanos(this.modified.getNano())
                    .build());
        }
        if (this.creator != null) {
            builder.setCreator(this.creator);
        }
        if (this.modifier != null) {
            builder.setModifier(this.modifier);
        }
        return builder.build();
    }
}
