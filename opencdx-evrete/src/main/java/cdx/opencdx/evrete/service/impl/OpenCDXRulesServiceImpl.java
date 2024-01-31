package cdx.opencdx.evrete.service.impl;

import java.io.IOException;

import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;
import org.evrete.dsl.annotation.Fact;
import org.evrete.dsl.annotation.Rule;
import org.evrete.dsl.annotation.Where;
import org.springframework.stereotype.Service;

import cdx.opencdx.evrete.domain.BloodPressure;
import cdx.opencdx.evrete.service.OpenCDXRulesService;

@Service
public class OpenCDXRulesServiceImpl implements OpenCDXRulesService {

	@Override
	public BloodPressure checkBloodPressure(BloodPressure bp) throws IOException {
		KnowledgeService service = new KnowledgeService();
		Knowledge knowledge = service
				.newKnowledge("JAVA-CLASS", OpenCDXRulesServiceImpl.RuleSet.class);
		
		knowledge.newStatelessSession().insertAndFire(bp);
		
		return bp;
	}
	
	public static class RuleSet {
		
		@Rule
		@Where("$p.systolic < 120")
		public void normalBloodPressure(@Fact("$p") BloodPressure bp) {
			bp.setStatus("Normal");
		}

		@Rule
		@Where("$p.systolic >= 120 && $p.systolic <= 129")
		public void elevatedBloodPressure(@Fact("$p") BloodPressure bp) {
			bp.setStatus("Elevated");
		}
		
		@Rule
		@Where("$p.systolic > 129")
		public void highBloodPressure(@Fact("$p") BloodPressure bp) {
			bp.setStatus("High");
		}
	}

}
