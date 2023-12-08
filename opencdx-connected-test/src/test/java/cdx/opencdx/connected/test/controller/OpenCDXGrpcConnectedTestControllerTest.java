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

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.model.OpenCDXConnectedTestModel;
import cdx.opencdx.connected.test.repository.OpenCDXConnectedTestRepository;
import cdx.opencdx.connected.test.service.OpenCDXConnectedTestService;
import cdx.opencdx.connected.test.service.impl.OpenCDXConnectedTestServiceImpl;
import cdx.opencdx.grpc.connected.*;
import cdx.opencdx.grpc.profile.ContactInfo;
import cdx.opencdx.grpc.profile.FullName;
import cdx.opencdx.grpc.profile.PhoneNumber;
import cdx.opencdx.grpc.profile.PhoneType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.Collections;
import java.util.Optional;
import org.bson.types.ObjectId;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXGrpcConnectedTestControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    OpenCDXConnectedTestRepository openCDXConnectedTestRepository;

    OpenCDXConnectedTestService openCDXConnectedTestService;

    OpenCDXGrpcConnectedTestController openCDXGrpcConnectedTestController;

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @Autowired
    OpenCDXCommunicationService openCDXCommunicationService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(argument)
                                .password("{noop}pass")
                                .fullName(FullName.newBuilder()
                                        .setFirstName("bob")
                                        .setLastName("bob")
                                        .build())
                                .username("ab@safehealth.me")
                                .primaryContactInfo(ContactInfo.newBuilder()
                                        .setEmail("ab@safehealth.me")
                                        .setMobileNumber(PhoneNumber.newBuilder()
                                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                                .setNumber("1234567890")
                                                .build())
                                        .build())
                                .emailVerified(true)
                                .build());
                    }
                });

        this.openCDXConnectedTestService = new OpenCDXConnectedTestServiceImpl(
                this.openCDXAuditService,
                this.openCDXConnectedTestRepository,
                openCDXCurrentUser,
                objectMapper,
                openCDXCommunicationService,
                openCDXIAMUserRepository);
        this.openCDXGrpcConnectedTestController =
                new OpenCDXGrpcConnectedTestController(this.openCDXConnectedTestService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void submitTest() {
        StreamObserver<TestSubmissionResponse> responseObserver = Mockito.mock(StreamObserver.class);
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder(BasicInfo.getDefaultInstance())
                        .setId(ObjectId.get().toHexString())
                        .setNationalHealthId(10)
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        this.openCDXGrpcConnectedTestController.submitTest(connectedTest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(TestSubmissionResponse.newBuilder()
                        .setSubmissionId(connectedTest.getBasicInfo().getId())
                        .build());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getTestDetailsById() {
        StreamObserver<ConnectedTest> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        OpenCDXConnectedTestModel openCDXConnectedTestModel =
                new OpenCDXConnectedTestModel(ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                        .setBasicInfo(BasicInfo.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setNationalHealthId(10)
                                .setUserId(ObjectId.get().toHexString())
                                .build())
                        .build());

        Mockito.when(this.openCDXConnectedTestRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXConnectedTestModel));
        TestIdRequest testIdRequest = TestIdRequest.newBuilder()
                .setTestId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcConnectedTestController.getTestDetailsById(testIdRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(openCDXConnectedTestModel.getProtobufMessage());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getTestDetailsByIdFail() {
        StreamObserver<ConnectedTest> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXConnectedTestRepository.save(Mockito.any(OpenCDXConnectedTestModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Mockito.when(this.openCDXConnectedTestRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());
        TestIdRequest testIdRequest = TestIdRequest.newBuilder()
                .setTestId(ObjectId.get().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXGrpcConnectedTestController.getTestDetailsById(testIdRequest, responseObserver));
    }

    @Test
    void testListConnectedTests() {

        Mockito.when(this.openCDXConnectedTestRepository.findAllByUserId(
                        Mockito.any(ObjectId.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

        StreamObserver<ConnectedTestListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        ConnectedTestListRequest request = ConnectedTestListRequest.newBuilder()
                .setPageNumber(1)
                .setPageSize(10)
                .setSortAscending(true)
                .setUserId(new ObjectId().toHexString())
                .build();
        this.openCDXGrpcConnectedTestController.listConnectedTests(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testListConnectedByNIHTests() {

        Mockito.when(this.openCDXConnectedTestRepository.findAllByNationalHealthId(
                        Mockito.any(Integer.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

        StreamObserver<ConnectedTestListByNHIDResponse> responseObserver = Mockito.mock(StreamObserver.class);
        ConnectedTestListByNHIDRequest request = ConnectedTestListByNHIDRequest.newBuilder()
                .setPageNumber(1)
                .setPageSize(10)
                .setSortAscending(true)
                .setNationalHealthId(22)
                .build();
        this.openCDXGrpcConnectedTestController.listConnectedTestsByNHID(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
