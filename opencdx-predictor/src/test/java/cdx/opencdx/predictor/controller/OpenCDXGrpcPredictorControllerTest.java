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
package cdx.opencdx.predictor.controller;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.neural.predictor.PredictorInput;
import cdx.opencdx.grpc.neural.predictor.PredictorRequest;
import cdx.opencdx.grpc.neural.predictor.PredictorResponse;
import cdx.opencdx.predictor.service.impl.OpenCDXPredictorServiceImpl;
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
class OpenCDXGrpcPredictorControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    OpenCDXPredictorServiceImpl predictorService;

    OpenCDXGrpcPredictorController grpcPredictorController;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        this.predictorService = new OpenCDXPredictorServiceImpl(
               this.openCDXDocumentValidator);
        this.grpcPredictorController = new OpenCDXGrpcPredictorController(this.predictorService);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void testPredict() {
        PredictorRequest request = PredictorRequest.newBuilder()
                .setPredictorInput(PredictorInput.newBuilder()
                        .setTestId(ObjectId.get().toHexString())
                        .build())
                .build();
        StreamObserver<PredictorResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.grpcPredictorController.predict(request, responseObserver);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(PredictorResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
