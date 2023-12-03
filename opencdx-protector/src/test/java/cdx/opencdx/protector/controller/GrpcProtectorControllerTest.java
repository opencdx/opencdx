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

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.neural.protector.AnomalyDetectionDataRequest;
import cdx.opencdx.grpc.neural.protector.AuthorizationControlDataRequest;
import cdx.opencdx.grpc.neural.protector.PrivacyProtectionDataRequest;
import cdx.opencdx.grpc.neural.protector.RealTimeMonitoringDataRequest;
import cdx.opencdx.grpc.neural.protector.SecurityResponse;
import cdx.opencdx.grpc.neural.protector.UserBehaviorAnalysisDataRequest;
import cdx.opencdx.protector.model.Person;
import cdx.opencdx.protector.repository.PersonRepository;
import cdx.opencdx.protector.service.impl.ProtectorServiceImpl;
import io.grpc.stub.StreamObserver;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class GrpcProtectorControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    PersonRepository personRepository;

    ProtectorServiceImpl protectorService;

    GrpcProtectorController grpcProtectorController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        this.personRepository = Mockito.mock(PersonRepository.class);
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        Mockito.when(this.personRepository.save(Mockito.any(Person.class))).then(AdditionalAnswers.returnsFirstArg());
        this.protectorService = new ProtectorServiceImpl(this.openCDXAuditService, openCDXCurrentUser);
        this.grpcProtectorController = new GrpcProtectorController(this.protectorService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.personRepository);
    }

    @Test
    void detectAnomalies() {
        AnomalyDetectionDataRequest request =
                AnomalyDetectionDataRequest.newBuilder().build();
        StreamObserver<SecurityResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcProtectorController.detectAnomalies(request, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SecurityResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void enforceAuthorizationControl() {
        AuthorizationControlDataRequest request =
                AuthorizationControlDataRequest.newBuilder().build();
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
        RealTimeMonitoringDataRequest request =
                RealTimeMonitoringDataRequest.newBuilder().build();
        StreamObserver<SecurityResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcProtectorController.monitorRealTimeActivity(request, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SecurityResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void analyzeUserBehavior() {
        UserBehaviorAnalysisDataRequest request =
                UserBehaviorAnalysisDataRequest.newBuilder().build();
        StreamObserver<SecurityResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcProtectorController.analyzeUserBehavior(request, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SecurityResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
