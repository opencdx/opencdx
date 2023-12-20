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
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.neural.protector.AnomalyDetectionData;
import cdx.opencdx.grpc.neural.protector.AnomalyDetectionDataRequest;
import cdx.opencdx.grpc.neural.protector.AuthorizationControlData;
import cdx.opencdx.grpc.neural.protector.AuthorizationControlDataRequest;
import cdx.opencdx.grpc.neural.protector.PrivacyProtectionData;
import cdx.opencdx.grpc.neural.protector.PrivacyProtectionDataRequest;
import cdx.opencdx.grpc.neural.protector.RealTimeMonitoringData;
import cdx.opencdx.grpc.neural.protector.RealTimeMonitoringDataRequest;
import cdx.opencdx.grpc.neural.protector.SecurityResponse;
import cdx.opencdx.grpc.neural.protector.UserBehaviorAnalysisData;
import cdx.opencdx.grpc.neural.protector.UserBehaviorAnalysisDataRequest;
import cdx.opencdx.protector.service.OpenCDXProtectorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXProtectorServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXProtectorService protectorService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void beforeEach() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.protectorService = new OpenCDXProtectorServiceImpl(
                this.openCDXAuditService, this.objectMapper, openCDXCurrentUser, this.openCDXDocumentValidator);
    }

    @AfterEach
    void tearDown() {}

    // Test case: ProtectorServiceImplTest that calls the detectAnomalies
    @Test
    void detectAnomalies() {
        AnomalyDetectionDataRequest request = AnomalyDetectionDataRequest.newBuilder()
                .setAnomalyDetectionData(AnomalyDetectionData.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();
        SecurityResponse response = this.protectorService.detectAnomalies(request);

        Assertions.assertEquals("SecurityResponse [detectAnomalies]", response.getResponse());
        Assertions.assertEquals(request.getAnomalyDetectionDataOrBuilder().getEncounterId(), response.getEncounterId());
    }

    // Test case: ProtectorServiceImplTest that calls the enforceAuthorizationControl
    @Test
    void enforceAuthorizationControl() {
        AuthorizationControlDataRequest request = AuthorizationControlDataRequest.newBuilder()
                .setAuthorizationControlData(AuthorizationControlData.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();
        SecurityResponse response = this.protectorService.enforceAuthorizationControl(request);

        Assertions.assertEquals("SecurityResponse [enforceAuthorizationControl]", response.getResponse());
        Assertions.assertEquals(request.getAuthorizationControlData().getEncounterId(), response.getEncounterId());
    }

    // Test case: ProtectorServiceImplTest that calls the protectPrivacy
    @Test
    void protectPrivacy() {
        PrivacyProtectionDataRequest request =
                PrivacyProtectionDataRequest.newBuilder().build();
        SecurityResponse response = this.protectorService.protectPrivacy(request);

        Assertions.assertEquals("SecurityResponse [protectPrivacy]", response.getResponse());
        Assertions.assertEquals(request.getPrivacyProtectionData().getEncounterId(), response.getEncounterId());
    }

    // Test case: ProtectorServiceImplTest that calls the monitorRealTimeActivity
    @Test
    void monitorRealTimeActivity() {
        RealTimeMonitoringDataRequest request = RealTimeMonitoringDataRequest.newBuilder()
                .setRealTimeMonitoringData(RealTimeMonitoringData.newBuilder()
                        .setMonitoredEntity(ObjectId.get().toHexString())
                        .build())
                .build();
        SecurityResponse response = this.protectorService.monitorRealTimeActivity(request);

        Assertions.assertEquals("SecurityResponse [monitorRealTimeActivity]", response.getResponse());
        Assertions.assertEquals(request.getRealTimeMonitoringData().getEncounterId(), response.getEncounterId());
    }

    // Test case: ProtectorServiceImplTest that calls the analyzeUserBehavior
    @Test
    void analyzeUserBehavior() {
        UserBehaviorAnalysisDataRequest request = UserBehaviorAnalysisDataRequest.newBuilder()
                .setUserBehaviorAnalysisData(UserBehaviorAnalysisData.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();
        SecurityResponse response = this.protectorService.analyzeUserBehavior(request);

        Assertions.assertEquals("SecurityResponse [analyzeUserBehavior]", response.getResponse());
        Assertions.assertEquals(request.getUserBehaviorAnalysisData().getEncounterId(), response.getEncounterId());
    }

    // Failure

    // Failure test case: ProtectorServiceImplTest that calls the detectAnomalies and simulates a failure in JSON
    // processing
    @Test
    void detectAnomaliesFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.protectorService = new OpenCDXProtectorServiceImpl(
                this.openCDXAuditService, mapper, this.openCDXCurrentUser, this.openCDXDocumentValidator);

        AnomalyDetectionDataRequest request = AnomalyDetectionDataRequest.newBuilder()
                .setAnomalyDetectionData(AnomalyDetectionData.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .setEncounterId("789")
                        .build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> protectorService.detectAnomalies(request));
    }

    // Failure test case: ProtectorServiceImplTest that calls the enforceAuthorizationControl and simulates a failure in
    // JSON processing
    @Test
    void enforceAuthorizationControlFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.protectorService = new OpenCDXProtectorServiceImpl(
                this.openCDXAuditService, mapper, this.openCDXCurrentUser, this.openCDXDocumentValidator);

        AuthorizationControlDataRequest request = AuthorizationControlDataRequest.newBuilder()
                .setAuthorizationControlData(AuthorizationControlData.newBuilder()
                        .setEncounterId("789")
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> protectorService.enforceAuthorizationControl(request));
    }

    // Failure test case: ProtectorServiceImplTest that calls the protectPrivacy and simulates a failure in JSON
    // processing
    @Test
    void protectPrivacyFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.protectorService = new OpenCDXProtectorServiceImpl(
                this.openCDXAuditService, mapper, this.openCDXCurrentUser, this.openCDXDocumentValidator);

        PrivacyProtectionDataRequest request = PrivacyProtectionDataRequest.newBuilder()
                .setPrivacyProtectionData(
                        PrivacyProtectionData.newBuilder().setEncounterId("789").build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> protectorService.protectPrivacy(request));
    }

    // Failure test case: ProtectorServiceImplTest that calls the monitorRealTimeActivity and simulates a failure in
    // JSON processing
    @Test
    void monitorRealTimeActivityFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.protectorService = new OpenCDXProtectorServiceImpl(
                this.openCDXAuditService, mapper, this.openCDXCurrentUser, this.openCDXDocumentValidator);

        RealTimeMonitoringDataRequest request = RealTimeMonitoringDataRequest.newBuilder()
                .setRealTimeMonitoringData(RealTimeMonitoringData.newBuilder()
                        .setEncounterId("789")
                        .setMonitoredEntity(ObjectId.get().toHexString())
                        .build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> protectorService.monitorRealTimeActivity(request));
    }

    // Failure test case: ProtectorServiceImplTest that calls the analyzeUserBehavior and simulates a failure in JSON
    // processing
    @Test
    void getMedicationFail() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Mockito.when(mapper.writeValueAsString(Mockito.anyString())).thenThrow(JsonProcessingException.class);

        this.protectorService = new OpenCDXProtectorServiceImpl(
                this.openCDXAuditService, mapper, this.openCDXCurrentUser, this.openCDXDocumentValidator);

        UserBehaviorAnalysisDataRequest request = UserBehaviorAnalysisDataRequest.newBuilder()
                .setUserBehaviorAnalysisData(UserBehaviorAnalysisData.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .setEncounterId("789")
                        .build())
                .build();

        Assertions.assertThrows(OpenCDXNotAcceptable.class, () -> protectorService.analyzeUserBehavior(request));
    }
}
