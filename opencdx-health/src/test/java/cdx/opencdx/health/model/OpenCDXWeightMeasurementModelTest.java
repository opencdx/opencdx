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
import cdx.opencdx.grpc.data.WeightMeasurement;
import cdx.opencdx.grpc.types.WeightUnits;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXWeightMeasurementModelTest {

    @Test
    void getProtobufMessage() {
        WeightMeasurement weightMeasurement = WeightMeasurement.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setCreated(Timestamp.getDefaultInstance())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                .setWeight(40.0)
                .setUnitsOfMeasure(WeightUnits.KGS)
                .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();
        OpenCDXWeightMeasurementModel model = new OpenCDXWeightMeasurementModel(weightMeasurement);
        Assertions.assertNotNull(model.getProtobufMessage());
    }

    @Test
    void getProtobufMessageElse() {
        WeightMeasurement weightMeasurement = WeightMeasurement.newBuilder()
                .setCreated(Timestamp.getDefaultInstance())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                .setWeight(40.0)
                .build();
        OpenCDXWeightMeasurementModel model = new OpenCDXWeightMeasurementModel(weightMeasurement);
        Assertions.assertNotNull(model.getProtobufMessage());
    }
}
