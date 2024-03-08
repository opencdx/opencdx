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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.shipping.*;

/**
 * Interface for communicating with the Shipping Service.
 */
public interface OpenCDXShippingServiceClient {
    /**
     * Method to gRPC Call Shipping Service getShippingVendors() api.
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ShippingVendorResponse getShippingVendors(ShippingRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Shipping Service shipPackage() api.
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    ShippingResponse shipPackage(Shipping request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service createDeliveryTracking() api.
     * @param request Delivery Tracking Request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Delivery Tracking Response.
     */
    DeliveryTrackingResponse createDeliveryTracking(
            DeliveryTrackingRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Routine Service getDeliveryTracking() api.
     * @param request Delivery Tracking request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Delivery Tracking Response.
     */
    DeliveryTrackingResponse getDeliveryTracking(
            DeliveryTrackingRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
