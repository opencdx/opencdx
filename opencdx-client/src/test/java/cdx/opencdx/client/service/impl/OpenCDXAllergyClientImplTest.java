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
import cdx.opencdx.client.service.OpenCDXAllergyClient;
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
class OpenCDXAllergyClientImplTest {

    @Mock
    KnownAllergyServiceGrpc.KnownAllergyServiceBlockingStub allergyServiceBlockingStub;

    OpenCDXAllergyClient openCDXAllergyClient;

    @BeforeEach
    void setUp() {
        this.allergyServiceBlockingStub = Mockito.mock(KnownAllergyServiceGrpc.KnownAllergyServiceBlockingStub.class);
        this.openCDXAllergyClient = new OpenCDXAllergyClientImpl(this.allergyServiceBlockingStub);
        Mockito.when(allergyServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.allergyServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.allergyServiceBlockingStub);
    }

    @Test
    void createAllergy() {
        Mockito.when(this.allergyServiceBlockingStub.createAllergy(Mockito.any(CreateAllergyRequest.class)))
                .thenReturn(CreateAllergyResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateAllergyResponse.getDefaultInstance(),
                this.openCDXAllergyClient.createAllergy(
                        CreateAllergyRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createAllergyException() {
        Mockito.when(this.allergyServiceBlockingStub.createAllergy(Mockito.any(CreateAllergyRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateAllergyRequest request = CreateAllergyRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXAllergyClient.createAllergy(request, openCDXCallCredentials));
    }

    @Test
    void getAllergy() {
        Mockito.when(this.allergyServiceBlockingStub.getAllergy(Mockito.any(GetAllergyRequest.class)))
                .thenReturn(GetAllergyResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetAllergyResponse.getDefaultInstance(),
                this.openCDXAllergyClient.getAllergy(GetAllergyRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getAllergyException() {
        Mockito.when(this.allergyServiceBlockingStub.getAllergy(Mockito.any(GetAllergyRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetAllergyRequest request = GetAllergyRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXAllergyClient.getAllergy(request, openCDXCallCredentials));
    }

    @Test
    void updateAllergy() {
        Mockito.when(this.allergyServiceBlockingStub.updateAllergy(Mockito.any(UpdateAllergyRequest.class)))
                .thenReturn(UpdateAllergyResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateAllergyResponse.getDefaultInstance(),
                this.openCDXAllergyClient.updateAllergy(
                        UpdateAllergyRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateAllergyException() {
        Mockito.when(this.allergyServiceBlockingStub.updateAllergy(Mockito.any(UpdateAllergyRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateAllergyRequest request = UpdateAllergyRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXAllergyClient.updateAllergy(request, openCDXCallCredentials));
    }

    @Test
    void deleteAllergy() {
        Mockito.when(this.allergyServiceBlockingStub.deleteAllergy(Mockito.any(DeleteAllergyRequest.class)))
                .thenReturn(SuccessResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuccessResponse.getDefaultInstance(),
                this.openCDXAllergyClient.deleteAllergy(
                        DeleteAllergyRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteAllergyException() {
        Mockito.when(this.allergyServiceBlockingStub.deleteAllergy(Mockito.any(DeleteAllergyRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteAllergyRequest request = DeleteAllergyRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXAllergyClient.deleteAllergy(request, openCDXCallCredentials));
    }

    @Test
    void listAllergies() {
        Mockito.when(this.allergyServiceBlockingStub.listAllergies(Mockito.any(ListAllergyRequest.class)))
                .thenReturn(ListAllergyResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListAllergyResponse.getDefaultInstance(),
                this.openCDXAllergyClient.listAllergies(
                        ListAllergyRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listAllergiesException() {
        Mockito.when(this.allergyServiceBlockingStub.listAllergies(Mockito.any(ListAllergyRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListAllergyRequest request = ListAllergyRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXAllergyClient.listAllergies(request, openCDXCallCredentials));
    }
}
