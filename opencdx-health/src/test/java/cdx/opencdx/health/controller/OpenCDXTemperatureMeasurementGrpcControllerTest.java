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
package cdx.opencdx.health.controller;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.data.TemperatureMeasurement;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.model.OpenCDXTemperatureMeasurementModel;
import cdx.opencdx.health.repository.OpenCDXTemperatureMeasurementRepository;
import cdx.opencdx.health.service.OpenCDXTemperatureMeasurementService;
import cdx.opencdx.health.service.impl.OpenCDXTemperatureMeasurementServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class OpenCDXTemperatureMeasurementGrpcControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXTemperatureMeasurementRepository openCDXTemperatureMeasurementRepository;

    OpenCDXTemperatureMeasurementService temperatureMeasurementService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    OpenCDXTemperatureMeasurementGrpcController openCDXTemperatureMeasurementGrpcController;

    @BeforeEach
    void setUp() {
        this.openCDXTemperatureMeasurementRepository = Mockito.mock(OpenCDXTemperatureMeasurementRepository.class);

        Mockito.when(this.openCDXTemperatureMeasurementRepository.save(
                        Mockito.any(OpenCDXTemperatureMeasurementModel.class)))
                .thenAnswer(invocation -> {
                    OpenCDXTemperatureMeasurementModel argument = invocation.getArgument(0);
                    if (argument.getId() == null) {
                        argument.setId(OpenCDXIdentifier.get());
                    }
                    return argument;
                });

        Mockito.when(this.openCDXTemperatureMeasurementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(invocation -> Optional.of(OpenCDXTemperatureMeasurementModel.builder()
                        .id(invocation.getArgument(0))
                        .patientId(invocation.getArgument(0))
                        .nationalHealthId(UUID.randomUUID().toString())
                        .build()));

        Mockito.when(this.openCDXTemperatureMeasurementRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXTemperatureMeasurementModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXTemperatureMeasurementRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXTemperatureMeasurementModel.builder().build()), PageRequest.of(1, 10), 1));

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.temperatureMeasurementService = new OpenCDXTemperatureMeasurementServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXTemperatureMeasurementRepository);
        this.openCDXTemperatureMeasurementGrpcController =
                new OpenCDXTemperatureMeasurementGrpcController(this.temperatureMeasurementService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXTemperatureMeasurementRepository);
    }

    @Test
    void createTemperatureMeasurement() {
        StreamObserver<CreateTemperatureMeasurementResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXTemperatureMeasurementGrpcController.createTemperatureMeasurement(
                CreateTemperatureMeasurementRequest.newBuilder()
                        .setTemperatureMeasurement(TemperatureMeasurement.newBuilder()
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(Mockito.any(CreateTemperatureMeasurementResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getTemperatureMeasurement() {
        StreamObserver<GetTemperatureMeasurementResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXTemperatureMeasurementGrpcController.getTemperatureMeasurement(
                GetTemperatureMeasurementRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetTemperatureMeasurementResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateTemperatureMeasurement() {
        StreamObserver<UpdateTemperatureMeasurementResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXTemperatureMeasurementGrpcController.updateTemperatureMeasurement(
                UpdateTemperatureMeasurementRequest.newBuilder()
                        .setTemperatureMeasurement(TemperatureMeasurement.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(Mockito.any(UpdateTemperatureMeasurementResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteTemperatureMeasurement() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXTemperatureMeasurementGrpcController.deleteTemperatureMeasurement(
                DeleteTemperatureMeasurementRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuccessResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listTemperatureMeasurements() {
        StreamObserver<ListTemperatureMeasurementsResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXTemperatureMeasurementGrpcController.listTemperatureMeasurements(
                ListTemperatureMeasurementsRequest.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(Mockito.any(ListTemperatureMeasurementsResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
