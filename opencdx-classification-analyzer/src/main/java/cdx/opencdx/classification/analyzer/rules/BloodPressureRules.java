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