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
package cdx.opencdx.logistics.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXCountryModel;
import cdx.opencdx.grpc.common.Country;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXCountryModelTest {

    @Test
    void getProtobufMessage_1() {
        OpenCDXCountryModel openCDXCountryModel = new OpenCDXCountryModel();
        Assertions.assertDoesNotThrow(() -> openCDXCountryModel.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_2() {
        OpenCDXCountryModel openCDXCountryModel = new OpenCDXCountryModel(Country.newBuilder()
                .setName("name")
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .getDefaultInstanceForType());
        Assertions.assertDoesNotThrow(() -> openCDXCountryModel.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {
        OpenCDXCountryModel openCDXCountryModel =
                new OpenCDXCountryModel(Country.newBuilder().setName("name").build());
        Assertions.assertDoesNotThrow(() -> openCDXCountryModel.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_4() {
        OpenCDXCountryModel openCDXCountryModel = new OpenCDXCountryModel(Country.newBuilder()
                .setName("name")
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build());
        Assertions.assertDoesNotThrow(() -> openCDXCountryModel.getProtobufMessage());
    }
}
