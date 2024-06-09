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
class OpenCDXMedicalConditionsClientImplTest {

    @Mock
    MedicalConditionsServiceGrpc.MedicalConditionsServiceBlockingStub medicalConditionsServiceBlockingStub;

    OpenCDXMedicalConditionsClientImpl openCDXMedicalConditionsClient;

    @BeforeEach
    void setUp() {
        this.medicalConditionsServiceBlockingStub =
                Mockito.mock(MedicalConditionsServiceGrpc.MedicalConditionsServiceBlockingStub.class);
        this.openCDXMedicalConditionsClient =
                new OpenCDXMedicalConditionsClientImpl(this.medicalConditionsServiceBlockingStub);
        Mockito.when(medicalConditionsServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.medicalConditionsServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.medicalConditionsServiceBlockingStub);
    }

    @Test
    void createDiagnosis() {
        Mockito.when(this.medicalConditionsServiceBlockingStub.createDiagnosis(Mockito.any(DiagnosisRequest.class)))
                .thenReturn(DiagnosisResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DiagnosisResponse.getDefaultInstance(),
                this.openCDXMedicalConditionsClient.createDiagnosis(
                        DiagnosisRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createDiagnosisException() {
        Mockito.when(this.medicalConditionsServiceBlockingStub.createDiagnosis(Mockito.any(DiagnosisRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DiagnosisRequest request = DiagnosisRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicalConditionsClient.createDiagnosis(request, openCDXCallCredentials));
    }

    @Test
    void getDiagnosis() {
        Mockito.when(this.medicalConditionsServiceBlockingStub.getDiagnosis(Mockito.any(GetDiagnosisByIdRequest.class)))
                .thenReturn(DiagnosisResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DiagnosisResponse.getDefaultInstance(),
                this.openCDXMedicalConditionsClient.getDiagnosis(
                        GetDiagnosisByIdRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getDiagnosisException() {
        Mockito.when(this.medicalConditionsServiceBlockingStub.getDiagnosis(Mockito.any(GetDiagnosisByIdRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        GetDiagnosisByIdRequest request = GetDiagnosisByIdRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicalConditionsClient.getDiagnosis(request, openCDXCallCredentials));
    }

    @Test
    void updateDiagnosis() {
        Mockito.when(this.medicalConditionsServiceBlockingStub.updateDiagnosis(Mockito.any(DiagnosisRequest.class)))
                .thenReturn(DiagnosisResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DiagnosisResponse.getDefaultInstance(),
                this.openCDXMedicalConditionsClient.updateDiagnosis(
                        DiagnosisRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void updateDiagnosisException() {
        Mockito.when(this.medicalConditionsServiceBlockingStub.updateDiagnosis(Mockito.any(DiagnosisRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DiagnosisRequest request = DiagnosisRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicalConditionsClient.updateDiagnosis(request, openCDXCallCredentials));
    }

    @Test
    void deleteDiagnosis() {
        Mockito.when(this.medicalConditionsServiceBlockingStub.deleteDiagnosis(
                        Mockito.any(DeleteDiagnosisRequest.class)))
                .thenReturn(DiagnosisResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DiagnosisResponse.getDefaultInstance(),
                this.openCDXMedicalConditionsClient.deleteDiagnosis(
                        DeleteDiagnosisRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void deleteDiagnosisException() {
        Mockito.when(this.medicalConditionsServiceBlockingStub.deleteDiagnosis(
                        Mockito.any(DeleteDiagnosisRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DeleteDiagnosisRequest request = DeleteDiagnosisRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicalConditionsClient.deleteDiagnosis(request, openCDXCallCredentials));
    }

    @Test
    void listDiagnosis() {
        Mockito.when(this.medicalConditionsServiceBlockingStub.listDiagnosis(Mockito.any(ListDiagnosisRequest.class)))
                .thenReturn(ListDiagnosisResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ListDiagnosisResponse.getDefaultInstance(),
                this.openCDXMedicalConditionsClient.listDiagnosis(
                        ListDiagnosisRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void listDiagnosisException() {
        Mockito.when(this.medicalConditionsServiceBlockingStub.listDiagnosis(Mockito.any(ListDiagnosisRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ListDiagnosisRequest request = ListDiagnosisRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXMedicalConditionsClient.listDiagnosis(request, openCDXCallCredentials));
    }
}
