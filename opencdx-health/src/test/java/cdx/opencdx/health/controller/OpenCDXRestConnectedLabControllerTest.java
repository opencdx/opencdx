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
import cdx.opencdx.grpc.data.BasicInfo;
import cdx.opencdx.grpc.data.ConnectedLab;
import cdx.opencdx.grpc.data.LabFindings;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.CreateConnectedLabRequest;
import cdx.opencdx.grpc.service.logistics.ListOrdersRequest;
import cdx.opencdx.health.config.OpenCDXLabConnectionFactoryBean;
import cdx.opencdx.health.model.OpenCDXConnectedLabModel;
import cdx.opencdx.health.repository.OpenCDXConnectedLabRepository;
import cdx.opencdx.health.service.OpenCDXConnectedLabService;
import cdx.opencdx.health.service.impl.OpenCDXConnectedLabServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
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

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestConnectedLabControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXLabConnectionFactoryBean openCDXLabConnectionFactoryBean;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXConnectedLabRepository openCDXConnectedLabRepository;

    @MockBean
    OpenCDXConnectedLabService openCDXConnectedLabService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        Mockito.when(this.openCDXConnectedLabRepository.save(Mockito.any(OpenCDXConnectedLabModel.class)))
                .thenAnswer(new Answer<OpenCDXConnectedLabModel>() {
                    @Override
                    public OpenCDXConnectedLabModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXConnectedLabModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXConnectedLabRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXConnectedLabModel>>() {
                    @Override
                    public Optional<OpenCDXConnectedLabModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);

                        return Optional.of(OpenCDXConnectedLabModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .identifier("default")
                                .created(Instant.now())
                                .modified(Instant.now())
                                .creator(OpenCDXIdentifier.get())
                                .modifier(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        Mockito.when(this.openCDXConnectedLabRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);

        Mockito.when(this.openCDXConnectedLabRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXConnectedLabModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .identifier("default")
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXConnectedLabRepository.findByOrganizationIdAndWorkspaceId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXConnectedLabModel>>() {
                    @Override
                    public Optional<OpenCDXConnectedLabModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);

                        return Optional.of(OpenCDXConnectedLabModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .identifier("default")
                                .created(Instant.now())
                                .modified(Instant.now())
                                .creator(OpenCDXIdentifier.get())
                                .modifier(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        this.openCDXConnectedLabService = new OpenCDXConnectedLabServiceImpl(
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXConnectedLabRepository,
                openCDXLabConnectionFactoryBean);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void submitLabFindings() throws Exception {
        LabFindings request = LabFindings.newBuilder()
                .setBasicInfo(BasicInfo.newBuilder()
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/lab/findings")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void getConnectedLab() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/lab/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void createConnectedLab() throws Exception {
        CreateConnectedLabRequest request = CreateConnectedLabRequest.newBuilder()
                .setConnectedLab(ConnectedLab.newBuilder()
                        .setIdentifier("default")
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .setCreated(Timestamp.newBuilder().setSeconds(1000000).build())
                        .setModified(Timestamp.newBuilder().setSeconds(1000000).build())
                        .setCreator(OpenCDXIdentifier.get().toHexString())
                        .setModifier(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/lab")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void updateConnectedLabOrder() throws Exception {
        CreateConnectedLabRequest request = CreateConnectedLabRequest.newBuilder()
                .setConnectedLab(ConnectedLab.newBuilder()
                        .setIdentifier("default")
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .setCreated(Timestamp.newBuilder().setSeconds(1000000).build())
                        .setModified(Timestamp.newBuilder().setSeconds(1000000).build())
                        .setCreator(OpenCDXIdentifier.get().toHexString())
                        .setModifier(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(put("/lab")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteConnectedLab() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/lab/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void listConnectedLabs() throws Exception {
        ListOrdersRequest listOrdersRequest = ListOrdersRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/lab/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(listOrdersRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
    }
}
