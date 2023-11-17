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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXNationalHealthIdentifier;
import cdx.opencdx.grpc.audit.AgentType;
import cdx.opencdx.grpc.iam.IamUserType;
import cdx.opencdx.grpc.profile.DeleteUserProfileRequest;
import cdx.opencdx.grpc.profile.FullName;
import cdx.opencdx.grpc.profile.UpdateUserProfileRequest;
import cdx.opencdx.grpc.profile.UserProfileRequest;
import cdx.opencdx.iam.config.AppProperties;
import cdx.opencdx.iam.service.OpenCDXIAMProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXIAMProfileServiceImplTest {

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    OpenCDXIAMProfileService openCDXIAMProfileService;

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
    AppProperties appProperties;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenUtil jwtTokenUtil;

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
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUserType()).thenReturn(AgentType.AGENT_TYPE_HUMAN_USER);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXIAMUserRepository, this.objectMapper);
    }

    @Test
    void getUserProfile_1() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper, this.openCDXAuditService, this.openCDXIAMUserRepository, this.openCDXCurrentUser);
        UserProfileRequest request = UserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMProfileService.getUserProfile(request));
    }

    @Test
    void getUserProfile_2() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        when(this.openCDXIAMUserRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(ObjectId.get())
                        .username("ab@safehealth.me")
                        .type(IamUserType.IAM_USER_TYPE_REGULAR)
                        .fullName(FullName.newBuilder()
                                .setFirstName("bob")
                                .setLastName("bob")
                                .build())
                        .build()));

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper, this.openCDXAuditService, this.openCDXIAMUserRepository, this.openCDXCurrentUser);
        UserProfileRequest request = UserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.getUserProfile(request));
    }

    @Test
    void updateUserProfile_1() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper, this.openCDXAuditService, this.openCDXIAMUserRepository, this.openCDXCurrentUser);
        UpdateUserProfileRequest request = UpdateUserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMProfileService.updateUserProfile(request));
    }

    @Test
    void updateUserProfile_2() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        when(this.openCDXIAMUserRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(ObjectId.get())
                        .username("ab@safehealth.me")
                        .type(IamUserType.IAM_USER_TYPE_REGULAR)
                        .fullName(FullName.newBuilder()
                                .setFirstName("bob")
                                .setLastName("bob")
                                .build())
                        .build()));

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper, this.openCDXAuditService, this.openCDXIAMUserRepository, this.openCDXCurrentUser);
        UpdateUserProfileRequest request = UpdateUserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.updateUserProfile(request));
    }

    @Test
    void deleteUserProfile_1() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper, this.openCDXAuditService, this.openCDXIAMUserRepository, this.openCDXCurrentUser);
        DeleteUserProfileRequest request = DeleteUserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMProfileService.deleteUserProfile(request));
    }

    @Test
    void deleteUserProfile_2() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        when(this.openCDXIAMUserRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(OpenCDXIAMUserModel.builder()
                        .id(ObjectId.get())
                        .username("ab@safehealth.me")
                        .type(IamUserType.IAM_USER_TYPE_REGULAR)
                        .fullName(FullName.newBuilder()
                                .setFirstName("bob")
                                .setLastName("bob")
                                .build())
                        .build()));

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper, this.openCDXAuditService, this.openCDXIAMUserRepository, this.openCDXCurrentUser);
        DeleteUserProfileRequest request = DeleteUserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.deleteUserProfile(request));
    }
}
