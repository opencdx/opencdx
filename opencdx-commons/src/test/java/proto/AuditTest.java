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

import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.types.AgentType;
import cdx.opencdx.grpc.types.AuditEventType;
import cdx.opencdx.grpc.types.SensitivityLevel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.Timestamp;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class AuditTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testAuditEvent() throws JsonProcessingException {
        AuditEvent auditEvent = AuditEvent.newBuilder()
                .setCreated(Timestamp.newBuilder().setSeconds(1696432104))
                .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PII_ACCESSED)
                .setActor(Actor.newBuilder()
                        .setIdentity("identityT")
                        .setRole("roleT")
                        .setNetworkAddress("networkAddressT")
                        .setAgentType(AgentType.AGENT_TYPE_HUMAN_USER)
                        .build())
                .setDataObject(DataObject.newBuilder()
                        .setResource("resourceT")
                        .setData("dataT")
                        .setSensitivity(SensitivityLevel.SENSITIVITY_LEVEL_UNSPECIFIED)
                        .build())
                .setPurposeOfUse("purposeOfUseT")
                .setAuditSource(AuditSource.newBuilder()
                        .setSystemInfo("systemInfoT")
                        .setConfiguration("configurationT")
                        .build())
                .setAuditEntity(AuditEntity.newBuilder()
                        .setPatientId("patientIdentifierT")
                        .setNationalHealthId("nationalHealthId")
                        .build())
                .build();
        log.info("AuditEvent: {}", this.mapper.writeValueAsString(auditEvent));
    }

    @Test
    void testAuditStatus() throws JsonProcessingException {
        AuditStatus auditStatus = AuditStatus.newBuilder().setSuccess(true).build();
        log.info("AuditStatus: {}", this.mapper.writeValueAsString(auditStatus));
    }
}
