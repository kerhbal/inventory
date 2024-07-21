package domainapp.modules.simple.dom.inventory.product;

import com.fasterxml.jackson.core.JsonProcessingException;

import domainapp.modules.simple.dom.inventory.rule.Rule;
import domainapp.modules.simple.dom.inventory.rule.RuleDetail;
import domainapp.modules.simple.dom.inventory.rule.RuleDto;
import domainapp.modules.simple.dom.inventory.rule.RuleUnit;
import domainapp.modules.simple.dom.inventory.rule.RuleUnitDto;
import domainapp.modules.simple.external.Record;
import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.service.HistoryService;
import domainapp.modules.simple.service.ProductService;
import domainapp.modules.simple.service.RuleService;
import domainapp.modules.simple.util.CommonUtil;
import jakarta.inject.Named;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import lombok.Getter;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Nature;

import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Title;

import org.apache.causeway.applib.services.message.MessageService;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named("simple.ProductViewModel")
@DomainObject(nature = Nature.VIEW_MODEL)
@XmlRootElement(name = "ProductViewModel")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductViewModel {

    @Autowired
    private ProductService productService;

    @Autowired
    private RuleService ruleService;

    @Title
    @Getter
    private final String viewModelTitle = "Product List";

    @Autowired
    @XmlTransient
    private MessageService messageService;

    @Autowired
    HistoryService historyService;

    @XmlTransient
    @Collection
    public List<Product> getProductList() throws URISyntaxException {
        RecordsResponse<Record<Product>> response = productService.getAllProducts();
        return CommonUtil.getObjectFromResponse(response);
    }

    @Action
    public ProductViewModel adjustProductQuantity(String name, int adjustQuantity) throws URISyntaxException {
        Result result = getCurrentQuantityAndProductId(name);
        historyService.createHistory("adjustProductQuantity", name + " with quantity change " + adjustQuantity);

        if (adjustQuantity > 0) {
            if (result.currentQuantity() == 0) {
                productService.createProduct(name, adjustQuantity);
            } else {
                productService.updateProductCount(result.productId(),  adjustQuantity + result.currentQuantity());
            }
            return new ProductViewModel();
        }
        if (result.currentQuantity() + adjustQuantity < 0) {
            messageService.raiseError("not enough to remove!");
        } else if (result.currentQuantity() + adjustQuantity == 0){
            productService.deleteProductRow(result.productId());
        } else {
            productService.updateProductCount(result.productId(),  result.currentQuantity() + adjustQuantity);
        }
        return new ProductViewModel();
    }

    @MemberSupport
    public List<String> choices0AdjustProductQuantity() throws URISyntaxException {
        return getProductList().stream().map(Product::getName).toList();
    }

    @Action
    public ProductViewModel addNewProduct(String name, int adjustQuantity) throws URISyntaxException {
        adjustProductQuantity(name, adjustQuantity);
        return new ProductViewModel();
    }

    @Action
    public ProductViewModel applyRule(RuleDto rule) throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        RecordsResponse<Record<Product>> response = productService.getAllProducts();
        historyService.createHistory("apply rule", rule.getName());

        RuleDetail ruleDetailObj = rule.getRuleDetailObj();
        for (RuleUnit ru : ruleDetailObj.getInput()) {
            adjustProductQuantity(ru.getProductName(), -ru.getProductQuantity());
        }
        for (RuleUnit ru : ruleDetailObj.getOutput()) {
            adjustProductQuantity(ru.getProductName(), ru.getProductQuantity());
        }
        return new ProductViewModel();
    }

    @MemberSupport
    public String validateApplyRule(RuleDto rule) throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        RecordsResponse<Record<Product>> response = productService.getAllProducts();
        List<Product> products = CommonUtil.getObjectFromResponse(response);
        Map<String, Product> productMap = products.stream().collect(Collectors.toMap(Product::getName, Function.identity()));
        List<RuleUnitDto> inputList = rule.getInputList();
        for (RuleUnitDto unit : inputList) {
            Product product = productMap.get(unit.getProductName());
            if (product == null || product.quantity-unit.getProductQuantity() < 0) {
                return "Not enough " + unit.getProductName() + "!";
            }
        }
        return null;
    }

    @MemberSupport
    public List<RuleDto> choices0ApplyRule() throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        return ruleService.getRuleDtoList();
    }

    private Result getCurrentQuantityAndProductId(String name) throws URISyntaxException {
        List<Record<Product>> productRecords = productService.getAllProducts().getRecords();
        int currentQuantity = 0;
        String productId = null;
        for (Record<Product> p : productRecords) {
            if (p.getFields().getName().equals(name)) {
                currentQuantity = currentQuantity + p.getFields().getQuantity();
                productId = p.getId();
            }
        }
        return new Result(currentQuantity, productId);
    }

    private record Result(int currentQuantity, String productId) {
    }
}
