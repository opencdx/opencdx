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
package proto;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.service.logistics.DeliveryTrackingRequest;
import cdx.opencdx.grpc.types.ClinicalProtocolStatus;
import cdx.opencdx.grpc.types.RoutineStatus;
import cdx.opencdx.grpc.types.ShippingStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.Timestamp;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class RoutineTest {

    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testRoutineRequest() throws JsonProcessingException {
        RoutineRequest item = RoutineRequest.newBuilder()
                .setRoutine(Routine.newBuilder()
                        .setRoutineId(OpenCDXIdentifier.get().toString())
                        .setName("Test Routine")
                        .setDescription("Test Routine Description")
                        .setStatus(RoutineStatus.ROUTINE_COMPLETED)
                        .setCreationDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setLastUpdatedDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setAssignedUser(OpenCDXIdentifier.get().toHexString())
                        .addAllAssociatedProtocols(
                                List.of(OpenCDXIdentifier.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testDeliveryTrackingRequest() throws JsonProcessingException {
        DeliveryTrackingRequest item = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(DeliveryTracking.newBuilder()
                        .setTrackingId(OpenCDXIdentifier.get().toHexString())
                        .setOrderId(OpenCDXIdentifier.get().toHexString())
                        .setStatus(ShippingStatus.DELAYED)
                        .setStartDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setEndDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setCurrentLocation("Current Location")
                        .setRecipient(OpenCDXIdentifier.get().toHexString())
                        .addAllDeliveryItems(List.of(OpenCDXIdentifier.get().toHexString()))
                        .setAssignedCourier(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testClinicalProtocolExecution() throws JsonProcessingException {
        ClinicalProtocolExecutionRequest item = ClinicalProtocolExecutionRequest.newBuilder()
                .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                        .setExecutionId(OpenCDXIdentifier.get().toHexString())
                        .setRoutineId(OpenCDXIdentifier.get().toHexString())
                        .setProtocolId(OpenCDXIdentifier.get().toHexString())
                        .setStatus(ClinicalProtocolStatus.CLINICAL_PROTOCOL_COMPLETED)
                        .setStartDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setEndDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setResults("ClinicalProtocolExecution Results")
                        .setAssignedMedicalStaff(OpenCDXIdentifier.get().toHexString())
                        .addAllSteps(List.of("Step 1", "Step 2", "Step 3"))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testLabOrderRequest() throws JsonProcessingException {
        LabOrderRequest item = LabOrderRequest.newBuilder()
                .setLabOrder(LabOrder.newBuilder()
                        .setLabOrderId(OpenCDXIdentifier.get().toHexString())
                        .setTestName("Test Name")
                        .setOrderDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setMatchedValueSet("Matched Value Set")
                        .addAllRelatedEntities(List.of(
                                OpenCDXIdentifier.get().toHexString(),
                                OpenCDXIdentifier.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testDiagnosisRequest() throws JsonProcessingException {
        DiagnosisRequest item = DiagnosisRequest.newBuilder()
                .setDiagnosis(Diagnosis.newBuilder()
                        .setDiagnosisId(OpenCDXIdentifier.get().toHexString())
                        .setDiagnosisCode("Diagnosis Code")
                        .setDiagnosisDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setMatchedValueSet("Matched Value Set")
                        .addAllRelatedEntities(List.of(
                                OpenCDXIdentifier.get().toHexString(),
                                OpenCDXIdentifier.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testSuspectedDiagnosisRequest() throws JsonProcessingException {
        SuspectedDiagnosisRequest item = SuspectedDiagnosisRequest.newBuilder()
                .setSuspectedDiagnosis(SuspectedDiagnosis.newBuilder()
                        .setSuspectedDiagnosisId(OpenCDXIdentifier.get().toHexString())
                        .setDiagnosisCode("Diagnosis Code")
                        .setDiagnosisDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setMatchedValueSet("Matched Value Set")
                        .addAllRelatedEntities(List.of(
                                OpenCDXIdentifier.get().toHexString(),
                                OpenCDXIdentifier.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testLabResultRequest() throws JsonProcessingException {
        LabResultRequest item = LabResultRequest.newBuilder()
                .setLabResult(LabResult.newBuilder()
                        .setResultId(OpenCDXIdentifier.get().toHexString())
                        .setResultValue("Result Value")
                        .setResultDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setMatchedValueSet("Matched Value Set")
                        .addAllRelatedEntities(List.of(
                                OpenCDXIdentifier.get().toHexString(),
                                OpenCDXIdentifier.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }
}
