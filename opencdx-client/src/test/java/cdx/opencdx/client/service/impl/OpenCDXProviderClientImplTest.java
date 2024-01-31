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
import cdx.opencdx.client.service.OpenCDXProviderClient;
import cdx.opencdx.grpc.provider.*;
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
class OpenCDXProviderClientImplTest {

    @Mock
    ProviderServiceGrpc.ProviderServiceBlockingStub providerServiceBlockingStub;

    OpenCDXProviderClient openCDXProviderClient;

    @BeforeEach
    void setUp() {
        this.providerServiceBlockingStub = Mockito.mock(ProviderServiceGrpc.ProviderServiceBlockingStub.class);
        this.openCDXProviderClient = new OpenCDXProviderClientImpl(this.providerServiceBlockingStub);
        Mockito.when(providerServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.providerServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.providerServiceBlockingStub);
    }

    @Test
    void getProviderByNumber() {
        Mockito.when(this.providerServiceBlockingStub.getProviderByNumber(Mockito.any(GetProviderRequest.class)))
                .thenReturn(GetProviderResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetProviderResponse.getDefaultInstance(),
                this.openCDXProviderClient.getProviderByNumber(
                        GetProviderRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getProviderByNumberException() {
        Mockito.when(this.providerServiceBlockingStub.getProviderByNumber(Mockito.any(GetProviderRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetProviderRequest request = GetProviderRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXProviderClient.getProviderByNumber(
                        request, openCDXCallCredentials));
    }

    @Test
    void deleteProvider() {
        Mockito.when(this.providerServiceBlockingStub.deleteProvider(Mockito.any(DeleteProviderRequest.class)))
                .thenReturn(DeleteProviderResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DeleteProviderResponse.getDefaultInstance(),
                this.openCDXProviderClient.deleteProvider(
                        DeleteProviderRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteProviderException() {
        Mockito.when(this.providerServiceBlockingStub.deleteProvider(Mockito.any(DeleteProviderRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteProviderRequest request = DeleteProviderRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXProviderClient.deleteProvider(
                        request, openCDXCallCredentials));
    }

    @Test
    void listProviders() {
        Mockito.when(this.providerServiceBlockingStub.listProviders(Mockito.any(ListProvidersRequest.class)))
                .thenReturn(ListProvidersResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListProvidersResponse.getDefaultInstance(),
                this.openCDXProviderClient.listProviders(
                        ListProvidersRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listProvidersException() {
        Mockito.when(this.providerServiceBlockingStub.listProviders(Mockito.any(ListProvidersRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListProvidersRequest request = ListProvidersRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXProviderClient.listProviders(
                        request, openCDXCallCredentials));
    }

    @Test
    void loadProvider() {
        Mockito.when(this.providerServiceBlockingStub.loadProvider(Mockito.any(LoadProviderRequest.class)))
                .thenReturn(LoadProviderResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                LoadProviderResponse.getDefaultInstance(),
                this.openCDXProviderClient.loadProvider(
                        LoadProviderRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void loadProviderException() {
        Mockito.when(this.providerServiceBlockingStub.loadProvider(Mockito.any(LoadProviderRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        LoadProviderRequest request = LoadProviderRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXProviderClient.loadProvider(
                        request, openCDXCallCredentials));
    }
}
