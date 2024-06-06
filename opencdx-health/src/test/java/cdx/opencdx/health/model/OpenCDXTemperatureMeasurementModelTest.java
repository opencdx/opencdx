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
import cdx.opencdx.grpc.data.TemperatureMeasurement;
import cdx.opencdx.grpc.types.TemperatureScale;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXTemperatureMeasurementModelTest {

    @Test
    void getProtobufMessage() {
        TemperatureMeasurement temperatureMeasurement = TemperatureMeasurement.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setCreated(Timestamp.getDefaultInstance())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                .setTemperatureValue(37.0)
                .setTemperatureScale(TemperatureScale.CELSIUS)
                .setTimeOfMeasurement(Timestamp.newBuilder()
                        .setSeconds(1696732104)
                        .setNanos(1696732104)
                        .build())
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();
        OpenCDXTemperatureMeasurementModel model = new OpenCDXTemperatureMeasurementModel(temperatureMeasurement);
        Assertions.assertNotNull(model.getProtobufMessage());
    }

    @Test
    void getProtobufMessageElse() {
        TemperatureMeasurement temperatureMeasurement = TemperatureMeasurement.newBuilder()
                .setCreated(Timestamp.getDefaultInstance())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setTemperatureValue(37.0)
                .build();
        OpenCDXTemperatureMeasurementModel model = new OpenCDXTemperatureMeasurementModel(temperatureMeasurement);
        Assertions.assertNotNull(model.getProtobufMessage());
    }
}
