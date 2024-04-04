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
package cdx.opencdx.health.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.*;
import cdx.opencdx.commons.service.impl.OpenCDXClassificationMessageServiceImpl;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.connected.*;
import cdx.opencdx.health.model.OpenCDXConnectedTestModel;
import cdx.opencdx.health.repository.OpenCDXConnectedTestRepository;
import cdx.opencdx.health.service.OpenCDXConnectedTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXConnectedTestServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXConnectedTestService openCDXConnectedTestService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @Autowired
    OpenCDXCommunicationService openCDXCommunicationService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Autowired
    OpenCDXMessageService openCDXMessageService;

    OpenCDXClassificationMessageService openCDXClassificationMessageService;

    @BeforeEach
    void beforeEach() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .fullName(FullName.newBuilder()
                                        .setFirstName("bob")
                                        .setLastName("bob")
                                        .build())
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
                                .build());
                    }
                });

        this.openCDXClassificationMessageService = new OpenCDXClassificationMessageServiceImpl(
                this.openCDXMessageService,
                this.openCDXDocumentValidator,
                this.openCDXProfileRepository,
                openCDXCurrentUser);

        this.openCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService,
                openCDXConnectedTestRepository,
                openCDXCurrentUser,
                objectMapper,
                openCDXCommunicationService,
                openCDXProfileRepository,
                openCDXDocumentValidator,
                openCDXClassificationMessageService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void submitTest() {
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder(BasicInfo.getDefaultInstance())
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setTestDetails(TestDetails.newBuilder()
                        .setDeviceIdentifier(OpenCDXIdentifier.get().toHexString())
                        .setMediaId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertEquals(
                TestSubmissionResponse.newBuilder()
                        .setSubmissionId(connectedTest.getBasicInfo().getId())
                        .build(),
                this.openCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void submitTest2() {

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .fullName(FullName.newBuilder()
                                        .setFirstName("bob")
                                        .setLastName("bob")
                                        .build())
                                .gender(Gender.GENDER_FEMALE)
                                .primaryContactInfo(ContactInfo.newBuilder()
                                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                                .setNumber("1234567890")
                                                .build()))
                                        .build())
                                .build());
                    }
                });
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder(BasicInfo.getDefaultInstance())
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setTestDetails(TestDetails.newBuilder()
                        .setDeviceIdentifier(OpenCDXIdentifier.get().toHexString())
                        .setMediaId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertEquals(
                TestSubmissionResponse.newBuilder()
                        .setSubmissionId(connectedTest.getBasicInfo().getId())
                        .build(),
                this.openCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void submitTest_fail() {
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest =
                ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance()).build();
        Assertions.assertThrows(
                OpenCDXFailedPrecondition.class, () -> this.openCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void submitTest_fail2() {
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest =
                ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance()).build();
        Assertions.assertThrows(
                OpenCDXFailedPrecondition.class, () -> this.openCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void submitTestFail() throws JsonProcessingException {
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder(BasicInfo.getDefaultInstance())
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setTestDetails(TestDetails.newBuilder()
                        .setDeviceIdentifier(OpenCDXIdentifier.get().toHexString())
                        .setMediaId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        OpenCDXConnectedTestServiceImpl testOpenCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService,
                this.openCDXConnectedTestRepository,
                openCDXCurrentUser,
                mapper,
                openCDXCommunicationService,
                openCDXProfileRepository,
                openCDXDocumentValidator,
                openCDXClassificationMessageService);
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> testOpenCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void submitTestFail2() throws JsonProcessingException {
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder(BasicInfo.getDefaultInstance())
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setTestDetails(TestDetails.newBuilder()
                        .setDeviceIdentifier(OpenCDXIdentifier.get().toHexString())
                        .setMediaId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        this.openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.empty();
                    }
                });

        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        OpenCDXConnectedTestServiceImpl testOpenCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService,
                this.openCDXConnectedTestRepository,
                openCDXCurrentUser,
                mapper,
                openCDXCommunicationService,
                openCDXProfileRepository,
                openCDXDocumentValidator,
                openCDXClassificationMessageService);
        Assertions.assertThrows(OpenCDXNotFound.class, () -> testOpenCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void submitTestFail3() throws JsonProcessingException {
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder(BasicInfo.getDefaultInstance())
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setTestDetails(TestDetails.newBuilder()
                        .setDeviceIdentifier(OpenCDXIdentifier.get().toHexString())
                        .setMediaId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        this.openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .fullName(FullName.newBuilder()
                                        .setFirstName("bob")
                                        .setLastName("bob")
                                        .build())
                                .primaryContactInfo(null)
                                .build());
                    }
                });

        OpenCDXConnectedTestServiceImpl testOpenCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService,
                this.openCDXConnectedTestRepository,
                openCDXCurrentUser,
                objectMapper,
                openCDXCommunicationService,
                openCDXProfileRepository,
                openCDXDocumentValidator,
                openCDXClassificationMessageService);
        Assertions.assertDoesNotThrow(() -> testOpenCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void submitTestFail4() throws JsonProcessingException {
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder(BasicInfo.getDefaultInstance())
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setTestDetails(TestDetails.newBuilder()
                        .setDeviceIdentifier(OpenCDXIdentifier.get().toHexString())
                        .setMediaId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        this.openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .fullName(FullName.newBuilder()
                                        .setFirstName("bob")
                                        .setLastName("bob")
                                        .build())
                                .primaryContactInfo(ContactInfo.newBuilder()
                                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                                .setType(EmailType.EMAIL_TYPE_WORK)
                                                .setEmail("test@opencdx.org")
                                                .build()))
                                        .build())
                                .build());
                    }
                });

        OpenCDXConnectedTestServiceImpl testOpenCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService,
                this.openCDXConnectedTestRepository,
                openCDXCurrentUser,
                objectMapper,
                openCDXCommunicationService,
                openCDXProfileRepository,
                openCDXDocumentValidator,
                openCDXClassificationMessageService);
        Assertions.assertDoesNotThrow(() -> testOpenCDXConnectedTestService.submitTest(connectedTest));
    }

    @Test
    void getTestDetailsById() {
        OpenCDXConnectedTestModel openCDXConnectedTestModel =
                new OpenCDXConnectedTestModel(ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                        .setBasicInfo(BasicInfo.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .setNationalHealthId(UUID.randomUUID().toString())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .setTestDetails(TestDetails.newBuilder()
                                .setMediaId(OpenCDXIdentifier.get().toHexString()))
                        .build());

        Mockito.when(this.openCDXConnectedTestRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXConnectedTestModel));
        Assertions.assertEquals(
                openCDXConnectedTestModel.getProtobufMessage(),
                this.openCDXConnectedTestService.getTestDetailsById(TestIdRequest.newBuilder()
                        .setTestId(openCDXConnectedTestModel.getId().toHexString())
                        .build()));
    }

    @Test
    void getTestDetailsByIdFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        OpenCDXConnectedTestModel openCDXConnectedTestModel =
                new OpenCDXConnectedTestModel(ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                        .setBasicInfo(BasicInfo.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .setNationalHealthId(UUID.randomUUID().toString())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build());

        Mockito.when(this.openCDXConnectedTestRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXConnectedTestModel));

        OpenCDXConnectedTestServiceImpl testOpenCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService,
                this.openCDXConnectedTestRepository,
                openCDXCurrentUser,
                mapper,
                openCDXCommunicationService,
                openCDXProfileRepository,
                openCDXDocumentValidator,
                openCDXClassificationMessageService);
        TestIdRequest testIdRequest = TestIdRequest.newBuilder()
                .setTestId(openCDXConnectedTestModel.getId().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> testOpenCDXConnectedTestService.getTestDetailsById(testIdRequest));
    }
}
