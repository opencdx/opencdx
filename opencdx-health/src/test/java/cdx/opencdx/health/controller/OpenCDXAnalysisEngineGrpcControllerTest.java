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
import cdx.opencdx.grpc.data.AnalysisEngine;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.model.OpenCDXAnalysisEngineModel;
import cdx.opencdx.health.repository.OpenCDXAnalysisEngineRepository;
import cdx.opencdx.health.service.OpenCDXAnalysisEngineService;
import cdx.opencdx.health.service.impl.OpenCDXAnalysisEngineServiceImpl;
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
class OpenCDXAnalysisEngineGrpcControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ObjectMapper objectMapper1;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXAnalysisEngineRepository openCDXAnalysisEngineRepository;

    OpenCDXAnalysisEngineService AnalysisEngineService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser1;

    OpenCDXAnalysisEngineGrpcController openCDXAnalysisEngineGrpcController;

    @BeforeEach
    void setUp() {
        this.openCDXAnalysisEngineRepository = Mockito.mock(OpenCDXAnalysisEngineRepository.class);

        Mockito.when(this.openCDXAnalysisEngineRepository.save(Mockito.any(OpenCDXAnalysisEngineModel.class)))
                .thenAnswer(invocation -> {
                    OpenCDXAnalysisEngineModel argument = invocation.getArgument(0);
                    if (argument.getId() == null) {
                        argument.setId(OpenCDXIdentifier.get());
                    }
                    return argument;
                });

        Mockito.when(this.openCDXAnalysisEngineRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(invocation -> Optional.of(OpenCDXAnalysisEngineModel.builder()
                        .id(invocation.getArgument(0))
                        .build()));

        Mockito.when(this.openCDXAnalysisEngineRepository.findAllByOrganizationId(
                        Mockito.any(), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXAnalysisEngineModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .organizationId((OpenCDXIdentifier.get().toHexString()))
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.AnalysisEngineService = new OpenCDXAnalysisEngineServiceImpl(
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper,
                this.openCDXDocumentValidator,
                this.openCDXAnalysisEngineRepository);
        this.openCDXAnalysisEngineGrpcController = new OpenCDXAnalysisEngineGrpcController(this.AnalysisEngineService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXAnalysisEngineRepository);
    }

    @Test
    void createAnalysisEngine() {
        StreamObserver<CreateAnalysisEngineResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXAnalysisEngineGrpcController.createAnalysisEngine(
                CreateAnalysisEngineRequest.newBuilder()
                        .setAnalysisEngine(AnalysisEngine.newBuilder()
                                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateAnalysisEngineResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getAnalysisEngine() {
        StreamObserver<GetAnalysisEngineResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXAnalysisEngineGrpcController.getAnalysisEngine(
                GetAnalysisEngineRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetAnalysisEngineResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateAnalysisEngine() {
        StreamObserver<UpdateAnalysisEngineResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXAnalysisEngineGrpcController.updateAnalysisEngine(
                UpdateAnalysisEngineRequest.newBuilder()
                        .setAnalysisEngine(AnalysisEngine.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateAnalysisEngineResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteAnalysisEngine() {
        StreamObserver<SuccessResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXAnalysisEngineGrpcController.deleteAnalysisEngine(
                DeleteAnalysisEngineRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SuccessResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listAnalysisEngines() {
        StreamObserver<ListAnalysisEnginesResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXAnalysisEngineGrpcController.listAnalysisEngines(
                ListAnalysisEnginesRequest.newBuilder()
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build())
                        .build(),
                responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListAnalysisEnginesResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
