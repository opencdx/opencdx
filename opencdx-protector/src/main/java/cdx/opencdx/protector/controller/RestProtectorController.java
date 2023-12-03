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
package cdx.opencdx.protector.controller;

import cdx.opencdx.grpc.neural.protector.AnomalyDetectionDataRequest;
import cdx.opencdx.grpc.neural.protector.AuthorizationControlDataRequest;
import cdx.opencdx.grpc.neural.protector.PrivacyProtectionDataRequest;
import cdx.opencdx.grpc.neural.protector.RealTimeMonitoringDataRequest;
import cdx.opencdx.grpc.neural.protector.SecurityResponse;
import cdx.opencdx.grpc.neural.protector.UserBehaviorAnalysisDataRequest;
import cdx.opencdx.protector.service.ProtectorService;
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
public class RestProtectorController {

    private final ProtectorService protectorService;

    /**
     * Constructor that takes a protectorService
     * @param protectorService service for processing requests.
     */
    @Autowired
    public RestProtectorController(ProtectorService protectorService) {
        this.protectorService = protectorService;
    }

    /**
     * Post protector Rest API
     * @param request AnomalyDetectionData indicating data to be analyzed.
     * @return SecurityResponse with the analysis.
     */
    @PostMapping("/detectAnomalies")
    public ResponseEntity<SecurityResponse> postDetectAnomalies(@RequestBody AnomalyDetectionDataRequest request) {
        return new ResponseEntity<>(
                SecurityResponse.newBuilder()
                        .setResponse(protectorService.detectAnomalies(request).getResponse())
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Post protector Rest API
     * @param request AuthorizationControlDataRequest indicating data to be analyzed.
     * @return SecurityResponse with the analysis.
     */
    @PostMapping("/authorize")
    public ResponseEntity<SecurityResponse> postAuthorize(@RequestBody AuthorizationControlDataRequest request) {
        return new ResponseEntity<>(
                SecurityResponse.newBuilder()
                        .setResponse(protectorService
                                .enforceAuthorizationControl(request)
                                .getResponse())
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Post protector Rest API
     * @param request PrivacyProtectionDataRequest indicating data to be analyzed.
     * @return SecurityResponse with the analysis.
     */
    @PostMapping("/protectPrivacy")
    public ResponseEntity<SecurityResponse> postProtectPrivacy(@RequestBody PrivacyProtectionDataRequest request) {
        return new ResponseEntity<>(
                SecurityResponse.newBuilder()
                        .setResponse(protectorService.protectPrivacy(request).getResponse())
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Post protector Rest API
     * @param request RealTimeMonitoringDataRequest indicating data to be analyzed.
     * @return SecurityResponse with the analysis.
     */
    @PostMapping("/monitorRealTime")
    public ResponseEntity<SecurityResponse> postMonitorRealTime(@RequestBody RealTimeMonitoringDataRequest request) {
        return new ResponseEntity<>(
                SecurityResponse.newBuilder()
                        .setResponse(protectorService
                                .monitorRealTimeActivity(request)
                                .getResponse())
                        .build(),
                HttpStatus.OK);
    }

    /**
     * Post protector Rest API
     * @param request UserBehaviorAnalysisDataRequest indicating data to be analyzed.
     * @return SecurityResponse with the analysis.
     */
    @PostMapping("/analyzeUserBehavior")
    public ResponseEntity<SecurityResponse> postAnalyzeUserBehavior(
            @RequestBody UserBehaviorAnalysisDataRequest request) {
        return new ResponseEntity<>(
                SecurityResponse.newBuilder()
                        .setResponse(
                                protectorService.analyzeUserBehavior(request).getResponse())
                        .build(),
                HttpStatus.OK);
    }
}
