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

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.connected.test.model.OpenCDXTestCaseModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXTestCaseService;
import cdx.opencdx.connected.test.service.impl.OpenCDXTestCaseServiceImpl;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.inventory.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXGrpcTestCaseControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @Mock
    OpenCDXVendorRepository openCDXVendorRepository;

    @Mock
    OpenCDXTestCaseRepository openCDXTestCaseRepository;

    @Mock
    OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    OpenCDXTestCaseService openCDXTestCaseService;

    OpenCDXGrpcTestCaseController openCDXGrpcTestCaseController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.openCDXTestCaseService = new OpenCDXTestCaseServiceImpl(
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        this.openCDXGrpcTestCaseController = new OpenCDXGrpcTestCaseController(this.openCDXTestCaseService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getTestCaseById() {
        StreamObserver<TestCase> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXTestCaseModel openCDXTestCaseModel =
                OpenCDXTestCaseModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXTestCaseRepository.save(Mockito.any(OpenCDXTestCaseModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXTestCaseRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXTestCaseModel));
        TestCaseIdRequest testCaseIdRequest = TestCaseIdRequest.newBuilder()
                .setTestCaseId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcTestCaseController.getTestCaseById(testCaseIdRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(openCDXTestCaseModel.getProtobufMessage());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void addTestCase() {
        StreamObserver<TestCase> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXTestCaseModel openCDXTestCaseModel =
                OpenCDXTestCaseModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXTestCaseRepository.save(Mockito.any(OpenCDXTestCaseModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXTestCaseRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXTestCaseModel));
        TestCase testCase = TestCase.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setManufacturerId(ObjectId.get().toHexString())
                .setVendorId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcTestCaseController.addTestCase(testCase, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(testCase);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateTestCase() {
        StreamObserver<TestCase> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXTestCaseModel openCDXTestCaseModel =
                OpenCDXTestCaseModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXTestCaseRepository.save(Mockito.any(OpenCDXTestCaseModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXTestCaseRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXTestCaseModel));
        TestCase testCase = TestCase.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setManufacturerId(ObjectId.get().toHexString())
                .setVendorId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcTestCaseController.updateTestCase(testCase, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(testCase);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteTestCase() {
        StreamObserver<DeleteResponse> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXTestCaseModel openCDXTestCaseModel =
                OpenCDXTestCaseModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXTestCaseRepository.save(Mockito.any(OpenCDXTestCaseModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXTestCaseRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXTestCaseModel));
        TestCaseIdRequest testCaseIdRequest = TestCaseIdRequest.newBuilder()
                .setTestCaseId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcTestCaseController.deleteTestCase(testCaseIdRequest, responseObserver);

        String message = "TestCase: " + testCaseIdRequest.getTestCaseId() + " is deleted.";
        DeleteResponse deleteResponse =
                DeleteResponse.newBuilder().setSuccess(true).setMessage(message).build();
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(deleteResponse);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listTestCase() {
        StreamObserver<TestCaseListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXTestCaseRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXTestCaseModel.builder()
                                .manufacturerId(ObjectId.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        TestCaseListRequest testCaseListRequest = TestCaseListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .build();
        this.openCDXGrpcTestCaseController.listTestCase(testCaseListRequest, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listTestCaseManufacturer() {
        StreamObserver<TestCaseListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXTestCaseRepository.findAllByManufacturerId(
                        Mockito.any(ObjectId.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXTestCaseModel.builder()
                                .manufacturerId(ObjectId.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        TestCaseListRequest testCaseListRequest = TestCaseListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .setManufacturerId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcTestCaseController.listTestCase(testCaseListRequest, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listTestCaseVendorElse() {
        StreamObserver<TestCaseListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXTestCaseRepository.findAllByVendorId(
                        Mockito.any(ObjectId.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXTestCaseModel.builder()
                                .manufacturerId(ObjectId.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        TestCaseListRequest testCaseListRequest = TestCaseListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .setVendorId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcTestCaseController.listTestCase(testCaseListRequest, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
