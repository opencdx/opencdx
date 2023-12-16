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
package cdx.opencdx.classification.controller;

import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
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
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestClassificationController {

    /**
     * Constructor that takes a ClassificationService
     * @param classificationService service for processing requests.
     */
    @Autowired
    public OpenCDXRestClassificationController(OpenCDXClassificationService classificationService) {}

    /**
     * Post Classification Rest API
     * @param request ClassificationRequest indicating classification realted data
     * @return ClassificationResponse with the message.
     */
    @PostMapping(value = "/classify")
    public ResponseEntity<ClassificationResponse> submitClassification(@RequestBody ClassificationRequest request) {

        return new ResponseEntity<>(
                ClassificationResponse.newBuilder()
                        .setMessage("Executed classify operation.")
                        .build(),
                HttpStatus.OK);
    }
}
