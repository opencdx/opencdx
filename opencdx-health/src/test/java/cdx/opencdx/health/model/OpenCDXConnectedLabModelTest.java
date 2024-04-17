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
package cdx.opencdx.health.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.types.EmailType;
import cdx.opencdx.grpc.types.PhoneType;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXConnectedLabModelTest {
    @Test
    void getProtobufMessage() {
        ConnectedLab connectedLab = ConnectedLab.newBuilder(ConnectedLab.getDefaultInstance())
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .setLabAddress(Address.newBuilder()
                        .setCity("New York")
                        .setCountryId(ObjectId.get().toHexString())
                        .setPostalCode("10001")
                        .setAddress1("123 Main St")
                        .build())
                .setLabEmail(EmailAddress.newBuilder()
                        .setType(EmailType.EMAIL_TYPE_WORK)
                        .setEmail("Bob@anyManufacturer")
                        .build())
                .setLabPhone(PhoneNumber.newBuilder()
                        .setType(PhoneType.PHONE_TYPE_WORK)
                        .setNumber("123-456-7890")
                        .build())
                .setLabProcessor(LabMetaDataProcessor.newBuilder().getDefaultInstanceForType())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();

        OpenCDXConnectedLabModel model = new OpenCDXConnectedLabModel(connectedLab);
        Assertions.assertNotEquals(connectedLab, model.getProtobuf());
    }
}
