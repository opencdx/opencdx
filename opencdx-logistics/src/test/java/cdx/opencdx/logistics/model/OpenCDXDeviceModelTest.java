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
import cdx.opencdx.grpc.data.Device;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

class OpenCDXDeviceModelTest {
    @Test
    void getProtobufMessage_1() {
        OpenCDXDeviceModel deviceModel = OpenCDXDeviceModel.builder()
                .manufacturerDate(Instant.now())
                .expiryDate(Instant.now())
                .testValidationDate(Instant.now())
                .testCaseIds(List.of(OpenCDXIdentifier.get()))
                .build();
        Assertions.assertDoesNotThrow(() -> deviceModel.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_2() {
        OpenCDXDeviceModel deviceModel = new OpenCDXDeviceModel();
        Assertions.assertDoesNotThrow(() -> deviceModel.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {
        Device device = Device.newBuilder()
                .setManufacturerId(new OpenCDXIdentifier().toHexString())
                .setManufacturerCountryId(new OpenCDXIdentifier().toHexString())
                .setVendorCountryId(new OpenCDXIdentifier().toHexString())
                .setVendorId(new OpenCDXIdentifier().toHexString())
                .setManufactureDate(Timestamp.newBuilder().setSeconds(1696732104))
                .setExpiryDate(Timestamp.newBuilder().setSeconds(1696732104))
                .setTestValidationDate(Timestamp.newBuilder().setSeconds(1696732104))
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();
        OpenCDXDeviceModel model = new OpenCDXDeviceModel(device);
        Assertions.assertEquals(
                device.getExpiryDate(), model.getProtobufMessage().getExpiryDate());
    }
}
