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
package cdx.opencdx.health.controller;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXConnectedTestModel;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.model.OpenCDXUserQuestionnaireModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXMedicalRecordMessageService;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.service.questionnaire.GetQuestionnaireRequest;
import cdx.opencdx.grpc.types.*;
import cdx.opencdx.health.model.*;
import cdx.opencdx.health.repository.OpenCDXMedicalRecordRepository;
import cdx.opencdx.health.service.OpenCDXMedicalRecordService;
import cdx.opencdx.health.service.impl.OpenCDXMedicalRecordServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXMedicalRecordGrpcControllerTest {
    OpenCDXMedicalRecordGrpcController openCDXMedicalRecordGrpcController;
    OpenCDXMedicalRecordService openCDXMedicalRecordService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Autowired
    OpenCDXMedicalRecordMessageService openCDXMedicalRecordMessageService;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXMedicalRecordRepository openCDXMedicalRecordRepository;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @MockBean
    OpenCDXQuestionnaireClient openCDXQuestionnaireClient;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicalRecordRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalRecordModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalRecordModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalRecordModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE)
                                .userProfile(UserProfile.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .build())
                                .medicationList(List.of(new OpenCDXMedicationModel(Medication.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setMedicationName("medication")
                                        .setStartDate(Timestamp.newBuilder().setSeconds(1696733104))
                                        .build())))
                                .medicationAdministrationList(List.of(
                                        new OpenCDXMedicationAdministrationModel(MedicationAdministration.newBuilder()
                                                .setId(ObjectId.get().toHexString())
                                                .setPatientId(ObjectId.get().toHexString())
                                                .setMedicationId(ObjectId.get().toHexString())
                                                .setNationalHealthId(
                                                        ObjectId.get().toHexString())
                                                .setAdministratedBy("Doctor")
                                                .setAdministrationNotes("notes")
                                                .build())))
                                .knownAllergyList(List.of(new OpenCDXAllergyModel(KnownAllergy.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setAllergen("allergen")
                                        .setReaction("reaction")
                                        .setIsSevere(true)
                                        .setOnsetDate(Timestamp.getDefaultInstance())
                                        .setLastOccurrence(Timestamp.getDefaultInstance())
                                        .setNotes("notes")
                                        .build())))
                                .doctorNotesList(List.of(new OpenCDXDoctorNotesModel(DoctorNotes.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setProviderNumber("Provider Number")
                                        .addAllTags(List.of("tag1", "tag2"))
                                        .setNoteDatetime(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setNotes("Notes")
                                        .setCreated(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setModified(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setCreator("Creator")
                                        .setModifier("Modifier")
                                        .build())))
                                .vaccineList(List.of(new OpenCDXVaccineModel(Vaccine.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setAdministrationDate(Timestamp.getDefaultInstance())
                                        .setFips("fips")
                                        .setLocation(Address.newBuilder()
                                                .setCountryId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .build())
                                        .setHealthDistrict("district")
                                        .setFacilityType("facilityType")
                                        .setVaccine(Medication.newBuilder().getDefaultInstanceForType())
                                        .setDoseNumber(2)
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .heightMeasurementList(
                                        List.of(new OpenCDXHeightMeasurementModel(HeightMeasurement.newBuilder()
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setNationalHealthId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setHeight(10.0)
                                                .setUnitsOfMeasure(HeightUnits.CM)
                                                .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setModified(Timestamp.getDefaultInstance())
                                                .setCreator(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setModifier(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .build())))
                                .weightMeasurementList(
                                        List.of(new OpenCDXWeightMeasurementModel(WeightMeasurement.newBuilder()
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setNationalHealthId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setWeight(40.0)
                                                .setUnitsOfMeasure(WeightUnits.KGS)
                                                .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setModified(Timestamp.getDefaultInstance())
                                                .setCreator(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setModifier(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .build())))
                                .bpmList(List.of(new OpenCDXBPMModel(BPM.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setCuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                                        .setArmUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                                        .setSystolic(80)
                                        .setDiastolic(120)
                                        .setUnit(BPMUnits.BARS)
                                        .setSittingPosition5Minutes(true)
                                        .setUrinated30MinutesPrior(false)
                                        .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .heartRPMList(List.of(new OpenCDXHeartRPMModel(HeartRPM.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setMeasurementTakenOverInSeconds(60)
                                        .setSittingPosition5Minutes(true)
                                        .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .userQuestionnaireDataList(
                                        List.of(new OpenCDXUserQuestionnaireModel(UserQuestionnaireData.newBuilder()
                                                .setId("65bb9634ab091e2343ff7ef7")
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .addAllQuestionnaireData(List.of(Questionnaire.getDefaultInstance()))
                                                .build())))
                                .connectedTestList(List.of(new OpenCDXConnectedTestModel(ConnectedTest.newBuilder()
                                        .setBasicInfo(BasicInfo.newBuilder()
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .setCreated(
                                                        Timestamp.newBuilder().setSeconds(1696432104))
                                                .setCreator("creator")
                                                .setModified(
                                                        Timestamp.newBuilder().setSeconds(1696435104))
                                                .setModifier("modifier")
                                                .setVendorLabTestId("vendorLabTestId")
                                                .setType("type")
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setNationalHealthId(
                                                        UUID.randomUUID().toString())
                                                .setHealthServiceId("hea;thServiceId")
                                                .setWorkspaceId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setOrganizationId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setSource("source")
                                                .build())
                                        .setOrderInfo(OrderInfo.newBuilder()
                                                .setOrderId("orderId")
                                                .setStatus("statusId")
                                                .setStatusMessage("statusMessage")
                                                .addAllStatusMessageActions(List.of(StatusMessageAction.newBuilder()
                                                        .setId("id")
                                                        .setDescription("description")
                                                        .setValue("value")
                                                        .buildPartial()))
                                                .setEncounterId("encounterId")
                                                .setIsSyncedWithEhr(true)
                                                .setResult("result")
                                                .setQuestionnaireId("questionnaireId")
                                                .build())
                                        .setTestNotes(TestNotes.newBuilder()
                                                .setNotes("notes")
                                                .setMedicationNotes("medicationNotes")
                                                .setReferralNotes("referralNotes")
                                                .setDiagnosticNotes("diagnosticNotes")
                                                .buildPartial())
                                        .setPaymentDetails(PaymentDetails.newBuilder()
                                                .setPaymentMode("paymentMode")
                                                .setInsuranceInfoId("insuranceInfoId")
                                                .setPaymentTransactionId("paymentTransactionId")
                                                .setPaymentDetails("paymentDetails")
                                                .build())
                                        .setProviderInfo(ProviderInfo.newBuilder()
                                                .setOrderingProviderId("orderingProviderId")
                                                .setAssignedProviderId(1)
                                                .setOrderingProviderNpi(1)
                                                .build())
                                        .setTestDetails(TestDetails.newBuilder()
                                                .setMetadata(Metadata.newBuilder()
                                                        .setQrData("qrdata")
                                                        .setKitUploadId("kitUploadID")
                                                        .setResponseMessage("responseMessage")
                                                        .setResponseTitle("responseTitle")
                                                        .setResponseCode(200)
                                                        .setImageType("imageType")
                                                        .setType("type")
                                                        .setManufacturer("manufacturer")
                                                        .setCassetteLotNumber("cassetteLotNumber")
                                                        .setOutcomeIgm(true)
                                                        .setOutcomeIgg(true)
                                                        .setOutcome("outcome")
                                                        .setSelfAssessmentOutcomeIgm(true)
                                                        .setSelfAssessmentOutcomeIgg(true)
                                                        .setSelfAssessmentOutcome("selfAssessmentOutcome")
                                                        .setCassetteExpirationDate("expDate")
                                                        .setLabTestOrderableId("labTestOrderableId")
                                                        .setSkuId("skuID")
                                                        .setCassetteVerification("verification")
                                                        .build())
                                                .setRequisitionBase64("requisitionBase64")
                                                .setInternalTestId("internalTestId")
                                                .setMedications("medications")
                                                .setReferrals("referrals")
                                                .setDiagnostics("diagnostics")
                                                .addAllOrderableTestIds(List.of("orderableTestIds"))
                                                .addAllOrderableTests(List.of(OrderableTest.newBuilder()
                                                        .setOrderableTestId("orderableTestId")
                                                        .build()))
                                                .addAllOrderableTestResults(List.of(OrderableTestResult.newBuilder()
                                                        .setOrderableTestId("orderableTestId")
                                                        .setCollectionDate("collectionDate")
                                                        .setTestResult("testResult")
                                                        .setOutcome("outcome")
                                                        .setResponseMessage("responseMessage")
                                                        .setResponseTitle("responseTitle")
                                                        .setResponseCode(200)
                                                        .build()))
                                                .setTestClassification(TestClassification.TEST_CLASSIFICATION_GENERAL)
                                                .setIsOnsiteTest(true)
                                                .setSpecimenId("specimenId")
                                                .setLabVendorConfirmationId("labVendorConfirmationId")
                                                .setDeviceIdentifier("2")
                                                .setDeviceSerialNumber("deviceSerialNumber")
                                                .setIsAutoGenerated(true)
                                                .setReportingFlag("reportingFlag")
                                                .setNotificationFlag("notificationFlag")
                                                .setOrderStatusFlag("orderStatusFlag")
                                                .setOrderReceiptPath("orderReceiptPath")
                                                .setLabTestType("labTestType")
                                                .setSpecimenType("specimenType")
                                                .setMedicalCode("medicalCode")
                                                .build())
                                        .build())))
                                .build());
                    }
                });

        Mockito.when(this.openCDXQuestionnaireClient.getUserQuestionnaireData(
                        Mockito.any(GetQuestionnaireRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenAnswer(new Answer<UserQuestionnaireData>() {
                    @Override
                    public UserQuestionnaireData answer(InvocationOnMock invocation) throws Throwable {
                        GetQuestionnaireRequest argument = invocation.getArgument(0);
                        return UserQuestionnaireData.newBuilder()
                                .setId(argument.getId())
                                .build();
                    }
                });

        Mockito.when(this.openCDXMedicalRecordRepository.save(Mockito.any(OpenCDXMedicalRecordModel.class)))
                .thenAnswer(new Answer<OpenCDXMedicalRecordModel>() {
                    @Override
                    public OpenCDXMedicalRecordModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMedicalRecordModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return OpenCDXMedicalRecordModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE)
                                .userProfile(UserProfile.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .build())
                                .medicationList(List.of(new OpenCDXMedicationModel(Medication.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setMedicationName("medication")
                                        .setStartDate(Timestamp.newBuilder().setSeconds(1696733104))
                                        .build())))
                                .medicationAdministrationList(List.of(
                                        new OpenCDXMedicationAdministrationModel(MedicationAdministration.newBuilder()
                                                .setId(ObjectId.get().toHexString())
                                                .setPatientId(ObjectId.get().toHexString())
                                                .setMedicationId(ObjectId.get().toHexString())
                                                .setNationalHealthId(
                                                        ObjectId.get().toHexString())
                                                .setAdministratedBy("Doctor")
                                                .setAdministrationNotes("notes")
                                                .build())))
                                .knownAllergyList(List.of(new OpenCDXAllergyModel(KnownAllergy.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setAllergen("allergen")
                                        .setReaction("reaction")
                                        .setIsSevere(true)
                                        .setOnsetDate(Timestamp.getDefaultInstance())
                                        .setLastOccurrence(Timestamp.getDefaultInstance())
                                        .setNotes("notes")
                                        .build())))
                                .doctorNotesList(List.of(new OpenCDXDoctorNotesModel(DoctorNotes.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setProviderNumber("Provider Number")
                                        .addAllTags(List.of("tag1", "tag2"))
                                        .setNoteDatetime(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setNotes("Notes")
                                        .setCreated(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setModified(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setCreator("Creator")
                                        .setModifier("Modifier")
                                        .build())))
                                .vaccineList(List.of(new OpenCDXVaccineModel(Vaccine.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setAdministrationDate(Timestamp.getDefaultInstance())
                                        .setFips("fips")
                                        .setLocation(Address.newBuilder()
                                                .setCountryId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .build())
                                        .setHealthDistrict("district")
                                        .setFacilityType("facilityType")
                                        .setVaccine(Medication.newBuilder().getDefaultInstanceForType())
                                        .setDoseNumber(2)
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .heightMeasurementList(
                                        List.of(new OpenCDXHeightMeasurementModel(HeightMeasurement.newBuilder()
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setNationalHealthId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setHeight(10.0)
                                                .setUnitsOfMeasure(HeightUnits.CM)
                                                .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setModified(Timestamp.getDefaultInstance())
                                                .setCreator(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setModifier(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .build())))
                                .weightMeasurementList(
                                        List.of(new OpenCDXWeightMeasurementModel(WeightMeasurement.newBuilder()
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setNationalHealthId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setWeight(40.0)
                                                .setUnitsOfMeasure(WeightUnits.KGS)
                                                .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setModified(Timestamp.getDefaultInstance())
                                                .setCreator(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setModifier(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .build())))
                                .bpmList(List.of(new OpenCDXBPMModel(BPM.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setCuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                                        .setArmUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                                        .setSystolic(80)
                                        .setDiastolic(120)
                                        .setUnit(BPMUnits.BARS)
                                        .setSittingPosition5Minutes(true)
                                        .setUrinated30MinutesPrior(false)
                                        .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .heartRPMList(List.of(new OpenCDXHeartRPMModel(HeartRPM.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setMeasurementTakenOverInSeconds(60)
                                        .setSittingPosition5Minutes(true)
                                        .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .userQuestionnaireDataList(
                                        List.of(new OpenCDXUserQuestionnaireModel(UserQuestionnaireData.newBuilder()
                                                .setId("65bb9634ab091e2343ff7ef7")
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .addAllQuestionnaireData(List.of(Questionnaire.getDefaultInstance()))
                                                .build())))
                                .connectedTestList(List.of(new OpenCDXConnectedTestModel(ConnectedTest.newBuilder()
                                        .setBasicInfo(BasicInfo.newBuilder()
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .setCreated(
                                                        Timestamp.newBuilder().setSeconds(1696432104))
                                                .setCreator("creator")
                                                .setModified(
                                                        Timestamp.newBuilder().setSeconds(1696435104))
                                                .setModifier("modifier")
                                                .setVendorLabTestId("vendorLabTestId")
                                                .setType("type")
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setNationalHealthId(
                                                        UUID.randomUUID().toString())
                                                .setHealthServiceId("hea;thServiceId")
                                                .setWorkspaceId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setOrganizationId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setSource("source")
                                                .build())
                                        .setOrderInfo(OrderInfo.newBuilder()
                                                .setOrderId("orderId")
                                                .setStatus("statusId")
                                                .setStatusMessage("statusMessage")
                                                .addAllStatusMessageActions(List.of(StatusMessageAction.newBuilder()
                                                        .setId("id")
                                                        .setDescription("description")
                                                        .setValue("value")
                                                        .buildPartial()))
                                                .setEncounterId("encounterId")
                                                .setIsSyncedWithEhr(true)
                                                .setResult("result")
                                                .setQuestionnaireId("questionnaireId")
                                                .build())
                                        .setTestNotes(TestNotes.newBuilder()
                                                .setNotes("notes")
                                                .setMedicationNotes("medicationNotes")
                                                .setReferralNotes("referralNotes")
                                                .setDiagnosticNotes("diagnosticNotes")
                                                .buildPartial())
                                        .setPaymentDetails(PaymentDetails.newBuilder()
                                                .setPaymentMode("paymentMode")
                                                .setInsuranceInfoId("insuranceInfoId")
                                                .setPaymentTransactionId("paymentTransactionId")
                                                .setPaymentDetails("paymentDetails")
                                                .build())
                                        .setProviderInfo(ProviderInfo.newBuilder()
                                                .setOrderingProviderId("orderingProviderId")
                                                .setAssignedProviderId(1)
                                                .setOrderingProviderNpi(1)
                                                .build())
                                        .setTestDetails(TestDetails.newBuilder()
                                                .setMetadata(Metadata.newBuilder()
                                                        .setQrData("qrdata")
                                                        .setKitUploadId("kitUploadID")
                                                        .setResponseMessage("responseMessage")
                                                        .setResponseTitle("responseTitle")
                                                        .setResponseCode(200)
                                                        .setImageType("imageType")
                                                        .setType("type")
                                                        .setManufacturer("manufacturer")
                                                        .setCassetteLotNumber("cassetteLotNumber")
                                                        .setOutcomeIgm(true)
                                                        .setOutcomeIgg(true)
                                                        .setOutcome("outcome")
                                                        .setSelfAssessmentOutcomeIgm(true)
                                                        .setSelfAssessmentOutcomeIgg(true)
                                                        .setSelfAssessmentOutcome("selfAssessmentOutcome")
                                                        .setCassetteExpirationDate("expDate")
                                                        .setLabTestOrderableId("labTestOrderableId")
                                                        .setSkuId("skuID")
                                                        .setCassetteVerification("verification")
                                                        .build())
                                                .setRequisitionBase64("requisitionBase64")
                                                .setInternalTestId("internalTestId")
                                                .setMedications("medications")
                                                .setReferrals("referrals")
                                                .setDiagnostics("diagnostics")
                                                .addAllOrderableTestIds(List.of("orderableTestIds"))
                                                .addAllOrderableTests(List.of(OrderableTest.newBuilder()
                                                        .setOrderableTestId("orderableTestId")
                                                        .build()))
                                                .addAllOrderableTestResults(List.of(OrderableTestResult.newBuilder()
                                                        .setOrderableTestId("orderableTestId")
                                                        .setCollectionDate("collectionDate")
                                                        .setTestResult("testResult")
                                                        .setOutcome("outcome")
                                                        .setResponseMessage("responseMessage")
                                                        .setResponseTitle("responseTitle")
                                                        .setResponseCode(200)
                                                        .build()))
                                                .setTestClassification(TestClassification.TEST_CLASSIFICATION_GENERAL)
                                                .setIsOnsiteTest(true)
                                                .setSpecimenId("specimenId")
                                                .setLabVendorConfirmationId("labVendorConfirmationId")
                                                .setDeviceIdentifier("2")
                                                .setDeviceSerialNumber("deviceSerialNumber")
                                                .setIsAutoGenerated(true)
                                                .setReportingFlag("reportingFlag")
                                                .setNotificationFlag("notificationFlag")
                                                .setOrderStatusFlag("orderStatusFlag")
                                                .setOrderReceiptPath("orderReceiptPath")
                                                .setLabTestType("labTestType")
                                                .setSpecimenType("specimenType")
                                                .setMedicalCode("medicalCode")
                                                .build())
                                        .build())))
                                .build();
                    }
                });

        // this.objectMapper = Mockito.mock(ObjectMapper.class);
        // Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);
        this.openCDXMedicalRecordGrpcController =
                new OpenCDXMedicalRecordGrpcController(this.openCDXMedicalRecordService);
    }

    @AfterEach
    void tearDown() {
        // Mockito.reset(this.objectMapper);
        Mockito.reset(this.openCDXMedicalRecordRepository);
        Mockito.reset(this.openCDXProfileRepository);
    }

    @Test
    void requestMedicalRecord() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        StreamObserver<GetMedicalRecordResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXMedicalRecordGrpcController.requestMedicalRecord(
                GetMedicalRecordRequest.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetMedicalRecordResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getMedicalRecordStatus() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXMedicalRecordGrpcController.getMedicalRecordStatus(
                MedicalRecordByIdRequest.newBuilder()
                        .setMedicalRecordId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuccessResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getMedicalRecordById() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        StreamObserver<MedicalRecordByIdResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXMedicalRecordGrpcController.getMedicalRecordById(
                MedicalRecordByIdRequest.newBuilder()
                        .setMedicalRecordId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(MedicalRecordByIdResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void createMedicalRecord() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        StreamObserver<CreateMedicalRecordResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXMedicalRecordGrpcController.createMedicalRecord(
                CreateMedicalRecordRequest.newBuilder()
                        .setMedicalRecord(OpenCDXMedicalRecordModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_EXPORT)
                                .userProfile(UserProfile.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .build())
                                .medicationList(List.of(new OpenCDXMedicationModel(Medication.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setMedicationName("medication")
                                        .setStartDate(Timestamp.newBuilder().setSeconds(1696733104))
                                        .build())))
                                .medicationAdministrationList(List.of(
                                        new OpenCDXMedicationAdministrationModel(MedicationAdministration.newBuilder()
                                                .setId(ObjectId.get().toHexString())
                                                .setPatientId(ObjectId.get().toHexString())
                                                .setMedicationId(ObjectId.get().toHexString())
                                                .setNationalHealthId(
                                                        ObjectId.get().toHexString())
                                                .setAdministratedBy("Doctor")
                                                .setAdministrationNotes("notes")
                                                .build())))
                                .knownAllergyList(List.of(new OpenCDXAllergyModel(KnownAllergy.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setAllergen("allergen")
                                        .setReaction("reaction")
                                        .setIsSevere(true)
                                        .setOnsetDate(Timestamp.getDefaultInstance())
                                        .setLastOccurrence(Timestamp.getDefaultInstance())
                                        .setNotes("notes")
                                        .build())))
                                .doctorNotesList(List.of(new OpenCDXDoctorNotesModel(DoctorNotes.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setProviderNumber("Provider Number")
                                        .addAllTags(List.of("tag1", "tag2"))
                                        .setNoteDatetime(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setNotes("Notes")
                                        .setCreated(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setModified(Timestamp.newBuilder()
                                                .setSeconds(1696733104)
                                                .build())
                                        .setCreator("Creator")
                                        .setModifier("Modifier")
                                        .build())))
                                .vaccineList(List.of(new OpenCDXVaccineModel(Vaccine.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setAdministrationDate(Timestamp.getDefaultInstance())
                                        .setFips("fips")
                                        .setLocation(Address.newBuilder()
                                                .setCountryId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .build())
                                        .setHealthDistrict("district")
                                        .setFacilityType("facilityType")
                                        .setVaccine(Medication.newBuilder().getDefaultInstanceForType())
                                        .setDoseNumber(2)
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .heightMeasurementList(
                                        List.of(new OpenCDXHeightMeasurementModel(HeightMeasurement.newBuilder()
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setNationalHealthId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setHeight(10.0)
                                                .setUnitsOfMeasure(HeightUnits.CM)
                                                .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setModified(Timestamp.getDefaultInstance())
                                                .setCreator(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setModifier(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .build())))
                                .weightMeasurementList(
                                        List.of(new OpenCDXWeightMeasurementModel(WeightMeasurement.newBuilder()
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setNationalHealthId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setWeight(40.0)
                                                .setUnitsOfMeasure(WeightUnits.KGS)
                                                .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                                .setCreated(Timestamp.getDefaultInstance())
                                                .setModified(Timestamp.getDefaultInstance())
                                                .setCreator(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setModifier(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .build())))
                                .bpmList(List.of(new OpenCDXBPMModel(BPM.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setCuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                                        .setArmUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                                        .setSystolic(80)
                                        .setDiastolic(120)
                                        .setUnit(BPMUnits.BARS)
                                        .setSittingPosition5Minutes(true)
                                        .setUrinated30MinutesPrior(false)
                                        .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .heartRPMList(List.of(new OpenCDXHeartRPMModel(HeartRPM.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setMeasurementTakenOverInSeconds(60)
                                        .setSittingPosition5Minutes(true)
                                        .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .userQuestionnaireDataList(
                                        List.of(new OpenCDXUserQuestionnaireModel(UserQuestionnaireData.newBuilder()
                                                .setId("65bb9634ab091e2343ff7ef7")
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .addAllQuestionnaireData(List.of(Questionnaire.getDefaultInstance()))
                                                .build())))
                                .connectedTestList(List.of(new OpenCDXConnectedTestModel(ConnectedTest.newBuilder()
                                        .setBasicInfo(BasicInfo.newBuilder()
                                                .setId(OpenCDXIdentifier.get().toHexString())
                                                .setCreated(
                                                        Timestamp.newBuilder().setSeconds(1696432104))
                                                .setCreator("creator")
                                                .setModified(
                                                        Timestamp.newBuilder().setSeconds(1696435104))
                                                .setModifier("modifier")
                                                .setVendorLabTestId("vendorLabTestId")
                                                .setType("type")
                                                .setPatientId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setNationalHealthId(
                                                        UUID.randomUUID().toString())
                                                .setHealthServiceId("hea;thServiceId")
                                                .setWorkspaceId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setOrganizationId(
                                                        OpenCDXIdentifier.get().toHexString())
                                                .setSource("source")
                                                .build())
                                        .setOrderInfo(OrderInfo.newBuilder()
                                                .setOrderId("orderId")
                                                .setStatus("statusId")
                                                .setStatusMessage("statusMessage")
                                                .addAllStatusMessageActions(List.of(StatusMessageAction.newBuilder()
                                                        .setId("id")
                                                        .setDescription("description")
                                                        .setValue("value")
                                                        .buildPartial()))
                                                .setEncounterId("encounterId")
                                                .setIsSyncedWithEhr(true)
                                                .setResult("result")
                                                .setQuestionnaireId("questionnaireId")
                                                .build())
                                        .setTestNotes(TestNotes.newBuilder()
                                                .setNotes("notes")
                                                .setMedicationNotes("medicationNotes")
                                                .setReferralNotes("referralNotes")
                                                .setDiagnosticNotes("diagnosticNotes")
                                                .buildPartial())
                                        .setPaymentDetails(PaymentDetails.newBuilder()
                                                .setPaymentMode("paymentMode")
                                                .setInsuranceInfoId("insuranceInfoId")
                                                .setPaymentTransactionId("paymentTransactionId")
                                                .setPaymentDetails("paymentDetails")
                                                .build())
                                        .setProviderInfo(ProviderInfo.newBuilder()
                                                .setOrderingProviderId("orderingProviderId")
                                                .setAssignedProviderId(1)
                                                .setOrderingProviderNpi(1)
                                                .build())
                                        .setTestDetails(TestDetails.newBuilder()
                                                .setMetadata(Metadata.newBuilder()
                                                        .setQrData("qrdata")
                                                        .setKitUploadId("kitUploadID")
                                                        .setResponseMessage("responseMessage")
                                                        .setResponseTitle("responseTitle")
                                                        .setResponseCode(200)
                                                        .setImageType("imageType")
                                                        .setType("type")
                                                        .setManufacturer("manufacturer")
                                                        .setCassetteLotNumber("cassetteLotNumber")
                                                        .setOutcomeIgm(true)
                                                        .setOutcomeIgg(true)
                                                        .setOutcome("outcome")
                                                        .setSelfAssessmentOutcomeIgm(true)
                                                        .setSelfAssessmentOutcomeIgg(true)
                                                        .setSelfAssessmentOutcome("selfAssessmentOutcome")
                                                        .setCassetteExpirationDate("expDate")
                                                        .setLabTestOrderableId("labTestOrderableId")
                                                        .setSkuId("skuID")
                                                        .setCassetteVerification("verification")
                                                        .build())
                                                .setRequisitionBase64("requisitionBase64")
                                                .setInternalTestId("internalTestId")
                                                .setMedications("medications")
                                                .setReferrals("referrals")
                                                .setDiagnostics("diagnostics")
                                                .addAllOrderableTestIds(List.of("orderableTestIds"))
                                                .addAllOrderableTests(List.of(OrderableTest.newBuilder()
                                                        .setOrderableTestId("orderableTestId")
                                                        .build()))
                                                .addAllOrderableTestResults(List.of(OrderableTestResult.newBuilder()
                                                        .setOrderableTestId("orderableTestId")
                                                        .setCollectionDate("collectionDate")
                                                        .setTestResult("testResult")
                                                        .setOutcome("outcome")
                                                        .setResponseMessage("responseMessage")
                                                        .setResponseTitle("responseTitle")
                                                        .setResponseCode(200)
                                                        .build()))
                                                .setTestClassification(TestClassification.TEST_CLASSIFICATION_GENERAL)
                                                .setIsOnsiteTest(true)
                                                .setSpecimenId("specimenId")
                                                .setLabVendorConfirmationId("labVendorConfirmationId")
                                                .setDeviceIdentifier("2")
                                                .setDeviceSerialNumber("deviceSerialNumber")
                                                .setIsAutoGenerated(true)
                                                .setReportingFlag("reportingFlag")
                                                .setNotificationFlag("notificationFlag")
                                                .setOrderStatusFlag("orderStatusFlag")
                                                .setOrderReceiptPath("orderReceiptPath")
                                                .setLabTestType("labTestType")
                                                .setSpecimenType("specimenType")
                                                .setMedicalCode("medicalCode")
                                                .build())
                                        .build())))
                                .build()
                                .getProtobufMessage())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateMedicalRecordResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
