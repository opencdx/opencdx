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
package cdx.opencdx.protector.service;

import cdx.opencdx.grpc.neural.protector.AnomalyDetectionDataRequest;
import cdx.opencdx.grpc.neural.protector.AuthorizationControlDataRequest;
import cdx.opencdx.grpc.neural.protector.PrivacyProtectionDataRequest;
import cdx.opencdx.grpc.neural.protector.RealTimeMonitoringDataRequest;
import cdx.opencdx.grpc.neural.protector.SecurityResponse;
import cdx.opencdx.grpc.neural.protector.UserBehaviorAnalysisDataRequest;

/**
 * Interface for the Protector Service.
 * <p>
 * This service provides methods for various security and protection functionalities.
 * Implementations of this interface handle anomaly detection, authorization control,
 * privacy protection, real-time monitoring, and user behavior analysis.
 */
public interface OpenCDXProtectorService {

    /**
     * Process the Anomaly Detection Data.
     *
     * @param request The request containing data for anomaly detection.
     * @return A security response message generated for the anomaly detection request.
     */
    SecurityResponse detectAnomalies(AnomalyDetectionDataRequest request);

    /**
     * Enforce Authorization Control.
     *
     * @param request The request containing data for authorization control.
     * @return A security response message generated for the authorization control request.
     */
    SecurityResponse enforceAuthorizationControl(AuthorizationControlDataRequest request);

    /**
     * Protect Privacy.
     *
     * @param request The request containing data for privacy protection.
     * @return A security response message generated for the privacy protection request.
     */
    SecurityResponse protectPrivacy(PrivacyProtectionDataRequest request);

    /**
     * Monitor Real-Time Activity.
     *
     * @param request The request containing data for real-time monitoring.
     * @return A security response message generated for the real-time monitoring request.
     */
    SecurityResponse monitorRealTimeActivity(RealTimeMonitoringDataRequest request);

    /**
     * Analyze User Behavior.
     *
     * @param request The request containing data for user behavior analysis.
     * @return A security response message generated for the user behavior analysis request.
     */
    SecurityResponse analyzeUserBehavior(UserBehaviorAnalysisDataRequest request);
}
