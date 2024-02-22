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
package cdx.opencdx.predictor.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.neural.predictor.PredictorInput;
import cdx.opencdx.grpc.neural.predictor.PredictorRequest;
import cdx.opencdx.grpc.neural.predictor.PredictorResponse;
import cdx.opencdx.predictor.service.OpenCDXPredictorService;
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
class OpenCDXPredictorServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    OpenCDXPredictorService predictorService;

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

        this.predictorService = new OpenCDXPredictorServiceImpl(
                this.openCDXAuditService, this.objectMapper, openCDXCurrentUser, this.openCDXDocumentValidator);
    }

    @AfterEach
    void tearDown() {}

    // Test case: OpenCDXPredictorServiceImplTest that calls the predict
    @Test
    void testPredict() {
        PredictorRequest request = PredictorRequest.newBuilder()
                .setPredictorInput(PredictorInput.newBuilder()
                        .setTestId(ObjectId.get().toHexString())
                        .build())
                .build();
        PredictorResponse response = this.predictorService.predict(request);

        Assertions.assertEquals(
                "OpenCDXPredictorServiceImpl [predict]",
                response.getPredictorOutput().getPredictedValue());
        Assertions.assertEquals(
                request.getPredictorInput().getEncounterId(),
                response.getPredictorOutput().getEncounterId());
    }
}
