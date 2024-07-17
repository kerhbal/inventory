package domainapp.modules.simple.dom.inventory.rule;

import jakarta.inject.Named;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import jakarta.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Named("simple.Rule")
@DomainObject(nature = Nature.VIEW_MODEL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        propOrder = {
                "productName",
                "productQuantity"
        }
)
public class RuleUnitDto {
    @Property
    String productName;
    @Property
    int productQuantity;

    @Override
    public String toString() {
        return productQuantity + " pieces of " + productName;
    }

    public RuleUnitDto(RuleUnit ruleUnit) {
        this.productName = ruleUnit.getProductName();
        this.productQuantity = ruleUnit.getProductQuantity();
    }
}
