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
package cdx.opencdx.protector.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.neural.protector.*;
import cdx.opencdx.protector.service.OpenCDXProtectorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing protector Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXProtectorServiceImpl implements OpenCDXProtectorService {

    // Constants for error handling
    private static final String CONVERSION_ERROR = "Failed to convert Protector Request";
    private static final String OBJECT = "OBJECT";

    // Dependencies injected via constructor
    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor for OpenCDXProtectorServiceImpl.
     *
     * @param openCDXAuditService Audit Service for recording
     * @param objectMapper        Object mapper for JSON processing
     * @param openCDXCurrentUser  Current user system
     */
    @Autowired
    public OpenCDXProtectorServiceImpl(
            OpenCDXAuditService openCDXAuditService, ObjectMapper objectMapper, OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXCurrentUser = openCDXCurrentUser;
    }

    @Override
    public SecurityResponse detectAnomalies(AnomalyDetectionDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Anomaly Detection record",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    request.getAnomalyDetectionData().getEncounterId(),
                    "ANOMALY_DETECTION: 145",
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        // Placeholder implementation for detecting anomalies
        // Actual implementation should analyze the request and provide a SecurityResponse
        return SecurityResponse.newBuilder()
                .setEncounterId(request.getAnomalyDetectionData().getEncounterId())
                .setResponse("SecurityResponse [detectAnomalies]")
                .build();
    }

    @Override
    public SecurityResponse enforceAuthorizationControl(AuthorizationControlDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Authorization Control record",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    request.getAuthorizationControlData().getEncounterId(),
                    "AUTHORIZATION_CONTROL: 145",
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        // Placeholder implementation for enforcing authorization control
        // Actual implementation should analyze the request and provide a SecurityResponse
        return SecurityResponse.newBuilder()
                .setEncounterId(request.getAuthorizationControlData().getEncounterId())
                .setResponse("SecurityResponse [enforceAuthorizationControl]")
                .build();
    }

    @Override
    public SecurityResponse protectPrivacy(PrivacyProtectionDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Privacy Protection record",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    request.getPrivacyProtectionData().getEncounterId(),
                    "PRIVACY_PROTECTION: 145",
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        // Placeholder implementation for privacy protection
        // Actual implementation should analyze the request and provide a SecurityResponse
        return SecurityResponse.newBuilder()
                .setEncounterId(request.getPrivacyProtectionData().getEncounterId())
                .setResponse("SecurityResponse [protectPrivacy]")
                .build();
    }

    @Override
    public SecurityResponse monitorRealTimeActivity(RealTimeMonitoringDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Real-time Monitoring record",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    request.getRealTimeMonitoringData().getEncounterId(),
                    "REAL_TIME_MONITORING: 145",
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        // Placeholder implementation for monitoring real-time activity
        // Actual implementation should analyze the request and provide a SecurityResponse
        return SecurityResponse.newBuilder()
                .setEncounterId(request.getRealTimeMonitoringData().getEncounterId())
                .setResponse("SecurityResponse [monitorRealTimeActivity]")
                .build();
    }

    @Override
    public SecurityResponse analyzeUserBehavior(UserBehaviorAnalysisDataRequest request) {
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        try {
            this.openCDXAuditService.phiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "User Behavior Analysis record",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    request.getUserBehaviorAnalysisData().getEncounterId(),
                    "USER_BEHAVIOR_ANALYSIS: 145",
                    this.objectMapper.writeValueAsString(request.toString()));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(this.getClass().getName(), 2, CONVERSION_ERROR, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        // Placeholder implementation for analyzing user behavior
        // Actual implementation should analyze the request and provide a SecurityResponse
        return SecurityResponse.newBuilder()
                .setEncounterId(request.getUserBehaviorAnalysisData().getEncounterId())
                .setResponse("SecurityResponse [analyzeUserBehavior]")
                .build();
    }
}
