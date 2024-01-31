package cdx.opencdx.evrete.service;

import java.io.IOException;

import cdx.opencdx.evrete.domain.BloodPressure;

public interface OpenCDXRulesService {
	
	public BloodPressure checkBloodPressure(BloodPressure bp) throws IOException;

}
