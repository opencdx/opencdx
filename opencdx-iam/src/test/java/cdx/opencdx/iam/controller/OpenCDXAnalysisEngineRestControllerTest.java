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
package cdx.opencdx.iam.controller;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.AnalysisEngine;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.iam.CreateAnalysisEngineRequest;
import cdx.opencdx.grpc.service.iam.ListAnalysisEnginesRequest;
import cdx.opencdx.grpc.service.iam.UpdateAnalysisEngineRequest;
import cdx.opencdx.commons.model.OpenCDXAnalysisEngineModel;
import cdx.opencdx.commons.repository.OpenCDXAnalysisEngineRepository;
import cdx.opencdx.iam.service.OpenCDXAnalysisEngineService;
import cdx.opencdx.iam.service.impl.OpenCDXAnalysisEngineServiceImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXAnalysisEngineRestControllerTest {

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
    OpenCDXAnalysisEngineRepository openCDXAnalysisEngineRepository;

    OpenCDXAnalysisEngineService AnalysisEngineService;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    @MockBean
    Connection connection;

    OpenCDXAnalysisEngineRestController openCDXAnalysisEngineRestController;

    @BeforeEach
    public void setup() {

        Mockito.when(this.openCDXAnalysisEngineRepository.save(Mockito.any(OpenCDXAnalysisEngineModel.class)))
                .thenAnswer(new Answer<OpenCDXAnalysisEngineModel>() {
                    @Override
                    public OpenCDXAnalysisEngineModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXAnalysisEngineModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                            argument.setOrganizationId(OpenCDXIdentifier.get());
                            argument.setWorkspaceId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXAnalysisEngineRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXAnalysisEngineModel>>() {
                    @Override
                    public Optional<OpenCDXAnalysisEngineModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXAnalysisEngineModel.builder()
                                .id(argument)
                                .organizationId(OpenCDXIdentifier.get())
                                .workspaceId(OpenCDXIdentifier.get())
                                .name("default")
                                .build());
                    }
                });
        Mockito.when(this.openCDXAnalysisEngineRepository.findAllByOrganizationId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXAnalysisEngineModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .organizationId(OpenCDXIdentifier.get())
                                .workspaceId(OpenCDXIdentifier.get())
                                .name("default")
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
        this.AnalysisEngineService = new OpenCDXAnalysisEngineServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXAnalysisEngineRepository);
        this.openCDXAnalysisEngineRestController = new OpenCDXAnalysisEngineRestController(AnalysisEngineService);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
        Mockito.reset(this.openCDXAnalysisEngineRepository);
    }

    @Test
    void getAnalysisEngineRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/analysis-engine/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void createAnalysisEngineRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/analysis-engine")
                        .content(this.objectMapper.writeValueAsString(CreateAnalysisEngineRequest.newBuilder()
                                .setAnalysisEngine(AnalysisEngine.newBuilder()
                                        .setOrganizationId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void updateAnalysisEngineRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/analysis-engine")
                        .content(this.objectMapper.writeValueAsString(UpdateAnalysisEngineRequest.newBuilder()
                                .setAnalysisEngine(AnalysisEngine.newBuilder()
                                        .setOrganizationId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                                        .setId(OpenCDXIdentifier.get().toHexString())
                                        .build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void deleteAnalysisEngine() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/analysis-engine/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void listAnalysisEngineRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/analysis-engine/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ListAnalysisEnginesRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }
}
