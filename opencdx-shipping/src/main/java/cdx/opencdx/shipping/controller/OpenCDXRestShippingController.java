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
import cdx.opencdx.shipping.service.OpenCDXShippingService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /greeting api's
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestShippingController {

    private final OpenCDXShippingService openCDXShippingService;

    /**
     * Constructor that takes a HelloWorldService
     * @param openCDXShippingService service for processing requests.
     */
    @Autowired
    public OpenCDXRestShippingController(OpenCDXShippingService openCDXShippingService) {
        this.openCDXShippingService = openCDXShippingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable("id") String id) {
        return new ResponseEntity<>(
                this.openCDXShippingService.getOrder(
                        GetOrderRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        return new ResponseEntity<>(this.openCDXShippingService.createOrder(request), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateOrderResponse> updateOrder(@RequestBody UpdateOrderRequest request) {
        return new ResponseEntity<>(this.openCDXShippingService.updateOrder(request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXShippingService.cancelOrder(
                        CancelOrderRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListOrdersResponse> listOrders(@RequestBody ListOrdersRequest request) {
        return new ResponseEntity<>(this.openCDXShippingService.listOrders(request), HttpStatus.OK);
    }
}
