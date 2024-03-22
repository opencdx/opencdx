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

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.grpc.health.HeightMeasurement;
import com.google.protobuf.Timestamp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXHeightMeasurementModelTest {

    @Test
    void getProtobufMessage() {
        HeightMeasurement heightMeasurement = HeightMeasurement.newBuilder()
                .setCreated(Timestamp.getDefaultInstance())
                .setPatientId(ObjectId.get().toHexString())
                .setNationalHealthId(ObjectId.get().toHexString())
                .setHeight(10.0)
                .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(ObjectId.get().toHexString())
                .setModifier(ObjectId.get().toHexString())
                .build();
        OpenCDXHeightMeasurementModel model = new OpenCDXHeightMeasurementModel(heightMeasurement);
        Assertions.assertNotNull(model.getProtobufMessage());
    }

    @Test
    void getProtobufMessageElse() {
        HeightMeasurement heightMeasurement = HeightMeasurement.newBuilder()
                .setCreated(Timestamp.getDefaultInstance())
                .setPatientId(ObjectId.get().toHexString())
                .setNationalHealthId(ObjectId.get().toHexString())
                .setHeight(10.0)
                .build();
        OpenCDXHeightMeasurementModel model = new OpenCDXHeightMeasurementModel(heightMeasurement);
        Assertions.assertNotNull(model.getProtobufMessage());
    }
}
