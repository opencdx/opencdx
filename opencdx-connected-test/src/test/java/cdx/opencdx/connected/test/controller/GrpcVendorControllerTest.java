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

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.model.OpenCDXVendorModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXVendorService;
import cdx.opencdx.connected.test.service.impl.OpenCDXVendorServiceImpl;
import cdx.opencdx.grpc.audit.AgentType;
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
class GrpcVendorControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

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

    GrpcVendorController grpcVendorController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUserType()).thenReturn(AgentType.AGENT_TYPE_HUMAN_USER);
        this.openCDXVendorService = new OpenCDXVendorServiceImpl(
                this.openCDXVendorRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService);
        this.grpcVendorController = new GrpcVendorController(this.openCDXVendorService);
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
        this.grpcVendorController.getVendorById(vendorIdRequest, responseObserver);

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
        Vendor vendor = Vendor.newBuilder().setId(ObjectId.get().toHexString()).build();
        this.grpcVendorController.addVendor(vendor, responseObserver);

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
        Vendor vendor = Vendor.newBuilder().setId(ObjectId.get().toHexString()).build();
        this.grpcVendorController.updateVendor(vendor, responseObserver);

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
        this.grpcVendorController.deleteVendor(vendorIdRequest, responseObserver);

        String message = "Vendor: " + vendorIdRequest.getVendorId() + " is deleted.";
        DeleteResponse deleteResponse =
                DeleteResponse.newBuilder().setSuccess(true).setMessage(message).build();
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(deleteResponse);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
