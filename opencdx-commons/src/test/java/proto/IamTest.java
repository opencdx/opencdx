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
package proto;

import cdx.opencdx.grpc.iam.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class IamTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testSignUpRequest() throws JsonProcessingException {
        log.info(
                "SignUpRequest: {}",
                this.mapper.writeValueAsString(SignUpRequest.newBuilder()
                        .setUsername("bob@bob.com")
                        .setFirstName("First Name")
                        .setLastName("Last Name")
                        .setPassword("password")
                        .setSystemName("System Name")
                        .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                        .build()));
    }

    @Test
    void LoginRequest() throws JsonProcessingException {
        log.info(
                "LoginRequest: {}",
                this.mapper.writeValueAsString(LoginRequest.newBuilder()
                        .setUserName("jeff.miller@safehealth.me")
                        .setPassword("password")
                        .build()));
    }

    @Test
    void UserExistsRequest() throws JsonProcessingException {
        log.info(
                "UserExistsRequest:\n {}",
                this.mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(UserExistsRequest.newBuilder()
                                .setId("653f1755c4203f57f39843f3")
                                .build()));
    }

    @Test
    void testSignUpResponse() throws JsonProcessingException {
        log.info(
                "SignUpResponse: {}",
                this.mapper.writeValueAsString(SignUpResponse.newBuilder()
                        .setIamUser(IamUser.newBuilder()
                                .setUsername("email")
                                .setSystemName("system")
                                .setEmailVerified(false)
                                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                                .build())
                        .build()));
    }

    @Test
    void testListIamUsersRequest() throws JsonProcessingException {
        log.info(
                "ListIamUsersRequest: {}",
                this.mapper.writeValueAsString(ListIamUsersRequest.newBuilder()
                        .setPageSize(10)
                        .setPageNumber(1)
                        .setSortAscending(true)
                        .build()));
    }

    @Test
    void testListIamUsersResponse() throws JsonProcessingException {
        log.info(
                "ListIamUsersResponse: {}",
                this.mapper.writeValueAsString(ListIamUsersResponse.newBuilder()
                        .setPageSize(10)
                        .setPageNumber(1)
                        .setSortAscending(true)
                        .setPageCount(20)
                        .addAllIamUsers(List.of(IamUser.newBuilder()
                                .setUsername("email")
                                .setSystemName("system")
                                .setEmailVerified(false)
                                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                                .build()))));
    }

    @Test
    void testGetIamUserRequest() throws JsonProcessingException {
        log.info(
                "GetIamUserRequest: {}",
                this.mapper.writeValueAsString(
                        GetIamUserRequest.newBuilder().setId("id").build()));
    }

    @Test
    void testUpdateIamUserRequest() throws JsonProcessingException {
        log.info(
                "UpdateIamUserRequest: {}",
                this.mapper.writeValueAsString(UpdateIamUserRequest.newBuilder()
                        .setIamUser(IamUser.newBuilder()
                                .setUsername("email")
                                .setSystemName("system")
                                .setEmailVerified(false)
                                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                                .build())
                        .build()));
    }

    @Test
    void testUpdateIamUserResponse() throws JsonProcessingException {
        log.info(
                "UpdateIamUserResponse: {}",
                this.mapper.writeValueAsString(UpdateIamUserRequest.newBuilder()
                        .setIamUser(IamUser.newBuilder()
                                .setUsername("email")
                                .setSystemName("system")
                                .setEmailVerified(false)
                                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                                .build())
                        .build()));
    }

    @Test
    void testChangePasswordRequest() throws JsonProcessingException {
        log.info(
                "ChangePasswordRequest: {}",
                this.mapper.writeValueAsString(ChangePasswordRequest.newBuilder()
                        .setId("id")
                        .setOldPassword("oldPassword")
                        .setNewPassword("newPassword")
                        .setNewPasswordConfirmation("newPasswordConfirmation")
                        .build()));
    }

    @Test
    void testChangePasswordResponse() throws JsonProcessingException {
        log.info(
                "ChangePasswordResponse: {}",
                this.mapper.writeValueAsString(ChangePasswordResponse.newBuilder()
                        .setIamUser(IamUser.newBuilder()
                                .setUsername("email")
                                .setSystemName("system")
                                .setEmailVerified(false)
                                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                                .build())
                        .build()));
    }

    @Test
    void testDeleteIamUserResponse() throws JsonProcessingException {
        log.info(
                "DeleteIamUserResponse: {}",
                this.mapper.writeValueAsString(DeleteIamUserResponse.newBuilder()
                        .setIamUser(IamUser.newBuilder()
                                .setUsername("email")
                                .setSystemName("system")
                                .setEmailVerified(false)
                                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                                .build())
                        .build()));
    }

    @Test
    void testUserExistsRequest() throws JsonProcessingException {
        log.info(
                "UserExistsRequest: {}",
                this.mapper.writeValueAsString(
                        UserExistsRequest.newBuilder().setId("id").build()));
    }

    @Test
    void testUserExistsResponse() throws JsonProcessingException {
        log.info(
                "UserExistsResponse: {}",
                this.mapper.writeValueAsString(UserExistsResponse.newBuilder()
                        .setIamUser(IamUser.newBuilder()
                                .setUsername("email")
                                .setSystemName("system")
                                .setEmailVerified(false)
                                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                                .build())
                        .build()));
    }
}
