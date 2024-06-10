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

import cdx.opencdx.classification.service.OpenCDXCDCPayloadService;
import cdx.opencdx.classification.service.impl.OpenCDXClassificationServiceImpl;
import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.service.*;
import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXClassificationModel;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXClassificationRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.*;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.classification.ClassificationRequest;
import cdx.opencdx.grpc.service.classification.ClassificationResponse;
import cdx.opencdx.grpc.service.classification.RuleSetsRequest;
import cdx.opencdx.grpc.service.classification.RuleSetsResponse;
import cdx.opencdx.grpc.service.health.TestIdRequest;
import cdx.opencdx.grpc.service.logistics.*;
import cdx.opencdx.grpc.types.EmailType;
import cdx.opencdx.grpc.types.Gender;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXGrpcClassificationControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXOrderMessageService openCDXOrderMessageService;

    OpenCDXClassificationServiceImpl classificationService;

    OpenCDXGrpcClassificationController openCDXGrpcClassificationController;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Autowired
    OpenCDXAnalysisEngine openCDXClassifyProcessorService;

    @Autowired
    OpenCDXConnectedLabMessageService openCDXConnectedLabMessageService;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXMediaClient openCDXMediaClient;

    @Mock
    OpenCDXConnectedTestClient openCDXConnectedTestClient;

    @Mock
    OpenCDXQuestionnaireClient openCDXQuestionnaireClient;

    @Mock
    OpenCDXClassificationRepository openCDXClassificationRepository;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @Mock
    OpenCDXCDCPayloadService openCDXCDCPayloadService;

    @Mock
    OpenCDXCommunicationService openCDXCommunicationService;

    @MockBean
    OpenCDXDeviceClient openCDXDeviceClient;

    @MockBean
    OpenCDXManufacturerClient openCDXManufacturerClient;

    @MockBean
    OpenCDXTestCaseClient openCDXTestCaseClient;

    @BeforeEach
    void setUp() {

        Mockito.when(this.openCDXTestCaseClient.listTestCase(
                        Mockito.any(TestCaseListRequest.class), Mockito.any(OpenCDXCallCredentials.class)))
                .thenReturn(TestCaseListResponse.newBuilder()
                        .addTestCases(TestCase.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
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
                                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                                        .setPostalCode("10001")
                                        .setAddress1("123 Main St")
                                        .build()))
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(argument)
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
                                .userId(OpenCDXIdentifier.get())
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

        Mockito.when(this.openCDXClassificationRepository.save(Mockito.any(OpenCDXClassificationModel.class)))
                .thenAnswer(new Answer<OpenCDXClassificationModel>() {
                    @Override
                    public OpenCDXClassificationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXClassificationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        this.classificationService = new OpenCDXClassificationServiceImpl(
                this.openCDXAuditService,
                this.objectMapper,
                openCDXCurrentUser,
                openCDXDocumentValidator,
                openCDXMediaClient,
                openCDXConnectedTestClient,
                openCDXQuestionnaireClient,
                this.openCDXClassifyProcessorService,
                openCDXClassificationRepository,
                openCDXProfileRepository,
                openCDXOrderMessageService,
                openCDXCommunicationService,
                openCDXCDCPayloadService,
                openCDXConnectedLabMessageService);
        this.openCDXGrpcClassificationController = new OpenCDXGrpcClassificationController(this.classificationService);
    }

    @RepeatedTest(100)
    void submitClassification() {
        StreamObserver<ClassificationResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ClassificationRequest request = ClassificationRequest.newBuilder()
                .setUserAnswer(UserAnswer.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setConnectedTestId(OpenCDXIdentifier.get().toHexString())
                        .setGender(Gender.GENDER_MALE)
                        .setAge(30))
                .build();

        this.openCDXGrpcClassificationController.classify(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ClassificationResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getRuleSets() {
        StreamObserver<RuleSetsResponse> responseObserver = Mockito.mock(StreamObserver.class);

        RuleSetsRequest request = RuleSetsRequest.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .build();

        this.openCDXGrpcClassificationController.getRuleSets(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(RuleSetsResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
