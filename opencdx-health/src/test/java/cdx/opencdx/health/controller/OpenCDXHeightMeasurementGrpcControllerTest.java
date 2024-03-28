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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.health.*;
import cdx.opencdx.health.model.OpenCDXHeightMeasurementModel;
import cdx.opencdx.health.repository.OpenCDXHeightMeasurementRepository;
import cdx.opencdx.health.service.OpenCDXHeightMeasurementService;
import cdx.opencdx.health.service.impl.OpenCDXHeightMeasurementServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
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

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXHeightMeasurementGrpcControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXHeightMeasurementRepository openCDXHeightMeasurementRepository;

    OpenCDXHeightMeasurementService heightMeasurementService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    OpenCDXHeightMeasurementGrpcController openCDXHeightMeasurementGrpcController;

    @BeforeEach
    void setUp() {
        this.openCDXHeightMeasurementRepository = mock(OpenCDXHeightMeasurementRepository.class);

        Mockito.when(this.openCDXHeightMeasurementRepository.save(Mockito.any(OpenCDXHeightMeasurementModel.class)))
                .thenAnswer(new Answer<OpenCDXHeightMeasurementModel>() {
                    @Override
                    public OpenCDXHeightMeasurementModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXHeightMeasurementModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXHeightMeasurementRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXHeightMeasurementModel>>() {
                    @Override
                    public Optional<OpenCDXHeightMeasurementModel> answer(InvocationOnMock invocation)
                            throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXHeightMeasurementModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .build());
                    }
                });

        Mockito.when(this.openCDXHeightMeasurementRepository.findAllByPatientId(
                        Mockito.any(ObjectId.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXHeightMeasurementModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(ObjectId.get().toHexString())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXHeightMeasurementRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXHeightMeasurementModel.builder().build()), PageRequest.of(1, 10), 1));

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.heightMeasurementService = new OpenCDXHeightMeasurementServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXHeightMeasurementRepository);
        this.openCDXHeightMeasurementGrpcController =
                new OpenCDXHeightMeasurementGrpcController(this.heightMeasurementService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXHeightMeasurementRepository);
    }

    @Test
    void createHeightMeasurement() {
        StreamObserver<CreateHeightMeasurementResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXHeightMeasurementGrpcController.createHeightMeasurement(
                CreateHeightMeasurementRequest.newBuilder()
                        .setHeightMeasurement(HeightMeasurement.newBuilder()
                                .setPatientId(ObjectId.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateHeightMeasurementResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getHeightMeasurement() {
        StreamObserver<GetHeightMeasurementResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXHeightMeasurementGrpcController.getHeightMeasurement(
                GetHeightMeasurementRequest.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetHeightMeasurementResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateHeightMeasurement() {
        StreamObserver<UpdateHeightMeasurementResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXHeightMeasurementGrpcController.updateHeightMeasurement(
                UpdateHeightMeasurementRequest.newBuilder()
                        .setHeightMeasurement(HeightMeasurement.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .setPatientId(ObjectId.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateHeightMeasurementResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteHeightMeasurement() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXHeightMeasurementGrpcController.deleteHeightMeasurement(
                DeleteHeightMeasurementRequest.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuccessResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listHeightMeasurements() {
        StreamObserver<ListHeightMeasurementsResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXHeightMeasurementGrpcController.listHeightMeasurements(
                ListHeightMeasurementsRequest.newBuilder()
                        .setPatientId(ObjectId.get().toHexString())
                        .setPatientId(ObjectId.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListHeightMeasurementsResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
