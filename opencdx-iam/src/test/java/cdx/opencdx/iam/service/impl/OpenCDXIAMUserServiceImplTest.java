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
package cdx.opencdx.iam.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.exceptions.OpenCDXUnauthorized;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXNationalHealthIdentifier;
import cdx.opencdx.grpc.data.IamUser;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.iam.*;
import cdx.opencdx.grpc.types.IamUserType;
import cdx.opencdx.iam.config.AppProperties;
import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXIAMUserServiceImplTest {

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    OpenCDXIAMUserService openCDXIAMUserService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXNationalHealthIdentifier openCDXNationalHealthIdentifier;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    ObjectMapper objectMapper1;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OpenCDXCommunicationService openCDXCommunicationService;

    @Autowired
    AppProperties appProperties;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {

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

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserRepository = Mockito.mock(OpenCDXIAMUserRepository.class);
        Mockito.when(this.openCDXIAMUserRepository.save(any(OpenCDXIAMUserModel.class)))
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
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXIAMUserRepository, this.objectMapper);
    }

    @Test
    void signUp() {
        SignUpRequest request = SignUpRequest.newBuilder().build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.signUp(request));
    }

    @Test
    void getIamUser() {
        GetIamUserRequest request = GetIamUserRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.getIamUser(request));
    }

    @Test
    void updateIamUser() {
        UpdateIamUserRequest request = UpdateIamUserRequest.newBuilder()
                .setIamUser(IamUser.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.updateIamUser(request));
    }

    @Test
    void deleteIamUser() {
        DeleteIamUserRequest request = DeleteIamUserRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.deleteIamUser(request));
    }

    @Test
    void userExists() {
        UserExistsRequest request = UserExistsRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.userExists(request));
    }

    @Test
    void changePassword() {
        ChangePasswordRequest request = ChangePasswordRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setNewPassword("newpass")
                .setOldPassword("pass")
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.changePassword(request));
    }

    @Test
    void changePasswordElse() {
        when(this.openCDXIAMUserRepository.findById(any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .password("{noop}pass")
                        .build()));
        ChangePasswordRequest request = ChangePasswordRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setNewPassword("newpass")
                .setOldPassword("password")
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.changePassword(request));
    }

    @Test
    void listIamUsers() throws JsonProcessingException {
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.authenticationManager,
                this.jwtTokenUtil,
                this.openCDXCurrentUser,
                this.appProperties,
                this.openCDXCommunicationService,
                this.openCDXProfileRepository);
        OpenCDXIAMUserModel model3 =
                OpenCDXIAMUserModel.builder().id(OpenCDXIdentifier.get()).build();
        when(this.openCDXIAMUserRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(model3), PageRequest.of(1, 10), 1));
        ListIamUsersRequest request = ListIamUsersRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.listIamUsers(request));
    }

    @Test
    void getIamUsersCatch() throws JsonProcessingException {
        GetIamUserRequest request = GetIamUserRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.authenticationManager,
                this.jwtTokenUtil,
                this.openCDXCurrentUser,
                this.appProperties,
                this.openCDXCommunicationService,
                this.openCDXProfileRepository);
        when(this.openCDXIAMUserRepository.findById(any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .password("{noop}pass")
                        .build()));
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.getIamUser(request));
    }

    @Test
    void updateIamUserCatch() throws JsonProcessingException {
        UpdateIamUserRequest request = UpdateIamUserRequest.newBuilder()
                .setIamUser(IamUser.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.authenticationManager,
                this.jwtTokenUtil,
                this.openCDXCurrentUser,
                this.appProperties,
                this.openCDXCommunicationService,
                this.openCDXProfileRepository);
        when(this.openCDXIAMUserRepository.findById(any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .password("{noop}pass")
                        .build()));
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.updateIamUser(request));
    }

    @Test
    void deleteIamUserCatch() throws JsonProcessingException {
        DeleteIamUserRequest request = DeleteIamUserRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.authenticationManager,
                this.jwtTokenUtil,
                this.openCDXCurrentUser,
                this.appProperties,
                this.openCDXCommunicationService,
                this.openCDXProfileRepository);
        when(this.openCDXIAMUserRepository.findById(any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .password("{noop}pass")
                        .build()));
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.deleteIamUser(request));
    }

    @Test
    void userExistsCatch() throws JsonProcessingException {
        UserExistsRequest request = UserExistsRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.authenticationManager,
                this.jwtTokenUtil,
                this.openCDXCurrentUser,
                this.appProperties,
                this.openCDXCommunicationService,
                this.openCDXProfileRepository);
        when(this.openCDXIAMUserRepository.findById(any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .username("test@opencdx.org")
                        .type(IamUserType.IAM_USER_TYPE_REGULAR)
                        .password("{noop}pass")
                        .build()));
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.userExists(request));
    }

    @Test
    void login() {
        when(this.openCDXIAMUserRepository.findByUsername(any(String.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build()));
        LoginRequest request = LoginRequest.newBuilder()
                .setUserName("username")
                .setPassword("password")
                .build();
        Assertions.assertThrows(OpenCDXFailedPrecondition.class, () -> this.openCDXIAMUserService.login(request));
    }

    @Test
    void loginElse() {
        LoginRequest request = LoginRequest.newBuilder()
                .setUserName("username")
                .setPassword("password")
                .build();
        Assertions.assertThrows(OpenCDXUnauthorized.class, () -> this.openCDXIAMUserService.login(request));
    }

    @Test
    void loginCatch() {
        when(this.openCDXIAMUserRepository.findByUsername(any(String.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .emailVerified(true)
                        .build()));
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);
        LoginRequest request = LoginRequest.newBuilder()
                .setUserName("username")
                .setPassword("password")
                .build();
        Assertions.assertThrows(OpenCDXUnauthorized.class, () -> this.openCDXIAMUserService.login(request));
    }

    @Test
    void loginCatchLockedException() {
        when(this.openCDXIAMUserRepository.findByUsername(any(String.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .emailVerified(true)
                        .build()));
        when(authenticationManager.authenticate(any())).thenThrow(LockedException.class);
        LoginRequest request = LoginRequest.newBuilder()
                .setUserName("username")
                .setPassword("password")
                .build();
        Assertions.assertThrows(OpenCDXUnauthorized.class, () -> this.openCDXIAMUserService.login(request));
    }

    @Test
    void loginCatchDisabledException() {
        when(this.openCDXIAMUserRepository.findByUsername(any(String.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .emailVerified(true)
                        .build()));
        when(authenticationManager.authenticate(any())).thenThrow(DisabledException.class);
        LoginRequest request = LoginRequest.newBuilder()
                .setUserName("username")
                .setPassword("password")
                .build();
        Assertions.assertThrows(OpenCDXUnauthorized.class, () -> this.openCDXIAMUserService.login(request));
    }

    @Test
    void verifyEmailIamUser() {
        String id = OpenCDXIdentifier.get().toHexString();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.verifyEmailIamUser(id));
    }

    @Test
    void verifyEmailIamUserElse() {
        when(this.openCDXIAMUserRepository.findById(any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .username("ab@safehealth.me")
                        .type(IamUserType.IAM_USER_TYPE_REGULAR)
                        .build()));
        String id = OpenCDXIdentifier.get().toHexString();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.verifyEmailIamUser(id));
    }

    @Test
    void verifyEmailIamUserPrimary() {
        OpenCDXIdentifier openCDXIdentifier = OpenCDXIdentifier.get();
        when(this.openCDXIAMUserRepository.findById(any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(openCDXIdentifier)
                        .username("ab@safehealth.me")
                        .type(IamUserType.IAM_USER_TYPE_REGULAR)
                        .build()));

        String id = openCDXIdentifier.toHexString();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.verifyEmailIamUser(id));
    }

    @Test
    void verifyEmailIamUserCatch() throws JsonProcessingException {
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.authenticationManager,
                this.jwtTokenUtil,
                this.openCDXCurrentUser,
                this.appProperties,
                this.openCDXCommunicationService,
                this.openCDXProfileRepository);
        when(this.openCDXIAMUserRepository.findById(any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .username("ab@safehealth.me")
                        .type(IamUserType.IAM_USER_TYPE_REGULAR)
                        .build()));
        String id = OpenCDXIdentifier.get().toHexString();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.verifyEmailIamUser(id));
    }

    @Test
    void testCurrentUser() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("");

        OpenCDXIdentifier id = OpenCDXIdentifier.get();
        OpenCDXCurrentUser currentUser = Mockito.mock(OpenCDXCurrentUser.class);
        Mockito.when(currentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(id)
                        .username("ab@safehealth.me")
                        .type(IamUserType.IAM_USER_TYPE_REGULAR)
                        .build());

        Mockito.when(currentUser.checkCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
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
                currentUser,
                this.appProperties,
                this.openCDXCommunicationService,
                this.openCDXProfileRepository);
        Assertions.assertEquals(
                id.toHexString(),
                this.openCDXIAMUserService
                        .currentUser(CurrentUserRequest.newBuilder().build())
                        .getIamUser()
                        .getId());
    }

    @Test
    void testCurrentUserCatch() throws JsonProcessingException {
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.authenticationManager,
                this.jwtTokenUtil,
                this.openCDXCurrentUser,
                this.appProperties,
                this.openCDXCommunicationService,
                this.openCDXProfileRepository);

        CurrentUserRequest currentUserRequest = CurrentUserRequest.newBuilder().build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.currentUser(currentUserRequest));
    }
}
