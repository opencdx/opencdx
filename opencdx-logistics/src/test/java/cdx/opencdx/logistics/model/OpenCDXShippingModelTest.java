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
package cdx.opencdx.logistics.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class OpenCDXShippingModelTest {

    @Test
    void toProtobuf() {
        OpenCDXShippingModel openCDXShippingModel = new OpenCDXShippingModel();
        Assertions.assertDoesNotThrow(() -> openCDXShippingModel.toProtobuf());
    }

    @Test
    void toProtobufMsg() {
        OpenCDXShippingModel openCDXShippingModel = new OpenCDXShippingModel(Shipping.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setPaymentDetails(PaymentDetails.newBuilder().getDefaultInstanceForType())
                .build());
        Assertions.assertDoesNotThrow(() -> openCDXShippingModel.toProtobuf());
    }

    @Test
    void testToString() {
        OpenCDXShippingModel openCDXShippingModel = new OpenCDXShippingModel(OpenCDXShippingModel.builder()
                .id(OpenCDXIdentifier.get())
                .senderAddress(Address.newBuilder().getDefaultInstanceForType())
                .recipientAddress(Address.newBuilder().getDefaultInstanceForType())
                .packageDetails(Order.newBuilder().build())
                .serviceLevel(ServiceLevel.newBuilder().build())
                .additionalServices(List.of(AdditionalService.newBuilder().build()))
                .requireSignature(true)
                .declaredValue(10.0)
                .shippingCost(100.0)
                .shippingVendorId(OpenCDXIdentifier.get().toHexString())
                .paymentDetails(PaymentDetails.newBuilder().getDefaultInstanceForType())
                .build());
        Assertions.assertDoesNotThrow(() -> openCDXShippingModel.toString());
    }
}
