package domainapp.modules.simple.dom.inventory.rule;

import com.fasterxml.jackson.core.JsonProcessingException;

import domainapp.modules.simple.dom.inventory.history.History;
import domainapp.modules.simple.dom.inventory.product.Product;
import domainapp.modules.simple.dom.inventory.product.ProductViewModel;
import domainapp.modules.simple.external.Record;
import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.service.APIAccessService;
import domainapp.modules.simple.service.HistoryService;
import domainapp.modules.simple.service.RuleService;
import domainapp.modules.simple.util.CommonUtil;
import jakarta.inject.Named;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import lombok.Getter;

import lombok.SneakyThrows;

import org.apache.causeway.applib.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Named("simple.RuleViewModel")
@DomainObject(nature = Nature.VIEW_MODEL)
@XmlRootElement(name = "RuleViewModel")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleViewModel {

    @Autowired
    private APIAccessService apiAccessService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private HistoryService historyService;

    @Title
    @Getter
    private final String viewModelTitle = "Rule List";

    @XmlTransient
    @Collection
    public List<RuleDto> getRuleList() throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        return ruleService.getRuleDtoList();
    }

    @Action
    public RuleViewModel addRule(String name) throws URISyntaxException {
        ruleService.createRule(name);
        historyService.createHistory("add rule", name);

        return new RuleViewModel();
    }

}
