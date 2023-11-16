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
package cdx.opencdx.connected.test.model;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.grpc.inventory.Country;
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
        OpenCDXCountryModel openCDXCountryModel =
                new OpenCDXCountryModel(Country.newBuilder().setName("name").getDefaultInstanceForType());
        Assertions.assertDoesNotThrow(() -> openCDXCountryModel.getProtobufMessage());
    }
}
