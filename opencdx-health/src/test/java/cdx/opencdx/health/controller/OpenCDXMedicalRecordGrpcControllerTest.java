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
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXMedicalRecordMessageService;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.service.questionnaire.GetQuestionnaireRequest;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import cdx.opencdx.health.model.OpenCDXMedicalRecordModel;
import cdx.opencdx.health.repository.OpenCDXMedicalRecordRepository;
import cdx.opencdx.health.service.OpenCDXMedicalRecordService;
import cdx.opencdx.health.service.impl.OpenCDXMedicalRecordServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
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
                                .id(argument)
                                .status(MedicalRecordStatus.MEDICAL_RECORD_STATUS_COMPLETE)
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
                                .build()
                                .getProtobufMessage())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateMedicalRecordResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
