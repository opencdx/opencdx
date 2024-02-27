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

import cdx.opencdx.grpc.shipping.*;
import cdx.opencdx.shipping.service.OpenCDXShippingService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Service
@Observed(name = "opencdx")
public class OpenCDXShippingServiceImpl implements OpenCDXShippingService {

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        return null;
    }

    @Override
    public GetOrderResponse getOrder(GetOrderRequest request) {
        return null;
    }

    @Override
    public UpdateOrderResponse updateOrder(UpdateOrderRequest request) {
        return null;
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest request) {
        return null;
    }

    @Override
    public ListOrdersResponse listOrders(ListOrdersRequest request) {
        return null;
    }
}
