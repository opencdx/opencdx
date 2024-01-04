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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.organization.CreateWorkspaceRequest;
import cdx.opencdx.grpc.organization.UpdateWorkspaceRequest;
import cdx.opencdx.grpc.organization.Workspace;
import cdx.opencdx.iam.model.OpenCDXIAMWorkspaceModel;
import cdx.opencdx.iam.repository.OpenCDXIAMWorkspaceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
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
import org.springframework.security.authentication.AuthenticationManager;
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
class OpenCDXIAMWorkspaceRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OpenCDXIAMWorkspaceRepository openCDXIAMWorkspaceRepository;

    @MockBean
    Connection connection;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        when(this.openCDXIAMWorkspaceRepository.save(Mockito.any(OpenCDXIAMWorkspaceModel.class)))
                .thenAnswer(new Answer<OpenCDXIAMWorkspaceModel>() {
                    @Override
                    public OpenCDXIAMWorkspaceModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIAMWorkspaceModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        when(this.openCDXIAMWorkspaceRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMWorkspaceModel>>() {
                    @Override
                    public Optional<OpenCDXIAMWorkspaceModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXIAMWorkspaceModel.builder().id(argument).build());
                    }
                });
        when(this.openCDXIAMWorkspaceRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
        Mockito.reset(this.openCDXIAMWorkspaceRepository);
    }

    @Test
    void checkMockMvc() throws Exception { // Assertions.assertNotNull(greetingController);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void createWorkspace() throws Exception {
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/workspace")
                        .content(this.objectMapper.writeValueAsString(CreateWorkspaceRequest.newBuilder()
                                .setWorkspace(Workspace.newBuilder()
                                        .setCreatedDate(Timestamp.newBuilder()
                                                .setSeconds(10L)
                                                .setNanos(5)
                                                .build())
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void getWorkspaceDetailsById() throws Exception {
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get(
                                "/workspace/" + ObjectId.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void updateWorkspace() throws Exception {
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.put("/workspace")
                        .content(this.objectMapper.writeValueAsString(UpdateWorkspaceRequest.newBuilder()
                                .setWorkspace(Workspace.newBuilder()
                                        .setId(ObjectId.get().toHexString())
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void listWorkspaces() throws Exception {
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/workspace/list")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }
}
