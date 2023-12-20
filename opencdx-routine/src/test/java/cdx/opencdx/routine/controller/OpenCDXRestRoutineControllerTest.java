/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package cdx.opencdx.routine.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.profile.ContactInfo;
import cdx.opencdx.grpc.profile.FullName;
import cdx.opencdx.grpc.profile.PhoneNumber;
import cdx.opencdx.grpc.profile.PhoneType;
import cdx.opencdx.grpc.routine.ClinicalProtocolExecution;
import cdx.opencdx.grpc.routine.ClinicalProtocolExecutionRequest;
import cdx.opencdx.grpc.routine.DeliveryTracking;
import cdx.opencdx.grpc.routine.DeliveryTrackingRequest;
import cdx.opencdx.grpc.routine.Diagnosis;
import cdx.opencdx.grpc.routine.DiagnosisRequest;
import cdx.opencdx.grpc.routine.LabOrder;
import cdx.opencdx.grpc.routine.LabOrderRequest;
import cdx.opencdx.grpc.routine.LabResult;
import cdx.opencdx.grpc.routine.LabResultRequest;
import cdx.opencdx.grpc.routine.Medication;
import cdx.opencdx.grpc.routine.MedicationRequest;
import cdx.opencdx.grpc.routine.Routine;
import cdx.opencdx.grpc.routine.RoutineRequest;
import cdx.opencdx.grpc.routine.SuspectedDiagnosis;
import cdx.opencdx.grpc.routine.SuspectedDiagnosisRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestRoutineControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    Connection connection;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @MockBean
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @BeforeEach
    public void setup() {
        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(argument)
                                .password("{noop}pass")
                                .fullName(FullName.newBuilder()
                                        .setFirstName("bob")
                                        .setLastName("bob")
                                        .build())
                                .username("ab@safehealth.me")
                                .primaryContactInfo(ContactInfo.newBuilder()
                                        .setEmail("ab@safehealth.me")
                                        .setMobileNumber(PhoneNumber.newBuilder()
                                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                                .setNumber("1234567890")
                                                .build())
                                        .build())
                                .emailVerified(true)
                                .build());
                    }
                });
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
    }

    @Test
    void checkMockMvc() throws Exception { // Assertions.assertNotNull(greetingController);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void testCreateRoutine() throws Exception {
        this.mockMvc
                .perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(RoutineRequest.newBuilder()
                                .setRoutine(
                                        Routine.newBuilder().setRoutineId("789").build())
                                .build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.routine.routineId").value("789"));
    }

    @Test
    void testGetRoutine() throws Exception {
        this.mockMvc
                .perform(get("/789").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.routine.routineId").value("789"));
    }

    @Test
    void testCreateDeliveryTracking() throws Exception {
        this.mockMvc
                .perform(post("/deliveryTracking")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeliveryTrackingRequest.newBuilder()
                                .setDeliveryTracking(DeliveryTracking.newBuilder()
                                        .setDeliveryId("789")
                                        .build())
                                .build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deliveryTracking.deliveryId").value("789"));
    }

    // Test cases for getDeliveryTracking
    @Test
    void testGetDeliveryTrackingValid() throws Exception {
        this.mockMvc
                .perform(get("/deliveryTracking/789").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deliveryTracking.deliveryId").value("789"));
    }

    // Test cases for createClinicalProtocolExecution
    @Test
    void testCreateClinicalProtocolExecutionValid() throws Exception {
        this.mockMvc
                .perform(post("/clinicalProtocolExecution")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ClinicalProtocolExecutionRequest.newBuilder()
                                .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                                        .setExecutionId("789")
                                        .build())
                                .build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clinicalProtocolExecution.executionId").value("789"));
    }

    // Test cases for getClinicalProtocolExecution
    @Test
    void testGetClinicalProtocolExecutionValid() throws Exception {
        this.mockMvc
                .perform(get("/clinicalProtocolExecution/789").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clinicalProtocolExecution.executionId").value("789"));
    }

    // Test cases for triggerLabOrder
    @Test
    void testTriggerLabOrderValid() throws Exception {
        this.mockMvc
                .perform(post("/labOrder")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(LabOrderRequest.newBuilder()
                                .setLabOrder(LabOrder.newBuilder()
                                        .setLabOrderId("789")
                                        .build())
                                .build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.labOrder.labOrderId").value("789"));
    }

    // Test cases for getLabOrder
    @Test
    void testGetLabOrderValid() throws Exception {
        this.mockMvc
                .perform(get("/labOrder/789").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.labOrder.labOrderId").value("789"));
    }

    // Test cases for triggerDiagnosis
    @Test
    void testTriggerDiagnosisValid() throws Exception {
        this.mockMvc
                .perform(post("/diagnosis")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DiagnosisRequest.newBuilder()
                                .setDiagnosis(Diagnosis.newBuilder()
                                        .setDiagnosisId("789")
                                        .build())
                                .build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.diagnosis.diagnosisId").value("789"));
    }

    // Test cases for getDiagnosis
    @Test
    void testGetDiagnosisValid() throws Exception {
        this.mockMvc
                .perform(get("/diagnosis/789").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.diagnosis.diagnosisId").value("789"));
    }

    // Test cases for triggerSuspectedDiagnosis
    @Test
    void testTriggerSuspectedDiagnosisValid() throws Exception {
        this.mockMvc
                .perform(post("/suspectedDiagnosis")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(SuspectedDiagnosisRequest.newBuilder()
                                .setSuspectedDiagnosis(SuspectedDiagnosis.newBuilder()
                                        .setSuspectedDiagnosisId("789")
                                        .build())
                                .build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.suspectedDiagnosis.suspectedDiagnosisId").value("789"));
    }

    // Test cases for getSuspectedDiagnosis
    @Test
    void testGetSuspectedDiagnosisValid() throws Exception {
        this.mockMvc
                .perform(get("/suspectedDiagnosis/" + "789").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.suspectedDiagnosis.suspectedDiagnosisId").value("789"));
    }

    // Test cases for triggerLabResult
    @Test
    void testTriggerLabResultValid() throws Exception {
        this.mockMvc
                .perform(post("/labResult")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(LabResultRequest.newBuilder()
                                .setLabResult(LabResult.newBuilder()
                                        .setResultId("789")
                                        .build())
                                .build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.labResult.resultId").value("789"));
    }

    // Test cases for getLabResult
    @Test
    void testGetLabResultValid() throws Exception {
        this.mockMvc
                .perform(get("/labResult/789").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.labResult.resultId").value("789"));
    }

    // Test cases for triggerMedication
    @Test
    void testTriggerMedicationValid() throws Exception {
        this.mockMvc
                .perform(post("/medication")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(MedicationRequest.newBuilder()
                                .setMedication(Medication.newBuilder()
                                        .setMedicationId("789")
                                        .build())
                                .build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.medication.medicationId").value("789"));
    }

    // Test cases for getMedication
    @Test
    void testGetMedicationValid() throws Exception {
        this.mockMvc
                .perform(get("/medication/789").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.medication.medicationId").value("789"));
    }
}
