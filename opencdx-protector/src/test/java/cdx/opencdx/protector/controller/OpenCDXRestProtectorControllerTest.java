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
package cdx.opencdx.protector.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.neural.protector.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.Optional;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestProtectorControllerTest {

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
        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(argument)
                                .password("{noop}pass")
                                .username("ab@safehealth.me")
                                .emailVerified(true)
                                .build());
                    }
                });
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

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
    void postDetectAnomalies() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/detectAnomalies")
                        .content(objectMapper.writeValueAsString(AnomalyDetectionDataRequest.newBuilder()
                                .setAnomalyDetectionData(AnomalyDetectionData.newBuilder()
                                        .setUserId(OpenCDXIdentifier.get().toHexString())
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(
                "{\"response\":\"SecurityResponse [detectAnomalies]\"}",
                result.getResponse().getContentAsString());
    }

    @Test
    void postEnforceAuthorizationControl() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/authorize")
                        .content(this.objectMapper.writeValueAsString(AuthorizationControlDataRequest.newBuilder()
                                .setAuthorizationControlData(AuthorizationControlData.newBuilder()
                                        .setUserId(OpenCDXIdentifier.get().toHexString())
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(
                "{\"response\":\"SecurityResponse [enforceAuthorizationControl]\"}",
                result.getResponse().getContentAsString());
    }

    @Test
    void postProtectPrivacy() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/protectPrivacy").content("{}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(
                "{\"response\":\"SecurityResponse [protectPrivacy]\"}",
                result.getResponse().getContentAsString());
    }

    @Test
    void postMonitorRealTimeActivity() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/monitorRealTime")
                        .content(this.objectMapper.writeValueAsString(RealTimeMonitoringDataRequest.newBuilder()
                                .setRealTimeMonitoringData(RealTimeMonitoringData.newBuilder()
                                        .setMonitoredEntity(
                                                OpenCDXIdentifier.get().toHexString())
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(
                "{\"response\":\"SecurityResponse [monitorRealTimeActivity]\"}",
                result.getResponse().getContentAsString());
    }

    @Test
    void postAnalyzeUserBehavior() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/analyzeUserBehavior")
                        .content(objectMapper.writeValueAsString(UserBehaviorAnalysisDataRequest.newBuilder()
                                .setUserBehaviorAnalysisData(UserBehaviorAnalysisData.newBuilder()
                                        .setUserId(OpenCDXIdentifier.get().toHexString())
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(
                "{\"response\":\"SecurityResponse [analyzeUserBehavior]\"}",
                result.getResponse().getContentAsString());
    }
}
