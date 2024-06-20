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
import cdx.opencdx.commons.model.OpenCDXConnectedTestModel;
import cdx.opencdx.grpc.data.BasicInfo;
import cdx.opencdx.grpc.data.ConnectedTest;
import com.google.protobuf.Timestamp;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class OpenCDXConnectedTestModelTest {

    @Test
    void getProtobufMessage_1() {
        BasicInfo basicInfo = BasicInfo.newBuilder()
                .setId(new OpenCDXIdentifier().toHexString())
                .setNationalHealthId(UUID.randomUUID().toString())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(basicInfo)
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();

        OpenCDXConnectedTestModel model = new OpenCDXConnectedTestModel(connectedTest);

        Assertions.assertEquals(
                connectedTest.getBasicInfo().getId(),
                model.getProtobufMessage().getBasicInfo().getId());
    }

    @Test
    void getProtobufMessage_2() {
        BasicInfo basicInfo = BasicInfo.newBuilder()
                .setId(new OpenCDXIdentifier().toHexString())
                .setNationalHealthId(UUID.randomUUID().toString())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
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
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        OpenCDXConnectedTestModel model = new OpenCDXConnectedTestModel(connectedTest);
        log.info(model.toString());
        Assertions.assertEquals(
                connectedTest.getBasicInfo().getId(),
                model.getProtobufMessage().getBasicInfo().getId());
    }

    @Test
    void getProtobufMessage_5() {
        BasicInfo basicInfo = BasicInfo.newBuilder()
                .setNationalHealthId(UUID.randomUUID().toString())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .build();
        ConnectedTest connectedTest = ConnectedTest.newBuilder(ConnectedTest.getDefaultInstance())
                .setBasicInfo(basicInfo)
                .build();

        OpenCDXConnectedTestModel model = new OpenCDXConnectedTestModel(connectedTest);

        Assertions.assertDoesNotThrow(() -> {
            model.getNationalHealthId();
        });
    }
}
