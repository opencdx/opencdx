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
package cdx.opencdx.protector.controller;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.neural.protector.*;
import cdx.opencdx.protector.service.impl.OpenCDXProtectorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
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
class OpenCDXGrpcProtectorControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    OpenCDXProtectorServiceImpl protectorService;

    OpenCDXGrpcProtectorController grpcProtectorController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.protectorService = new OpenCDXProtectorServiceImpl(this.openCDXDocumentValidator);
        this.grpcProtectorController = new OpenCDXGrpcProtectorController(this.protectorService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void detectAnomalies() {
        AnomalyDetectionDataRequest request = AnomalyDetectionDataRequest.newBuilder()
                .setAnomalyDetectionData(AnomalyDetectionData.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();
        StreamObserver<SecurityResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcProtectorController.detectAnomalies(request, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SecurityResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void enforceAuthorizationControl() {
        AuthorizationControlDataRequest request = AuthorizationControlDataRequest.newBuilder()
                .setAuthorizationControlData(AuthorizationControlData.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();
        StreamObserver<SecurityResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcProtectorController.enforceAuthorizationControl(request, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SecurityResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void protectPrivacy() {
        PrivacyProtectionDataRequest request =
                PrivacyProtectionDataRequest.newBuilder().build();
        StreamObserver<SecurityResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcProtectorController.protectPrivacy(request, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SecurityResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void monitorRealTimeActivity() {
        RealTimeMonitoringDataRequest request = RealTimeMonitoringDataRequest.newBuilder()
                .setRealTimeMonitoringData(RealTimeMonitoringData.newBuilder()
                        .setMonitoredEntity(ObjectId.get().toHexString())
                        .build())
                .build();
        StreamObserver<SecurityResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcProtectorController.monitorRealTimeActivity(request, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SecurityResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void analyzeUserBehavior() {
        UserBehaviorAnalysisDataRequest request = UserBehaviorAnalysisDataRequest.newBuilder()
                .setUserBehaviorAnalysisData(UserBehaviorAnalysisData.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .build())
                .build();
        StreamObserver<SecurityResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcProtectorController.analyzeUserBehavior(request, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SecurityResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
