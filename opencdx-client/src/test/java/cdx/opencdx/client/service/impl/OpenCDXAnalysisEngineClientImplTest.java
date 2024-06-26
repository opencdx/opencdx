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
import cdx.opencdx.client.service.OpenCDXAnalysisEngineClient;
import cdx.opencdx.grpc.service.health.*;
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
class OpenCDXAnalysisEngineClientImplTest {

    @Mock
    AnalysisEngineServiceGrpc.AnalysisEngineServiceBlockingStub
            AnalysisEngineServiceBlockingStub;

    OpenCDXAnalysisEngineClient openCDXAnalysisEngineClient;

    @BeforeEach
    void setUp() {
        this.AnalysisEngineServiceBlockingStub =
                Mockito.mock(AnalysisEngineServiceGrpc.AnalysisEngineServiceBlockingStub.class);
        this.openCDXAnalysisEngineClient =
                new OpenCDXAnalysisEngineClientImpl(this.AnalysisEngineServiceBlockingStub);
        Mockito.when(AnalysisEngineServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.AnalysisEngineServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.AnalysisEngineServiceBlockingStub);
    }

    @Test
    void createAnalysisEngine() {
        Mockito.when(this.AnalysisEngineServiceBlockingStub.createAnalysisEngine(
                        Mockito.any(CreateAnalysisEngineRequest.class)))
                .thenReturn(CreateAnalysisEngineResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateAnalysisEngineResponse.getDefaultInstance(),
                this.openCDXAnalysisEngineClient.createAnalysisEngine(
                        CreateAnalysisEngineRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createAnalysisEngineException() {
        Mockito.when(this.AnalysisEngineServiceBlockingStub.createAnalysisEngine(
                        Mockito.any(CreateAnalysisEngineRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateAnalysisEngineRequest request = CreateAnalysisEngineRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXAnalysisEngineClient.createAnalysisEngine(
                        request, openCDXCallCredentials));
    }

    @Test
    void getAnalysisEngine() {
        Mockito.when(this.AnalysisEngineServiceBlockingStub.getAnalysisEngine(
                        Mockito.any(GetAnalysisEngineRequest.class)))
                .thenReturn(GetAnalysisEngineResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetAnalysisEngineResponse.getDefaultInstance(),
                this.openCDXAnalysisEngineClient.getAnalysisEngine(
                        GetAnalysisEngineRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getAnalysisEngineException() {
        Mockito.when(this.AnalysisEngineServiceBlockingStub.getAnalysisEngine(
                        Mockito.any(GetAnalysisEngineRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetAnalysisEngineRequest request = GetAnalysisEngineRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXAnalysisEngineClient.getAnalysisEngine(
                        request, openCDXCallCredentials));
    }

    @Test
    void updateAnalysisEngine() {
        Mockito.when(this.AnalysisEngineServiceBlockingStub.updateAnalysisEngine(
                        Mockito.any(UpdateAnalysisEngineRequest.class)))
                .thenReturn(UpdateAnalysisEngineResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateAnalysisEngineResponse.getDefaultInstance(),
                this.openCDXAnalysisEngineClient.updateAnalysisEngine(
                        UpdateAnalysisEngineRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateAnalysisEngineException() {
        Mockito.when(this.AnalysisEngineServiceBlockingStub.updateAnalysisEngine(
                        Mockito.any(UpdateAnalysisEngineRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateAnalysisEngineRequest request = UpdateAnalysisEngineRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXAnalysisEngineClient.updateAnalysisEngine(
                        request, openCDXCallCredentials));
    }

    @Test
    void deleteAnalysisEngine() {
        Mockito.when(this.AnalysisEngineServiceBlockingStub.deleteAnalysisEngine(
                        Mockito.any(DeleteAnalysisEngineRequest.class)))
                .thenReturn(SuccessResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuccessResponse.getDefaultInstance(),
                this.openCDXAnalysisEngineClient.deleteAnalysisEngine(
                        DeleteAnalysisEngineRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteAnalysisEngineException() {
        Mockito.when(this.AnalysisEngineServiceBlockingStub.deleteAnalysisEngine(
                        Mockito.any(DeleteAnalysisEngineRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteAnalysisEngineRequest request = DeleteAnalysisEngineRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXAnalysisEngineClient.deleteAnalysisEngine(
                        request, openCDXCallCredentials));
    }

    @Test
    void listAnalysisEngines() {
        Mockito.when(this.AnalysisEngineServiceBlockingStub.listAnalysisEngines(
                        Mockito.any(ListAnalysisEnginesRequest.class)))
                .thenReturn(ListAnalysisEnginesResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListAnalysisEnginesResponse.getDefaultInstance(),
                this.openCDXAnalysisEngineClient.listAnalysisEngines(
                        ListAnalysisEnginesRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listAnalysisEnginesException() {
        Mockito.when(this.AnalysisEngineServiceBlockingStub.listAnalysisEngines(
                        Mockito.any(ListAnalysisEnginesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListAnalysisEnginesRequest request = ListAnalysisEnginesRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXAnalysisEngineClient.listAnalysisEngines(
                        request, openCDXCallCredentials));
    }
}
