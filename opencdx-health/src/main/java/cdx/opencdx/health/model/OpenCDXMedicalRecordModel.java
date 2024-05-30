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
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import com.google.protobuf.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

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
    List<Classification> classificationsList;
    List<Medication> medicationList;
    List<MedicationAdministration> medicationAdministrationList;
    List<KnownAllergy> knownAllergyList;
    List<DoctorNotes> doctorNotesList;
    List<Vaccine> vaccineList;
    List<HeightMeasurement> heightMeasurementList;
    List<WeightMeasurement> weightMeasurementList;
    List<BPM> bpmList;
    List<HeartRPM> heartRPMList;
    List<UserQuestionnaireData> userQuestionnaireDataList;
    List<UserAnswer> userAnswerList;
    List<ConnectedTest> connectedTestList;
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
        this.classificationsList = medicalRecord.getClassificationList();
        this.medicationList = medicalRecord.getMedicationList();
        this.medicationAdministrationList = medicalRecord.getMedicationAdministrationList();
        this.knownAllergyList = medicalRecord.getKnownAllergyList();
        this.doctorNotesList = medicalRecord.getDoctorNotesList();
        this.vaccineList = medicalRecord.getVaccineList();
        this.heightMeasurementList = medicalRecord.getHeightMeasurementList();
        this.weightMeasurementList = medicalRecord.getWeightMeasurementList();
        this.bpmList = medicalRecord.getBpmList();
        this.heartRPMList = medicalRecord.getHeartRPMList();
        this.userQuestionnaireDataList = medicalRecord.getUserQuestionnaireDataList();
        this.connectedTestList = medicalRecord.getConnectedTestList();

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
        builder.addAllClassification(this.classificationsList);
        builder.addAllMedication(this.medicationList);
        builder.addAllMedicationAdministration(this.medicationAdministrationList);
        builder.addAllKnownAllergy(this.knownAllergyList);
        builder.addAllDoctorNotes(this.doctorNotesList);
        builder.addAllVaccine(this.vaccineList);
        builder.addAllHeightMeasurement(this.heightMeasurementList);
        builder.addAllWeightMeasurement(this.weightMeasurementList);
        builder.addAllBpm(this.bpmList);
        builder.addAllHeartRPM(this.heartRPMList);
        builder.addAllUserQuestionnaireData(this.userQuestionnaireDataList);
        builder.addAllConnectedTest(this.connectedTestList);
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
