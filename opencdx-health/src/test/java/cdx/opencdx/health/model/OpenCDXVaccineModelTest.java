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
import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.health.Vaccine;
import cdx.opencdx.grpc.health.medication.Medication;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXVaccineModelTest {
    @Test
    void getProtobufMessage() {
        Vaccine vaccine = Vaccine.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                .setAdministrationDate(Timestamp.getDefaultInstance())
                .setFips("fips")
                .setLocation(Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .setHealthDistrict("district")
                .setFacilityType("facilityType")
                .setVaccine(Medication.newBuilder().getDefaultInstanceForType())
                .setDoseNumber(2)
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();
        OpenCDXVaccineModel model = new OpenCDXVaccineModel(vaccine);
        Assertions.assertNotNull(model.getProtobufMessage());
    }
}
