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
import cdx.opencdx.client.service.OpenCDXTinkarClient;
import cdx.opencdx.grpc.service.tinkar.*;
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
class OpenCDXTinkarClientImplTest {

    @Mock
    TinkarQueryServiceGrpc.TinkarQueryServiceBlockingStub tinkarQueryServiceBlockingStub;

    OpenCDXTinkarClient openCDXTinkarClient;

    @BeforeEach
    void setUp() {
        this.tinkarQueryServiceBlockingStub = Mockito.mock(TinkarQueryServiceGrpc.TinkarQueryServiceBlockingStub.class);
        this.openCDXTinkarClient = new OpenCDXTinkarClientImpl(this.tinkarQueryServiceBlockingStub);
        Mockito.when(tinkarQueryServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.tinkarQueryServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.tinkarQueryServiceBlockingStub);
    }

    @Test
    void testSearchTinkar() {
        Mockito.when(this.tinkarQueryServiceBlockingStub.searchTinkar(Mockito.any(TinkarSearchQueryRequest.class)))
                .thenReturn(TinkarSearchQueryResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                TinkarSearchQueryResponse.getDefaultInstance(),
                this.openCDXTinkarClient.searchTinkar(
                        TinkarSearchQueryRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void testSearchTinkarException() {
        Mockito.when(this.tinkarQueryServiceBlockingStub.searchTinkar(Mockito.any(TinkarSearchQueryRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        TinkarSearchQueryRequest request = TinkarSearchQueryRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTinkarClient.searchTinkar(request, openCDXCallCredentials));
    }

    @Test
    void testGetTinkarEntity() {
        Mockito.when(this.tinkarQueryServiceBlockingStub.getTinkarEntity(Mockito.any(TinkarGetRequest.class)))
                .thenReturn(TinkarGetResult.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                TinkarGetResult.getDefaultInstance(),
                this.openCDXTinkarClient.getTinkarEntity(
                        TinkarGetRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void testGetTinkarEntityException() {
        Mockito.when(this.tinkarQueryServiceBlockingStub.getTinkarEntity(Mockito.any(TinkarGetRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        TinkarGetRequest request = TinkarGetRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTinkarClient.getTinkarEntity(request, openCDXCallCredentials));
    }

    @Test
    void testGetTinkarChildConcepts() {
        Mockito.when(this.tinkarQueryServiceBlockingStub.getTinkarChildConcepts(Mockito.any(TinkarGetRequest.class)))
                .thenReturn(TinkarGetResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                TinkarGetResponse.getDefaultInstance(),
                this.openCDXTinkarClient.getTinkarChildConcepts(
                        TinkarGetRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void testGetTinkarChildConceptsException() {
        Mockito.when(this.tinkarQueryServiceBlockingStub.getTinkarChildConcepts(Mockito.any(TinkarGetRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        TinkarGetRequest request = TinkarGetRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTinkarClient.getTinkarChildConcepts(request, openCDXCallCredentials));
    }

    @Test
    void testGetTinkarDescendantConcepts() {
        Mockito.when(this.tinkarQueryServiceBlockingStub.getTinkarDescendantConcepts(
                        Mockito.any(TinkarGetRequest.class)))
                .thenReturn(TinkarGetResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                TinkarGetResponse.getDefaultInstance(),
                this.openCDXTinkarClient.getTinkarDescendantConcepts(
                        TinkarGetRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void testGetTinkarDescendantConceptsException() {
        Mockito.when(this.tinkarQueryServiceBlockingStub.getTinkarDescendantConcepts(
                        Mockito.any(TinkarGetRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        TinkarGetRequest request = TinkarGetRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXTinkarClient.getTinkarDescendantConcepts(request, openCDXCallCredentials));
    }
}
