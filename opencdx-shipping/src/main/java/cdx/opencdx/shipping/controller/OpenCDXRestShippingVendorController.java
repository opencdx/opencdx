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
package cdx.opencdx.shipping.controller;

import cdx.opencdx.grpc.shipping.*;
import cdx.opencdx.shipping.service.OpenCDXShippingVendorService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for Shipping Vendor
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestShippingVendorController {
    private final OpenCDXShippingVendorService openCDXShippingVendorService;

    /**
     * Constructor
     * @param openCDXShippingVendorService Service for shipping vendor
     */
    public OpenCDXRestShippingVendorController(OpenCDXShippingVendorService openCDXShippingVendorService) {
        this.openCDXShippingVendorService = openCDXShippingVendorService;
    }

    /**
     * Get shipping vendors
     * @param request Shipping request
     * @return Shipping vendor response
     */
    @PostMapping(value = "/vendors", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShippingVendorResponse> getShippingVendors(@RequestBody ShippingRequest request) {
        return new ResponseEntity<>(this.openCDXShippingVendorService.getShippingVendors(request), HttpStatus.OK);
    }

    /**
     * Ship package
     * @param request Shipping request
     * @return Shipping response
     */
    @PostMapping(value = "/ship", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShippingResponse> shipPackage(@RequestBody Shipping request) {
        return new ResponseEntity<>(this.openCDXShippingVendorService.shipPackage(request), HttpStatus.OK);
    }

    // Delivery Tracking
    /**
     * Post Delivery Tracking Rest API
     * @param request DeliveryTrackingRequest indicating input.
     * @return DeliveryTrackingResponse with the data.
     */
    @PostMapping(value = "/deliveryTracking", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeliveryTrackingResponse> createDeliveryTracking(
            @RequestBody DeliveryTrackingRequest request) {
        DeliveryTrackingResponse response = openCDXShippingVendorService.createDeliveryTracking(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get Delivery Tracking using GET method
     * @param deliveryId The ID of the delivery tracking to retrieve.
     * @return DeliveryTrackingResponse with the data.
     */
    @GetMapping("/deliveryTracking/{deliveryId}")
    public ResponseEntity<DeliveryTrackingResponse> getDeliveryTracking(
            @PathVariable(value = "deliveryId") String deliveryId) {
        DeliveryTrackingRequest request = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(
                        DeliveryTracking.newBuilder().setTrackingId(deliveryId).build())
                .build();
        DeliveryTrackingResponse response = openCDXShippingVendorService.getDeliveryTracking(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
