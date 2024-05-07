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
import cdx.opencdx.grpc.data.DoctorNotes;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.CreateDoctorNotesRequest;
import cdx.opencdx.grpc.service.health.ListDoctorNotesRequest;
import cdx.opencdx.grpc.service.health.UpdateDoctorNotesRequest;
import cdx.opencdx.health.model.OpenCDXDoctorNotesModel;
import cdx.opencdx.health.repository.OpenCDXDoctorNotesRepository;
import cdx.opencdx.health.service.OpenCDXDoctorNotesService;
import cdx.opencdx.health.service.impl.OpenCDXDoctorNotesServiceImpl;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXDoctorNotesRestControllerTest {
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
    OpenCDXDoctorNotesRepository openCDXDoctorNotesRepository;

    OpenCDXDoctorNotesService openCDXDoctorNotesService;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    @MockBean
    Connection connection;

    OpenCDXDoctorNotesRestController openCDXDoctorNotesRestController;

    @BeforeEach
    public void setup() {

        Mockito.when(this.openCDXDoctorNotesRepository.save(Mockito.any(OpenCDXDoctorNotesModel.class)))
                .thenAnswer(new Answer<OpenCDXDoctorNotesModel>() {
                    @Override
                    public OpenCDXDoctorNotesModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXDoctorNotesModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                            argument.setPatientId(OpenCDXIdentifier.get());
                            argument.setNationalHealthId(OpenCDXIdentifier.get().toHexString());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXDoctorNotesRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXDoctorNotesModel>>() {
                    @Override
                    public Optional<OpenCDXDoctorNotesModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXDoctorNotesModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .providerNumber("providerNumber")
                                .tags(List.of("tags"))
                                .noteDatetime(Instant.now())
                                .notes("notes")
                                .build());
                    }
                });
        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
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
        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
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
        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientIdAndTags(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(String.class), Mockito.any(Pageable.class)))
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

        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientIdAndTagsContainingAndNoteDatetimeBetween(
                        Mockito.any(OpenCDXIdentifier.class),
                        Mockito.any(String.class),
                        Mockito.any(Instant.class),
                        Mockito.any(Instant.class),
                        Mockito.any(Pageable.class)))
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

        Mockito.when(this.openCDXDoctorNotesRepository.findAllByPatientIdAndNoteDatetimeBetween(
                        Mockito.any(OpenCDXIdentifier.class),
                        Mockito.any(Instant.class),
                        Mockito.any(Instant.class),
                        Mockito.any(Pageable.class)))
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

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        this.openCDXDoctorNotesService = new OpenCDXDoctorNotesServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXDoctorNotesRepository);
        this.openCDXDoctorNotesRestController = new OpenCDXDoctorNotesRestController(openCDXDoctorNotesService);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
        Mockito.reset(this.openCDXDoctorNotesRepository);
    }

    @Test
    void getDoctorNotesequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/doctor-notes/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void createDoctorNotesRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/doctor-notes")
                        .content(this.objectMapper.writeValueAsString(CreateDoctorNotesRequest.newBuilder()
                                .setDoctorNotes(DoctorNotes.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void updateDoctorNotesRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/doctor-notes")
                        .content(this.objectMapper.writeValueAsString(UpdateDoctorNotesRequest.newBuilder()
                                .setDoctorNotes(DoctorNotes.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void deleteDoctorNotes() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/doctor-notes/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void listDoctorNotesRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/doctor-notes/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ListDoctorNotesRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }
}
