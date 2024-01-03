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

import cdx.opencdx.grpc.routine.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.Timestamp;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
                        .setRoutineId(ObjectId.get().toString())
                        .setName("Test Routine")
                        .setDescription("Test Routine Description")
                        .setStatus(Routine.Status.COMPLETED)
                        .setCreationDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setLastUpdatedDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setAssignedUser(ObjectId.get().toHexString())
                        .addAllAssociatedProtocols(List.of(ObjectId.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testDeliveryTrackingRequest() throws JsonProcessingException {
        DeliveryTrackingRequest item = DeliveryTrackingRequest.newBuilder()
                .setDeliveryTracking(DeliveryTracking.newBuilder()
                        .setDeliveryId(ObjectId.get().toHexString())
                        .setOrderId(ObjectId.get().toHexString())
                        .setStatus(DeliveryTracking.Status.DELAYED)
                        .setStartDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setEndDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setCurrentLocation("Current Location")
                        .setRecipient(ObjectId.get().toHexString())
                        .addAllDeliveryItems(List.of(ObjectId.get().toHexString()))
                        .setAssignedCourier(ObjectId.get().toHexString())
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testClinicalProtocolExecution() throws JsonProcessingException {
        ClinicalProtocolExecutionRequest item = ClinicalProtocolExecutionRequest.newBuilder()
                .setClinicalProtocolExecution(ClinicalProtocolExecution.newBuilder()
                        .setExecutionId(ObjectId.get().toHexString())
                        .setRoutineId(ObjectId.get().toHexString())
                        .setProtocolId(ObjectId.get().toHexString())
                        .setStatus(ClinicalProtocolExecution.Status.COMPLETED)
                        .setStartDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setEndDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setResults("ClinicalProtocolExecution Results")
                        .setAssignedMedicalStaff(ObjectId.get().toHexString())
                        .addAllSteps(List.of("Step 1", "Step 2", "Step 3"))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testLabOrderRequest() throws JsonProcessingException {
        LabOrderRequest item = LabOrderRequest.newBuilder()
                .setLabOrder(LabOrder.newBuilder()
                        .setLabOrderId(ObjectId.get().toHexString())
                        .setTestName("Test Name")
                        .setOrderDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setMatchedValueSet("Matched Value Set")
                        .addAllRelatedEntities(List.of(
                                ObjectId.get().toHexString(), ObjectId.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testDiagnosisRequest() throws JsonProcessingException {
        DiagnosisRequest item = DiagnosisRequest.newBuilder()
                .setDiagnosis(Diagnosis.newBuilder()
                        .setDiagnosisId(ObjectId.get().toHexString())
                        .setDiagnosisCode("Diagnosis Code")
                        .setDiagnosisDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setMatchedValueSet("Matched Value Set")
                        .addAllRelatedEntities(List.of(
                                ObjectId.get().toHexString(), ObjectId.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testSuspectedDiagnosisRequest() throws JsonProcessingException {
        SuspectedDiagnosisRequest item = SuspectedDiagnosisRequest.newBuilder()
                .setSuspectedDiagnosis(SuspectedDiagnosis.newBuilder()
                        .setSuspectedDiagnosisId(ObjectId.get().toHexString())
                        .setDiagnosisCode("Diagnosis Code")
                        .setDiagnosisDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setMatchedValueSet("Matched Value Set")
                        .addAllRelatedEntities(List.of(
                                ObjectId.get().toHexString(), ObjectId.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testLabResultRequest() throws JsonProcessingException {
        LabResultRequest item = LabResultRequest.newBuilder()
                .setLabResult(LabResult.newBuilder()
                        .setResultId(ObjectId.get().toHexString())
                        .setResultValue("Result Value")
                        .setResultDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setMatchedValueSet("Matched Value Set")
                        .addAllRelatedEntities(List.of(
                                ObjectId.get().toHexString(), ObjectId.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testMedicationRequest() throws JsonProcessingException {
        MedicationRequest item = MedicationRequest.newBuilder()
                .setMedication(Medication.newBuilder()
                        .setMedicationId(ObjectId.get().toHexString())
                        .setMedicationName("Medication name")
                        .setAdministrationDatetime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setMatchedValueSet("Matched Value Set")
                        .addAllRelatedEntities(List.of(
                                ObjectId.get().toHexString(), ObjectId.get().toHexString()))
                        .build())
                .build();

        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }
}
