package domainapp.modules.simple.dom.inventory.history;

import domainapp.modules.simple.dom.inventory.product.ProductViewModel;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import lombok.RequiredArgsConstructor;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.SemanticsOf;

@Named("simple.HistoryView")
@DomainService
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class HistoryView {

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public HistoryViewModel listAll() {
        return new HistoryViewModel();
    }
}
