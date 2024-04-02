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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.health.*;
import cdx.opencdx.health.model.OpenCDXBPMModel;
import cdx.opencdx.health.repository.OpenCDXBPMRepository;
import cdx.opencdx.health.service.OpenCDXBPMService;
import cdx.opencdx.health.service.impl.OpenCDXBPMServiceImpl;
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

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXBPMRestControllerTest {

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
    OpenCDXBPMRepository openCDXBPMRepository;

    OpenCDXBPMService openCDXBPMService;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    @MockBean
    Connection connection;

    OpenCDXBPMRestController openCDXBPMRestController;

    @BeforeEach
    public void setup() {

        Mockito.when(this.openCDXBPMRepository.save(Mockito.any(OpenCDXBPMModel.class)))
                .thenAnswer(new Answer<OpenCDXBPMModel>() {
                    @Override
                    public OpenCDXBPMModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXBPMModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                            argument.setPatientId(OpenCDXIdentifier.get());
                            argument.setNationalHealthId(OpenCDXIdentifier.get().toHexString());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXBPMRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXBPMModel>>() {
                    @Override
                    public Optional<OpenCDXBPMModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXBPMModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .nationalHealthId(argument.toHexString())
                                .cuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                                .armUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                                .systolic(80)
                                .diastolic(120)
                                .bpmUnits(BPMUnits.BARS)
                                .measurementTakenUsingCuff("yes")
                                .sittingPositionFiveMinutes(true)
                                .urinatedThirtyMinutesPrior(false)
                                .build());
                    }
                });
        Mockito.when(this.openCDXBPMRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXBPMModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .cuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                                .armUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                                .systolic(80)
                                .diastolic(120)
                                .bpmUnits(BPMUnits.BARS)
                                .measurementTakenUsingCuff("yes")
                                .sittingPositionFiveMinutes(true)
                                .urinatedThirtyMinutesPrior(false)
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
        this.openCDXBPMService = new OpenCDXBPMServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXBPMRepository);
        this.openCDXBPMRestController =
                new OpenCDXBPMRestController(openCDXBPMService);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
        Mockito.reset(this.openCDXBPMRepository);
    }

    @Test
    void getBPMRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/vitals/bpm/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void createBPMRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/vitals/bpm")
                        .content(this.objectMapper.writeValueAsString(CreateBPMRequest.newBuilder()
                                .setBpmMeasurement(BPM.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void updateBPMRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/vitals/bpm")
                        .content(this.objectMapper.writeValueAsString(UpdateBPMRequest.newBuilder()
                                .setBpmMeasurement(BPM.newBuilder()
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
    void deleteBPM() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/vitals/bpm/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void listBPMRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/vitals/bpm/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ListBPMRequest.newBuilder()
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
