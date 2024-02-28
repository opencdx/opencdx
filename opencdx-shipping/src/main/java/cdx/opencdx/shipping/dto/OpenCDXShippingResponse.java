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
package cdx.opencdx.shipping.dto;

import cdx.opencdx.grpc.common.ShippingStatus;
import cdx.opencdx.grpc.shipping.ShippingResponse;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The OpenCDXShippingResponse class is a DTO that represents the response of the shipping service.
 */
@Slf4j
@Data
@Builder
public class OpenCDXShippingResponse {
    private final String trackingNumber;
    private final ShippingStatus status;
    private final Double totalCost;
    private final Instant estimatedDelivery;


    /**
     * Converts the OpenCDXShippingResponse to a ShippingResponse protobuf.
     *
     * @return ShippingResponse
     */
    public ShippingResponse toProtobuf() {
        ShippingResponse.Builder builder = ShippingResponse.newBuilder();

        if (this.trackingNumber != null) {
            builder.setTrackingNumber(this.trackingNumber);
        }

        if (this.status != null) {
            builder.setStatus(this.status);
        }

        if (this.totalCost != null) {
            builder.setTotalCost(this.totalCost);
        }

        if (this.estimatedDelivery != null) {
            builder.setEstimatedDeliveryDate(Timestamp.newBuilder()
                    .setSeconds(this.estimatedDelivery.getEpochSecond())
                    .setNanos(this.estimatedDelivery.getNano())
                    .build());
        }

        return builder.build();
    }
}
