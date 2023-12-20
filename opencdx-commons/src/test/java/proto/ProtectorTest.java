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
package proto;

import cdx.opencdx.grpc.neural.protector.*;
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
class ProtectorTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testAnomalyDetectionDataRequest() throws JsonProcessingException {
        AnomalyDetectionDataRequest item = AnomalyDetectionDataRequest.newBuilder()
                .setAnomalyDetectionData(AnomalyDetectionData.newBuilder()
                        .setEncounterId(ObjectId.get().toHexString())
                        .setUserId(ObjectId.get().toHexString())
                        .setDataAccessPattern("dataAccessPattern")
                        .setTimeStamp(Timestamp.newBuilder().setSeconds(1696435104))
                        .addAllAnomaliesDetected(List.of("anomaly1", "anomaly2"))
                        .setSourceIp("sourceIp")
                        .setLocation("location")
                        .addAllAffectedSystems(List.of("affectedSystem1", "affectedSystem2"))
                        .setSeverityLevel("severityLevel")
                        .build())
                .build();
        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testAuthorizationControlDataRequest() throws JsonProcessingException {
        AuthorizationControlDataRequest item = AuthorizationControlDataRequest.newBuilder()
                .setAuthorizationControlData(AuthorizationControlData.newBuilder()
                        .setEncounterId(ObjectId.get().toHexString())
                        .setUserId(ObjectId.get().toHexString())
                        .setDataAccessLevel("dataAccessLevel")
                        .setAccessGrantedBy(ObjectId.get().toHexString())
                        .addAllAccessScopes(List.of("accessScope1", "accessScope2"))
                        .setAccessValidityPeriod("accessValidityPeriod")
                        .setIsTemporaryAccess(true)
                        .addAllConditionalAccessParams(List.of("conditionalAccessParam1", "conditionalAccessParam2"))
                        .build())
                .build();
        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testPrivacyProtectionDataRequest() throws JsonProcessingException {
        PrivacyProtectionDataRequest item = PrivacyProtectionDataRequest.newBuilder()
                .setPrivacyProtectionData(PrivacyProtectionData.newBuilder()
                        .setEncounterId(ObjectId.get().toHexString())
                        .setDataType("dataType")
                        .setAnonymizedData("anonymizedData")
                        .setIsDataEncrypted(true)
                        .setEncryptionMethod("encryptionMethod")
                        .setDataRetentionPolicy("dataRetentionPolicy")
                        .setHasDataSharingAgreement(true)
                        .setDataPurpose("dataPurpose")
                        .build())
                .build();
        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }

    @Test
    void testRealTimeMonitoringDataRequest() throws JsonProcessingException {
        RealTimeMonitoringDataRequest item = RealTimeMonitoringDataRequest.newBuilder()
                .setRealTimeMonitoringData(RealTimeMonitoringData.newBuilder()
                        .setEncounterId(ObjectId.get().toHexString())
                        .setMonitoredEntity(ObjectId.get().toHexString())
                        .setMonitoringDetails("monitoringDetails")
                        .setMonitoringStartTime(Timestamp.newBuilder().setSeconds(1696435104))
                        .setMonitoringEndTime(Timestamp.newBuilder().setSeconds(1696435104))
                        .addAllTriggeredAlerts(List.of("triggeredAlert1", "triggeredAlert2"))
                        .setMonitoringFrequency("monitoringFrequency")
                        .setIsContinuousMonitoring(true)
                        .setResponsibleMonitoringTeam("responsibleMonitoringTeam")
                        .build())
                .build();
        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }
    @Test
    void testUserBehaviorAnalysisDataRequest() throws JsonProcessingException {
        UserBehaviorAnalysisDataRequest item = UserBehaviorAnalysisDataRequest.newBuilder()
                .setUserBehaviorAnalysisData(UserBehaviorAnalysisData.newBuilder()
                        .setEncounterId(ObjectId.get().toHexString())
                        .setUserId(ObjectId.get().toHexString())
                        .setBehaviorPattern("behaviorPattern")
                        .addAllAssociatedActivities(List.of("associatedActivity1", "associatedActivity2"))
                        .setAnalysisTimeFrame("analysisTimeFrame")
                        .addAllHistoricalBehaviorData(List.of("historicalBehaviorData1", "historicalBehaviorData2"))
                        .setRiskAssessment("riskAssessment")
                        .setIsBehaviorOutlier(true)
                        .setBehaviorConsequence("behaviorConsequence")
                        .build())
                .build();
        log.info("Item: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
    }
}
