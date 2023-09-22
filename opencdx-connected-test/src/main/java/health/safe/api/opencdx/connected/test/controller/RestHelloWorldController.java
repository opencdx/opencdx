/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package health.safe.api.opencdx.connected.test.controller;

import health.safe.api.opencdx.connected.test.service.HelloWorldService;
import health.safe.api.opencdx.grpc.helloworld.HelloReply;
import health.safe.api.opencdx.grpc.helloworld.HelloRequest;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the /greeting api's
 */
@Slf4j
@RestController
@RequestMapping(
        value = "/greeting",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class RestHelloWorldController {

    private final HelloWorldService helloWorldService;

    /**
     * Constructor that takes a HelloWorldService
     * @param helloWorldService service for processing requests.
     */
    @Autowired
    public RestHelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    /**
     * Post Hello Rest API
     * @param request HelloRequest indicating who to say hello to.
     * @return HelloReply with the hello message.
     */
    @PostMapping(value = "/hello")
    public ResponseEntity<HelloReply> sayHello(@RequestBody HelloRequest request) {

        return new ResponseEntity<>(
                HelloReply.newBuilder()
                        .setMessage(helloWorldService.sayHello(request))
                        .build(),
                HttpStatus.OK);
    }
}
