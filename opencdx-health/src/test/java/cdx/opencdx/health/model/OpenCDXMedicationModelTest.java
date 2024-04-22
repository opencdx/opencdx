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

import static org.mockito.Mockito.when;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.Medication;
import cdx.opencdx.health.dto.openfda.Product;
import cdx.opencdx.health.dto.openfda.Result;
import com.google.protobuf.Timestamp;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class OpenCDXMedicationModelTest {
    @Mock
    Result result;

    @Mock
    Product product;

    @Test
    void test() {
        this.product = Mockito.mock(Product.class);
        this.result = Mockito.mock(Result.class);
        Mockito.when(this.product.getRoute()).thenReturn("notKnow");
        Mockito.when(this.product.getDosage_form()).thenReturn("dosage");
        Mockito.when(this.product.getMarketing_status()).thenReturn("Prescription");
        Mockito.when(this.result.getDosage_and_administration()).thenReturn(List.of("dosage"));
        when(this.product.getRoute()).thenReturn("notKnow");
        OpenCDXMedicationModel medicationModel = new OpenCDXMedicationModel("medname", this.result, this.product, true);
        Assertions.assertNotNull(
                medicationModel, medicationModel.getProtobufMessage().toString());
    }

    @Test
    void test2() {
        this.product = Mockito.mock(Product.class);
        this.result = Mockito.mock(Result.class);
        Mockito.when(this.product.getRoute()).thenReturn("notKnow");
        Mockito.when(this.product.getDosage_form()).thenReturn("dosage");
        when(this.product.getRoute()).thenReturn("notKnow");
        OpenCDXMedicationModel medicationModel = new OpenCDXMedicationModel(Medication.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .setOtherRouteOfAdministration("notKnow")
                .setEndDate(Timestamp.getDefaultInstance())
                .setPharmacyId(OpenCDXIdentifier.get().toHexString())
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build());
        Assertions.assertNotNull(
                medicationModel, medicationModel.getProtobufMessage().toString());
    }
}
