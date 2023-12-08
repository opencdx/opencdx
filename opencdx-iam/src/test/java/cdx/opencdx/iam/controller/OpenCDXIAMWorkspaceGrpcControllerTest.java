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
package cdx.opencdx.iam.controller;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.organization.*;
import cdx.opencdx.iam.config.AppProperties;
import cdx.opencdx.iam.model.OpenCDXIAMWorkspaceModel;
import cdx.opencdx.iam.repository.OpenCDXIAMWorkspaceRepository;
import cdx.opencdx.iam.service.OpenCDXIAMWorkspaceService;
import cdx.opencdx.iam.service.impl.OpenCDXIAMWorkspaceServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXIAMWorkspaceGrpcControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Mock
    OpenCDXIAMWorkspaceRepository openCDXIAMWorkspaceRepository;

    OpenCDXIAMWorkspaceService openCDXIAMWorkspaceService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    OpenCDXIAMWorkspaceGrpcController openCDXIAMWorkspaceGrpcController;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    OpenCDXCommunicationService openCDXCommunicationService;

    @Autowired
    AppProperties appProperties;

    @BeforeEach
    void setUp() {
        this.openCDXIAMWorkspaceRepository = Mockito.mock(OpenCDXIAMWorkspaceRepository.class);
        Mockito.when(this.openCDXIAMWorkspaceRepository.save(Mockito.any(OpenCDXIAMWorkspaceModel.class)))
                .thenAnswer(new Answer<OpenCDXIAMWorkspaceModel>() {
                    @Override
                    public OpenCDXIAMWorkspaceModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIAMWorkspaceModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXIAMWorkspaceRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMWorkspaceModel>>() {
                    @Override
                    public Optional<OpenCDXIAMWorkspaceModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXIAMWorkspaceModel.builder().id(argument).build());
                    }
                });
        Mockito.when(this.openCDXIAMWorkspaceRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(true);

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.openCDXIAMWorkspaceService = new OpenCDXIAMWorkspaceServiceImpl(
                this.openCDXIAMWorkspaceRepository,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper);
        this.openCDXIAMWorkspaceGrpcController = new OpenCDXIAMWorkspaceGrpcController(this.openCDXIAMWorkspaceService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXIAMWorkspaceRepository);
    }

    @Test
    void createWorkspace() {
        StreamObserver<CreateWorkspaceResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMWorkspaceGrpcController.createWorkspace(
                CreateWorkspaceRequest.newBuilder(CreateWorkspaceRequest.getDefaultInstance())
                        .setWorkspace(Workspace.newBuilder(Workspace.getDefaultInstance())
                                .setName("test")
                                .setCreated(Timestamp.getDefaultInstance())
                                .setModified(Timestamp.getDefaultInstance())
                                .setCreator(ObjectId.get().toHexString())
                                .setModifier(ObjectId.get().toHexString())
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateWorkspaceResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void createWorkspace_fail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMWorkspaceService = new OpenCDXIAMWorkspaceServiceImpl(
                this.openCDXIAMWorkspaceRepository, this.openCDXAuditService, this.openCDXCurrentUser, mapper);

        this.openCDXIAMWorkspaceGrpcController = new OpenCDXIAMWorkspaceGrpcController(this.openCDXIAMWorkspaceService);

        StreamObserver<CreateWorkspaceResponse> responseObserver = Mockito.mock(StreamObserver.class);
        CreateWorkspaceRequest request = CreateWorkspaceRequest.getDefaultInstance();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXIAMWorkspaceGrpcController.createWorkspace(request, responseObserver));
    }

    @Test
    void getWorkspaceDetailsById() {
        StreamObserver<GetWorkspaceDetailsByIdResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMWorkspaceGrpcController.getWorkspaceDetailsById(
                GetWorkspaceDetailsByIdRequest.newBuilder(GetWorkspaceDetailsByIdRequest.getDefaultInstance())
                        .setWorkspaceId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetWorkspaceDetailsByIdResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getWorkspaceDetailsById_fail() {
        this.openCDXIAMWorkspaceRepository = Mockito.mock(OpenCDXIAMWorkspaceRepository.class);
        Mockito.when(this.openCDXIAMWorkspaceRepository.findById(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());

        this.openCDXIAMWorkspaceService = new OpenCDXIAMWorkspaceServiceImpl(
                this.openCDXIAMWorkspaceRepository,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper);

        this.openCDXIAMWorkspaceGrpcController = new OpenCDXIAMWorkspaceGrpcController(this.openCDXIAMWorkspaceService);

        StreamObserver<GetWorkspaceDetailsByIdResponse> responseObserver = Mockito.mock(StreamObserver.class);
        GetWorkspaceDetailsByIdRequest request = GetWorkspaceDetailsByIdRequest.newBuilder(
                        GetWorkspaceDetailsByIdRequest.getDefaultInstance())
                .setWorkspaceId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXIAMWorkspaceGrpcController.getWorkspaceDetailsById(request, responseObserver));
    }

    @Test
    void updateWorkspace() {
        StreamObserver<UpdateWorkspaceResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMWorkspaceGrpcController.updateWorkspace(
                UpdateWorkspaceRequest.newBuilder(UpdateWorkspaceRequest.getDefaultInstance())
                        .setWorkspace(Workspace.newBuilder(Workspace.getDefaultInstance())
                                .setId(ObjectId.get().toHexString())
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateWorkspaceResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateWorkspace_fail1() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMWorkspaceService = new OpenCDXIAMWorkspaceServiceImpl(
                this.openCDXIAMWorkspaceRepository, this.openCDXAuditService, this.openCDXCurrentUser, mapper);

        this.openCDXIAMWorkspaceGrpcController = new OpenCDXIAMWorkspaceGrpcController(this.openCDXIAMWorkspaceService);
        StreamObserver<UpdateWorkspaceResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UpdateWorkspaceRequest request = UpdateWorkspaceRequest.newBuilder(UpdateWorkspaceRequest.getDefaultInstance())
                .setWorkspace(Workspace.newBuilder(Workspace.getDefaultInstance())
                        .setId(ObjectId.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXIAMWorkspaceGrpcController.updateWorkspace(request, responseObserver));
    }

    @Test
    void updateWorkspace_fail2() throws JsonProcessingException {
        this.openCDXIAMWorkspaceRepository = Mockito.mock(OpenCDXIAMWorkspaceRepository.class);
        Mockito.when(this.openCDXIAMWorkspaceRepository.existsById(Mockito.any(ObjectId.class)))
                .thenReturn(false);

        this.openCDXIAMWorkspaceService = new OpenCDXIAMWorkspaceServiceImpl(
                this.openCDXIAMWorkspaceRepository,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper);

        this.openCDXIAMWorkspaceGrpcController = new OpenCDXIAMWorkspaceGrpcController(this.openCDXIAMWorkspaceService);
        StreamObserver<UpdateWorkspaceResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UpdateWorkspaceRequest request = UpdateWorkspaceRequest.newBuilder(UpdateWorkspaceRequest.getDefaultInstance())
                .setWorkspace(Workspace.newBuilder(Workspace.getDefaultInstance())
                        .setId(ObjectId.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXIAMWorkspaceGrpcController.updateWorkspace(request, responseObserver));
    }

    @Test
    void listWorkspaces() {
        StreamObserver<ListWorkspacesResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMWorkspaceGrpcController.listWorkspaces(Empty.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListWorkspacesResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
