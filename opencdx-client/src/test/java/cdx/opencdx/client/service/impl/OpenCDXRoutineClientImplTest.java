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
import cdx.opencdx.client.service.OpenCDXRoutineClient;
import cdx.opencdx.grpc.routine.*;
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
class OpenCDXRoutineClientImplTest {

    @Mock
    RoutineSystemServiceGrpc.RoutineSystemServiceBlockingStub routineSystemServiceBlockingStub;

    OpenCDXRoutineClient openCDXRoutineClient;

    @BeforeEach
    void setUp() {
        this.routineSystemServiceBlockingStub =
                Mockito.mock(RoutineSystemServiceGrpc.RoutineSystemServiceBlockingStub.class);
        this.openCDXRoutineClient = new OpenCDXRoutineClientImpl(this.routineSystemServiceBlockingStub);
        Mockito.when(routineSystemServiceBlockingStub.withCallCredentials(Mockito.any()))
                .thenReturn(this.routineSystemServiceBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.routineSystemServiceBlockingStub);
    }

    @Test
    void createRoutine() {
        Mockito.when(this.routineSystemServiceBlockingStub.createRoutine(Mockito.any(RoutineRequest.class)))
                .thenReturn(RoutineResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                RoutineResponse.getDefaultInstance(),
                this.openCDXRoutineClient.createRoutine(RoutineRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createRoutineException() {
        Mockito.when(this.routineSystemServiceBlockingStub.createRoutine(Mockito.any(RoutineRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        RoutineRequest request = RoutineRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.createRoutine(request, openCDXCallCredentials));
    }

    @Test
    void getRoutine() {
        Mockito.when(this.routineSystemServiceBlockingStub.getRoutine(Mockito.any(RoutineRequest.class)))
                .thenReturn(RoutineResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                RoutineResponse.getDefaultInstance(),
                this.openCDXRoutineClient.getRoutine(RoutineRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getRoutineException() {
        Mockito.when(this.routineSystemServiceBlockingStub.getRoutine(Mockito.any(RoutineRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        RoutineRequest request = RoutineRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.getRoutine(request, openCDXCallCredentials));
    }

    @Test
    void createClinicalProtocolExecution() {
        Mockito.when(this.routineSystemServiceBlockingStub.createClinicalProtocolExecution(
                        Mockito.any(ClinicalProtocolExecutionRequest.class)))
                .thenReturn(ClinicalProtocolExecutionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ClinicalProtocolExecutionResponse.getDefaultInstance(),
                this.openCDXRoutineClient.createClinicalProtocolExecution(
                        ClinicalProtocolExecutionRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createClinicalProtocolExecutionException() {
        Mockito.when(this.routineSystemServiceBlockingStub.createClinicalProtocolExecution(
                        Mockito.any(ClinicalProtocolExecutionRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ClinicalProtocolExecutionRequest request = ClinicalProtocolExecutionRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.createClinicalProtocolExecution(request, openCDXCallCredentials));
    }

    @Test
    void getClinicalProtocolExecution() {
        Mockito.when(this.routineSystemServiceBlockingStub.getClinicalProtocolExecution(
                        Mockito.any(ClinicalProtocolExecutionRequest.class)))
                .thenReturn(ClinicalProtocolExecutionResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                ClinicalProtocolExecutionResponse.getDefaultInstance(),
                this.openCDXRoutineClient.getClinicalProtocolExecution(
                        ClinicalProtocolExecutionRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getClinicalProtocolExecutionException() {
        Mockito.when(this.routineSystemServiceBlockingStub.getClinicalProtocolExecution(
                        Mockito.any(ClinicalProtocolExecutionRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        ClinicalProtocolExecutionRequest request = ClinicalProtocolExecutionRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.getClinicalProtocolExecution(request, openCDXCallCredentials));
    }

    @Test
    void createLabOrder() {
        Mockito.when(this.routineSystemServiceBlockingStub.createLabOrder(Mockito.any(LabOrderRequest.class)))
                .thenReturn(LabOrderResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                LabOrderResponse.getDefaultInstance(),
                this.openCDXRoutineClient.createLabOrder(LabOrderRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createLabOrderException() {
        Mockito.when(this.routineSystemServiceBlockingStub.createLabOrder(Mockito.any(LabOrderRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        LabOrderRequest request = LabOrderRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.createLabOrder(request, openCDXCallCredentials));
    }

    @Test
    void getLabOrder() {
        Mockito.when(this.routineSystemServiceBlockingStub.getLabOrder(Mockito.any(LabOrderRequest.class)))
                .thenReturn(LabOrderResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                LabOrderResponse.getDefaultInstance(),
                this.openCDXRoutineClient.getLabOrder(LabOrderRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getLabOrderException() {
        Mockito.when(this.routineSystemServiceBlockingStub.getLabOrder(Mockito.any(LabOrderRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        LabOrderRequest request = LabOrderRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.getLabOrder(request, openCDXCallCredentials));
    }

    @Test
    void createDiagnosis() {
        Mockito.when(this.routineSystemServiceBlockingStub.createDiagnosis(Mockito.any(DiagnosisRequest.class)))
                .thenReturn(DiagnosisResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DiagnosisResponse.getDefaultInstance(),
                this.openCDXRoutineClient.createDiagnosis(
                        DiagnosisRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createDiagnosisException() {
        Mockito.when(this.routineSystemServiceBlockingStub.createDiagnosis(Mockito.any(DiagnosisRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DiagnosisRequest request = DiagnosisRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.createDiagnosis(request, openCDXCallCredentials));
    }

    @Test
    void getDiagnosis() {
        Mockito.when(this.routineSystemServiceBlockingStub.getDiagnosis(Mockito.any(DiagnosisRequest.class)))
                .thenReturn(DiagnosisResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                DiagnosisResponse.getDefaultInstance(),
                this.openCDXRoutineClient.getDiagnosis(DiagnosisRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getDiagnosisException() {
        Mockito.when(this.routineSystemServiceBlockingStub.getDiagnosis(Mockito.any(DiagnosisRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        DiagnosisRequest request = DiagnosisRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.getDiagnosis(request, openCDXCallCredentials));
    }

    @Test
    void createSuspectedDiagnosis() {
        Mockito.when(this.routineSystemServiceBlockingStub.createSuspectedDiagnosis(
                        Mockito.any(SuspectedDiagnosisRequest.class)))
                .thenReturn(SuspectedDiagnosisResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuspectedDiagnosisResponse.getDefaultInstance(),
                this.openCDXRoutineClient.createSuspectedDiagnosis(
                        SuspectedDiagnosisRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createSuspectedDiagnosisException() {
        Mockito.when(this.routineSystemServiceBlockingStub.createSuspectedDiagnosis(
                        Mockito.any(SuspectedDiagnosisRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        SuspectedDiagnosisRequest request = SuspectedDiagnosisRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.createSuspectedDiagnosis(request, openCDXCallCredentials));
    }

    @Test
    void getSuspectedDiagnosis() {
        Mockito.when(this.routineSystemServiceBlockingStub.getSuspectedDiagnosis(
                        Mockito.any(SuspectedDiagnosisRequest.class)))
                .thenReturn(SuspectedDiagnosisResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                SuspectedDiagnosisResponse.getDefaultInstance(),
                this.openCDXRoutineClient.getSuspectedDiagnosis(
                        SuspectedDiagnosisRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getSuspectedDiagnosisException() {
        Mockito.when(this.routineSystemServiceBlockingStub.getSuspectedDiagnosis(
                        Mockito.any(SuspectedDiagnosisRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        SuspectedDiagnosisRequest request = SuspectedDiagnosisRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.getSuspectedDiagnosis(request, openCDXCallCredentials));
    }

    @Test
    void createLabResult() {
        Mockito.when(this.routineSystemServiceBlockingStub.createLabResult(Mockito.any(LabResultRequest.class)))
                .thenReturn(LabResultResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                LabResultResponse.getDefaultInstance(),
                this.openCDXRoutineClient.createLabResult(
                        LabResultRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void createLabResultException() {
        Mockito.when(this.routineSystemServiceBlockingStub.createLabResult(Mockito.any(LabResultRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        LabResultRequest request = LabResultRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.createLabResult(request, openCDXCallCredentials));
    }

    @Test
    void getLabResult() {
        Mockito.when(this.routineSystemServiceBlockingStub.getLabResult(Mockito.any(LabResultRequest.class)))
                .thenReturn(LabResultResponse.getDefaultInstance());
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertEquals(
                LabResultResponse.getDefaultInstance(),
                this.openCDXRoutineClient.getLabResult(LabResultRequest.getDefaultInstance(), openCDXCallCredentials));
    }

    @Test
    void getLabResultException() {
        Mockito.when(this.routineSystemServiceBlockingStub.getLabResult(Mockito.any(LabResultRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));
        LabResultRequest request = LabResultRequest.getDefaultInstance();
        OpenCDXCallCredentials openCDXCallCredentials = new OpenCDXCallCredentials("Bearer");
        Assertions.assertThrows(
                OpenCDXClientException.class,
                () -> this.openCDXRoutineClient.getLabResult(request, openCDXCallCredentials));
    }
}
