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
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.logistics.DeleteResponse;
import cdx.opencdx.grpc.service.logistics.ManufacturerIdRequest;
import cdx.opencdx.grpc.service.logistics.ManufacturerListRequest;
import cdx.opencdx.grpc.service.logistics.ManufacturersListResponse;
import cdx.opencdx.logistics.model.OpenCDXManufacturerModel;
import cdx.opencdx.logistics.repository.OpenCDXDeviceRepository;
import cdx.opencdx.logistics.repository.OpenCDXManufacturerRepository;
import cdx.opencdx.logistics.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.logistics.service.OpenCDXManufacturerService;
import cdx.opencdx.logistics.service.impl.OpenCDXManufacturerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
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
class OpenCDXGrpcManufacturerControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @Mock
    OpenCDXTestCaseRepository openCDXTestCaseRepository;

    @Mock
    OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    OpenCDXManufacturerService openCDXManufacturerService;

    OpenCDXGrpcManufacturerController openCDXGrpcManufacturerController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.openCDXManufacturerService = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        this.openCDXGrpcManufacturerController = new OpenCDXGrpcManufacturerController(this.openCDXManufacturerService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getManufacturerById() {
        StreamObserver<Manufacturer> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(OpenCDXIdentifier.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        ManufacturerIdRequest manufacturerIdRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXGrpcManufacturerController.getManufacturerById(manufacturerIdRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(openCDXManufacturerModel.getProtobufMessage());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void addManufacturer() {
        StreamObserver<Manufacturer> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(OpenCDXIdentifier.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        Manufacturer manufacturer = Manufacturer.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setManufacturerContact(ContactInfo.newBuilder().build())
                .setManufacturerEmail(EmailAddress.newBuilder().build())
                .setManufacturerPhone(PhoneNumber.newBuilder().build())
                .build();
        this.openCDXGrpcManufacturerController.addManufacturer(manufacturer, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(manufacturer);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateManufacturer() {
        StreamObserver<Manufacturer> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(OpenCDXIdentifier.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        Manufacturer manufacturer = Manufacturer.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setManufacturerContact(ContactInfo.newBuilder().build())
                .setManufacturerEmail(EmailAddress.newBuilder().build())
                .setManufacturerPhone(PhoneNumber.newBuilder().build())
                .build();
        this.openCDXGrpcManufacturerController.updateManufacturer(manufacturer, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(manufacturer);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteManufacturer() {
        StreamObserver<DeleteResponse> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(OpenCDXIdentifier.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        ManufacturerIdRequest manufacturerIdRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.openCDXGrpcManufacturerController.deleteManufacturer(manufacturerIdRequest, responseObserver);

        String message = "Manufacturer: " + manufacturerIdRequest.getManufacturerId() + " is deleted.";
        DeleteResponse deleteResponse =
                DeleteResponse.newBuilder().setSuccess(true).setMessage(message).build();
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(deleteResponse);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listManufacturers() {
        StreamObserver<ManufacturersListResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXManufacturerRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXManufacturerModel.builder().name("USA").build()), PageRequest.of(1, 10), 1));
        ManufacturerListRequest countryListRequest = ManufacturerListRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .build();
        this.openCDXGrpcManufacturerController.listManufacturers(countryListRequest, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
