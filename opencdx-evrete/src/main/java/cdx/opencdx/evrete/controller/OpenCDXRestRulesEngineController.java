package cdx.opencdx.evrete.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cdx.opencdx.evrete.domain.BloodPressure;
import cdx.opencdx.evrete.service.OpenCDXRulesService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class OpenCDXRestRulesEngineController {
	
	@Autowired
	private OpenCDXRulesService openCDXRulesService;
	
	@PostMapping
	public BloodPressure checkBloodPressure(@RequestBody BloodPressure bp) throws IOException {
		return openCDXRulesService.checkBloodPressure(bp);
	}

}
