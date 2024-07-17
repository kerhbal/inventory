package domainapp.modules.simple.dom.inventory.product;

import domainapp.modules.simple.dom.inventory.ObjectParent;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.Title;

@Getter @Setter
@NoArgsConstructor
@Named("simple.Product")
@DomainObject(nature = Nature.VIEW_MODEL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        propOrder = {
                "name",
                "quantity"
        }
)
public class Product extends ObjectParent {
    @Title@Property
    String name;

    @Property
    int quantity;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}


