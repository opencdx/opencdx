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
package health.safe.api.opencdx.connected.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.open_connected_test.v2alpha.BasicInfo;
import cdx.open_connected_test.v2alpha.ConnectedTest;
import cdx.open_connected_test.v2alpha.TestIdRequest;
import cdx.open_connected_test.v2alpha.TestSubmissionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.connected.test.model.OpenCDXConnectedTest;
import health.safe.api.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import io.nats.client.Connection;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class RestConnectedTestControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    Connection connection;

    @MockBean
    OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    @BeforeEach
    public void setup() {
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
    void submitTest() throws Exception {
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTest.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest = ConnectedTest.newBuilder()
                .setBasicInfo(
                        BasicInfo.newBuilder().setId("6511c2ffc289850d8dda157b").build())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/connected-test")
                        .content(this.objectMapper.writeValueAsString(connectedTest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals("{\"submissionId\":\"6511c2ffc289850d8dda157b\"}", content);
    }

    @Test
    void getTestDetailsById() throws Exception {
        OpenCDXConnectedTest openCDXConnectedTest =
                new OpenCDXConnectedTest(ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                        .setBasicInfo(BasicInfo.newBuilder()
                                .setId(new ObjectId().toHexString())
                                .build())
                        .build());

        Mockito.when(this.openCDXConnectedTestRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXConnectedTest));

        MvcResult result = this.mockMvc
                .perform(get("/connected-test/" + openCDXConnectedTest.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(
                this.objectMapper.writeValueAsString(openCDXConnectedTest.getProtobufMessage()), content);
        this.generateJSON();
    }

    void generateJSON() throws JsonProcessingException {
        log.info("Generating JSON");
        log.info("ConnectedTest:\n{}", this.objectMapper.writeValueAsString(ConnectedTest.newBuilder().setBasicInfo(BasicInfo.newBuilder().setId(new ObjectId().toHexString()).build())));
        log.info(
                "TestSubmissionResponse:\n{}",
                this.objectMapper.writeValueAsString(TestSubmissionResponse.getDefaultInstance()));
        log.info("TestIdRequest:\n{}", this.objectMapper.writeValueAsString(TestIdRequest.getDefaultInstance()));
    }
}
