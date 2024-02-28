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
package cdx.opencdx.shipping.service;

import cdx.opencdx.grpc.shipping.Shipping;
import cdx.opencdx.grpc.shipping.ShippingRequest;
import cdx.opencdx.grpc.shipping.ShippingResponse;
import cdx.opencdx.grpc.shipping.ShippingVendorResponse;

/**
 * Service for processing Shipping Vendor Requests
 */
public interface OpenCDXShippingVendorService {

    /**
     * Get shipping vendors
     * @param request Shipping request
     * @return Shipping vendor response
     */
    ShippingVendorResponse getShippingVendors(ShippingRequest request);

    /**
     * Ship a package
     * @param request Shipping request
     * @return Shipping response
     */
    ShippingResponse shipPackage(Shipping request);
}
