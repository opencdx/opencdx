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
package cdx.opencdx.classification.analyzer.rules;

import cdx.opencdx.classification.analyzer.dto.RuleResult;
import org.evrete.dsl.annotation.Fact;
import org.evrete.dsl.annotation.Rule;
import org.evrete.dsl.annotation.Where;

public class BloodPressureRules {

    @Rule
    @Where("$s < 120")
    public void normalBloodPressure(@Fact("$s") int systolic, RuleResult ruleResult) {
        ruleResult.setFurtherActions("Normal blood pressure. No further actions needed.");
    }

    @Rule
    @Where("$s >= 120 && $s <= 129")
    public void elevatedBloodPressure(@Fact("$s") int systolic, RuleResult ruleResult) {
        ruleResult.setFurtherActions("Elevated blood pressure. Please continue monitoring.");
    }

    @Rule
    @Where("$s > 129")
    public void highBloodPressure(@Fact("$s") int systolic, RuleResult ruleResult) {
        ruleResult.setFurtherActions("High blood pressure. Please seek additional assistance.");
    }
}
