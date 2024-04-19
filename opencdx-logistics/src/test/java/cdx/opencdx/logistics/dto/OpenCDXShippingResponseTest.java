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
package cdx.opencdx.logistics.dto;

import cdx.opencdx.grpc.service.logistics.ShippingResponse;
import cdx.opencdx.grpc.types.ShippingStatus;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXShippingResponseTest {

    @Test
    void ShippingResponseTest() {
        ShippingResponse shippingResponse = ShippingResponse.newBuilder()
                .setTrackingNumber("123")
                .setStatus(ShippingStatus.IN_TRANSIT)
                .setTotalCost(20.0)
                .setEstimatedDeliveryDate(Timestamp.getDefaultInstance())
                .build();
        OpenCDXShippingResponse response = new OpenCDXShippingResponse(shippingResponse);
        Assertions.assertNotNull(response);
    }

    @Test
    void ShippingResponseTestElse() {
        ShippingResponse shippingResponse = ShippingResponse.newBuilder()
                .setTrackingNumber("123")
                .setStatus(ShippingStatus.IN_TRANSIT)
                .setTotalCost(20.0)
                .build();
        OpenCDXShippingResponse response = new OpenCDXShippingResponse(shippingResponse);
        Assertions.assertNotNull(response);
    }

    @Test
    void ShippingResponseTestNotSet() {
        ShippingResponse shippingResponse = ShippingResponse.newBuilder().build();
        OpenCDXShippingResponse response = new OpenCDXShippingResponse(shippingResponse);
        Assertions.assertNotNull(response.toProtobuf());
    }
}
