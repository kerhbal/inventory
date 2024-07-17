package domainapp.modules.simple.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import domainapp.modules.simple.dom.inventory.rule.Rule;
import domainapp.modules.simple.dom.inventory.rule.RuleDto;
import domainapp.modules.simple.dom.inventory.rule.RuleToCreate;
import domainapp.modules.simple.dom.inventory.rule.RuleToUpdate;
import domainapp.modules.simple.external.Record;
import domainapp.modules.simple.external.RecordToCreate;
import domainapp.modules.simple.external.RecordsCreateRequest;
import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RuleService {

    @Autowired
    APIAccessService apiAccessService;

    @Value("${airtable.products.path}")
    private String airtableProductsPath;

    @Value("${airtable.rules.path}")
    private String airtableRulesPath;

    public Rule getRuleById(String id) throws URISyntaxException {
        Record<Rule> record = apiAccessService.getSingleRecord(airtableRulesPath + "/" + id, new ParameterizedTypeReference<>() {
        });
        return CommonUtil.getObjectFromSingleResponse(record);
    }

    public RecordsResponse<Record<Rule>> getAllRules() throws URISyntaxException {
        return apiAccessService.getRecords(airtableRulesPath, new ParameterizedTypeReference<>() {
        });
    }

    public void createRule(String ruleName) throws URISyntaxException {
        RecordsCreateRequest<RecordToCreate> ruleRecordRecords = new RecordsCreateRequest<>();
        ruleRecordRecords.setRecords(List.of(new RecordToCreate(new RuleToCreate(ruleName))));
        apiAccessService.createRecord(ruleRecordRecords, airtableRulesPath, new ParameterizedTypeReference<>() {
        });
    }

    public void updateRuleDetail(String ruleId, String ruleDetail) throws URISyntaxException {
        RecordToCreate recordToUpdate = new RecordToCreate();
        recordToUpdate.setFields(new RuleToUpdate(ruleDetail));
        apiAccessService.updateRecord(recordToUpdate, airtableRulesPath + "/" + ruleId, new ParameterizedTypeReference<>() {
        });
    }

    public List<RuleDto> getRuleDtoList() throws UnsupportedEncodingException, JsonProcessingException, URISyntaxException {
        RecordsResponse<Record<Rule>> response = getAllRules();
        List<RuleDto> list = new ArrayList<>();
        for (Rule rule : CommonUtil.getObjectFromResponse(response)) {
            RuleDto ruleDto = new RuleDto(rule);
            list.add(ruleDto);
        }
        return list;
    }

}
