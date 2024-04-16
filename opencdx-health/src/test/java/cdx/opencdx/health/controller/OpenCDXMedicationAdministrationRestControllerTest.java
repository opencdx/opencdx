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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.MedicationAdministration;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.GetMedicationByIdRequest;
import cdx.opencdx.grpc.service.health.ListMedicationsRequest;
import cdx.opencdx.health.model.OpenCDXMedicationAdministrationModel;
import cdx.opencdx.health.model.OpenCDXMedicationModel;
import cdx.opencdx.health.repository.OpenCDXMedicationAdministrationRepository;
import cdx.opencdx.health.repository.OpenCDXMedicationRepository;
import cdx.opencdx.health.service.OpenCDXMedicationAdministrationService;
import cdx.opencdx.health.service.impl.OpenCDXMedicationAdministrationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import org.bson.types.ObjectId;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXMedicationAdministrationRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @MockBean
    OpenCDXMedicationAdministrationRepository openCDXMedicationAdministrationRepository;

    @MockBean
    OpenCDXMedicationRepository openCDXMedicationRepository;

    OpenCDXMedicationAdministrationService openCDXMedicationAdministrationService;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    @MockBean
    Connection connection;

    OpenCDXMedicationAdministrationRestController openCDXMedicationAdministrationRestController;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXMedicationAdministrationRepository.save(
                        Mockito.any(OpenCDXMedicationAdministrationModel.class)))
                .thenAnswer(new Answer<OpenCDXMedicationAdministrationModel>() {
                    @Override
                    public OpenCDXMedicationAdministrationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMedicationAdministrationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                            argument.setPatientId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXMedicationRepository.save(Mockito.any(OpenCDXMedicationModel.class)))
                .thenAnswer(new Answer<OpenCDXMedicationModel>() {
                    @Override
                    public OpenCDXMedicationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMedicationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXMedicationAdministrationRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicationAdministrationModel>>() {
                    @Override
                    public Optional<OpenCDXMedicationAdministrationModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicationAdministrationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .medicationId(OpenCDXIdentifier.get())
                                .administratedBy("Doctor")
                                .administrationNotes("notes")
                                .nationalHealthId(ObjectId.get().toHexString())
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicationRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicationModel>>() {
                    @Override
                    public Optional<OpenCDXMedicationModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicationRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXMedicationRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicationModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        //        this.objectMapper = Mockito.mock(ObjectMapper.class);
        //        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        this.openCDXMedicationAdministrationService = new OpenCDXMedicationAdministrationServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.openCDXMedicationAdministrationRepository,
                this.openCDXMedicationRepository,
                this.openCDXDocumentValidator);
        this.openCDXMedicationAdministrationRestController =
                new OpenCDXMedicationAdministrationRestController(openCDXMedicationAdministrationService);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
        Mockito.reset(this.openCDXMedicationAdministrationRepository);
        Mockito.reset(this.openCDXMedicationRepository);
    }

    @Test
    void trackMedicationAdministration() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/medication/administer")
                        .content(this.objectMapper.writeValueAsString(MedicationAdministration.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setPatientId(ObjectId.get().toHexString())
                                .setMedicationId(ObjectId.get().toHexString())
                                .setNationalHealthId(ObjectId.get().toHexString())
                                .setAdministratedBy("Doctor")
                                .setAdministrationNotes("notes")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void getMedicationById() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/medication/administer/" + OpenCDXIdentifier.get())
                        .content(this.objectMapper.writeValueAsString(GetMedicationByIdRequest.newBuilder()
                                .setMedicationId(ObjectId.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void getMedicationsByPatientId() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/medication/administer/list")
                        .content(this.objectMapper.writeValueAsString(ListMedicationsRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(false)
                                        .setSort("id")
                                        .build())
                                .setPatientId(ObjectId.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }
}
