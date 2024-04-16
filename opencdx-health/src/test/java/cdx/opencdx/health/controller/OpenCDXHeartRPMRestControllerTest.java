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
import cdx.opencdx.grpc.data.HeartRPM;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.CreateHeartRPMRequest;
import cdx.opencdx.grpc.service.health.ListHeartRPMRequest;
import cdx.opencdx.grpc.service.health.UpdateHeartRPMRequest;
import cdx.opencdx.health.model.OpenCDXHeartRPMModel;
import cdx.opencdx.health.repository.OpenCDXHeartRPMRepository;
import cdx.opencdx.health.service.OpenCDXHeartRPMService;
import cdx.opencdx.health.service.impl.OpenCDXHeartRPMServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.List;
import java.util.Optional;
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

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXHeartRPMRestControllerTest {
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
    OpenCDXHeartRPMRepository openCDXHeartRPMRepository;

    OpenCDXHeartRPMService openCDXHeartRPMService;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    @MockBean
    Connection connection;

    OpenCDXHeartRPMRestController openCDXHeartRPMRestController;

    @BeforeEach
    public void setup() {

        Mockito.when(this.openCDXHeartRPMRepository.save(Mockito.any(OpenCDXHeartRPMModel.class)))
                .thenAnswer(new Answer<OpenCDXHeartRPMModel>() {
                    @Override
                    public OpenCDXHeartRPMModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXHeartRPMModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                            argument.setPatientId(OpenCDXIdentifier.get());
                            argument.setNationalHealthId(OpenCDXIdentifier.get().toHexString());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXHeartRPMRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXHeartRPMModel>>() {
                    @Override
                    public Optional<OpenCDXHeartRPMModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXHeartRPMModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .nationalHealthId(argument.toHexString())
                                .measurementTakenOverInSeconds(60)
                                .sittingPositionFiveMinutes(true)
                                .build());
                    }
                });
        Mockito.when(this.openCDXHeartRPMRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXHeartRPMModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .measurementTakenOverInSeconds(60)
                                .sittingPositionFiveMinutes(true)
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
        this.openCDXHeartRPMService = new OpenCDXHeartRPMServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXHeartRPMRepository);
        this.openCDXHeartRPMRestController = new OpenCDXHeartRPMRestController(openCDXHeartRPMService);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
        Mockito.reset(this.openCDXHeartRPMRepository);
    }

    @Test
    void getHeartRPMRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/vitals/heartrpm/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void createHeartRPMRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/vitals/heartrpm")
                        .content(this.objectMapper.writeValueAsString(CreateHeartRPMRequest.newBuilder()
                                .setHeartRpmMeasurement(HeartRPM.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void updateHeartRPMRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/vitals/heartrpm")
                        .content(this.objectMapper.writeValueAsString(UpdateHeartRPMRequest.newBuilder()
                                .setHeartRpmMeasurement(HeartRPM.newBuilder()
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
    void deleteHeartRPM() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/vitals/heartrpm/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void listHeartRPMRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/vitals/heartrpm/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ListHeartRPMRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }
}
