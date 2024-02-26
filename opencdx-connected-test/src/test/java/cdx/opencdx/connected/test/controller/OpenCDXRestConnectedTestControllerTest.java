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
package cdx.opencdx.connected.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.model.OpenCDXConnectedTestModel;
import cdx.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.connected.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestConnectedTestControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    Connection connection;

    @MockBean
    OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @MockBean
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @BeforeEach
    public void setup() {
        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(argument)
                                .password("{noop}pass")
                                .nationalHealthId(UUID.randomUUID().toString())
                                .fullName(FullName.newBuilder()
                                        .setFirstName("bob")
                                        .setLastName("bob")
                                        .build())
                                .username("ab@safehealth.me")
                                .gender(Gender.GENDER_FEMALE)
                                .primaryContactInfo(ContactInfo.newBuilder()
                                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                                .setType(EmailType.EMAIL_TYPE_WORK)
                                                .setEmail("ab@safehealth.me")
                                                .build()))
                                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                                .setNumber("1234567890")
                                                .build()))
                                        .build())
                                .emailVerified(true)
                                .build());
                    }
                });
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

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
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest = ConnectedTest.newBuilder()
                .setBasicInfo(BasicInfo.newBuilder()
                        .setId("6511c2ffc289850d8dda157b")
                        .setOrganizationId(ObjectId.get().toHexString())
                        .setWorkspaceId(ObjectId.get().toHexString())
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .setTestDetails(TestDetails.newBuilder()
                        .setDeviceIdentifier(ObjectId.get().toHexString())
                        .setMediaId(ObjectId.get().toHexString())
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/")
                        .content(this.objectMapper.writeValueAsString(connectedTest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals("{\"submissionId\":\"6511c2ffc289850d8dda157b\"}", content);
    }

    @Test
    void getTestDetailsById() throws Exception {
        OpenCDXConnectedTestModel openCDXConnectedTestModel =
                new OpenCDXConnectedTestModel(ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                        .setBasicInfo(BasicInfo.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setNationalHealthId(UUID.randomUUID().toString())
                                .setUserId(ObjectId.get().toHexString())
                                .build())
                        .build());

        Mockito.when(this.openCDXConnectedTestRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXConnectedTestModel));

        MvcResult result = this.mockMvc
                .perform(get("/" + openCDXConnectedTestModel.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(
                this.objectMapper.writeValueAsString(openCDXConnectedTestModel.getProtobufMessage()), content);
        this.generateJSON();
    }

    void generateJSON() throws JsonProcessingException {
        log.info("Generating JSON");
        log.info(
                "ConnectedTest:\n{}",
                this.objectMapper.writeValueAsString(ConnectedTest.newBuilder()
                        .setBasicInfo(BasicInfo.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .build())));
        log.info(
                "TestSubmissionResponse:\n{}",
                this.objectMapper.writeValueAsString(TestSubmissionResponse.getDefaultInstance()));
        log.info("TestIdRequest:\n{}", this.objectMapper.writeValueAsString(TestIdRequest.getDefaultInstance()));
    }

    @Test
    void listConnectedTests() throws Exception {
        Mockito.when(this.openCDXConnectedTestRepository.findAllByUserId(
                        Mockito.any(ObjectId.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

        MvcResult result = this.mockMvc
                .perform(post("/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ConnectedTestListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .setUserId(new ObjectId().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(ConnectedTestListRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void listConnectedTests_2() throws Exception {
        Mockito.when(this.openCDXConnectedTestRepository.findAllByUserId(
                        Mockito.any(ObjectId.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXConnectedTestModel.builder()
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(ObjectId.get())
                                .id(ObjectId.get())
                                .basicInfo(BasicInfo.newBuilder()
                                        .setUserId(ObjectId.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .build())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        MvcResult result = this.mockMvc
                .perform(post("/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ConnectedTestListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .setUserId(new ObjectId().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(ConnectedTestListRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void listConnectedTestsByNHID() throws Exception {
        Mockito.when(this.openCDXConnectedTestRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

        MvcResult result = this.mockMvc
                .perform(post("/listbynhid")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ConnectedTestListByNHIDRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .setNationalHealthId(UUID.randomUUID().toString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info(
                "JSON: \n{}",
                this.objectMapper.writeValueAsString(ConnectedTestListByNHIDRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }

    @Test
    void listConnectedTestsByNHID_2() throws Exception {
        Mockito.when(this.openCDXConnectedTestRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXConnectedTestModel.builder()
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(ObjectId.get())
                                .id(ObjectId.get())
                                .basicInfo(BasicInfo.newBuilder()
                                        .setUserId(ObjectId.get().toHexString())
                                        .setNationalHealthId(UUID.randomUUID().toString())
                                        .build())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        MvcResult result = this.mockMvc
                .perform(post("/listbynhid")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ConnectedTestListByNHIDRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSortAscending(true)
                                        .build())
                                .setNationalHealthId(UUID.randomUUID().toString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("JSON: \n{}", this.objectMapper.writeValueAsString(ConnectedTestListRequest.getDefaultInstance()));
        log.info("Received\n {}", content);
    }
}
