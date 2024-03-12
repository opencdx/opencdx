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

import cdx.opencdx.grpc.common.ShippingStatus;
import cdx.opencdx.grpc.shipping.AdditionalService;
import cdx.opencdx.grpc.shipping.ServiceLevel;
import cdx.opencdx.logistics.dto.OpenCDXShippingRequest;
import cdx.opencdx.logistics.dto.OpenCDXShippingResponse;
import cdx.opencdx.logistics.model.OpenCDXShippingModel;
import cdx.opencdx.logistics.service.OpenCDXShippingVendor;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Fedex shipping vendor simulation
 */
public class FedexShippingVendor implements OpenCDXShippingVendor {

    private static final String VENDOR_ID = "fedex";

    /**
     * Default constructor
     */
    public FedexShippingVendor() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Override
    public String getVendorId() {
        return VENDOR_ID;
    }

    @Override
    public List<OpenCDXShippingModel> getShippingVendors(OpenCDXShippingRequest request) {

        OpenCDXShippingModel model = new OpenCDXShippingModel(request);
        List<OpenCDXShippingModel> list = new ArrayList<>();

        model.setShippingVendorId(VENDOR_ID);
        model.setShippingCost(10.0);

        model.setServiceLevel(ServiceLevel.newBuilder()
                .setCode("FEDEX_GROUND")
                .setShortDescription("Fedex Ground")
                .setLongDescription("Fedex Ground 7-10 days")
                .build());
        model.setShippingCost(10 + Math.random() * 40);
        list.add(new OpenCDXShippingModel(model));

        if (Math.random() < 0.5) {
            model.setServiceLevel(ServiceLevel.newBuilder()
                    .setCode("Fedex_2_DAY")
                    .setShortDescription("Fedex 2 Day")
                    .setLongDescription("Fedex Ground 2 days")
                    .build());
            model.setShippingCost(20 + Math.random() * 40);
            list.add(new OpenCDXShippingModel(model));
        }

        if (Math.random() < 0.5) {
            model.setServiceLevel(ServiceLevel.newBuilder()
                    .setCode("FEDEX_NEXT_DAY")
                    .setShortDescription("Fedex Next Day")
                    .setLongDescription("Fedex next day")
                    .build());
            model.setShippingCost(30 + Math.random() * 40);
            list.add(new OpenCDXShippingModel(model));
        }
        list.forEach(item -> item.setAdditionalServices(new ArrayList<>()));

        if (request.isRequireSignature()) {
            list.forEach(item -> item.getAdditionalServices()
                    .add(AdditionalService.newBuilder()
                            .setCode("FEDEX_SIGNATURE_REQUIRED")
                            .setShortDescription("Fedex Signature Required")
                            .setLongDescription("Fedex Signature Required")
                            .build()));
        }

        list.forEach(item -> item.getAdditionalServices()
                .add((AdditionalService.newBuilder()
                        .setCode("FEDEX_DELIVERY_CONFIRMATION")
                        .setShortDescription("Fedex Delivery Confirmation")
                        .setLongDescription("Fedex Delivery Confirmation")
                        .build())));

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
