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
package cdx.opencdx.questionnaire.changelog;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.questionnaire.QuestionnaireStatus;
import cdx.opencdx.questionnaire.model.OpenCDXRuleSet;
import cdx.opencdx.questionnaire.repository.OpenCDXRuleSetRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import org.bson.types.ObjectId;

/**
 * Change set to load up default templates used by OpenCDX.
 */
@ChangeLog(order = "001")
@ExcludeFromJacocoGeneratedReport
public class QuestionnaireChangeSet {

    private static final String SYSTEM = "SYSTEM";

    /**
     * Default Consructor
     */
    public QuestionnaireChangeSet() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Blood Pressure Change Set.
     * @param openCDXRuleSetRepository Rule Set Repository.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "001", id = "Categorize blood pressure", author = "Jeff Miller")
    public void categorizeBloodPressure(
            OpenCDXRuleSetRepository openCDXRuleSetRepository, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);

        openCDXRuleSetRepository.save(OpenCDXRuleSet.builder()
                .id(new ObjectId("60f1e6b1f075a367a94d3760"))
                .category("Process user response")
                .type("User Rule")
                .description("Categorize blood pressure")
                .status(QuestionnaireStatus.active)
                .rule(
                        """
package cdx.opencdx.classification.service;

import cdx.opencdx.classification.model.RuleResult;
import org.evrete.dsl.annotation.Fact;
import org.evrete.dsl.annotation.Rule;
import org.evrete.dsl.annotation.Where;

public class BloodPressureRules {

    @Rule
    @Where("$s < 120")
    public void normalBloodPressure(@Fact("$s") int systolic, RuleResult ruleResult) {
        ruleResult.setResult("Normal blood pressure. No further actions needed.");
    }
    @Rule
    @Where("$s >= 120 && $s <= 129")
    public void elevatedBloodPressure(@Fact("$s") int systolic, RuleResult ruleResult) {
        ruleResult.setResult("Elevated blood pressure. Please continue monitoring.");
    }
    @Rule
    @Where("$s > 129")
    public void highBloodPressure(@Fact("$s") int systolic, RuleResult ruleResult) {
        ruleResult.setResult("High blood pressure. Please seek additional assistance.");
    }
}
""")
                .build());
    }

    /**
     * Create an index based on the id
     * @param mongockTemplate MongockTemplate to modify MongoDB.
     * @param openCDXCurrentUser Current User to use for authentication.
     */
    @ChangeSet(order = "001", id = "Setup Questionnaire Index", author = "Gaurav Mishra")
    public void setupQuestionnaireIndex(MongockTemplate mongockTemplate, OpenCDXCurrentUser openCDXCurrentUser) {
        openCDXCurrentUser.configureAuthentication(SYSTEM);
    }
}
