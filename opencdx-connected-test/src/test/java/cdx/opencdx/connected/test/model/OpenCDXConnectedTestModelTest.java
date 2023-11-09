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

import cdx.opencdx.grpc.connected.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class OpenCDXConnectedTestModelTest {

    @Test
    void getProtobufMessage_1() {
        BasicInfo basicInfo = BasicInfo.newBuilder()
                .setId(new ObjectId().toHexString())
                .setNationalHealthId(10)
                .setUserId(ObjectId.get().toHexString())
                .build();
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(basicInfo)
                .build();

        OpenCDXConnectedTestModel model = new OpenCDXConnectedTestModel(connectedTest);

        Assertions.assertEquals(
                connectedTest.getBasicInfo().getId(),
                model.getProtobufMessage().getBasicInfo().getId());
    }

    @Test
    void getProtobufMessage_2() {
        BasicInfo basicInfo = BasicInfo.newBuilder()
                .setId(new ObjectId().toHexString())
                .setNationalHealthId(10)
                .setUserId(ObjectId.get().toHexString())
                .build();
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(basicInfo)
                .build();

        OpenCDXConnectedTestModel model = new OpenCDXConnectedTestModel(connectedTest);
        log.info(model.toString());
        Assertions.assertNotEquals(connectedTest, model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {
        OpenCDXConnectedTestModel model = new OpenCDXConnectedTestModel();
        Assertions.assertDoesNotThrow(() -> model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_4() {
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(BasicInfo.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .setNationalHealthId(10)
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();

        OpenCDXConnectedTestModel model = new OpenCDXConnectedTestModel(connectedTest);
        log.info(model.toString());
        Assertions.assertEquals(
                connectedTest.getBasicInfo().getId(),
                model.getProtobufMessage().getBasicInfo().getId());
    }
}
