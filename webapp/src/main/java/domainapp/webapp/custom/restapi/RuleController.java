package domainapp.webapp.custom.restapi;

import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.dom.inventory.rule.Rule;
import domainapp.modules.simple.service.APIAccessService;
import domainapp.modules.simple.service.RuleService;
import domainapp.modules.simple.util.CommonUtil;
import domainapp.modules.simple.external.Record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RuleController {

	@Autowired
    RuleService ruleService;

	@GetMapping("/rules")
	public List<Rule> showAllRules() throws URISyntaxException {
		RecordsResponse<Record<Rule>> response = ruleService.getAllRules();
		return CommonUtil.getObjectFromResponse(response);

	}
}
