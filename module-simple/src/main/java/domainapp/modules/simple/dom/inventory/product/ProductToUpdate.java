package domainapp.modules.simple.dom.inventory.product;

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

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Named("simple.ProductToUpdate")
@DomainObject(nature = Nature.VIEW_MODEL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        propOrder = {
                "quantity"
        }
)
public class ProductToUpdate {
    int quantity;
}


