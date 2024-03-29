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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXNationalHealthIdentifier;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.iam.*;
import cdx.opencdx.iam.config.AppProperties;
import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import cdx.opencdx.iam.service.impl.OpenCDXIAMUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.Collections;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXIAMUserGrpcControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OpenCDXNationalHealthIdentifier openCDXNationalHealthIdentifier;

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    OpenCDXIAMUserService openCDXIAMUserService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    OpenCDXIAMUserGrpcController openCDXIAMUserGrpcController;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    OpenCDXCommunicationService openCDXCommunicationService;

    @Autowired
    AppProperties appProperties;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    void setUp() {

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findByUserId(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(argument)
                                .build());
                    }
                });
        Mockito.when(this.openCDXProfileRepository.findByNationalHealthId(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(argument)
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        this.openCDXIAMUserRepository = Mockito.mock(OpenCDXIAMUserRepository.class);
        Mockito.when(this.openCDXIAMUserRepository.save(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenAnswer(new Answer<OpenCDXIAMUserModel>() {
                    @Override
                    public OpenCDXIAMUserModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIAMUserModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(argument)
                                .password("{noop}pass")
                                .username("ab@safehealth.me")
                                .emailVerified(true)
                                .build());
                    }
                });
        when(this.openCDXIAMUserRepository.findByUsername(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .password("{noop}pass")
                                .username("ab@safehealth.me")
                                .emailVerified(true)
                                .build());
                    }
                });

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.checkCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.authenticationManager,
                this.jwtTokenUtil,
                this.openCDXCurrentUser,
                this.appProperties,
                this.openCDXCommunicationService,
                this.openCDXProfileRepository);
        this.openCDXIAMUserGrpcController = new OpenCDXIAMUserGrpcController(this.openCDXIAMUserService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXIAMUserRepository);
    }

    @Test
    void signUp() {
        StreamObserver<SignUpResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.signUp(SignUpRequest.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SignUpResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listIamUsers() {
        StreamObserver<ListIamUsersResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXIAMUserRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));
        this.openCDXIAMUserGrpcController.listIamUsers(
                ListIamUsersRequest.newBuilder()
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListIamUsersResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listIamUsers_2() {
        StreamObserver<ListIamUsersResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXIAMUserRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));
        this.openCDXIAMUserGrpcController.listIamUsers(
                ListIamUsersRequest.newBuilder()
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .setSort("username")
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListIamUsersResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listIamUsers_3() {
        StreamObserver<ListIamUsersResponse> responseObserver = Mockito.mock(StreamObserver.class);
        Mockito.when(this.openCDXIAMUserRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));
        this.openCDXIAMUserGrpcController.listIamUsers(
                ListIamUsersRequest.newBuilder()
                        .setPagination(Pagination.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(false)
                                .setSort("username")
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListIamUsersResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getIamUser() {
        StreamObserver<GetIamUserResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.getIamUser(
                GetIamUserRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetIamUserResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateIamUser() {
        StreamObserver<UpdateIamUserResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.updateIamUser(
                UpdateIamUserRequest.newBuilder()
                        .setIamUser(IamUser.newBuilder()
                                .setId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateIamUserResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void changePassword() {
        StreamObserver<ChangePasswordResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.changePassword(
                ChangePasswordRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setNewPassword("newpass")
                        .setOldPassword("pass")
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ChangePasswordResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteIamUser() {
        StreamObserver<DeleteIamUserResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.deleteIamUser(
                DeleteIamUserRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DeleteIamUserResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void userExists() {
        StreamObserver<UserExistsResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.userExists(
                UserExistsRequest.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UserExistsResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void login() {
        when(jwtTokenUtil.generateAccessToken(any())).thenReturn("token");
        StreamObserver<LoginResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.login(
                LoginRequest.newBuilder()
                        .setUserName("username")
                        .setPassword("password")
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(LoginResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void currentUser() {
        StreamObserver<CurrentUserResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.currentUser(
                CurrentUserRequest.newBuilder().build(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CurrentUserResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
