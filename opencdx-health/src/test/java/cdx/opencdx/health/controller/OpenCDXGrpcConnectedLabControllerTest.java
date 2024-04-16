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
import cdx.opencdx.grpc.data.BasicInfo;
import cdx.opencdx.grpc.data.ConnectedLab;
import cdx.opencdx.grpc.data.LabFindings;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.health.config.OpenCDXLabConnectionFactoryBean;
import cdx.opencdx.health.model.OpenCDXConnectedLabModel;
import cdx.opencdx.health.repository.OpenCDXConnectedLabRepository;
import cdx.opencdx.health.service.OpenCDXConnectedLabService;
import cdx.opencdx.health.service.impl.OpenCDXConnectedLabServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Assertions;
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

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXGrpcConnectedLabControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXLabConnectionFactoryBean openCDXLabConnectionFactoryBean;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXConnectedLabRepository openCDXConnectedLabRepository;

    OpenCDXConnectedLabService openCDXConnectedLabService;

    OpenCDXGrpcConnectedLabController openCDXGrpcConnectedLabController;

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

        Mockito.when(this.openCDXConnectedLabRepository.save(Mockito.any(OpenCDXConnectedLabModel.class)))
                .thenAnswer(new Answer<OpenCDXConnectedLabModel>() {
                    @Override
                    public OpenCDXConnectedLabModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXConnectedLabModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXConnectedLabRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXConnectedLabModel>>() {
                    @Override
                    public Optional<OpenCDXConnectedLabModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);

                        return Optional.of(OpenCDXConnectedLabModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .identifier("default")
                                .created(Instant.now())
                                .modified(Instant.now())
                                .creator(OpenCDXIdentifier.get())
                                .modifier(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        Mockito.when(this.openCDXConnectedLabRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);

        Mockito.when(this.openCDXConnectedLabRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXConnectedLabModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .identifier("default")
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXConnectedLabRepository.findByOrganizationIdAndWorkspaceId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXConnectedLabModel>>() {
                    @Override
                    public Optional<OpenCDXConnectedLabModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);

                        return Optional.of(OpenCDXConnectedLabModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .identifier("default")
                                .created(Instant.now())
                                .modified(Instant.now())
                                .creator(OpenCDXIdentifier.get())
                                .modifier(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        this.openCDXConnectedLabService = new OpenCDXConnectedLabServiceImpl(
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXConnectedLabRepository,
                openCDXLabConnectionFactoryBean);
        this.openCDXGrpcConnectedLabController = new OpenCDXGrpcConnectedLabController(openCDXConnectedLabService);
    }

    @Test
    void submitLabFindings() {
        StreamObserver<LabFindingsResponse> responseObserver = Mockito.mock(StreamObserver.class);

        LabFindings request = LabFindings.newBuilder()
                .setBasicInfo(BasicInfo.newBuilder()
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcConnectedLabController.submitLabFindings(request, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void createConnectedLab() {
        StreamObserver<CreateConnectedLabResponse> responseObserver = Mockito.mock(StreamObserver.class);

        CreateConnectedLabRequest request = CreateConnectedLabRequest.newBuilder()
                .setConnectedLab(ConnectedLab.newBuilder()
                        .setIdentifier("default")
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .setCreated(Timestamp.newBuilder().setSeconds(1000000).build())
                        .setModified(Timestamp.newBuilder().setSeconds(1000000).build())
                        .setCreator(OpenCDXIdentifier.get().toHexString())
                        .setModifier(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcConnectedLabController.createConnectedLab(request, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getConnectedLab() {
        StreamObserver<GetConnectedLabResponse> responseObserver = Mockito.mock(StreamObserver.class);

        GetConnectedLabRequest request = GetConnectedLabRequest.newBuilder()
                .setConnectedLabId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcConnectedLabController.getConnectedLab(request, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateConnectedLab() {
        StreamObserver<UpdateConnectedLabResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UpdateConnectedLabRequest request = UpdateConnectedLabRequest.newBuilder()
                .setConnectedLab(ConnectedLab.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setIdentifier("default")
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .setCreated(Timestamp.newBuilder().setSeconds(1000000).build())
                        .setModified(Timestamp.newBuilder().setSeconds(1000000).build())
                        .setCreator(OpenCDXIdentifier.get().toHexString())
                        .setModifier(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcConnectedLabController.updateConnectedLab(request, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteConnectedLab() {
        StreamObserver<DeleteConnectedLabResponse> responseObserver = Mockito.mock(StreamObserver.class);

        DeleteConnectedLabRequest request = DeleteConnectedLabRequest.newBuilder()
                .setConnectedLabId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcConnectedLabController.deleteConnectedLab(request, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listConnectedLabs() {
        StreamObserver<ListConnectedLabsResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ListConnectedLabsRequest listOrdersRequest = ListConnectedLabsRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcConnectedLabController.listConnectedLabs(listOrdersRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
