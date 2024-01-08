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
import cdx.opencdx.connected.test.model.OpenCDXVendorModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXVendorService;
import cdx.opencdx.connected.test.service.impl.OpenCDXVendorServiceImpl;
import cdx.opencdx.grpc.common.ContactInfo;
import cdx.opencdx.grpc.common.EmailAddress;
import cdx.opencdx.grpc.common.PhoneNumber;
import cdx.opencdx.grpc.inventory.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXGrpcVendorControllerTest {

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
    OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    @Mock
    OpenCDXTestCaseRepository openCDXTestCaseRepository;

    OpenCDXVendorService openCDXVendorService;

    OpenCDXGrpcVendorController openCDXGrpcVendorController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.openCDXVendorService = new OpenCDXVendorServiceImpl(
                this.openCDXVendorRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService,
                this.openCDXDocumentValidator);
        this.openCDXGrpcVendorController = new OpenCDXGrpcVendorController(this.openCDXVendorService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getVendorById() {
        StreamObserver<Vendor> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXVendorModel openCDXVendorModel =
                OpenCDXVendorModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXVendorRepository.save(Mockito.any(OpenCDXVendorModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXVendorRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXVendorModel));
        VendorIdRequest vendorIdRequest = VendorIdRequest.newBuilder()
                .setVendorId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcVendorController.getVendorById(vendorIdRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(openCDXVendorModel.getProtobufMessage());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void addVendor() {
        StreamObserver<Vendor> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXVendorModel openCDXVendorModel =
                OpenCDXVendorModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXVendorRepository.save(Mockito.any(OpenCDXVendorModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXVendorRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXVendorModel));
        Vendor vendor = Vendor.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setVendorContact(ContactInfo.newBuilder().build())
                .setVendorEmail(EmailAddress.newBuilder().build())
                .setVendorPhone(PhoneNumber.newBuilder().build())
                .build();
        this.openCDXGrpcVendorController.addVendor(vendor, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(vendor);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateVendor() {
        StreamObserver<Vendor> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXVendorModel openCDXVendorModel =
                OpenCDXVendorModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXVendorRepository.save(Mockito.any(OpenCDXVendorModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXVendorRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXVendorModel));
        Vendor vendor = Vendor.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setVendorContact(ContactInfo.newBuilder().build())
                .setVendorEmail(EmailAddress.newBuilder().build())
                .setVendorPhone(PhoneNumber.newBuilder().build())
                .build();
        this.openCDXGrpcVendorController.updateVendor(vendor, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(vendor);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteVendor() {
        StreamObserver<DeleteResponse> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXVendorModel openCDXVendorModel =
                OpenCDXVendorModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXVendorRepository.save(Mockito.any(OpenCDXVendorModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXVendorRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXVendorModel));
        VendorIdRequest vendorIdRequest = VendorIdRequest.newBuilder()
                .setVendorId(ObjectId.get().toHexString())
                .build();
        this.openCDXGrpcVendorController.deleteVendor(vendorIdRequest, responseObserver);

        String message = "Vendor: " + vendorIdRequest.getVendorId() + " is deleted.";
        DeleteResponse deleteResponse =
                DeleteResponse.newBuilder().setSuccess(true).setMessage(message).build();
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(deleteResponse);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
