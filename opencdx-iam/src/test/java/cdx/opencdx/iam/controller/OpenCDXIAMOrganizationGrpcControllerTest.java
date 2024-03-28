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
package cdx.opencdx.iam.controller;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.organization.*;
import cdx.opencdx.iam.config.AppProperties;
import cdx.opencdx.iam.model.OpenCDXIAMOrganizationModel;
import cdx.opencdx.iam.repository.OpenCDXIAMOrganizationRepository;
import cdx.opencdx.iam.service.OpenCDXIAMOrganizationService;
import cdx.opencdx.iam.service.impl.OpenCDXIAMOrganizationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
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
class OpenCDXIAMOrganizationGrpcControllerTest {
    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Mock
    OpenCDXIAMOrganizationRepository openCDXIAMOrganizationRepository;

    OpenCDXIAMOrganizationService openCDXIAMOrganizationService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    OpenCDXIAMOrganizationGrpcController openCDXIAMOrganizationGrpcController;

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
        this.openCDXIAMOrganizationRepository = Mockito.mock(OpenCDXIAMOrganizationRepository.class);
        Mockito.when(this.openCDXIAMOrganizationRepository.save(Mockito.any(OpenCDXIAMOrganizationModel.class)))
                .thenAnswer(new Answer<OpenCDXIAMOrganizationModel>() {
                    @Override
                    public OpenCDXIAMOrganizationModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIAMOrganizationModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXIAMOrganizationRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMOrganizationModel>>() {
                    @Override
                    public Optional<OpenCDXIAMOrganizationModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMOrganizationModel.builder()
                                .id(argument)
                                .build());
                    }
                });
        Mockito.when(this.openCDXIAMOrganizationRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.openCDXIAMOrganizationService = new OpenCDXIAMOrganizationServiceImpl(
                this.openCDXIAMOrganizationRepository,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper);
        this.openCDXIAMOrganizationGrpcController =
                new OpenCDXIAMOrganizationGrpcController(this.openCDXIAMOrganizationService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXIAMOrganizationRepository);
    }

    @Test
    void createOrganization() {
        StreamObserver<CreateOrganizationResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMOrganizationGrpcController.createOrganization(
                CreateOrganizationRequest.newBuilder(CreateOrganizationRequest.getDefaultInstance())
                        .setOrganization(Organization.newBuilder(Organization.getDefaultInstance())
                                .setName("test")
                                .setCreated(Timestamp.getDefaultInstance())
                                .setModified(Timestamp.getDefaultInstance())
                                .setCreator(OpenCDXIdentifier.get().toHexString())
                                .setModifier(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateOrganizationResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void createOrganization_fail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMOrganizationService = new OpenCDXIAMOrganizationServiceImpl(
                this.openCDXIAMOrganizationRepository, this.openCDXAuditService, this.openCDXCurrentUser, mapper);

        this.openCDXIAMOrganizationGrpcController =
                new OpenCDXIAMOrganizationGrpcController(this.openCDXIAMOrganizationService);

        StreamObserver<CreateOrganizationResponse> responseObserver = Mockito.mock(StreamObserver.class);
        CreateOrganizationRequest request = CreateOrganizationRequest.getDefaultInstance();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXIAMOrganizationGrpcController.createOrganization(request, responseObserver));
    }

    @Test
    void getOrganizationDetailsById() {
        StreamObserver<GetOrganizationDetailsByIdResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMOrganizationGrpcController.getOrganizationDetailsById(
                GetOrganizationDetailsByIdRequest.newBuilder(GetOrganizationDetailsByIdRequest.getDefaultInstance())
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1))
                .onNext(Mockito.any(GetOrganizationDetailsByIdResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getOrganizationDetailsById_fail() {
        this.openCDXIAMOrganizationRepository = Mockito.mock(OpenCDXIAMOrganizationRepository.class);
        Mockito.when(this.openCDXIAMOrganizationRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXIAMOrganizationService = new OpenCDXIAMOrganizationServiceImpl(
                this.openCDXIAMOrganizationRepository,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper);

        this.openCDXIAMOrganizationGrpcController =
                new OpenCDXIAMOrganizationGrpcController(this.openCDXIAMOrganizationService);

        StreamObserver<GetOrganizationDetailsByIdResponse> responseObserver = Mockito.mock(StreamObserver.class);
        GetOrganizationDetailsByIdRequest request = GetOrganizationDetailsByIdRequest.newBuilder(
                        GetOrganizationDetailsByIdRequest.getDefaultInstance())
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXIAMOrganizationGrpcController.getOrganizationDetailsById(request, responseObserver));
    }

    @Test
    void updateOrganization() {
        StreamObserver<UpdateOrganizationResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMOrganizationGrpcController.updateOrganization(
                UpdateOrganizationRequest.newBuilder(UpdateOrganizationRequest.getDefaultInstance())
                        .setOrganization(Organization.newBuilder(Organization.getDefaultInstance())
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateOrganizationResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateOrganization_fail1() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMOrganizationService = new OpenCDXIAMOrganizationServiceImpl(
                this.openCDXIAMOrganizationRepository, this.openCDXAuditService, this.openCDXCurrentUser, mapper);

        this.openCDXIAMOrganizationGrpcController =
                new OpenCDXIAMOrganizationGrpcController(this.openCDXIAMOrganizationService);
        StreamObserver<UpdateOrganizationResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UpdateOrganizationRequest request = UpdateOrganizationRequest.newBuilder(
                        UpdateOrganizationRequest.getDefaultInstance())
                .setOrganization(Organization.newBuilder(Organization.getDefaultInstance())
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXIAMOrganizationGrpcController.updateOrganization(request, responseObserver));
    }

    @Test
    void updateOrganization_fail2() throws JsonProcessingException {
        this.openCDXIAMOrganizationRepository = Mockito.mock(OpenCDXIAMOrganizationRepository.class);
        Mockito.when(this.openCDXIAMOrganizationRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(false);

        this.openCDXIAMOrganizationService = new OpenCDXIAMOrganizationServiceImpl(
                this.openCDXIAMOrganizationRepository,
                this.openCDXAuditService,
                this.openCDXCurrentUser,
                this.objectMapper);

        this.openCDXIAMOrganizationGrpcController =
                new OpenCDXIAMOrganizationGrpcController(this.openCDXIAMOrganizationService);
        StreamObserver<UpdateOrganizationResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UpdateOrganizationRequest request = UpdateOrganizationRequest.newBuilder(
                        UpdateOrganizationRequest.getDefaultInstance())
                .setOrganization(Organization.newBuilder(Organization.getDefaultInstance())
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotFound.class,
                () -> this.openCDXIAMOrganizationGrpcController.updateOrganization(request, responseObserver));
    }

    @Test
    void listOrganizations() {
        StreamObserver<ListOrganizationsResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMOrganizationGrpcController.listOrganizations(Empty.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListOrganizationsResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
