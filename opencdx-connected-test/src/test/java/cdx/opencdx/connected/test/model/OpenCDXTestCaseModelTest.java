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

import cdx.opencdx.grpc.inventory.TestCase;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXTestCaseModelTest {
    @Test
    void getProtobufMessage_1() {
        OpenCDXTestCaseModel testCaseModel = OpenCDXTestCaseModel.builder()
                .packagingDate(Instant.now())
                .expiryDate(Instant.now())
                .deviceIds(List.of(ObjectId.get()))
                .build();
        Assertions.assertDoesNotThrow(() -> testCaseModel.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_2() {
        OpenCDXTestCaseModel testCaseModel = new OpenCDXTestCaseModel();
        Assertions.assertDoesNotThrow(() -> testCaseModel.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {
        TestCase testCase = TestCase.newBuilder()
                .setVendorId(new ObjectId().toHexString())
                .setManufacturerId(ObjectId.get().toHexString())
                .setPackagingDate(Timestamp.newBuilder().setSeconds(1696732104))
                .setExpiryDate(Timestamp.newBuilder().setSeconds(1700138692))
                .addAllDeviceIds(List.of(new ObjectId().toHexString()))
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(ObjectId.get().toHexString())
                .setModifier(ObjectId.get().toHexString())
                .build();
        OpenCDXTestCaseModel model = new OpenCDXTestCaseModel(testCase);
        Assertions.assertEquals(
                model.getManufacturerId().toHexString(),
                model.getProtobufMessage().getManufacturerId());
    }
}
