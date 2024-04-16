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
package cdx.opencdx.logistics.controller;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.data.TestCase;
import cdx.opencdx.grpc.service.logistics.TestCaseListRequest;
import cdx.opencdx.logistics.model.OpenCDXTestCaseModel;
import cdx.opencdx.logistics.repository.OpenCDXTestCaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestTestCaseControllerTest {

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
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        Mockito.when(openCDXTestCaseRepository.save(Mockito.any(OpenCDXTestCaseModel.class)))
                .thenAnswer(new Answer<OpenCDXTestCaseModel>() {
                    @Override
                    public OpenCDXTestCaseModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXTestCaseModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXTestCaseRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXTestCaseModel>>() {
                    @Override
                    public Optional<OpenCDXTestCaseModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
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
                .setId(OpenCDXIdentifier.get().toHexString())
                .setManufacturerId("manufacturerId")
                .setBatchNumber("2")
                .build();

        MvcResult result = this.mockMvc
                .perform(get("/testcase/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addTestCase() throws Exception {
        TestCase testCase = TestCase.newBuilder(TestCase.getDefaultInstance())
                .setId(OpenCDXIdentifier.get().toHexString())
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .setVendorId(OpenCDXIdentifier.get().toHexString())
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
                .setId(OpenCDXIdentifier.get().toHexString())
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .setVendorId(OpenCDXIdentifier.get().toHexString())
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
                .setId(OpenCDXIdentifier.get().toHexString())
                .setManufacturerId("manufacturerId")
                .setBatchNumber("2")
                .build();

        MvcResult result = this.mockMvc
                .perform(delete("/testcase/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void listTestCase() throws Exception {
        Mockito.when(this.openCDXTestCaseRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXTestCaseModel.builder()
                                .manufacturerId(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        MvcResult result2 = this.mockMvc
                .perform(post("/testcase/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(TestCaseListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSort("true")
                                        .setSortAscending(true)
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content2 = result2.getResponse().getContentAsString();
        log.info("Received\n {}", content2);
    }

    @Test
    void listTestCaseElse() throws Exception {
        Mockito.when(this.openCDXTestCaseRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXTestCaseModel.builder()
                                .manufacturerId(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        MvcResult result2 = this.mockMvc
                .perform(post("/testcase/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(TestCaseListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSort("true")
                                        .setSortAscending(false)
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content2 = result2.getResponse().getContentAsString();
        log.info("Received\n {}", content2);
    }
}
