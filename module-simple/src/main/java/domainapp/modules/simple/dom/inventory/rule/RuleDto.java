package domainapp.modules.simple.dom.inventory.rule;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;

import domainapp.modules.simple.dom.inventory.product.Product;
import domainapp.modules.simple.external.Record;
import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.service.APIAccessService;
import domainapp.modules.simple.service.HistoryService;
import domainapp.modules.simple.service.ProductService;
import domainapp.modules.simple.service.RuleService;
import domainapp.modules.simple.util.CommonUtil;
import jakarta.inject.Named;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.causeway.applib.annotation.*;

import org.apache.causeway.applib.services.message.MessageService;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Named("simple.RuleDto")
@DomainObject(nature = Nature.VIEW_MODEL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        propOrder = {
                "name",
                "id",
                "ruleDesc"
        }
)
public class RuleDto {
    @Title @Property
    String name;

    @Property
    String id;

    @Property
    String ruleDesc;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    @XmlTransient
    private MessageService messageService;

    @XmlTransient
    @Collection
    public List<RuleUnitDto> getInputList() throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        Rule rule = ruleService.getRuleById(id);
        RuleDetail ruleDetail = rule.getRuleDetailObj(rule.getRuleDetail());
        if (ruleDetail == null) {
            return new ArrayList<>();
        }
        return ruleDetail.getInput().stream().map(RuleUnitDto::new).toList();
    }

    @XmlTransient
    @Collection
    public List<RuleUnitDto> getOutputList() throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        Rule rule = ruleService.getRuleById(id);
        RuleDetail ruleDetail = rule.getRuleDetailObj(rule.getRuleDetail());
        if (ruleDetail == null) {
            return new ArrayList<>();
        }
        return ruleDetail.getOutput().stream().map(RuleUnitDto::new).toList();
    }

    @Action
    public RuleDto adjustInputUnit(String name, int quantity) throws URISyntaxException, JsonProcessingException, UnsupportedEncodingException {
        RuleDetail ruleDetailObj = getRuleDetailObj();
        adjustRuleUnitList(ruleDetailObj.getInput(), name, quantity);
        historyService.createHistory("adjust input unit", name + " with quantity change " + quantity);

        return updateRuleDetail(ruleDetailObj);
    }

    public List<String> choices0AdjustInputUnit() throws URISyntaxException {
        return productService.getAllProductNames();
    }

    @Action
    public RuleDto adjustOutputUnit(String name, int quantity) throws URISyntaxException, JsonProcessingException, UnsupportedEncodingException {
        RuleDetail ruleDetailObj = getRuleDetailObj();
        adjustRuleUnitList(ruleDetailObj.getOutput(), name, quantity);
        historyService.createHistory("adjust output unit", name + " with quantity change " + quantity);

        return updateRuleDetail(ruleDetailObj);
    }

    public List<String> choices0AdjustOutputUnit() throws URISyntaxException {
        return productService.getAllProductNames();
    }

    private RuleDto updateRuleDetail(RuleDetail ruleDetailObj) throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(ruleDetailObj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ruleService.updateRuleDetail(id, URLEncoder.encode(json, "UTF-8"));
        return new RuleDto(ruleService.getRuleById(id));
    }

    public RuleDetail getRuleDetailObj() throws URISyntaxException, JsonProcessingException, UnsupportedEncodingException {
        Rule rule = ruleService.getRuleById(id);
        RuleDetail ruleDetailObjFromCurrentRule = rule.getRuleDetailObj(rule.getRuleDetail());
        return ruleDetailObjFromCurrentRule == null ? RuleDetail.getEmptyDetailObj() : ruleDetailObjFromCurrentRule;
    }

    private void adjustRuleUnitList(List<RuleUnit> ruleUnitList, String name, int quantity) throws UnsupportedEncodingException, URISyntaxException, JsonProcessingException {
        boolean foundExistingUnitWithSameProduct = false;
        for (RuleUnit ru : ruleUnitList) {
            if (ru.getProductName().equals(name)) {
                int currentQuantity = ru.getProductQuantity();
                if (quantity < 0 && currentQuantity + quantity < 0) {
                    messageService.raiseError("Not enough to remove");
                } else if (quantity < 0 && currentQuantity + quantity == 0) {
                    ruleUnitList.remove(ru);
                } else {
                    ru.setProductQuantity(currentQuantity + quantity);
                }
                foundExistingUnitWithSameProduct = true;
                break;
            }
        }
        if (!foundExistingUnitWithSameProduct && quantity > 0) {
            ruleUnitList.add(new RuleUnit(name, quantity));
        }
    }

    public RuleDto(Rule rule) throws UnsupportedEncodingException, JsonProcessingException {
        this.name = rule.getName();
        this.id = rule.getId();
        this.ruleDesc = rule.getRuleDetail() == null ? null : rule.getRuleDetailObj(rule.getRuleDetail()).toString();
    }

    private record Result(int currentQuantity, String productId) {
    }
}
