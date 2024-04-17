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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.service.iam.*;

/**
 * Interface for communicating with the Protector microservice.
 */
public interface OpenCDXProtectorClient {
    /**
     * Method to gRPC Call Neural Protector Service detectAnomalies() api.
     * @param request Anomaly Detection Data request to pass.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Security Response.
     */
    SecurityResponse detectAnomalies(AnomalyDetectionDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Neural Protector Service enforceAuthorizationControl() api.
     * @param request Authorization Control Data request to pass.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Security Response.
     */
    SecurityResponse enforceAuthorizationControl(
            AuthorizationControlDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Neural Protector Service protectPrivacy() api.
     * @param request Privacy Protection Data request to pass.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Security Response.
     */
    SecurityResponse protectPrivacy(PrivacyProtectionDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Neural Protector Service monitorRealTimeActivity() api.
     * @param request Real Time Monitoring Data request to pass.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Security Response.
     */
    SecurityResponse monitorRealTimeActivity(
            RealTimeMonitoringDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call Neural Protector Service analyzeUserBehavior() api.
     * @param request User Behavior Analysis Data request to pass.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Security Response.
     */
    SecurityResponse analyzeUserBehavior(
            UserBehaviorAnalysisDataRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
