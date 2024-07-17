package domainapp.modules.simple.dom.inventory.rule;

import domainapp.modules.simple.dom.inventory.product.ProductViewModel;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import lombok.RequiredArgsConstructor;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.SemanticsOf;

@Named("simple.RuleView")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class RuleView {

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public RuleViewModel listAll() {
        return new RuleViewModel();
    }
}
