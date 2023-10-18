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

import cdx.media.v2alpha.*;
import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.model.OpenCDXIAMUserModel;
import health.safe.api.opencdx.commons.repository.OpenCDXIAMUserRepository;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
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

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
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
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper, this.openCDXAuditService, this.openCDXIAMUserRepository);
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
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.getIamUser(request));
    }

    @Test
    void updateIamUser() {
        UpdateIamUserRequest request = UpdateIamUserRequest.newBuilder()
                .setIamUser(
                        IamUser.newBuilder().setId(ObjectId.get().toHexString()).build())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.updateIamUser(request));
    }

    @Test
    void deleteIamUser() {
        DeleteIamUserRequest request = DeleteIamUserRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.deleteIamUser(request));
    }

    @Test
    void userExists() {
        UserExistsRequest request = UserExistsRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> this.openCDXIAMUserService.userExists(request));
    }
}
