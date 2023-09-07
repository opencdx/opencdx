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
package health.safe.api.opencdx.tinkar.controller;

import health.safe.api.opencdx.grpc.tinkar.TinkarReply;
import health.safe.api.opencdx.grpc.tinkar.TinkarRequest;
import health.safe.api.opencdx.tinkar.service.TinkarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Tinkar Rest API.
 */
@Slf4j
@RestController
@RequestMapping("/greeting")
public class RestTinkarController {

    private final TinkarService tinkarService;

    /**
     * Constructor for RestTinkarContorlller
     * @param tinkarService Service for processing API
     */
    @Autowired
    public RestTinkarController(TinkarService tinkarService) {
        this.tinkarService = tinkarService;
    }

    /**
     * Rest API to Process Hello API
     * @param request TinkarRequest
     * @return Hello Response.
     */
    @PostMapping(value = "/hello")
    public ResponseEntity<TinkarReply> hello(@RequestBody TinkarRequest request) {
        return new ResponseEntity<>(
                TinkarReply.newBuilder()
                        .setMessage(tinkarService.sayTinkar(request))
                        .build(),
                HttpStatus.OK);
    }
}
