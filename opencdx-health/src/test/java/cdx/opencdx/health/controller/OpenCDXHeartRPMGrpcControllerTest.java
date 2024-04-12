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

import static org.mockito.Mockito.mock;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.health.*;
import cdx.opencdx.health.model.OpenCDXHeartRPMModel;
import cdx.opencdx.health.repository.OpenCDXHeartRPMRepository;
import cdx.opencdx.health.service.OpenCDXHeartRPMService;
import cdx.opencdx.health.service.impl.OpenCDXHeartRPMServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
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
class OpenCDXHeartRPMGrpcControllerTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXHeartRPMRepository openCDXHeartRPMRepository;

    OpenCDXHeartRPMService openCDXHeartRPMService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    OpenCDXHeartRPMGrpcController openCDXHeartRPMGrpcController;

    @BeforeEach
    void setUp() {
        this.openCDXHeartRPMRepository = mock(OpenCDXHeartRPMRepository.class);

        Mockito.when(this.openCDXHeartRPMRepository.save(Mockito.any(OpenCDXHeartRPMModel.class)))
                .thenAnswer(new Answer<OpenCDXHeartRPMModel>() {
                    @Override
                    public OpenCDXHeartRPMModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXHeartRPMModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXHeartRPMRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXHeartRPMModel>>() {
                    @Override
                    public Optional<OpenCDXHeartRPMModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXHeartRPMModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .measurementTakenOverInSeconds(60)
                                .sittingPositionFiveMinutes(true)
                                .build());
                    }
                });

        Mockito.when(this.openCDXHeartRPMRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXHeartRPMModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .measurementTakenOverInSeconds(60)
                                .sittingPositionFiveMinutes(true)
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXHeartRPMRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(
                        new PageImpl<>(List.of(OpenCDXHeartRPMModel.builder().build()), PageRequest.of(1, 10), 1));

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.openCDXHeartRPMService = new OpenCDXHeartRPMServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXHeartRPMRepository);
        this.openCDXHeartRPMGrpcController = new OpenCDXHeartRPMGrpcController(this.openCDXHeartRPMService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXHeartRPMRepository);
    }

    @Test
    void createHeartRPM() {
        StreamObserver<CreateHeartRPMResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXHeartRPMGrpcController.createHeartRPMMeasurement(
                CreateHeartRPMRequest.newBuilder()
                        .setHeartRpmMeasurement(HeartRPM.newBuilder()
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateHeartRPMResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getHeartRPM() {
        StreamObserver<GetHeartRPMResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXHeartRPMGrpcController.getHeartRPMMeasurement(
                GetHeartRPMRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetHeartRPMResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateHeartRPM() {
        StreamObserver<UpdateHeartRPMResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXHeartRPMGrpcController.updateHeartRPMMeasurement(
                UpdateHeartRPMRequest.newBuilder()
                        .setHeartRpmMeasurement(HeartRPM.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateHeartRPMResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteHeartRPM() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXHeartRPMGrpcController.deleteHeartRPMMeasurement(
                DeleteHeartRPMRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuccessResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listHeartRPMs() {
        StreamObserver<ListHeartRPMResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXHeartRPMGrpcController.listHeartRPMMeasurements(
                ListHeartRPMRequest.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListHeartRPMResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
