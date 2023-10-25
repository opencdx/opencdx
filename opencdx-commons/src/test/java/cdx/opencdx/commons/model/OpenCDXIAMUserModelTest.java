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
package cdx.opencdx.commons.model;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.grpc.iam.*;
import com.google.protobuf.Timestamp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXIAMUserModelTest {

    @Test
    void getProtobufMessage_1() {
        IamUser user = IamUser.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setCreatedAt(Timestamp.getDefaultInstance())
                .setUpdatedAt(Timestamp.getDefaultInstance())
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("email")
                .setSystemName("system")
                .setEmailVerified(false)
                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                .setPhone("123-456-7890")
                .build();

        OpenCDXIAMUserModel model = new OpenCDXIAMUserModel(user);

        assertEquals(user, model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_2() {
        IamUser user = IamUser.newBuilder()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("email")
                .setSystemName("system")
                .setEmailVerified(false)
                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                .setPhone("123-456-7890")
                .build();

        OpenCDXIAMUserModel model = new OpenCDXIAMUserModel(user);

        assertNotEquals(user, model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {

        OpenCDXIAMUserModel model = OpenCDXIAMUserModel.builder().build();

        Assertions.assertDoesNotThrow(() -> model.getProtobufMessage());
    }
}
