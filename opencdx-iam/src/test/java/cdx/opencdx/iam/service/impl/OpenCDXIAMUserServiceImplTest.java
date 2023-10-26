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
package cdx.opencdx.iam.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cdx.opencdx.client.service.OpenCDXCommunicationClient;
import cdx.opencdx.client.service.impl.OpenCDXCommunicationClientImpl;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.grpc.communication.CommunicationServiceGrpc;
import cdx.opencdx.grpc.communication.SuccessResponse;
import cdx.opencdx.grpc.iam.*;
import cdx.opencdx.iam.config.AppProperties;
import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXIAMUserServiceImplTest {

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    OpenCDXIAMUserService openCDXIAMUserService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    ObjectMapper objectMapper1;

    @Autowired
    PasswordEncoder passwordEncoder;

    OpenCDXCommunicationClient openCDXCommunicationClient;

    @Mock
    CommunicationServiceGrpc.CommunicationServiceBlockingStub blockingStub;

    @Autowired
    AppProperties appProperties;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserRepository = Mockito.mock(OpenCDXIAMUserRepository.class);
        Mockito.when(this.openCDXIAMUserRepository.save(any(OpenCDXIAMUserModel.class)))
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

        this.openCDXCommunicationClient = new OpenCDXCommunicationClientImpl(this.blockingStub);

        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.openCDXCommunicationClient,
                this.appProperties);
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
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.getIamUser(request));
    }

    @Test
    void updateIamUser() {
        UpdateIamUserRequest request = UpdateIamUserRequest.newBuilder()
                .setIamUser(
                        IamUser.newBuilder().setId(ObjectId.get().toHexString()).build())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.updateIamUser(request));
    }

    @Test
    void deleteIamUser() {
        DeleteIamUserRequest request = DeleteIamUserRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.deleteIamUser(request));
    }

    @Test
    void userExists() {
        UserExistsRequest request = UserExistsRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.userExists(request));
    }

    @Test
    void changePassword() {
        ChangePasswordRequest request = ChangePasswordRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setNewPassword("newpass")
                .setOldPassword("pass")
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.changePassword(request));
    }

    @Test
    void changePasswordElse() {
        when(this.openCDXIAMUserRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(ObjectId.get())
                        .password("{noop}pass")
                        .build()));
        ChangePasswordRequest request = ChangePasswordRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
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
                this.openCDXCommunicationClient,
                this.appProperties);
        OpenCDXIAMUserModel model3 = OpenCDXIAMUserModel.builder()
                .id(ObjectId.get())
                .firstName("name")
                .build();
        when(this.openCDXIAMUserRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(model3), PageRequest.of(1, 10), 1));
        ListIamUsersRequest request = ListIamUsersRequest.newBuilder()
                .setPageNumber(1)
                .setPageSize(10)
                .setSortAscending(true)
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.listIamUsers(request));
    }

    @Test
    void getIamUsersCatch() throws JsonProcessingException {
        GetIamUserRequest request = GetIamUserRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.openCDXCommunicationClient,
                this.appProperties);
        when(this.openCDXIAMUserRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(ObjectId.get())
                        .password("{noop}pass")
                        .build()));
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.getIamUser(request));
    }

    @Test
    void updateIamUserCatch() throws JsonProcessingException {
        UpdateIamUserRequest request = UpdateIamUserRequest.newBuilder()
                .setIamUser(
                        IamUser.newBuilder().setId(ObjectId.get().toHexString()).build())
                .build();
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.openCDXCommunicationClient,
                this.appProperties);
        when(this.openCDXIAMUserRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(ObjectId.get())
                        .password("{noop}pass")
                        .build()));
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.updateIamUser(request));
    }

    @Test
    void deleteIamUserCatch() throws JsonProcessingException {
        DeleteIamUserRequest request = DeleteIamUserRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.openCDXCommunicationClient,
                this.appProperties);
        when(this.openCDXIAMUserRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(ObjectId.get())
                        .password("{noop}pass")
                        .build()));
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.deleteIamUser(request));
    }

    @Test
    void userExistsCatch() throws JsonProcessingException {
        UserExistsRequest request = UserExistsRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        this.objectMapper1 = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper1.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper1,
                this.openCDXAuditService,
                this.openCDXIAMUserRepository,
                this.passwordEncoder,
                this.openCDXCommunicationClient,
                this.appProperties);
        when(this.openCDXIAMUserRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(ObjectId.get())
                        .password("{noop}pass")
                        .build()));
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.userExists(request));
    }

    @Test
    void verifyEmailIamUser() {
        when(this.blockingStub.sendNotification(any())).thenReturn(SuccessResponse.getDefaultInstance());
        String id = ObjectId.get().toHexString();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMUserService.verifyEmailIamUser(id));
    }

    @Test
    void verifyEmailIamUserElse() {
        when(this.openCDXIAMUserRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(ObjectId.get())
                        .firstName("FName")
                        .lastName("LName")
                        .email("ab@safehealth.me")
                        .build()));
        String id = ObjectId.get().toHexString();
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
                this.openCDXCommunicationClient,
                this.appProperties);
        when(this.openCDXIAMUserRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(ObjectId.get())
                        .firstName("FName")
                        .lastName("LName")
                        .email("ab@safehealth.me")
                        .build()));
        String id = ObjectId.get().toHexString();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.verifyEmailIamUser(id));
    }
}
