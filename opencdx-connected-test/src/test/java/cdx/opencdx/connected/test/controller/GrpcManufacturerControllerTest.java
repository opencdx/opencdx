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
import cdx.opencdx.connected.test.model.OpenCDXManufacturerModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.connected.test.service.OpenCDXManufacturerService;
import cdx.opencdx.connected.test.service.impl.OpenCDXManufacturerServiceImpl;
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
class GrpcManufacturerControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @Mock
    OpenCDXTestCaseRepository openCDXTestCaseRepository;

    @Mock
    OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @Mock
    OpenCDXDeviceRepository openCDXDeviceRepository;

    OpenCDXManufacturerService openCDXManufacturerService;

    GrpcManufacturerController grpcManufacturerController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUserType()).thenReturn(AgentType.AGENT_TYPE_HUMAN_USER);
        this.openCDXManufacturerService = new OpenCDXManufacturerServiceImpl(
                this.openCDXManufacturerRepository,
                this.openCDXDeviceRepository,
                this.openCDXTestCaseRepository,
                openCDXCurrentUser,
                objectMapper,
                this.openCDXAuditService);
        this.grpcManufacturerController = new GrpcManufacturerController(this.openCDXManufacturerService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getManufacturerById() {
        StreamObserver<Manufacturer> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        ManufacturerIdRequest manufacturerIdRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(ObjectId.get().toHexString())
                .build();
        this.grpcManufacturerController.getManufacturerById(manufacturerIdRequest, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(openCDXManufacturerModel.getProtobufMessage());
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void addManufacturer() {
        StreamObserver<Manufacturer> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        Manufacturer manufacturer =
                Manufacturer.newBuilder().setId(ObjectId.get().toHexString()).build();
        this.grpcManufacturerController.addManufacturer(manufacturer, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(manufacturer);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateManufacturer() {
        StreamObserver<Manufacturer> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        Manufacturer manufacturer =
                Manufacturer.newBuilder().setId(ObjectId.get().toHexString()).build();
        this.grpcManufacturerController.updateManufacturer(manufacturer, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(manufacturer);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteManufacturer() {
        StreamObserver<DeleteResponse> responseObserver = Mockito.mock(StreamObserver.class);

        OpenCDXManufacturerModel openCDXManufacturerModel =
                OpenCDXManufacturerModel.builder().id(ObjectId.get()).build();
        Mockito.when(this.openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.openCDXManufacturerRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(openCDXManufacturerModel));
        ManufacturerIdRequest manufacturerIdRequest = ManufacturerIdRequest.newBuilder()
                .setManufacturerId(ObjectId.get().toHexString())
                .build();
        this.grpcManufacturerController.deleteManufacturer(manufacturerIdRequest, responseObserver);

        String message = "Manufacturer: " + manufacturerIdRequest.getManufacturerId() + " is deleted.";
        DeleteResponse deleteResponse =
                DeleteResponse.newBuilder().setSuccess(true).setMessage(message).build();
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(deleteResponse);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}