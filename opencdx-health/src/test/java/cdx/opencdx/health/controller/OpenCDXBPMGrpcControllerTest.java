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
import cdx.opencdx.grpc.data.BPM;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.ArmUsed;
import cdx.opencdx.grpc.types.BPMUnits;
import cdx.opencdx.grpc.types.CuffSize;
import cdx.opencdx.health.model.OpenCDXBPMModel;
import cdx.opencdx.health.repository.OpenCDXBPMRepository;
import cdx.opencdx.health.service.OpenCDXBPMService;
import cdx.opencdx.health.service.impl.OpenCDXBPMServiceImpl;
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
class OpenCDXBPMGrpcControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXBPMRepository openCDXBPMRepository;

    OpenCDXBPMService openCDXBPMService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    OpenCDXBPMGrpcController openCDXBPMGrpcController;

    @BeforeEach
    void setUp() {
        this.openCDXBPMRepository = mock(OpenCDXBPMRepository.class);

        Mockito.when(this.openCDXBPMRepository.save(Mockito.any(OpenCDXBPMModel.class)))
                .thenAnswer(new Answer<OpenCDXBPMModel>() {
                    @Override
                    public OpenCDXBPMModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXBPMModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXBPMRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXBPMModel>>() {
                    @Override
                    public Optional<OpenCDXBPMModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXBPMModel.builder()
                                .id(argument)
                                .patientId(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .cuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                                .armUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                                .systolic(80)
                                .diastolic(120)
                                .bpmUnits(BPMUnits.BARS)
                                .sittingPositionFiveMinutes(true)
                                .urinatedThirtyMinutesPrior(false)
                                .build());
                    }
                });

        Mockito.when(this.openCDXBPMRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXBPMModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .nationalHealthId(OpenCDXIdentifier.get().toHexString())
                                .cuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                                .armUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                                .systolic(80)
                                .diastolic(120)
                                .bpmUnits(BPMUnits.BARS)
                                .sittingPositionFiveMinutes(true)
                                .urinatedThirtyMinutesPrior(false)
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXBPMRepository.findAllByNationalHealthId(
                        Mockito.any(String.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(OpenCDXBPMModel.builder().build()), PageRequest.of(1, 10), 1));

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.openCDXBPMService = new OpenCDXBPMServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXBPMRepository);
        this.openCDXBPMGrpcController = new OpenCDXBPMGrpcController(this.openCDXBPMService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXBPMRepository);
    }

    @Test
    void createBPM() {
        StreamObserver<CreateBPMResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXBPMGrpcController.createBPMMeasurement(
                CreateBPMRequest.newBuilder()
                        .setBpmMeasurement(BPM.newBuilder()
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateBPMResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getBPM() {
        StreamObserver<GetBPMResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXBPMGrpcController.getBPMMeasurement(
                GetBPMRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetBPMResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateBPM() {
        StreamObserver<UpdateBPMResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXBPMGrpcController.updateBPMMeasurement(
                UpdateBPMRequest.newBuilder()
                        .setBpmMeasurement(BPM.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .setPatientId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateBPMResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteBPM() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXBPMGrpcController.deleteBPMMeasurement(
                DeleteBPMRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuccessResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listBPMs() {
        StreamObserver<ListBPMResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXBPMGrpcController.listBPMMeasurements(
                ListBPMRequest.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListBPMResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
