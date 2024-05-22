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

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXClassificationModel;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXClassificationRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.questionnaire.GetQuestionnaireListRequest;
import cdx.opencdx.grpc.service.questionnaire.GetQuestionnaireRequest;
import cdx.opencdx.grpc.types.ArmUsed;
import cdx.opencdx.grpc.types.BPMUnits;
import cdx.opencdx.grpc.types.CuffSize;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import cdx.opencdx.health.model.*;
import cdx.opencdx.health.repository.*;
import cdx.opencdx.health.service.OpenCDXMedicalRecordProcessService;
import com.google.protobuf.Timestamp;
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

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

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
                                        .build())
                                .classificationsList(List.of(Classification.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .build()))
                                .medicationList(List.of(Medication.newBuilder().build()))
                                .medicationAdministrationList(List.of(
                                        MedicationAdministration.newBuilder().build()))
                                .knownAllergyList(
                                        List.of(KnownAllergy.newBuilder().build()))
                                .doctorNotesList(
                                        List.of(DoctorNotes.newBuilder().build()))
                                .vaccineList(List.of(Vaccine.newBuilder().build()))
                                .heightMeasurementList(
                                        List.of(HeightMeasurement.newBuilder().build()))
                                .weightMeasurementList(
                                        List.of(WeightMeasurement.newBuilder().build()))
                                .bpmList(List.of(BPM.newBuilder().build()))
                                .heartRPMList(List.of(HeartRPM.newBuilder().build()))
                                .userQuestionnaireDataList(List.of(
                                        UserQuestionnaireData.newBuilder().build()))
                                .connectedTestList(
                                        List.of(ConnectedTest.newBuilder().build()))
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
                        .thenReturn(
                                List.of(OpenCDXClassificationModel.builder()
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
                .thenReturn(new PageImpl<>( List.of(OpenCDXMedicationAdministrationModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .patientId(OpenCDXIdentifier.get())
                        .nationalHealthId(UUID.randomUUID().toString())
                        .medicationId(OpenCDXIdentifier.get())
                        .administratedBy("Doctor")
                        .administrationNotes("notes")
                        .nationalHealthId(ObjectId.get().toHexString())
                        .build()),  PageRequest.of(1, 10),
                                1));

        Mockito.when(this.openCDXAllergyRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(
                        OpenCDXAllergyModel.builder()
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .allergen("allergen")
                                .reaction("reaction")
                                .isSevere(true)
                                .onsetDate(Instant.now())
                                .lastOccurrence(Instant.now())
                                .notes("notes")
                                .build()),  PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(
                        OpenCDXDoctorNotesModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .providerNumber("providerNumber")
                                .tags(List.of("tags"))
                                .noteDatetime(Instant.now())
                                .notes("notes")
                                .build()),  PageRequest.of(1, 10),
                        1));


        Mockito.when(this.openCDXVaccineRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(
                        OpenCDXVaccineModel.builder()
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
                                .build()),  PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXHeightMeasurementRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(OpenCDXHeightMeasurementModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .patientId(OpenCDXIdentifier.get())
                        .nationalHealthId(UUID.randomUUID().toString())
                        .build()),  PageRequest.of(1, 10),
                        1));


        Mockito.when(this.openCDXWeightMeasurementRepository.findAllByPatientId(
                        any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(OpenCDXWeightMeasurementModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .patientId(OpenCDXIdentifier.get())
                        .nationalHealthId(UUID.randomUUID().toString())
                        .build()),  PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXBPMRepository.findAllByPatientId(any(OpenCDXIdentifier.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(
                        OpenCDXBPMModel.builder()
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
                .thenReturn(new PageImpl<>(List.of(
                        OpenCDXHeartRPMModel.builder()
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
                .thenReturn(new PageImpl<>(List.of(
                                OpenCDXConnectedTestModel.builder().id(OpenCDXIdentifier.get()).build()),
                        PageRequest.of(1, 10),
                        1));

        openCDXMedicalRecordProcessService = new OpenCDXMedicalRecordProcessServiceImpl(
                openCDXCurrentUser,
                openCDXProfileRepository,
                openCDXClassificationRepository,
                openCDXMedicationRepository,
                openCDXMedicationAdministrationRepository,
                openCDXAllergyRepository,
                openCDXDoctorNotesRepository,
                openCDXVaccineRepository,
                openCDXHeightMeasurementRepository,
                openCDXWeightMeasurementRepository,
                openCDXBPMRepository,
                openCDXHeartRPMRepository,
                openCDXMedicalRecordRepository,
                openCDXConnectedTestRepository
                // openCDXQuestionnaireClient
                );

        openCDXMedicalRecordProcessService.processMedicalRecord(OpenCDXIdentifier.get());
//        Mockito.verify(openCDXMedicalRecordProcessService, Mockito.times(1))
//                .processMedicalRecord(OpenCDXIdentifier.get());
    }
}
