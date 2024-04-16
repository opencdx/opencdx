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
package cdx.opencdx.logistics.service.impl;

import cdx.opencdx.grpc.data.ServiceLevel;
import cdx.opencdx.grpc.types.ShippingStatus;
import cdx.opencdx.logistics.dto.OpenCDXShippingRequest;
import cdx.opencdx.logistics.dto.OpenCDXShippingResponse;
import cdx.opencdx.logistics.model.OpenCDXShippingModel;
import cdx.opencdx.logistics.service.OpenCDXShippingVendor;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * DoorDash shipping vendor simulation
 */
public class DoorDashShippingVendor implements OpenCDXShippingVendor {
    private static final String VENDOR_ID = "doordash";

    /**
     * Default constructor
     */
    public DoorDashShippingVendor() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Override
    public String getVendorId() {
        return VENDOR_ID;
    }

    @Override
    public List<OpenCDXShippingModel> getShippingVendors(OpenCDXShippingRequest request) {

        if (Math.random() < 0.25) {
            return Collections.emptyList();
        }

        OpenCDXShippingModel model = new OpenCDXShippingModel(request);
        List<OpenCDXShippingModel> list = new ArrayList<>();

        model.setShippingVendorId(VENDOR_ID);
        model.setShippingCost(15.0);

        if (Math.random() < 0.5) {
            model.setServiceLevel(ServiceLevel.newBuilder()
                    .setCode("DOORDASH")
                    .setShortDescription("DoorDash Delivery")
                    .setLongDescription("Local DoorDash Delivery")
                    .build());
            model.setShippingCost(20 + Math.random() * 20);
            list.add(model);
        }

        list.forEach(item -> item.setAdditionalServices(new ArrayList<>()));

        if (request.isRequireSignature()) {
            return Collections.emptyList();
        }

        return list;
    }

    @Override
    public OpenCDXShippingResponse shipPackage(OpenCDXShippingModel request) {
        OpenCDXShippingResponse.OpenCDXShippingResponseBuilder builder = OpenCDXShippingResponse.builder();

        builder.trackingNumber(UUID.randomUUID().toString());
        builder.status(ShippingStatus.PENDING_PICKUP);
        builder.totalCost(request.getShippingCost());

        builder.estimatedDelivery(Instant.now().plus((long) (Math.random() * 2 + 1), ChronoUnit.DAYS));

        return builder.build();
    }
}
