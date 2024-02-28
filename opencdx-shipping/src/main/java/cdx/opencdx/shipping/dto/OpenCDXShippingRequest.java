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

import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.shipping.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The OpenCDXShippingRequest class is a DTO that is used to convert the ShippingRequest to a
 * OpenCDXShippingRequest.
 */

@Slf4j
@Data
public class OpenCDXShippingRequest {

    private final Address senderAddress;
    private final Address recipientAddress;
    private final Order packageDetails;
    private final boolean requireSignature;
    private final Double declaredValue;

    /**
     * Constructor for OpenCDXShippingRequest
     *
     * @param shipping the shipping request
     */
    public OpenCDXShippingRequest(ShippingRequest shipping) {
        this.senderAddress = shipping.getSenderAddress();
        this.recipientAddress = shipping.getRecipientAddress();
        this.packageDetails = shipping.getPackageDetails();
        this.requireSignature = shipping.getRequireSignature();
        this.declaredValue = shipping.getDeclaredValue();
    }
}
