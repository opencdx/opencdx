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

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.client.service.OpenCDXPredictorClient;
import cdx.opencdx.grpc.service.health.NeuralPredictorServiceGrpc;
import cdx.opencdx.grpc.service.health.PredictorRequest;
import cdx.opencdx.grpc.service.health.PredictorResponse;
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
class OpenCDXPredictorClientImplTest {
    @Mock
    NeuralPredictorServiceGrpc.NeuralPredictorServiceBlockingStub neuralPredictorServiceBlockingStub;

    OpenCDXPredictorClient openCDXPredictorClient;

    @BeforeEach
    void setUp() {
        this.neuralPredictorServiceBlockingStub =
                Mockito.mock(NeuralPredictorServiceGrpc.NeuralPredictorServiceBlockingStub.class);
        this.openCDXPredictorClient = new OpenCDXPredictorClientImpl(this.neuralPredictorServiceBlockingStub);
        Mockito.when(neuralPredictorServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.neuralPredictorServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.neuralPredictorServiceBlockingStub);
    }

    @Test
    void predictException() {
        Mockito.when(this.neuralPredictorServiceBlockingStub.predict(Mockito.any(PredictorRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        PredictorRequest request = PredictorRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXPredictorClient.predict(request, openCDXCallCredentials));
    }

    @Test
    void predict() {
        Mockito.when(this.neuralPredictorServiceBlockingStub.predict(Mockito.any(PredictorRequest.class)))
                .thenReturn(PredictorResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                PredictorResponse.getDefaultInstance(),
                this.openCDXPredictorClient.predict(PredictorRequest.getDefaultInstance(), openCDXCallCredentials));
    }
}
