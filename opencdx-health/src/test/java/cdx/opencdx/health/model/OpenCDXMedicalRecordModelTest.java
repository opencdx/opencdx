package cdx.opencdx.health.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OpenCDXMedicalRecordModelTest {

    @Test
    void getProtobufMessage() {
        MedicalRecord medicalRecord = MedicalRecord.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setStatus(MedicalRecordStatus.MEDICAL_RECORD_STATUS_EXPORT)
                .setUserProfile(UserProfile.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .addAllClassification(List.of(Classification.newBuilder()
                        .setPatientId(
                                OpenCDXIdentifier.get().toHexString())
                        .build()))
                .addAllMedication(
                        List.of(Medication.newBuilder().build()))
                .addAllMedicationAdministration(List.of(MedicationAdministration.newBuilder()
                        .build()))
                .addAllKnownAllergy(List.of(
                        KnownAllergy.newBuilder().build()))
                .addAllDoctorNotes(
                        List.of(DoctorNotes.newBuilder().build()))
                .addAllVaccine(
                        List.of(Vaccine.newBuilder().build()))
                .addAllHeightMeasurement(List.of(
                        HeightMeasurement.newBuilder().build()))
                .addAllWeightMeasurement(List.of(
                        WeightMeasurement.newBuilder().build()))
                .addAllBpm(List.of(BPM.newBuilder().build()))
                .addAllHeartRPM(
                        List.of(HeartRPM.newBuilder().build()))
                .addAllUserQuestionnaireData(List.of(UserQuestionnaireData.newBuilder()
                        .build()))
                .addAllConnectedTest(List.of(
                        ConnectedTest.newBuilder().build()))
                .setCreated(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModified(Timestamp.getDefaultInstance())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();
        OpenCDXMedicalRecordModel model = new OpenCDXMedicalRecordModel(medicalRecord);
        assertNotNull(model.getProtobufMessage());
    }

    @Test
    void getProtobufMessageElse() {
        MedicalRecord medicalRecord = MedicalRecord.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setStatus(MedicalRecordStatus.MEDICAL_RECORD_STATUS_EXPORT)
                .setUserProfile(UserProfile.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .addAllClassification(List.of(Classification.newBuilder()
                        .setPatientId(
                                OpenCDXIdentifier.get().toHexString())
                        .build()))
                .addAllMedication(
                        List.of(Medication.newBuilder().build()))
                .addAllMedicationAdministration(List.of(MedicationAdministration.newBuilder()
                        .build()))
                .addAllKnownAllergy(List.of(
                        KnownAllergy.newBuilder().build()))
                .addAllDoctorNotes(
                        List.of(DoctorNotes.newBuilder().build()))
                .addAllVaccine(
                        List.of(Vaccine.newBuilder().build()))
                .addAllHeightMeasurement(List.of(
                        HeightMeasurement.newBuilder().build()))
                .addAllWeightMeasurement(List.of(
                        WeightMeasurement.newBuilder().build()))
                .addAllBpm(List.of(BPM.newBuilder().build()))
                .addAllHeartRPM(
                        List.of(HeartRPM.newBuilder().build()))
                .addAllUserQuestionnaireData(List.of(UserQuestionnaireData.newBuilder()
                        .build()))
                .addAllConnectedTest(List.of(
                        ConnectedTest.newBuilder().build()))
                .build();
        OpenCDXMedicalRecordModel model = new OpenCDXMedicalRecordModel(medicalRecord);
        assertNotNull(model.getProtobufMessage());
    }
}