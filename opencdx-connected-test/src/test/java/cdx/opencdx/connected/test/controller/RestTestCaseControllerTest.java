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
package cdx.opencdx.connected.test.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.model.OpenCDXTestCaseModel;
import cdx.opencdx.connected.test.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.grpc.inventory.TestCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class RestTestCaseControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @MockBean
    private OpenCDXTestCaseRepository openCDXTestCaseRepository;

    @BeforeEach
    public void setup() {
        Mockito.when(openCDXTestCaseRepository.save(Mockito.any(OpenCDXTestCaseModel.class)))
                .thenAnswer(new Answer<OpenCDXTestCaseModel>() {
                    @Override
                    public OpenCDXTestCaseModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXTestCaseModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXTestCaseRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXTestCaseModel>>() {
                    @Override
                    public Optional<OpenCDXTestCaseModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXTestCaseModel.builder().id(argument).build());
                    }
                });
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getTestCaseById() throws Exception {
        TestCase testCase = TestCase.newBuilder(TestCase.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setManufacturerId("manufacturerId")
                .setBatchNumber("2")
                .build();

        MvcResult result = this.mockMvc
                .perform(get("/testcase/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addTestCase() throws Exception {
        TestCase testCase = TestCase.newBuilder(TestCase.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setManufacturerId(ObjectId.get().toHexString())
                .setVendorId(ObjectId.get().toHexString())
                .setBatchNumber("2")
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/testcase")
                        .content(this.objectMapper.writeValueAsString(testCase))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void updateTestCase() throws Exception {
        TestCase testCase = TestCase.newBuilder(TestCase.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setManufacturerId(ObjectId.get().toHexString())
                .setVendorId(ObjectId.get().toHexString())
                .setBatchNumber("2")
                .build();

        MvcResult result = this.mockMvc
                .perform(put("/testcase")
                        .content(this.objectMapper.writeValueAsString(testCase))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteTestCase() throws Exception {
        TestCase testCase = TestCase.newBuilder(TestCase.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setManufacturerId("manufacturerId")
                .setBatchNumber("2")
                .build();

        MvcResult result = this.mockMvc
                .perform(delete("/testcase/" + ObjectId.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }
}
