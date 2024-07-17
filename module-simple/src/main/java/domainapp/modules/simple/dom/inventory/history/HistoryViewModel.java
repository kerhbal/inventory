package domainapp.modules.simple.dom.inventory.history;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import domainapp.modules.simple.service.HistoryService;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Title;
import org.apache.causeway.applib.services.message.MessageService;

import lombok.Getter;

import domainapp.modules.simple.dom.inventory.product.Product;
import domainapp.modules.simple.dom.inventory.rule.RuleDetail;
import domainapp.modules.simple.dom.inventory.rule.RuleDto;
import domainapp.modules.simple.dom.inventory.rule.RuleUnit;
import domainapp.modules.simple.external.Record;
import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.service.ProductService;
import domainapp.modules.simple.service.RuleService;
import domainapp.modules.simple.util.CommonUtil;
import jakarta.inject.Named;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@Named("simple.HistoryViewModel")
@DomainObject(nature = Nature.VIEW_MODEL)
@XmlRootElement(name = "HistoryViewModel")
@XmlAccessorType(XmlAccessType.FIELD)
public class HistoryViewModel {

    @Autowired
    private HistoryService historyService;

    @Title
    @Getter
    private final String viewModelTitle = "History List";

    @Autowired
    @XmlTransient
    private MessageService messageService;

    @XmlTransient
    @Collection
    public List<History> getHistoryList() throws URISyntaxException {
        RecordsResponse<Record<History>> response = historyService.getAllHistories();
        return CommonUtil.getObjectFromResponse(response);
    }
}
