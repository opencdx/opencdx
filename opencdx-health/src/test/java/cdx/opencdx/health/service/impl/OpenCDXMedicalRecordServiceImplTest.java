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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

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
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import cdx.opencdx.health.model.OpenCDXMedicalRecordModel;
import cdx.opencdx.health.repository.OpenCDXMedicalRecordRepository;
import cdx.opencdx.health.service.OpenCDXMedicalRecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXMedicalRecordServiceImplTest {
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
                                .id(argument)
                                .userProfile(UserProfile.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .build())
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE)
                                .build());
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
                                .build();
                    }
                });

        this.objectMapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                openCDXAuditService,
                openCDXCurrentUser,
                objectMapper,
                openCDXDocumentValidator,
                openCDXMedicalRecordRepository,
                openCDXProfileRepository,
                openCDXMedicalRecordMessageService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper);
        Mockito.reset(this.openCDXMedicalRecordRepository);
        Mockito.reset(this.openCDXProfileRepository);
    }

    @Test
    void testRequestMedicalRecordHasPatientId() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(any(OpenCDXMedicalRecordModel.class)))
                .thenThrow(JsonProcessingException.class);
        GetMedicalRecordRequest request = GetMedicalRecordRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toString())
                .build();
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);

        assertThrows(OpenCDXNotAcceptable.class, () -> openCDXMedicalRecordService.requestMedicalRecord(request));
    }

    @Test
    void testRequestMedicalRecordHasPatientIdJSON() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        GetMedicalRecordRequest request = GetMedicalRecordRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toString())
                .build();
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);
        GetMedicalRecordResponse response = openCDXMedicalRecordService.requestMedicalRecord(request);
        assertNotNull(response.getMedicalRecordId());
    }

    @Test
    void testRequestMedicalRecordHasNHID() throws JsonProcessingException {
        Mockito.when(this.openCDXProfileRepository.findByNationalHealthId(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        // OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        GetMedicalRecordRequest request = GetMedicalRecordRequest.newBuilder()
                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                .build();
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);
        GetMedicalRecordResponse response = openCDXMedicalRecordService.requestMedicalRecord(request);
        assertNotNull(response.getMedicalRecordId());
    }

    @Test
    void testRequestMedicalRecordDoesNotHasNHID() {
        GetMedicalRecordRequest request = GetMedicalRecordRequest.newBuilder()
                .setNationalHealthId(OpenCDXIdentifier.get().toString())
                .build();
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);

        assertThrows(OpenCDXNotFound.class, () -> openCDXMedicalRecordService.requestMedicalRecord(request));
    }

    @Test
    void testRequestMedicalRecordDoesNotHasPatientId() {
        GetMedicalRecordRequest request = GetMedicalRecordRequest.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toString())
                .build();
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.empty();
                    }
                });
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);

        assertThrows(OpenCDXNotFound.class, () -> openCDXMedicalRecordService.requestMedicalRecord(request));
    }

    @Test
    void testGetMedicalRecordStatus() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        Mockito.when(this.openCDXProfileRepository.findByNationalHealthId(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        // OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        MedicalRecordByIdRequest request = MedicalRecordByIdRequest.newBuilder()
                .setMedicalRecordId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);
        assertNotNull(openCDXMedicalRecordService.getMedicalRecordStatus(request));
    }

    @Test
    void testGetMedicalRecordStatusNotComplete() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        Mockito.when(this.openCDXMedicalRecordRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalRecordModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalRecordModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalRecordModel.builder()
                                .id(argument)
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_EXPORT)
                                .userProfile(UserProfile.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .build())
                                .build());
                    }
                });
        MedicalRecordByIdRequest request = MedicalRecordByIdRequest.newBuilder()
                .setMedicalRecordId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);
        SuccessResponse response = openCDXMedicalRecordService.getMedicalRecordStatus(request);
        assertFalse(response.getSuccess());
    }

    @Test
    void testGetMedicalRecordStatusNotCompleteJson() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        Mockito.when(this.openCDXMedicalRecordRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalRecordModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalRecordModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalRecordModel.builder()
                                .id(argument)
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_EXPORT)
                                .userProfile(UserProfile.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .build())
                                .build());
                    }
                });
        MedicalRecordByIdRequest request = MedicalRecordByIdRequest.newBuilder()
                .setMedicalRecordId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);
        assertNotNull(openCDXMedicalRecordService.getMedicalRecordStatus(request));
    }

    @Test
    void testGetMedicalRecordById() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        Mockito.when(this.openCDXMedicalRecordRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalRecordModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalRecordModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalRecordModel.builder()
                                .id(argument)
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE)
                                .userProfile(UserProfile.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
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
        MedicalRecordByIdRequest request = MedicalRecordByIdRequest.newBuilder()
                .setMedicalRecordId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);
        MedicalRecordByIdResponse response = openCDXMedicalRecordService.getMedicalRecordById(request);
        assertNotNull(response.getMedicalRecord());
    }

    @Test
    void testGetMedicalRecordByIdJSON() throws JsonProcessingException {
        Mockito.when(this.objectMapper.writeValueAsString(any(OpenCDXMedicalRecordModel.class)))
                .thenThrow(JsonProcessingException.class);
        Mockito.when(this.openCDXMedicalRecordRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalRecordModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalRecordModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalRecordModel.builder()
                                .id(argument)
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE)
                                .userProfile(UserProfile.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
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
        MedicalRecordByIdRequest request = MedicalRecordByIdRequest.newBuilder()
                .setMedicalRecordId(OpenCDXIdentifier.get().toHexString())
                .build();
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{\"name\":\"test\"}");
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);
        MedicalRecordByIdResponse response = openCDXMedicalRecordService.getMedicalRecordById(request);
        assertNotNull(response.getMedicalRecord());
    }

    @Test
    void testGetMedicalRecordByIdException() {
        Mockito.when(this.openCDXMedicalRecordRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalRecordModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalRecordModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalRecordModel.builder()
                                .id(argument)
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_EXPORT)
                                .userProfile(UserProfile.newBuilder()
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .setNationalHealthId(
                                                OpenCDXIdentifier.get().toHexString())
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
        MedicalRecordByIdRequest request = MedicalRecordByIdRequest.newBuilder()
                .setMedicalRecordId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXMedicalRecordService = new OpenCDXMedicalRecordServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalRecordRepository,
                this.openCDXProfileRepository,
                this.openCDXMedicalRecordMessageService);
        assertThrows(OpenCDXForbidden.class, () -> openCDXMedicalRecordService.getMedicalRecordById(request));
    }
}
