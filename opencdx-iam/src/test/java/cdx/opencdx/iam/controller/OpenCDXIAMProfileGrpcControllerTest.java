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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXNationalHealthIdentifier;
import cdx.opencdx.grpc.profile.*;
import cdx.opencdx.iam.config.AppProperties;
import cdx.opencdx.iam.service.OpenCDXIAMProfileService;
import cdx.opencdx.iam.service.impl.OpenCDXIAMProfileServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import java.util.UUID;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXIAMProfileGrpcControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OpenCDXNationalHealthIdentifier openCDXNationalHealthIdentifier;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    OpenCDXIAMProfileService openCDXIAMProfileService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    OpenCDXIAMProfileGrpcController openCDXIAMProfileGrpcController;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AppProperties appProperties;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    void setUp() {

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(ObjectId.get())
                                .build());
                    }
                });
        Mockito.when(this.openCDXProfileRepository.save(Mockito.any(OpenCDXProfileModel.class)))
                .thenAnswer(new Answer<OpenCDXProfileModel>() {
                    @Override
                    public OpenCDXProfileModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXProfileModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(ObjectId.get())
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
                                .id(ObjectId.get())
                                .nationalHealthId(argument)
                                .userId(ObjectId.get())
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
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
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
                                .id(ObjectId.get())
                                .password("{noop}pass")
                                .username("ab@safehealth.me")
                                .emailVerified(true)
                                .build());
                    }
                });

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        this.openCDXIAMProfileGrpcController = new OpenCDXIAMProfileGrpcController(this.openCDXIAMProfileService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXIAMUserRepository);
    }

    @Test
    void getUserProfile() {
        StreamObserver<UserProfileResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMProfileGrpcController.getUserProfile(
                UserProfileRequest.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UserProfileResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateUserProfile() {
        StreamObserver<UpdateUserProfileResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMProfileGrpcController.updateUserProfile(
                UpdateUserProfileRequest.newBuilder(UpdateUserProfileRequest.getDefaultInstance())
                        .setUpdatedProfile(UserProfile.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .build())
                        .setUserId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateUserProfileResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void createUserProfile() {
        StreamObserver<CreateUserProfileResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMProfileGrpcController.createUserProfile(
                CreateUserProfileRequest.newBuilder(CreateUserProfileRequest.getDefaultInstance())
                        .setUserProfile(UserProfile.newBuilder()
                                .setUserId(ObjectId.get().toHexString()))
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(CreateUserProfileResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteUserProfile() {
        StreamObserver<DeleteUserProfileResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMProfileGrpcController.deleteUserProfile(
                DeleteUserProfileRequest.newBuilder(DeleteUserProfileRequest.getDefaultInstance())
                        .setUserId(ObjectId.get().toHexString())
                        .build(),
                responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DeleteUserProfileResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
