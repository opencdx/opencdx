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
package cdx.opencdx.questionnaire.model;

import cdx.opencdx.grpc.questionnaire.QuestionnaireStatus;
import cdx.opencdx.grpc.questionnaire.RuleSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents a rule set model.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("ruleset")
public class OpenCDXRuleSet {
    @Id
    private ObjectId id;

    private String type;
    private String category;
    private String description;
    private String rule;
    private QuestionnaireStatus status;

    /**
     * Instantiates a new OpenCDXRuleSet.
     *
     * @param ruleSet the rule set
     */
    public OpenCDXRuleSet(RuleSet ruleSet) {
        if (ruleSet.hasRuleId()) {
            this.id = new ObjectId(ruleSet.getRuleId());
        }
        this.type = ruleSet.getType();
        this.category = ruleSet.getCategory();
        this.description = ruleSet.getDescription();
        this.rule = ruleSet.getRule();
        if (ruleSet.hasStatus()) {
            this.status = ruleSet.getStatus();
        } else {
            this.status = QuestionnaireStatus.draft;
        }
    }

    /**
     * To rule set rule set.
     *
     * @return the rule set
     */
    public RuleSet getProtobufMessage() {
        RuleSet.Builder builder = RuleSet.newBuilder()
                .setRuleId(id.toHexString())
                .setType(type)
                .setCategory(category)
                .setStatus(this.status)
                .setDescription(description);

        if (rule != null) {
            builder.setRule(this.rule);
        }
        return builder.build();
    }
}
