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
class OpenCDXMedicalHistoryClientImplTest {

    @Mock
    MedicalHistoryServiceGrpc.MedicalHistoryServiceBlockingStub medicalHistoryServiceBlockingStub;

    OpenCDXMedicalHistoryClientImpl openCDXMedicalHistoryClient;

    @BeforeEach
    void setUp() {
        this.medicalHistoryServiceBlockingStub =
                Mockito.mock(MedicalHistoryServiceGrpc.MedicalHistoryServiceBlockingStub.class);
        this.openCDXMedicalHistoryClient = new OpenCDXMedicalHistoryClientImpl(this.medicalHistoryServiceBlockingStub);
        Mockito.when(medicalHistoryServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.medicalHistoryServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.medicalHistoryServiceBlockingStub);
    }

    @Test
    void createMedicalHistory() {
        Mockito.when(this.medicalHistoryServiceBlockingStub.createMedicalHistory(
                        Mockito.any(CreateMedicalHistoryRequest.class)))
                .thenReturn(CreateMedicalHistoryResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                CreateMedicalHistoryResponse.getDefaultInstance(),
                this.openCDXMedicalHistoryClient.createMedicalHistory(
                        CreateMedicalHistoryRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createMedicalHistoryException() {
        Mockito.when(this.medicalHistoryServiceBlockingStub.createMedicalHistory(
                        Mockito.any(CreateMedicalHistoryRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        CreateMedicalHistoryRequest request = CreateMedicalHistoryRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicalHistoryClient.createMedicalHistory(request, openCDXCallCredentials));
    }

    @Test
    void getMedicalHistory() {
        Mockito.when(this.medicalHistoryServiceBlockingStub.getMedicalHistory(
                        Mockito.any(GetMedicalHistoryRequest.class)))
                .thenReturn(GetMedicalHistoryResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                GetMedicalHistoryResponse.getDefaultInstance(),
                this.openCDXMedicalHistoryClient.getMedicalHistory(
                        GetMedicalHistoryRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getMedicalHistoryException() {
        Mockito.when(this.medicalHistoryServiceBlockingStub.getMedicalHistory(
                        Mockito.any(GetMedicalHistoryRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetMedicalHistoryRequest request = GetMedicalHistoryRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicalHistoryClient.getMedicalHistory(request, openCDXCallCredentials));
    }

    @Test
    void updateMedicalHistory() {
        Mockito.when(this.medicalHistoryServiceBlockingStub.updateMedicalHistory(
                        Mockito.any(UpdateMedicalHistoryRequest.class)))
                .thenReturn(UpdateMedicalHistoryResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                UpdateMedicalHistoryResponse.getDefaultInstance(),
                this.openCDXMedicalHistoryClient.updateMedicalHistory(
                        UpdateMedicalHistoryRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateMedicalHistoryException() {
        Mockito.when(this.medicalHistoryServiceBlockingStub.updateMedicalHistory(
                        Mockito.any(UpdateMedicalHistoryRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        UpdateMedicalHistoryRequest request = UpdateMedicalHistoryRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicalHistoryClient.updateMedicalHistory(request, openCDXCallCredentials));
    }

    @Test
    void deleteMedicalHistory() {
        Mockito.when(this.medicalHistoryServiceBlockingStub.deleteMedicalHistory(
                        Mockito.any(DeleteMedicalHistoryRequest.class)))
                .thenReturn(SuccessResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuccessResponse.getDefaultInstance(),
                this.openCDXMedicalHistoryClient.deleteMedicalHistory(
                        DeleteMedicalHistoryRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteMedicalHistoryException() {
        Mockito.when(this.medicalHistoryServiceBlockingStub.deleteMedicalHistory(
                        Mockito.any(DeleteMedicalHistoryRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteMedicalHistoryRequest request = DeleteMedicalHistoryRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicalHistoryClient.deleteMedicalHistory(request, openCDXCallCredentials));
    }

    @Test
    void listMedicalHistories() {
        Mockito.when(this.medicalHistoryServiceBlockingStub.listMedicalHistories(
                        Mockito.any(ListMedicalHistoriesRequest.class)))
                .thenReturn(ListMedicalHistoriesResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListMedicalHistoriesResponse.getDefaultInstance(),
                this.openCDXMedicalHistoryClient.listMedicalHistories(
                        ListMedicalHistoriesRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listMedicalHistoriesException() {
        Mockito.when(this.medicalHistoryServiceBlockingStub.listMedicalHistories(
                        Mockito.any(ListMedicalHistoriesRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListMedicalHistoriesRequest request = ListMedicalHistoriesRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicalHistoryClient.listMedicalHistories(request, openCDXCallCredentials));
    }
}
