package domainapp.modules.simple.dom.inventory.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.Title;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductToCreate {
    String name;
    int quantity;
}
