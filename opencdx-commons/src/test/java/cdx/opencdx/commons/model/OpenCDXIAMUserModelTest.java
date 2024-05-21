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
package cdx.opencdx.commons.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.dto.IamUser;
import cdx.opencdx.grpc.types.AgentType;
import cdx.opencdx.grpc.types.IamUserStatus;
import cdx.opencdx.grpc.types.IamUserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXIAMUserModelTest {

    @Test
    void getProtobufMessage_1() {
        IamUser user = IamUser.builder()
                .id(OpenCDXIdentifier.get().toHexString())
                .username("email")
                .systemName("system")
                .emailVerified(false)
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .type(IamUserType.IAM_USER_TYPE_REGULAR)
                .build();

        OpenCDXIAMUserModel model = new OpenCDXIAMUserModel(user);

        assertEquals(user, model.getIamUserProtobufMessage());
    }

    @Test
    void getProtobufMessage_2() {
        IamUser user = IamUser.builder()
                .username("email")
                .systemName("system")
                .emailVerified(false)
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .type(IamUserType.IAM_USER_TYPE_REGULAR)
                .build();

        OpenCDXIAMUserModel model = new OpenCDXIAMUserModel(user);

        assertEquals(user, model.getIamUserProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {

        OpenCDXIAMUserModel model = OpenCDXIAMUserModel.builder().build();

        Assertions.assertDoesNotThrow(() -> model.getIamUserProtobufMessage());
    }

    @Test
    void getProtobufMessage_4() {
        IamUser user = IamUser.builder()
                .id(OpenCDXIdentifier.get().toHexString())
                .username("email")
                .systemName("system")
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .build();

        OpenCDXIAMUserModel model = new OpenCDXIAMUserModel(user);

        assertEquals(user, model.getIamUserProtobufMessage());
    }

    @Test
    void getProtobufMessage_5() {
        IamUser user = IamUser.builder()
                .id(OpenCDXIdentifier.get().toHexString())
                .status(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .build();

        OpenCDXIAMUserModel model = new OpenCDXIAMUserModel(user);

        assertEquals(AgentType.AGENT_TYPE_SYSTEM, model.getAgentType());
    }
}
