package domainapp.modules.simple.dom.inventory.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleUnit {
    String productName;
    int productQuantity;

    @Override
    public String toString() {
        return productQuantity + " pieces of " + productName;
    }
}
