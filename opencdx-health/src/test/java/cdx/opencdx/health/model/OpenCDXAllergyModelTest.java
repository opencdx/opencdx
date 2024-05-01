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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.KnownAllergy;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Test;

class OpenCDXAllergyModelTest {

    @Test
    void getProtobufMessage() {
        KnownAllergy knownAllergy = KnownAllergy.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                .setAllergen("allergen")
                .setReaction("reaction")
                .setIsSevere(true)
                .setOnsetDate(Timestamp.getDefaultInstance())
                .setLastOccurrence(Timestamp.getDefaultInstance())
                .setNotes("notes")
                .build();
        OpenCDXAllergyModel model = new OpenCDXAllergyModel(knownAllergy);
        assertNotNull(model.getProtobufMessage());
    }

    @Test
    void getProtobufMessageElse() {
        KnownAllergy knownAllergy = KnownAllergy.newBuilder()
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setCreated(Timestamp.getDefaultInstance())
                .setCreator("creator")
                .setModified(Timestamp.getDefaultInstance())
                .setModifier("modifier")
                .build();
        OpenCDXAllergyModel model = new OpenCDXAllergyModel(knownAllergy);
        assertNotNull(model.getProtobufMessage());
    }
}
