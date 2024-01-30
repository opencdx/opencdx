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
package cdx.opencdx.client.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXProtectorClient;
import cdx.opencdx.grpc.neural.protector.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenCDXProtectorClientImplTest {

    @Mock
    NeuralProtectorServiceGrpc.NeuralProtectorServiceBlockingStub neuralProtectorServiceBlockingStub;

    OpenCDXProtectorClient openCDXProtectorClient;

    @BeforeEach
    void setUp() {
        this.neuralProtectorServiceBlockingStub =
                Mockito.mock(NeuralProtectorServiceGrpc.NeuralProtectorServiceBlockingStub.class);
        this.openCDXProtectorClient = new OpenCDXProtectorClientImpl(this.neuralProtectorServiceBlockingStub);
        Mockito.when(neuralProtectorServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.neuralProtectorServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.neuralProtectorServiceBlockingStub);
    }

    @Test
    void detectAnomalies() {
        Mockito.when(this.neuralProtectorServiceBlockingStub.detectAnomalies(
                        Mockito.any(AnomalyDetectionDataRequest.class)))
                .thenReturn(SecurityResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SecurityResponse.getDefaultInstance(),
                this.openCDXProtectorClient.detectAnomalies(
                        AnomalyDetectionDataRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void detectAnomaliesException() {
        Mockito.when(this.neuralProtectorServiceBlockingStub.detectAnomalies(
                        Mockito.any(AnomalyDetectionDataRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        AnomalyDetectionDataRequest request = AnomalyDetectionDataRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXProtectorClient.detectAnomalies(request, openCDXCallCredentials));
    }

    @Test
    void enforceAuthorizationControl() {
        Mockito.when(this.neuralProtectorServiceBlockingStub.enforceAuthorizationControl(
                        Mockito.any(AuthorizationControlDataRequest.class)))
                .thenReturn(SecurityResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SecurityResponse.getDefaultInstance(),
                this.openCDXProtectorClient.enforceAuthorizationControl(
                        AuthorizationControlDataRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void enforceAuthorizationControlException() {
        Mockito.when(this.neuralProtectorServiceBlockingStub.enforceAuthorizationControl(
                        Mockito.any(AuthorizationControlDataRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        AuthorizationControlDataRequest request = AuthorizationControlDataRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXProtectorClient.enforceAuthorizationControl(request, openCDXCallCredentials));
    }

    @Test
    void protectPrivacy() {
        Mockito.when(this.neuralProtectorServiceBlockingStub.protectPrivacy(
                        Mockito.any(PrivacyProtectionDataRequest.class)))
                .thenReturn(SecurityResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SecurityResponse.getDefaultInstance(),
                this.openCDXProtectorClient.protectPrivacy(
                        PrivacyProtectionDataRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void protectPrivacyException() {
        Mockito.when(this.neuralProtectorServiceBlockingStub.protectPrivacy(
                        Mockito.any(PrivacyProtectionDataRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        PrivacyProtectionDataRequest request = PrivacyProtectionDataRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXProtectorClient.protectPrivacy(request, openCDXCallCredentials));
    }

    @Test
    void monitorRealTimeActivity() {
        Mockito.when(this.neuralProtectorServiceBlockingStub.monitorRealTimeActivity(
                        Mockito.any(RealTimeMonitoringDataRequest.class)))
                .thenReturn(SecurityResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SecurityResponse.getDefaultInstance(),
                this.openCDXProtectorClient.monitorRealTimeActivity(
                        RealTimeMonitoringDataRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void monitorRealTimeActivityException() {
        Mockito.when(this.neuralProtectorServiceBlockingStub.monitorRealTimeActivity(
                        Mockito.any(RealTimeMonitoringDataRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        RealTimeMonitoringDataRequest request = RealTimeMonitoringDataRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXProtectorClient.monitorRealTimeActivity(request, openCDXCallCredentials));
    }

    @Test
    void analyzeUserBehavior() {
        Mockito.when(this.neuralProtectorServiceBlockingStub.analyzeUserBehavior(
                        Mockito.any(UserBehaviorAnalysisDataRequest.class)))
                .thenReturn(SecurityResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SecurityResponse.getDefaultInstance(),
                this.openCDXProtectorClient.analyzeUserBehavior(
                        UserBehaviorAnalysisDataRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void analyzeUserBehaviorException() {
        Mockito.when(this.neuralProtectorServiceBlockingStub.analyzeUserBehavior(
                        Mockito.any(UserBehaviorAnalysisDataRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UserBehaviorAnalysisDataRequest request = UserBehaviorAnalysisDataRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXProtectorClient.analyzeUserBehavior(request, openCDXCallCredentials));
    }
}
