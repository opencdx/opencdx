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

import cdx.opencdx.grpc.inventory.Address;
import cdx.opencdx.grpc.inventory.Vendor;
import com.google.protobuf.Timestamp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXVendorModelTest {

    @Test
    void getProtobufMessage_1() {
        OpenCDXVendorModel vendorModel = OpenCDXVendorModel.builder()
                .address(OpenCDXAddressModel.builder()
                        .street("Street")
                        .city("City")
                        .postalCode("Postcode")
                        .region("Region")
                        .countryId(new ObjectId())
                        .build())
                .build();
        Assertions.assertDoesNotThrow(() -> vendorModel.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_2() {
        OpenCDXVendorModel vendorModel = new OpenCDXVendorModel();
        Assertions.assertDoesNotThrow(() -> vendorModel.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {
        Vendor vendorModel = Vendor.newBuilder()
                .setVendorName("vendorName")
                .setVendorAddress(Address.newBuilder()
                        .setCountry(ObjectId.get().toHexString())
                        .build())
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(ObjectId.get().toHexString())
                .setModifier(ObjectId.get().toHexString())
                .build();
        OpenCDXVendorModel model = new OpenCDXVendorModel(vendorModel);
        Assertions.assertEquals(
                vendorModel.getVendorName(), model.getProtobufMessage().getVendorName());
    }

    @Test
    void getProtobufMessage_4() {
        Vendor vendorModel = Vendor.newBuilder()
                .setVendorName("vendorName")
                .setVendorAddress(Address.newBuilder()
                        .setCountry(ObjectId.get().toHexString())
                        .build())
                .build();
        OpenCDXVendorModel model = new OpenCDXVendorModel(vendorModel);
        Assertions.assertEquals(
                vendorModel.getVendorName(), model.getProtobufMessage().getVendorName());
    }
}
