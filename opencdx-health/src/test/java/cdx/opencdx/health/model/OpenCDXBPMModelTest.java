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
import cdx.opencdx.grpc.data.BPM;
import cdx.opencdx.grpc.types.ArmUsed;
import cdx.opencdx.grpc.types.BPMUnits;
import cdx.opencdx.grpc.types.CuffSize;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXBPMModelTest {

    @Test
    void getProtobufMessage() {
        BPM bpm = BPM.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setCreated(Timestamp.getDefaultInstance())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                .setCuffSize(CuffSize.CUFF_SIZE_UNSPECIFIED)
                .setArmUsed(ArmUsed.ARM_USED_UNSPECIFIED)
                .setSystolic(80)
                .setDiastolic(120)
                .setUnit(BPMUnits.BARS)
                .setSittingPosition5Minutes(true)
                .setUrinated30MinutesPrior(false)
                .setTimeOfMeasurement(Timestamp.getDefaultInstance())
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();
        OpenCDXBPMModel model = new OpenCDXBPMModel(bpm);
        Assertions.assertNotNull(model.getProtobufMessage());
    }

    @Test
    void getProtobufMessageElse() {
        BPM bpm = BPM.newBuilder()
                .setCreated(Timestamp.getDefaultInstance())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                .build();
        OpenCDXBPMModel model = new OpenCDXBPMModel(bpm);
        Assertions.assertNotNull(model.getProtobufMessage());
    }
}
