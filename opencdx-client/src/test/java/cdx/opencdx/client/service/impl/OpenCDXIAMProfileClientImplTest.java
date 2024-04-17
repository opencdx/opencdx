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
import cdx.opencdx.client.service.OpenCDXIAMProfileClient;
import cdx.opencdx.grpc.service.health.*;
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
class OpenCDXIAMProfileClientImplTest {

    @Mock
    UserProfileServiceGrpc.UserProfileServiceBlockingStub userProfileServiceBlockingStub;

    OpenCDXIAMProfileClient openCDXIAMProfileClient;

    @BeforeEach
    void setUp() {
        this.userProfileServiceBlockingStub = Mockito.mock(UserProfileServiceGrpc.UserProfileServiceBlockingStub.class);
        this.openCDXIAMProfileClient = new OpenCDXIAMProfileClientImpl(this.userProfileServiceBlockingStub);
        Mockito.when(userProfileServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.userProfileServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.userProfileServiceBlockingStub);
    }

    @Test
    void getUserProfile() {
        Mockito.when(this.userProfileServiceBlockingStub.getUserProfile(Mockito.any(UserProfileRequest.class)))
                .thenReturn(UserProfileResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UserProfileResponse.getDefaultInstance(),
                this.openCDXIAMProfileClient.getUserProfile(
                        UserProfileRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getUserProfileException() {
        Mockito.when(this.userProfileServiceBlockingStub.getUserProfile(Mockito.any(UserProfileRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UserProfileRequest request = UserProfileRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMProfileClient.getUserProfile(request, openCDXCallCredentials));
    }

    @Test
    void updateUserProfile() {
        Mockito.when(this.userProfileServiceBlockingStub.updateUserProfile(Mockito.any(UpdateUserProfileRequest.class)))
                .thenReturn(UpdateUserProfileResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateUserProfileResponse.getDefaultInstance(),
                this.openCDXIAMProfileClient.updateUserProfile(
                        UpdateUserProfileRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateUserProfileException() {
        Mockito.when(this.userProfileServiceBlockingStub.updateUserProfile(Mockito.any(UpdateUserProfileRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateUserProfileRequest request = UpdateUserProfileRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMProfileClient.updateUserProfile(request, openCDXCallCredentials));
    }

    @Test
    void deleteUserProfile() {
        Mockito.when(this.userProfileServiceBlockingStub.deleteUserProfile(Mockito.any(DeleteUserProfileRequest.class)))
                .thenReturn(DeleteUserProfileResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeleteUserProfileResponse.getDefaultInstance(),
                this.openCDXIAMProfileClient.deleteUserProfile(
                        DeleteUserProfileRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteUserProfileException() {
        Mockito.when(this.userProfileServiceBlockingStub.deleteUserProfile(Mockito.any(DeleteUserProfileRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteUserProfileRequest request = DeleteUserProfileRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXIAMProfileClient.deleteUserProfile(request, openCDXCallCredentials));
    }
}
