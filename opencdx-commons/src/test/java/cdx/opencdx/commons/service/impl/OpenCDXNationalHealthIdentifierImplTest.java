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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXNationalHealthIdentifier;
import cdx.opencdx.grpc.iam.IamUserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenCDXNationalHealthIdentifierImplTest {

    OpenCDXNationalHealthIdentifier openCDXNationalHealthIdentifier;

    @BeforeEach
    void setup() {
        this.openCDXNationalHealthIdentifier = new OpenCDXNationalHealthIdentifierImpl();
    }

    @Test
    void testGenerateNationalHealthId() {
        Assertions.assertFalse(this.openCDXNationalHealthIdentifier
                .generateNationalHealthId(OpenCDXIAMUserModel.builder()
                        .username("bob@bob.com")
                        .type(IamUserType.IAM_USER_TYPE_REGULAR)
                        .build())
                .isEmpty());
    }

    @Test
    void testGenerateNationalHealthIdFail() {
        OpenCDXIAMUserModel userModel = OpenCDXIAMUserModel.builder()
                .username("bob@bob.com")
                .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class,
                () -> this.openCDXNationalHealthIdentifier.generateNationalHealthId(userModel));
    }
}
