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
package cdx.opencdx.shipping.service.impl;

import cdx.opencdx.grpc.shipping.Shipping;
import cdx.opencdx.grpc.shipping.ShippingRequest;
import cdx.opencdx.grpc.shipping.ShippingResponse;
import cdx.opencdx.grpc.shipping.ShippingVendorResponse;
import cdx.opencdx.shipping.dto.OpenCDXShippingRequest;
import cdx.opencdx.shipping.dto.OpenCDXShippingResponse;
import cdx.opencdx.shipping.model.OpenCDXShippingModel;
import cdx.opencdx.shipping.service.OpenCDXShippingVendor;
import cdx.opencdx.shipping.service.OpenCDXShippingVendorService;
import io.micrometer.observation.annotation.Observed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * OpenCDX shipping vendor service implementation
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXShippingVendorServiceImpl implements OpenCDXShippingVendorService {

    private Map<String, OpenCDXShippingVendor> vendors;

    /**
     * Default constructor
     */
    public OpenCDXShippingVendorServiceImpl() {
        this.vendors = new HashMap<>();

        OpenCDXShippingVendor vendor = new UpsShippingVendor();
        this.vendors.put(vendor.getVendorId(), vendor);
        vendor = new UspsShippingVendor();
        this.vendors.put(vendor.getVendorId(), vendor);
        vendor = new FedexShippingVendor();
        this.vendors.put(vendor.getVendorId(), vendor);
        vendor = new DoorDashShippingVendor();
        this.vendors.put(vendor.getVendorId(), vendor);

        this.vendors.keySet().forEach(key -> log.info("Vendor: {}", key));
    }

    @Override
    public ShippingVendorResponse getShippingVendors(ShippingRequest request) {
        log.info("Getting Shipping Vendor Options");
        OpenCDXShippingRequest openCDXShippingRequest = new OpenCDXShippingRequest(request);
        List<OpenCDXShippingModel> models = new ArrayList<>();

        for (OpenCDXShippingVendor vendor : vendors.values()) {
            List<OpenCDXShippingModel> shippingVendors = vendor.getShippingVendors(openCDXShippingRequest);
            if (shippingVendors != null && !shippingVendors.isEmpty()) {
                models.addAll(shippingVendors);
            }
        }

        if (log.isInfoEnabled()) {
            models.forEach(item -> log.info("Shipping Option: {}", item.toString()));
        }

        return ShippingVendorResponse.newBuilder()
                .addAllOptions(
                        models.stream().map(OpenCDXShippingModel::toProtobuf).toList())
                .build();
    }

    @Override
    public ShippingResponse shipPackage(Shipping request) {
        log.info("Shipping Package");

        OpenCDXShippingResponse openCDXShippingResponse =
                this.vendors.get(request.getShippingVendorId()).shipPackage(new OpenCDXShippingModel(request));

        log.info("Shipping Response: {}", openCDXShippingResponse.toString());

        return openCDXShippingResponse.toProtobuf();
    }
}
