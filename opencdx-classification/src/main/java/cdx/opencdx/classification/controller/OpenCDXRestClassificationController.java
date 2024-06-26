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
package cdx.opencdx.classification.controller;

import cdx.opencdx.classification.service.OpenCDXClassificationService;
import cdx.opencdx.grpc.service.classification.ClassificationRequest;
import cdx.opencdx.grpc.service.classification.ClassificationResponse;
import cdx.opencdx.grpc.service.classification.RuleSetsRequest;
import cdx.opencdx.grpc.service.classification.RuleSetsResponse;
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
 * Controller for the /classify api's
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestClassificationController {

    private final OpenCDXClassificationService classificationService;

    /**
     * Constructor that takes a ClassificationService
     * @param classificationService service for processing requests.
     */
    @Autowired
    public OpenCDXRestClassificationController(OpenCDXClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    /**
     * Get RuleSets Rest API
     * @param request ClientRulesRequest indicating organization and workspace
     * @return RuleSetsResponse with the message.
     */
    @PostMapping(value = "/ruleset/list")
    public ResponseEntity<RuleSetsResponse> getRuleSets(@RequestBody RuleSetsRequest request) {
        RuleSetsResponse ruleSets = classificationService.getRuleSets(request);
        return new ResponseEntity<>(ruleSets, HttpStatus.OK);
    }

    /**
     * Post Classification Rest API
     * @param request ClassificationRequest indicating classification realted data
     * @return ClassificationResponse with the message.
     */
    @PostMapping(value = "/classify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClassificationResponse> submitClassification(@RequestBody ClassificationRequest request) {
        log.trace("Received classify request");
        return new ResponseEntity<>(this.classificationService.classify(request), HttpStatus.OK);
    }
}
