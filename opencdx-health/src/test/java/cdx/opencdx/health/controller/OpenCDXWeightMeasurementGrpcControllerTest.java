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
import cdx.opencdx.grpc.data.WeightMeasurement;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.model.OpenCDXWeightMeasurementModel;
import cdx.opencdx.health.repository.OpenCDXWeightMeasurementRepository;
import cdx.opencdx.health.service.OpenCDXWeightMeasurementService;
import cdx.opencdx.health.service.impl.OpenCDXWeightMeasurementServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXWeightMeasurementGrpcControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXWeightMeasurementRepository openCDXWeightMeasurementRepository;

    OpenCDXWeightMeasurementService weightMeasurementService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    OpenCDXWeightMeasurementGrpcController openCDXWeightMeasurementGrpcController;

    @BeforeEach
    void setUp() {
        this.openCDXWeightMeasurementRepository = mock(OpenCDXWeightMeasurementRepository.class);

        Mockito.when(this.openCDXWeightMeasurementRepository.save(Mockito.any(OpenCDXWeightMeasurementModel.class)))
                .thenAnswer(new Answer<OpenCDXWeightMeasurementModel>() {
                    @Override
                    public OpenCDXWeightMeasurementModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXWeightMeasurementModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXWeightMeasurementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXWeightMeasurementModel>>() {
                    @Override
                    public Optional<OpenCDXWeightMeasurementModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXWeightMeasurementModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .build());
                    }
                });

        Mockito.when(this.openCDXWeightMeasurementRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXWeightMeasurementModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXWeightMeasurementRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXWeightMeasurementModel.builder().build()), PageRequest.of(1, 10), 1));

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.weightMeasurementService = new OpenCDXWeightMeasurementServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXWeightMeasurementRepository);
        this.openCDXWeightMeasurementGrpcController =
                new OpenCDXWeightMeasurementGrpcController(this.weightMeasurementService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXWeightMeasurementRepository);
    }

    @Test
    void createWeightMeasurement() {
        StreamObserver<CreateWeightMeasurementResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXWeightMeasurementGrpcController.createWeightMeasurement(
                CreateWeightMeasurementRequest.newBuilder()
                        .setWeightMeasurement(WeightMeasurement.newBuilder()
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateWeightMeasurementResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getWeightMeasurement() {
        StreamObserver<GetWeightMeasurementResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXWeightMeasurementGrpcController.getWeightMeasurement(
                GetWeightMeasurementRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetWeightMeasurementResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateWeightMeasurement() {
        StreamObserver<UpdateWeightMeasurementResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXWeightMeasurementGrpcController.updateWeightMeasurement(
                UpdateWeightMeasurementRequest.newBuilder()
                        .setWeightMeasurement(WeightMeasurement.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateWeightMeasurementResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteWeightMeasurement() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXWeightMeasurementGrpcController.deleteWeightMeasurement(
                DeleteWeightMeasurementRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuccessResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listWeightMeasurements() {
        StreamObserver<ListWeightMeasurementsResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXWeightMeasurementGrpcController.listWeightMeasurements(
                ListWeightMeasurementsRequest.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListWeightMeasurementsResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
