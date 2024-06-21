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
package cdx.opencdx.classification.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.*;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXClassificationResponseModel;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXClassificationRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.classification.ClassificationRequest;
import cdx.opencdx.grpc.service.classification.RuleSetsRequest;
import cdx.opencdx.grpc.service.health.TestIdRequest;
import cdx.opencdx.grpc.service.logistics.*;
import cdx.opencdx.grpc.service.media.GetMediaRequest;
import cdx.opencdx.grpc.service.media.GetMediaResponse;
import cdx.opencdx.grpc.types.EmailType;
import cdx.opencdx.grpc.types.Gender;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
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
class OpenCDXRestClassificationControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    Connection connection;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OpenCDXQuestionnaireClient openCDXQuestionnaireClient;

    @MockBean
    OpenCDXConnectedTestClient openCDXConnectedTestClient;

    @MockBean
    OpenCDXClassificationRepository openCDXClassificationRepository;

    @MockBean
    OpenCDXDeviceClient openCDXDeviceClient;

    @MockBean
    OpenCDXMediaClient openCDXMediaClient;

    @MockBean
    OpenCDXManufacturerClient openCDXManufacturerClient;

    @MockBean
    OpenCDXTestCaseClient openCDXTestCaseClient;

    @MockBean
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    public void setup() {
        Mockito.when(this.openCDXTestCaseClient.listTestCase(
                        Mockito.any(TestCaseListRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(TestCaseListResponse.newBuilder()
                        .addTestCases(TestCase.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .build())
                        .build());

        Mockito.when(this.openCDXTestCaseClient.getTestCaseById(Mockito.any(), Mockito.any()))
                .thenAnswer(new Answer<TestCase>() {
                    @Override
                    public TestCase answer(InvocationOnMock invocation) throws Throwable {
                        TestCaseIdRequest argument = invocation.getArgument(0);
                        return TestCase.newBuilder()
                                .setId(argument.getTestCaseId())
                                .build();
                    }
                });

        Mockito.when(this.openCDXManufacturerClient.getManufacturerById(
                        Mockito.any(ManufacturerIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenAnswer(new Answer<Manufacturer>() {
                    @Override
                    public Manufacturer answer(InvocationOnMock invocation) throws Throwable {
                        ManufacturerIdRequest argument = invocation.getArgument(0);
                        return Manufacturer.newBuilder()
                                .setId(argument.getManufacturerId())
                                .build();
                    }
                });

        Mockito.when(this.openCDXDeviceClient.getDeviceById(
                        Mockito.any(DeviceIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenAnswer(new Answer<Device>() {
                    @Override
                    public Device answer(InvocationOnMock invocation) throws Throwable {
                        DeviceIdRequest argument = invocation.getArgument(0);
                        return Device.newBuilder().setId(argument.getDeviceId()).build();
                    }
                });

        Mockito.when(this.openCDXConnectedTestClient.getTestDetailsById(
                        Mockito.any(TestIdRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenAnswer(new Answer<ConnectedTest>() {
                    @Override
                    public ConnectedTest answer(InvocationOnMock invocation) throws Throwable {
                        TestIdRequest argument = invocation.getArgument(0);
                        return ConnectedTest.newBuilder()
                                .setBasicInfo(BasicInfo.newBuilder()
                                        .setId(argument.getTestId())
                                        .build())
                                .build();
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .fullName(FullName.newBuilder()
                                        .setLastName("Bobby")
                                        .setFirstName("Bob")
                                        .build())
                                .primaryContactInfo(ContactInfo.newBuilder()
                                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                                .setEmail("bob@opencdx.org")
                                                .setType(EmailType.EMAIL_TYPE_PERSONAL)
                                                .build()))
                                        .build())
                                .addresses(List.of(Address.newBuilder()
                                        .setCity("New York")
                                        .setCountryId(ObjectId.get().toHexString())
                                        .setPostalCode("10001")
                                        .setAddress1("123 Main St")
                                        .build()))
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findByNationalHealthId(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(argument)
                                .addresses(List.of(Address.newBuilder()
                                        .setCity("New York")
                                        .setCountryId(ObjectId.get().toHexString())
                                        .setPostalCode("10001")
                                        .setAddress1("123 Main St")
                                        .build()))
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        Mockito.when(this.openCDXQuestionnaireClient.getUserQuestionnaireData(Mockito.any(), Mockito.any()))
                .thenReturn(UserQuestionnaireData.getDefaultInstance());
        Mockito.when(this.openCDXClassificationRepository.save(Mockito.any(OpenCDXClassificationResponseModel.class)))
                .thenAnswer(new Answer<OpenCDXClassificationResponseModel>() {
                    @Override
                    public OpenCDXClassificationResponseModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXClassificationResponseModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
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

        Mockito.when(this.openCDXMediaClient.getMedia(
                        Mockito.any(GetMediaRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(GetMediaResponse.newBuilder()
                        .setMedia(Media.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build());
        Mockito.when(this.openCDXQuestionnaireClient.getUserQuestionnaireData(Mockito.any(), Mockito.any()))
                .thenReturn(UserQuestionnaireData.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build());

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
    }

    @Test
    void checkMockMvc() throws Exception {
        Assertions.assertNotNull(mockMvc);
    }

    @RepeatedTest(100)
    void testSubmitClassification() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/classify")
                        .content(this.objectMapper.writeValueAsString(ClassificationRequest.newBuilder()
                                .setUserAnswer(UserAnswer.newBuilder()
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setTextResult("Test")
                                        .setUserQuestionnaireId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                                        .setGender(Gender.GENDER_MALE)
                                        .setSubmittingUserId(
                                                OpenCDXIdentifier.get().toHexString())
                                        .setAge(30))
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testGetRuleSets() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/ruleset/list")
                        .content(this.objectMapper.writeValueAsString(RuleSetsRequest.newBuilder()
                                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }
}
