/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package proto; /*
                * Copyright 2023 Safe Health Systems, Inc.
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

import cdx.open_connected_test.v2alpha.*;
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
class ConnectTestTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testConnectedTest() throws JsonProcessingException {
        ConnectedTest connectedTest = ConnectedTest.newBuilder()
                .setBasicInfo(BasicInfo.newBuilder()
                        .setId("id")
                        .setCreated(Timestamp.newBuilder().setSeconds(1696432104))
                        .setCreator("creator")
                        .setModified(Timestamp.newBuilder().setSeconds(1696435104))
                        .setModifier("modifier")
                        .setVendorLabTestId("vendorLabTestId")
                        .setType("type")
                        .setUserId("userId")
                        .setNationalHealthId(1)
                        .setHealthServiceId("hea;thServiceId")
                        .setTenantId("tenantId")
                        .setSource("source")
                        .build())
                .setOrderInfo(OrderInfo.newBuilder()
                        .setOrderId("orderId")
                        .setStatus("statusId")
                        .setStatusMessage("statusMessage")
                        .addAllStatusMessageActions(List.of(StatusMessageAction.newBuilder()
                                .setId("id")
                                .setDescription("description")
                                .setValue("value")
                                .buildPartial()))
                        .setEncounterId("encounterId")
                        .setIsSyncedWithEHR(true)
                        .setResult("result")
                        .setQuestionnaireId("questionnaireId")
                        .build())
                .setTestNotes(TestNotes.newBuilder()
                        .setNotes("notes")
                        .setMedicationNotes("medicationNotes")
                        .setReferralNotes("referralNotes")
                        .setDiagnosticNotes("diagnosticNotes")
                        .buildPartial())
                .setPaymentDetails(PaymentDetails.newBuilder()
                        .setPaymentMode("paymentMode")
                        .setInsuranceInfoId("insuranceInfoId")
                        .setPaymentTransactionId("paymentTransactionId")
                        .setPaymentDetails("paymentDetails")
                        .build())
                .setProviderInfo(ProviderInfo.newBuilder()
                        .setOrderingProviderId("orderingProviderId")
                        .setAssignedProviderID(1)
                        .setOrderingProviderNPI(1)
                        .build())
                .setTestDetails(TestDetails.newBuilder()
                        .setMetadata(Metadata.newBuilder()
                                .setQrData("qrdata")
                                .setKitUploadID("kitUploadID")
                                .setResponseMessage("responseMessage")
                                .setResponseTitle("responseTitle")
                                .setResponseCode(200)
                                .setImageType("imageType")
                                .setType("type")
                                .setManufacturer("manufacturer")
                                .setCasseteLotNumber("casseteLotNumber")
                                .setOutcomeIgM(true)
                                .setOutcomeIgG(true)
                                .setOutcome("outcome")
                                .setSelfAssessmentOutcomeIgM(true)
                                .setSelfAssessmentOutcomeIgG(true)
                                .setSelfAssessmentOutcome("selfAssessmentOutcome")
                                .setCasseteExpirationDate("expDate")
                                .setLabTestOrderableId("labTestOrderableId")
                                .setSkuId("skuID")
                                .setCassetteVerification("verification")
                                .build())
                        .setRequisitionBase64("requisitionBase64")
                        .setInternalTestId("internalTestId")
                        .setMedications("medications")
                        .setReferrals("referrals")
                        .setDiagnostics("diagnostics")
                        .addAllOrderableTestIds(List.of("orderableTestIds"))
                        .addAllOrderableTests(List.of(OrderableTest.newBuilder()
                                .setOrderableTestId("orderableTestId")
                                .build()))
                        .addAllOrderableTestResults(List.of(OrderableTestResult.newBuilder()
                                .setOrderableTestId("orderableTestId")
                                .setCollectionDate("collectionDate")
                                .setTestResult("testResult")
                                .setOutcome("outcome")
                                .setResponseMessage("responseMessage")
                                .setResponseTitle("responseTitle")
                                .setResponseCode(200)
                                .build()))
                        .setTestClassification("testClassification")
                        .setIsOnsiteTest(true)
                        .setSpecimenId("specimenId")
                        .setLabVendorConfirmationId("labVendorConfirmationId")
                        .setDeviceIdentifier(2)
                        .setDeviceSerialNumber("deviceSerialNumber")
                        .setIsAutoGenerated(true)
                        .setReportingFlag("reportingFlag")
                        .setNotificationFlag("notificationFlag")
                        .setOrderStatusFlag("orderStatusFlag")
                        .setOrderReceiptPath("orderReceiptPath")
                        .setLabTestType("labTestType")
                        .setSpecimanType("specimanType")
                        .setMedicalCode("medicalCode")
                        .build())
                .build();

        log.info("ConnectedTest: {}", this.mapper.writeValueAsString(connectedTest));
    }

    @Test
    void testConnectedTestListRequest() throws JsonProcessingException {
        ConnectedTestListRequest connectedTestListRequest = ConnectedTestListRequest.newBuilder()
                .setPageSize(1)
                .setPageNumber(2)
                .setSortAscending(true)
                .setUserId("userId")
                .build();
        log.info("ConnectedTestListRequest: {}", this.mapper.writeValueAsString(connectedTestListRequest));
    }
}