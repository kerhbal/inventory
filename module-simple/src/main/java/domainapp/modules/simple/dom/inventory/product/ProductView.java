package domainapp.modules.simple.dom.inventory.product;

import domainapp.modules.simple.dom.so.SimpleObject;
import domainapp.modules.simple.types.Name;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import lombok.RequiredArgsConstructor;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.SemanticsOf;

@Named("simple.ProductView")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class ProductView {

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public ProductViewModel listAll() {
        return new ProductViewModel();
    }
}
