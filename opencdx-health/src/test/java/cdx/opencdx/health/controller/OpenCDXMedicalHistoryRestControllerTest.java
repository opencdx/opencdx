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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.MedicalHistory;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.CreateMedicalHistoryRequest;
import cdx.opencdx.grpc.service.health.ListMedicalHistoriesRequest;
import cdx.opencdx.grpc.service.health.UpdateMedicalHistoryRequest;
import cdx.opencdx.grpc.types.Relationship;
import cdx.opencdx.grpc.types.RelationshipFamilyLine;
import cdx.opencdx.health.model.OpenCDXMedicalHistoryModel;
import cdx.opencdx.health.repository.OpenCDXMedicalHistoryRepository;
import cdx.opencdx.health.service.OpenCDXMedicalHistoryService;
import cdx.opencdx.health.service.impl.OpenCDXMedicalHistoryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXMedicalHistoryRestControllerTest {

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
    OpenCDXMedicalHistoryRepository openCDXMedicalHistoryRepository;

    OpenCDXMedicalHistoryService openCDXMedicalHistoryService;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    @MockBean
    Connection connection;

    OpenCDXMedicalHistoryRestController openCDXMedicalHistoryRestController;

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

        Mockito.when(this.openCDXMedicalHistoryRepository.save(Mockito.any(OpenCDXMedicalHistoryModel.class)))
                .thenAnswer(new Answer<OpenCDXMedicalHistoryModel>() {
                    @Override
                    public OpenCDXMedicalHistoryModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMedicalHistoryModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXMedicalHistoryRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMedicalHistoryModel>>() {
                    @Override
                    public Optional<OpenCDXMedicalHistoryModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMedicalHistoryModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .relationship(Relationship.MOTHER)
                                .relationshipFamilyLine(RelationshipFamilyLine.IMMEDIATE)
                                .build());
                    }
                });

        Mockito.when(this.openCDXMedicalHistoryRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicalHistoryModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .relationship(Relationship.MOTHER)
                                .relationshipFamilyLine(RelationshipFamilyLine.IMMEDIATE)
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXMedicalHistoryRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXMedicalHistoryModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        this.openCDXMedicalHistoryService = new OpenCDXMedicalHistoryServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXMedicalHistoryRepository);
        this.openCDXMedicalHistoryRestController =
                new OpenCDXMedicalHistoryRestController(openCDXMedicalHistoryService);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
        Mockito.reset(this.openCDXMedicalHistoryRepository);
    }

    @Test
    void createMedicalHistory() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/medicalhistory")
                        .content(this.objectMapper.writeValueAsString(CreateMedicalHistoryRequest.newBuilder()
                                .setMedicalHistory(MedicalHistory.newBuilder()
                                        .setId(ObjectId.get().toHexString())
                                        .setPatientId(ObjectId.get().toHexString())
                                        .setNationalHealthId(ObjectId.get().toHexString())
                                        .setRelationship(Relationship.MOTHER)
                                        .setRelationshipFamilyLine(RelationshipFamilyLine.IMMEDIATE)
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void getMedicalHistoryById() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/medicalhistory/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void updateMedicalHistory() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/medicalhistory")
                        .content(this.objectMapper.writeValueAsString(UpdateMedicalHistoryRequest.newBuilder()
                                .setMedicalHistory(MedicalHistory.newBuilder()
                                        .setId(ObjectId.get().toHexString())
                                        .setPatientId(ObjectId.get().toHexString())
                                        .setNationalHealthId(ObjectId.get().toHexString())
                                        .setRelationship(Relationship.MOTHER)
                                        .setRelationshipFamilyLine(RelationshipFamilyLine.IMMEDIATE)
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void listMedicalHistories() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/medicalhistory/list")
                        .content(this.objectMapper.writeValueAsString(ListMedicalHistoriesRequest.newBuilder()
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
