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
package cdx.opencdx.commons.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.Questionnaire;
import cdx.opencdx.grpc.data.QuestionnaireItem;
import cdx.opencdx.grpc.types.QuestionnaireStatus;
import java.util.List;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXQuestionnaireModelTest {

    @Test
    void test() {
        Questionnaire questionnaire = Questionnaire.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setResourceType("resourceType")
                .setTitle("title")
                .setStatus(QuestionnaireStatus.active)
                .setDescription("description")
                .addAllItem(List.of(QuestionnaireItem.getDefaultInstance()))
                .setRuleId("ruleId")
                .addAllRuleQuestionId(List.of("ruleQuestionId"))
                .build();
        OpenCDXQuestionnaireModel model = new OpenCDXQuestionnaireModel(questionnaire);
        Assertions.assertNotNull(model.getProtobufMessage());
    }
}
