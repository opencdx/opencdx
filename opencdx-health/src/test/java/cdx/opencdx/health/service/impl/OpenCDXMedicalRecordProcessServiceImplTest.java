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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXClassificationModel;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXUserQuestionnaireModel;
import cdx.opencdx.commons.repository.OpenCDXClassificationRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXClassificationMessageService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.questionnaire.GetQuestionnaireListRequest;
import cdx.opencdx.grpc.service.questionnaire.GetQuestionnaireRequest;
import cdx.opencdx.grpc.types.*;
import cdx.opencdx.health.model.*;
import cdx.opencdx.health.repository.*;
import cdx.opencdx.health.service.*;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXMedicalRecordProcessServiceImplTest {
    OpenCDXMedicalRecordProcessService openCDXMedicalRecordProcessService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXMedicalRecordRepository openCDXMedicalRecordRepository;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @Mock
    OpenCDXMedicationRepository openCDXMedicationRepository;

    @Mock
    OpenCDXMedicationAdministrationRepository openCDXMedicationAdministrationRepository;

    @Mock
    OpenCDXAllergyRepository openCDXAllergyRepository;

    @Mock
    OpenCDXDoctorNotesRepository openCDXDoctorNotesRepository;

    @Mock
    OpenCDXVaccineRepository openCDXVaccineRepository;

    @Mock
    OpenCDXHeightMeasurementRepository openCDXHeightMeasurementRepository;

    @Mock
    OpenCDXWeightMeasurementRepository openCDXWeightMeasurementRepository;

    @Mock
    OpenCDXBPMRepository openCDXBPMRepository;

    @Mock
    OpenCDXHeartRPMRepository openCDXHeartRPMRepository;

    @MockBean
    OpenCDXQuestionnaireClient openCDXQuestionnaireClient;

    @Mock
    OpenCDXClassificationRepository openCDXClassificationRepository;

    @Mock
    OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    @Mock
    OpenCDXIAMProfileService openCDXIAMProfileService;

    @Mock
    OpenCDXMedicationService openCDXMedicationService;

    @Mock
    OpenCDXMedicationAdministrationService openCDXMedicationAdministrationService;

    @Mock
    OpenCDXAllergyService openCDXAllergyService;

    @Mock
    OpenCDXDoctorNotesService openCDXDoctorNotesService;

    @Mock
    OpenCDXVaccineService openCDXVaccineService;

    @Mock
    OpenCDXHeightMeasurementService openCDXHeightMeasurementService;

    @Mock
    OpenCDXWeightMeasurementService openCDXWeightMeasurementService;

    @Mock
    OpenCDXBPMService openCDXBPMService;

    @Mock
    OpenCDXHeartRPMService openCDXHeartRPMService;

    @Mock
    OpenCDXClassificationMessageService openCDXClassificationMessageService;

    @Mock
    OpenCDXConnectedTestService openCDXConnectedTestService;

    @Mock
    OpenCDXMedicalRecordService openCDXMedicalRecordService;

    @Mock
    OpenCDXMedicalConditionsRepository openCDXMedicalConditionsRepository;

    @Mock
    OpenCDXMedicalConditionsService openCDXMedicalConditionsService;

    @Test
    void test() {
        assertNotNull("AB");
    }

    @Test
    void testProcessMedicalRecord() {
        Mockito.when(this.openCDXMedicalRecordRepository.findById(any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalRecordModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalRecordModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalRecordModel.builder()
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
                                .build());
                    }
                });

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        Mockito.when(this.openCDXQuestionnaireClient.getUserQuestionnaireDataList(
                        any(GetQuestionnaireListRequest.class), any(OpenCDXCallCredentials.class)))
                .thenAnswer(new Answer<UserQuestionnaireData>() {
                    @Override
                    public UserQuestionnaireData answer(InvocationOnMock invocation) throws Throwable {
                        GetQuestionnaireRequest argument = invocation.getArgument(0);
                        return UserQuestionnaireData.newBuilder()
                                .setId(argument.getId())
                                .build();
                    }
                });

        Mockito.when(this.openCDXClassificationRepository.findAllByPatientId(any(OpenCDXIdentifier.class)))
                .thenReturn(List.of(OpenCDXClassificationModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build()));

        Mockito.when(this.openCDXMedicationRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXMedicationAdministrationRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationAdministrationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .medicationId(OpenCDXIdentifier.get())
                                .administratedBy("Doctor")
                                .administrationNotes("notes")
                                .nationalHealthId(ObjectId.get().toHexString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXAllergyRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXAllergyModel.builder()
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .allergen("allergen")
                                .reaction("reaction")
                                .isSevere(true)
                                .onsetDate(Instant.now())
                                .lastOccurrence(Instant.now())
                                .notes("notes")
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXDoctorNotesModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .providerNumber("providerNumber")
                                .tags(List.of("tags"))
                                .noteDatetime(Instant.now())
                                .notes("notes")
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXVaccineRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXVaccineModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(ObjectId.get().toHexString())
                                .fips("fips")
                                .facilityType("facilityType")
                                .vaccine(Medication.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .setMedicationName("medication")
                                        .setStartDate(Timestamp.newBuilder().setSeconds(1696733104))
                                        .build())
                                .healthDistrict("district")
                                .doseNumber(2)
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXHeightMeasurementRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXHeightMeasurementModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXWeightMeasurementRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXWeightMeasurementModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXBPMRepository.findAllByPatientId(any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXBPMModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .cuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                                .armUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                                .systolic(80)
                                .diastolic(120)
                                .bpmUnits(BPMUnits.BARS)
                                .sittingPositionFiveMinutes(true)
                                .urinatedThirtyMinutesPrior(false)
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXHeartRPMRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXHeartRPMModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .measurementTakenOverInSeconds(60)
                                .sittingPositionFiveMinutes(true)
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXConnectedTestRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXConnectedTestModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXMedicalConditionsRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicalConditionsModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        openCDXMedicalRecordProcessService = new OpenCDXMedicalRecordProcessServiceImpl(
                openCDXProfileRepository,
                openCDXIAMProfileService,
                openCDXMedicationRepository,
                openCDXMedicationService,
                openCDXMedicationAdministrationRepository,
                openCDXMedicationAdministrationService,
                openCDXAllergyRepository,
                openCDXAllergyService,
                openCDXDoctorNotesRepository,
                openCDXDoctorNotesService,
                openCDXVaccineRepository,
                openCDXVaccineService,
                openCDXHeightMeasurementRepository,
                openCDXHeightMeasurementService,
                openCDXWeightMeasurementRepository,
                openCDXWeightMeasurementService,
                openCDXBPMRepository,
                openCDXBPMService,
                openCDXHeartRPMRepository,
                openCDXHeartRPMService,
                openCDXMedicalRecordRepository,
                openCDXMedicalRecordService,
                openCDXConnectedTestRepository,
                openCDXConnectedTestService,
                openCDXMedicalConditionsRepository,
                openCDXMedicalConditionsService
                // openCDXQuestionnaireClient
                );

        openCDXMedicalRecordProcessService.processMedicalRecord(OpenCDXIdentifier.get());
        Mockito.verify(openCDXMedicalRecordRepository, Mockito.times(1)).findById(any(OpenCDXIdentifier.class));
    }

    @Test
    void testImport() {
        Mockito.when(this.openCDXProfileRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXMedicalRecordRepository.findById(any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalRecordModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalRecordModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalRecordModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_IMPORT)
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
                                .medicalConditions(List.of(new OpenCDXMedicalConditionsModel(Diagnosis.newBuilder()
                                        .setDiagnosisId(OpenCDXIdentifier.get().toHexString())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setDiagnosisDatetime(Timestamp.getDefaultInstance())
                                        .setDiagnosisStatus(DiagnosisStatus.SUSPECTED)
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .build());
                    }
                });
        openCDXMedicalRecordProcessService = new OpenCDXMedicalRecordProcessServiceImpl(
                openCDXProfileRepository,
                openCDXIAMProfileService,
                openCDXMedicationRepository,
                openCDXMedicationService,
                openCDXMedicationAdministrationRepository,
                openCDXMedicationAdministrationService,
                openCDXAllergyRepository,
                openCDXAllergyService,
                openCDXDoctorNotesRepository,
                openCDXDoctorNotesService,
                openCDXVaccineRepository,
                openCDXVaccineService,
                openCDXHeightMeasurementRepository,
                openCDXHeightMeasurementService,
                openCDXWeightMeasurementRepository,
                openCDXWeightMeasurementService,
                openCDXBPMRepository,
                openCDXBPMService,
                openCDXHeartRPMRepository,
                openCDXHeartRPMService,
                openCDXMedicalRecordRepository,
                openCDXMedicalRecordService,
                openCDXConnectedTestRepository,
                openCDXConnectedTestService,
                openCDXMedicalConditionsRepository,
                openCDXMedicalConditionsService
                // openCDXQuestionnaireClient
                );
        openCDXMedicalRecordProcessService.processMedicalRecord(OpenCDXIdentifier.get());
        Mockito.verify(openCDXMedicalRecordRepository, Mockito.times(1)).findById(any(OpenCDXIdentifier.class));
    }

    @Test
    void testImportElse() {
        Mockito.when(this.openCDXProfileRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(false);
        Mockito.when(this.openCDXMedicationAdministrationRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXAllergyRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXDoctorNotesRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXVaccineRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXHeightMeasurementRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXWeightMeasurementRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXBPMRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXHeartRPMRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXMedicalRecordRepository.findById(any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalRecordModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalRecordModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalRecordModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_IMPORT)
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
                                .medicalConditions(List.of(new OpenCDXMedicalConditionsModel(Diagnosis.newBuilder()
                                        .setDiagnosisId(OpenCDXIdentifier.get().toHexString())
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setDiagnosisDatetime(Timestamp.getDefaultInstance())
                                        .setDiagnosisStatus(DiagnosisStatus.SUSPECTED)
                                        .setCreated(Timestamp.getDefaultInstance())
                                        .setModified(Timestamp.getDefaultInstance())
                                        .setCreator(OpenCDXIdentifier.get().toHexString())
                                        .setModifier(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                                .build());
                    }
                });
        openCDXMedicalRecordProcessService = new OpenCDXMedicalRecordProcessServiceImpl(
                openCDXProfileRepository,
                openCDXIAMProfileService,
                openCDXMedicationRepository,
                openCDXMedicationService,
                openCDXMedicationAdministrationRepository,
                openCDXMedicationAdministrationService,
                openCDXAllergyRepository,
                openCDXAllergyService,
                openCDXDoctorNotesRepository,
                openCDXDoctorNotesService,
                openCDXVaccineRepository,
                openCDXVaccineService,
                openCDXHeightMeasurementRepository,
                openCDXHeightMeasurementService,
                openCDXWeightMeasurementRepository,
                openCDXWeightMeasurementService,
                openCDXBPMRepository,
                openCDXBPMService,
                openCDXHeartRPMRepository,
                openCDXHeartRPMService,
                openCDXMedicalRecordRepository,
                openCDXMedicalRecordService,
                openCDXConnectedTestRepository,
                openCDXConnectedTestService,
                openCDXMedicalConditionsRepository,
                openCDXMedicalConditionsService
                // openCDXQuestionnaireClient
                );
        openCDXMedicalRecordProcessService.processMedicalRecord(OpenCDXIdentifier.get());
        Mockito.verify(openCDXMedicalRecordRepository, Mockito.times(1)).findById(any(OpenCDXIdentifier.class));
    }

    @Test
    void testComplete() {
        Mockito.when(this.openCDXProfileRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXMedicalRecordRepository.findById(any(OpenCDXIdentifier.class)))
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
        openCDXMedicalRecordProcessService = new OpenCDXMedicalRecordProcessServiceImpl(
                openCDXProfileRepository,
                openCDXIAMProfileService,
                openCDXMedicationRepository,
                openCDXMedicationService,
                openCDXMedicationAdministrationRepository,
                openCDXMedicationAdministrationService,
                openCDXAllergyRepository,
                openCDXAllergyService,
                openCDXDoctorNotesRepository,
                openCDXDoctorNotesService,
                openCDXVaccineRepository,
                openCDXVaccineService,
                openCDXHeightMeasurementRepository,
                openCDXHeightMeasurementService,
                openCDXWeightMeasurementRepository,
                openCDXWeightMeasurementService,
                openCDXBPMRepository,
                openCDXBPMService,
                openCDXHeartRPMRepository,
                openCDXHeartRPMService,
                openCDXMedicalRecordRepository,
                openCDXMedicalRecordService,
                openCDXConnectedTestRepository,
                openCDXConnectedTestService,
                openCDXMedicalConditionsRepository,
                openCDXMedicalConditionsService

                // openCDXQuestionnaireClient
                );
        openCDXMedicalRecordProcessService.processMedicalRecord(OpenCDXIdentifier.get());
        Mockito.verify(openCDXMedicalRecordRepository, Mockito.times(1)).findById(any(OpenCDXIdentifier.class));
    }

    @Test
    void isEmpty() {
        Mockito.when(this.openCDXMedicalRecordRepository.findById(any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalRecordModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalRecordModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.empty();
                    }
                });
        openCDXMedicalRecordProcessService = new OpenCDXMedicalRecordProcessServiceImpl(
                openCDXProfileRepository,
                openCDXIAMProfileService,
                openCDXMedicationRepository,
                openCDXMedicationService,
                openCDXMedicationAdministrationRepository,
                openCDXMedicationAdministrationService,
                openCDXAllergyRepository,
                openCDXAllergyService,
                openCDXDoctorNotesRepository,
                openCDXDoctorNotesService,
                openCDXVaccineRepository,
                openCDXVaccineService,
                openCDXHeightMeasurementRepository,
                openCDXHeightMeasurementService,
                openCDXWeightMeasurementRepository,
                openCDXWeightMeasurementService,
                openCDXBPMRepository,
                openCDXBPMService,
                openCDXHeartRPMRepository,
                openCDXHeartRPMService,
                openCDXMedicalRecordRepository,
                openCDXMedicalRecordService,
                openCDXConnectedTestRepository,
                openCDXConnectedTestService,
                openCDXMedicalConditionsRepository,
                openCDXMedicalConditionsService
                // openCDXQuestionnaireClient
                );
        openCDXMedicalRecordProcessService.processMedicalRecord(OpenCDXIdentifier.get());
        Mockito.verify(openCDXMedicalRecordRepository, Mockito.times(1)).findById(any(OpenCDXIdentifier.class));
    }
}
