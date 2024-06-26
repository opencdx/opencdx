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
package cdx.opencdx.health.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRoutineServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXRoutineServiceImpl routineService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    void beforeEach() {

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        this.routineService = new OpenCDXRoutineServiceImpl(openCDXDocumentValidator);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void createRoutine() {
        RoutineRequest routineRequest = RoutineRequest.newBuilder()
                .setRoutine(Routine.newBuilder().setRoutineId("789").build())
                .build();

        RoutineResponse response = routineService.createRoutine(routineRequest);

        Assertions.assertEquals("789", response.getRoutine().getRoutineId());
    }

    @Test
    void getRoutine() {
        RoutineRequest routineRequest = RoutineRequest.newBuilder()
                .setRoutine(Routine.newBuilder().setRoutineId("789").build())
                .build();

        RoutineResponse response = routineService.getRoutine(routineRequest);

        Assertions.assertEquals("789", response.getRoutine().getRoutineId());
    }

    @Test
    void createClinicalProtocolExecution() {
        ClinicalProtocolExecutionRequest clinicalProtocolExecutionRequest =
                ClinicalProtocolExecutionRequest.newBuilder()
                        .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                                .setExecutionId("789")
                                .build())
                        .build();

        ClinicalProtocolExecutionResponse response =
                routineService.createClinicalProtocolExecution(clinicalProtocolExecutionRequest);

        Assertions.assertEquals("789", response.getClinicalProtocolExecution().getExecutionId());
    }

    @Test
    void getClinicalProtocolExecution() {
        ClinicalProtocolExecutionRequest clinicalProtocolExecutionRequest =
                ClinicalProtocolExecutionRequest.newBuilder()
                        .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                                .setExecutionId("789")
                                .build())
                        .build();

        ClinicalProtocolExecutionResponse response =
                routineService.getClinicalProtocolExecution(clinicalProtocolExecutionRequest);

        Assertions.assertEquals("789", response.getClinicalProtocolExecution().getExecutionId());
    }

    @Test
    void triggerLabOrder() {
        LabOrderRequest labOrderRequest = LabOrderRequest.newBuilder()
                .setLabOrder(LabOrder.newBuilder().setLabOrderId("789").build())
                .build();

        LabOrderResponse response = routineService.triggerLabOrder(labOrderRequest);

        Assertions.assertEquals("789", response.getLabOrder().getLabOrderId());
    }

    @Test
    void getLabOrder() {
        LabOrderRequest labOrderRequest = LabOrderRequest.newBuilder()
                .setLabOrder(LabOrder.newBuilder().setLabOrderId("789").build())
                .build();

        LabOrderResponse response = routineService.getLabOrder(labOrderRequest);

        Assertions.assertEquals("789", response.getLabOrder().getLabOrderId());
    }

    @Test
    void triggerLabResult() {
        LabResultRequest labResultRequest = LabResultRequest.newBuilder()
                .setLabResult(LabResult.newBuilder().setResultId("789").build())
                .build();

        LabResultResponse response = routineService.triggerLabResult(labResultRequest);

        Assertions.assertEquals("789", response.getLabResult().getResultId());
    }

    @Test
    void getLabResult() {
        LabResultRequest labResultRequest = LabResultRequest.newBuilder()
                .setLabResult(LabResult.newBuilder().setResultId("789").build())
                .build();

        LabResultResponse response = routineService.getLabResult(labResultRequest);

        Assertions.assertEquals("789", response.getLabResult().getResultId());
    }
}
