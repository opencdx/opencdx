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

import cdx.opencdx.grpc.shipping.*;

/**
 * Service for processing Order Requests
 */
public interface OpenCDXShippingService {

    /**
     * Create an order
     * @param request CreateOrderRequest
     * @return CreateOrderResponse
     */
    CreateOrderResponse createOrder(CreateOrderRequest request);

    /**
     * Get an order
     * @param request GetOrderRequest
     * @return GetOrderResponse
     */
    GetOrderResponse getOrder(GetOrderRequest request);

    /**
     * Update an order
     * @param request UpdateOrderRequest
     * @return UpdateOrderResponse
     */
    UpdateOrderResponse updateOrder(UpdateOrderRequest request);

    /**
     * Cancel an order
     * @param request CancelOrderRequest
     * @return CancelOrderResponse
     */
    CancelOrderResponse cancelOrder(CancelOrderRequest request);

    /**
     * List orders
     * @param request ListOrdersRequest
     * @return ListOrdersResponse
     */
    ListOrdersResponse listOrders(ListOrdersRequest request);
}
