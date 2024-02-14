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

import cdx.opencdx.grpc.questionnaire.ClientRulesRequest;
import cdx.opencdx.grpc.questionnaire.QuestionnaireRequest;
import cdx.opencdx.grpc.questionnaire.UserQuestionnaireData;
import cdx.opencdx.grpc.questionnaire.UserQuestionnaireDataRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class QuestionnaireTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testCreateRuleSetRequest() throws JsonProcessingException {
        cdx.opencdx.grpc.questionnaire.CreateRuleSetRequest request =
                cdx.opencdx.grpc.questionnaire.CreateRuleSetRequest.newBuilder()
                        .setRuleSet(cdx.opencdx.grpc.questionnaire.RuleSet.newBuilder()
                                .setRuleId(ObjectId.get().toHexString())
                                .setType("User Rule")
                                .setCategory("Process user response")
                                .setDescription("Categorize blood pressure")
                                .setRule(
                                        """
                                if (bloodPressure > 140) {
                                    return "High Blood Pressure";
                                } else if (bloodPressure < 90) {
                                    return "Low Blood Pressure";
                                } else {
                                    return "Normal Blood Pressure";
                                }
                                """)
                                .setStatus(cdx.opencdx.grpc.questionnaire.QuestionnaireStatus.active)
                                .build())
                        .build();

        log.info(
                "CreateRuleSetRequest: {}",
                this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
    }

    @Test
    void testClientRulesRequest() throws JsonProcessingException {
        ClientRulesRequest request = ClientRulesRequest.newBuilder()
                .setOrganizationId(ObjectId.get().toHexString())
                .setWorkspaceId(ObjectId.get().toHexString())
                .build();

        log.info(
                "ClientRulesRequest: {}",
                this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
    }

    @Test
    void testQuestionnaireRequest() throws JsonProcessingException {
        QuestionnaireRequest request = QuestionnaireRequest.newBuilder()
                .setQuestionnaire(cdx.opencdx.grpc.questionnaire.Questionnaire.newBuilder()
                        .setResourceType("form")
                        .setTitle("Questionnaire"))
                .build();

        log.info(
                "QuestionnaireRequest: {}",
                this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
    }

    @Test
    void testUserQuestionnaireDataRequest() throws JsonProcessingException {
        cdx.opencdx.grpc.questionnaire.UserQuestionnaireDataRequest request = UserQuestionnaireDataRequest.newBuilder()
                .setUserQuestionnaireData(UserQuestionnaireData.newBuilder().build())
                .build();

        log.info(
                "UserQuestionnaireDataRequest: {}",
                this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
    }
}
