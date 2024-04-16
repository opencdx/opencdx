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
package cdx.opencdx.logistics.service;


import cdx.opencdx.grpc.data.Shipping;
import cdx.opencdx.grpc.service.logistics.*;

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

    /**
     * Creates a delivery tracking record based on the provided DeliveryTrackingRequest.
     *
     * @param request The DeliveryTrackingRequest for creating a delivery tracking record.
     * @return A DeliveryTrackingResponse indicating the status of the delivery tracking creation.
     */
    DeliveryTrackingResponse createDeliveryTracking(DeliveryTrackingRequest request);

    /**
     * Retrieves information about a delivery tracking record based on the provided DeliveryTrackingRequest.
     *
     * @param request The DeliveryTrackingRequest for retrieving delivery tracking information.
     * @return A DeliveryTrackingResponse containing information about the requested delivery tracking record.
     */
    DeliveryTrackingResponse getDeliveryTracking(DeliveryTrackingRequest request);
}
