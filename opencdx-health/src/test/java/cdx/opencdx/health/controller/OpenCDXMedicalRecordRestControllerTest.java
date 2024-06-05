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
import cdx.opencdx.commons.model.OpenCDXUserQuestionnaireModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXMedicalRecordMessageService;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.CreateMedicalRecordRequest;
import cdx.opencdx.grpc.service.health.GetMedicalRecordRequest;
import cdx.opencdx.grpc.service.questionnaire.GetQuestionnaireRequest;
import cdx.opencdx.grpc.types.MedicalRecordStatus;
import cdx.opencdx.health.model.*;
import cdx.opencdx.health.repository.OpenCDXMedicalRecordRepository;
import cdx.opencdx.health.service.OpenCDXMedicalRecordService;
import cdx.opencdx.health.service.impl.OpenCDXMedicalRecordServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXMedicalRecordRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    OpenCDXMedicalRecordRestController openCDXMedicalRecordRestController;

    OpenCDXMedicalRecordService openCDXMedicalRecordService;

    @Autowired
    OpenCDXMedicalRecordMessageService openCDXMedicalRecordMessageService;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OpenCDXMedicalRecordRepository openCDXMedicalRecordRepository;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @MockBean
    OpenCDXQuestionnaireClient openCDXQuestionnaireClient;

    @MockBean
    Connection connection;

    @BeforeEach
    public void setup() throws JsonProcessingException {
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
                                .medicationList(List.of(new OpenCDXMedicationModel(Medication.newBuilder().build())))
                                .medicationAdministrationList(List.of(
                                        new OpenCDXMedicationAdministrationModel(MedicationAdministration.newBuilder().build())))
                                .knownAllergyList(
                                        List.of(new OpenCDXAllergyModel(KnownAllergy.newBuilder().build())))
                                .doctorNotesList(
                                        List.of(new OpenCDXDoctorNotesModel(DoctorNotes.newBuilder().build())))
                                .vaccineList(List.of(new OpenCDXVaccineModel(Vaccine.newBuilder().build())))
                                .heightMeasurementList(
                                        List.of(new OpenCDXHeightMeasurementModel(HeightMeasurement.newBuilder().build())))
                                .weightMeasurementList(
                                        List.of(new OpenCDXWeightMeasurementModel(WeightMeasurement.newBuilder().build())))
                                .bpmList(List.of(new OpenCDXBPMModel(BPM.newBuilder().build())))
                                .heartRPMList(List.of(new OpenCDXHeartRPMModel(HeartRPM.newBuilder().build())))
                                .userQuestionnaireDataList(List.of(
                                        new OpenCDXUserQuestionnaireModel(UserQuestionnaireData.newBuilder().build())))
                                .connectedTestList(
                                        List.of(new OpenCDXConnectedTestModel(ConnectedTest.newBuilder().build())))
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
                                .medicationList(List.of(new OpenCDXMedicationModel(Medication.newBuilder().build())))
                                .medicationAdministrationList(List.of(
                                        new OpenCDXMedicationAdministrationModel(MedicationAdministration.newBuilder().build())))
                                .knownAllergyList(
                                        List.of(new OpenCDXAllergyModel(KnownAllergy.newBuilder().build())))
                                .doctorNotesList(
                                        List.of(new OpenCDXDoctorNotesModel(DoctorNotes.newBuilder().build())))
                                .vaccineList(List.of(new OpenCDXVaccineModel(Vaccine.newBuilder().build())))
                                .heightMeasurementList(
                                        List.of(new OpenCDXHeightMeasurementModel(HeightMeasurement.newBuilder().build())))
                                .weightMeasurementList(
                                        List.of(new OpenCDXWeightMeasurementModel(WeightMeasurement.newBuilder().build())))
                                .bpmList(List.of(new OpenCDXBPMModel(BPM.newBuilder().build())))
                                .heartRPMList(List.of(new OpenCDXHeartRPMModel(HeartRPM.newBuilder().build())))
                                .userQuestionnaireDataList(List.of(
                                        new OpenCDXUserQuestionnaireModel(UserQuestionnaireData.newBuilder().build())))
                                .connectedTestList(
                                        List.of(new OpenCDXConnectedTestModel(ConnectedTest.newBuilder().build())))
                                .build();
                    }
                });

        this.objectMapper = Mockito.mock(ObjectMapper.class);
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
        this.openCDXMedicalRecordRestController = new OpenCDXMedicalRecordRestController(openCDXMedicalRecordService);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
        Mockito.reset(this.objectMapper);
        Mockito.reset(this.openCDXMedicalRecordRepository);
        Mockito.reset(this.openCDXProfileRepository);
    }

    // @Test
    void testRequestMedicalRecord() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/records/request")
                        .content(this.objectMapper.writeValueAsString(GetMedicalRecordRequest.newBuilder()
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void testGetMedicalRecordStatus() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/records/status/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void testGetMedicalRecordById() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/records/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    // @Test
    void testCreateMedicalRecord() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/records")
                        // .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(CreateMedicalRecordRequest.newBuilder()
                                .setMedicalRecord(MedicalRecord.newBuilder()
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
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }
}
