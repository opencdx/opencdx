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
import cdx.opencdx.protector.service.OpenCDXProtectorService;
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
 * Controller for the /protector API's.
 * <p>
 * This controller handles REST API endpoints related to the Protector service,
 * providing functionalities for anomaly detection, authorization control, privacy protection,
 * real-time monitoring, and user behavior analysis.
 */
@Slf4j
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestProtectorController {

    private final OpenCDXProtectorService protectorService;

    /**
     * Constructor that takes a protectorService.
     *
     * @param protectorService Service for processing requests.
     */
    @Autowired
    public OpenCDXRestProtectorController(OpenCDXProtectorService protectorService) {
        this.protectorService = protectorService;
    }

    /**
     * POST /protector/detectAnomalies API.
     * <p>
     * Endpoint for processing anomaly detection data.
     *
     * @param request AnomalyDetectionData indicating data to be analyzed.
     * @return ResponseEntity with a SecurityResponse containing the analysis.
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
     * POST /protector/authorize API.
     * <p>
     * Endpoint for processing authorization control data.
     *
     * @param request AuthorizationControlDataRequest indicating data to be analyzed.
     * @return ResponseEntity with a SecurityResponse containing the analysis.
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
     * POST /protector/protectPrivacy API.
     * <p>
     * Endpoint for processing privacy protection data.
     *
     * @param request PrivacyProtectionDataRequest indicating data to be analyzed.
     * @return ResponseEntity with a SecurityResponse containing the analysis.
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
     * POST /protector/monitorRealTime API.
     * <p>
     * Endpoint for processing real-time monitoring data.
     *
     * @param request RealTimeMonitoringDataRequest indicating data to be analyzed.
     * @return ResponseEntity with a SecurityResponse containing the analysis.
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
     * POST /protector/analyzeUserBehavior API.
     * <p>
     * Endpoint for processing user behavior analysis data.
     *
     * @param request UserBehaviorAnalysisDataRequest indicating data to be analyzed.
     * @return ResponseEntity with a SecurityResponse containing the analysis.
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
