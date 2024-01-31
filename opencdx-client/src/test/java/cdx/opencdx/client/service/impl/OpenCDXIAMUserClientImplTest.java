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
package cdx.opencdx.client.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXIAMUserClient;
import cdx.opencdx.grpc.iam.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenCDXIAMUserClientImplTest {

    @Mock
    IamUserServiceGrpc.IamUserServiceBlockingStub iamUserServiceBlockingStub;

    OpenCDXIAMUserClient openCDXIAMUserClient;

    @BeforeEach
    void setUp() {
        this.iamUserServiceBlockingStub = Mockito.mock(IamUserServiceGrpc.IamUserServiceBlockingStub.class);
        this.openCDXIAMUserClient = new OpenCDXIAMUserClientImpl(this.iamUserServiceBlockingStub);
        Mockito.when(iamUserServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.iamUserServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.iamUserServiceBlockingStub);
    }

    @Test
    void signUp() {
        Mockito.when(this.iamUserServiceBlockingStub.signUp(Mockito.any(SignUpRequest.class)))
                .thenReturn(SignUpResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SignUpResponse.getDefaultInstance(),
                this.openCDXIAMUserClient.signUp(SignUpRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void signUpException() {
        Mockito.when(this.iamUserServiceBlockingStub.signUp(Mockito.any(SignUpRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        SignUpRequest request = SignUpRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> this.openCDXIAMUserClient.signUp(request, openCDXCallCredentials));
    }

    @Test
    void getIamUser() {
        Mockito.when(this.iamUserServiceBlockingStub.getIamUser(Mockito.any(GetIamUserRequest.class)))
                .thenReturn(GetIamUserResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetIamUserResponse.getDefaultInstance(),
                this.openCDXIAMUserClient.getIamUser(GetIamUserRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getIamUserException() {
        Mockito.when(this.iamUserServiceBlockingStub.getIamUser(Mockito.any(GetIamUserRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetIamUserRequest request = GetIamUserRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMUserClient.getIamUser(request, openCDXCallCredentials));
    }

    @Test
    void updateIamUser() {
        Mockito.when(this.iamUserServiceBlockingStub.updateIamUser(Mockito.any(UpdateIamUserRequest.class)))
                .thenReturn(UpdateIamUserResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateIamUserResponse.getDefaultInstance(),
                this.openCDXIAMUserClient.updateIamUser(
                        UpdateIamUserRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateIamUserException() {
        Mockito.when(this.iamUserServiceBlockingStub.updateIamUser(Mockito.any(UpdateIamUserRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateIamUserRequest request = UpdateIamUserRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMUserClient.updateIamUser(request, openCDXCallCredentials));
    }

    @Test
    void listIamUsers() {
        Mockito.when(this.iamUserServiceBlockingStub.listIamUsers(Mockito.any(ListIamUsersRequest.class)))
                .thenReturn(ListIamUsersResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListIamUsersResponse.getDefaultInstance(),
                this.openCDXIAMUserClient.listIamUsers(
                        ListIamUsersRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listIamUsersException() {
        Mockito.when(this.iamUserServiceBlockingStub.listIamUsers(Mockito.any(ListIamUsersRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListIamUsersRequest request = ListIamUsersRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMUserClient.listIamUsers(request, openCDXCallCredentials));
    }

    @Test
    void changePassword() {
        Mockito.when(this.iamUserServiceBlockingStub.changePassword(Mockito.any(ChangePasswordRequest.class)))
                .thenReturn(ChangePasswordResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ChangePasswordResponse.getDefaultInstance(),
                this.openCDXIAMUserClient.changePassword(
                        ChangePasswordRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void changePasswordException() {
        Mockito.when(this.iamUserServiceBlockingStub.changePassword(Mockito.any(ChangePasswordRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ChangePasswordRequest request = ChangePasswordRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMUserClient.changePassword(request, openCDXCallCredentials));
    }

    @Test
    void deleteIamUser() {
        Mockito.when(this.iamUserServiceBlockingStub.deleteIamUser(Mockito.any(DeleteIamUserRequest.class)))
                .thenReturn(DeleteIamUserResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeleteIamUserResponse.getDefaultInstance(),
                this.openCDXIAMUserClient.deleteIamUser(
                        DeleteIamUserRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteIamUserException() {
        Mockito.when(this.iamUserServiceBlockingStub.deleteIamUser(Mockito.any(DeleteIamUserRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteIamUserRequest request = DeleteIamUserRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMUserClient.deleteIamUser(request, openCDXCallCredentials));
    }

    @Test
    void userExists() {
        Mockito.when(this.iamUserServiceBlockingStub.userExists(Mockito.any(UserExistsRequest.class)))
                .thenReturn(UserExistsResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UserExistsResponse.getDefaultInstance(),
                this.openCDXIAMUserClient.userExists(UserExistsRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void userExistsException() {
        Mockito.when(this.iamUserServiceBlockingStub.userExists(Mockito.any(UserExistsRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UserExistsRequest request = UserExistsRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMUserClient.userExists(request, openCDXCallCredentials));
    }

    @Test
    void login() {
        Mockito.when(this.iamUserServiceBlockingStub.login(Mockito.any(LoginRequest.class)))
                .thenReturn(LoginResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                LoginResponse.getDefaultInstance(),
                this.openCDXIAMUserClient.login(LoginRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void loginException() {
        Mockito.when(this.iamUserServiceBlockingStub.login(Mockito.any(LoginRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        LoginRequest request = LoginRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class, () -> this.openCDXIAMUserClient.login(request, openCDXCallCredentials));
    }

    @Test
    void currentUser() {
        Mockito.when(this.iamUserServiceBlockingStub.currentUser(Mockito.any(CurrentUserRequest.class)))
                .thenReturn(CurrentUserResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CurrentUserResponse.getDefaultInstance(),
                this.openCDXIAMUserClient.currentUser(CurrentUserRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void currentUserException() {
        Mockito.when(this.iamUserServiceBlockingStub.currentUser(Mockito.any(CurrentUserRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CurrentUserRequest request = CurrentUserRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMUserClient.currentUser(request, openCDXCallCredentials));
    }
}
