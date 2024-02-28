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

import cdx.opencdx.grpc.common.ShippingStatus;
import cdx.opencdx.grpc.shipping.AdditionalService;
import cdx.opencdx.grpc.shipping.ServiceLevel;
import cdx.opencdx.shipping.dto.OpenCDXShippingRequest;
import cdx.opencdx.shipping.dto.OpenCDXShippingResponse;
import cdx.opencdx.shipping.model.OpenCDXShippingModel;
import cdx.opencdx.shipping.service.OpenCDXShippingVendor;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UpsShippingVendor implements OpenCDXShippingVendor {

    private static final String VENDOR_ID = "ups";

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
                    .setCode("UPS_GROUND")
                    .setShortDescription("UPS Ground")
                    .setLongDescription("UPS Ground 7-10 days")
                    .build());
            model.setShippingCost(15 + Math.random() * 40);
            list.add(new OpenCDXShippingModel(model));
        }

        if (Math.random() < 0.5) {
            model.setServiceLevel(ServiceLevel.newBuilder()
                    .setCode("UPS_2_DAY")
                    .setShortDescription("UPS 2 Day")
                    .setLongDescription("UPS Ground 2 days")
                    .build());
            model.setShippingCost(25 + Math.random() * 40);
            list.add(new OpenCDXShippingModel(model));
        }

        if (Math.random() < 0.5) {
            model.setServiceLevel(ServiceLevel.newBuilder()
                    .setCode("UPS_NEXT_DAY")
                    .setShortDescription("UPS Next Day")
                    .setLongDescription("UPS next day")
                    .build());
            model.setShippingCost(35 + Math.random() * 40);
            list.add(new OpenCDXShippingModel(model));
        }
        list.forEach(item -> item.setAdditionalServices(new ArrayList<>()));

        if (request.isRequireSignature()) {
            list.forEach(item -> item.getAdditionalServices()
                    .add(AdditionalService.newBuilder()
                            .setCode("UPS_SIGNATURE_REQUIRED")
                            .setShortDescription("UPS Signature Required")
                            .setLongDescription("UPS Signature Required")
                            .build()));
        }

        list.forEach(item -> item.getAdditionalServices()
                .add((AdditionalService.newBuilder()
                        .setCode("UPS_DELIVERY_CONFIRMATION")
                        .setShortDescription("UPS Delivery Confirmation")
                        .setLongDescription("UPS Delivery Confirmation")
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
