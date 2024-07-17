package domainapp.modules.simple.dom.inventory.history;

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
public class HistoryToCreate {
    String uuid;

    String user;

    String action;

    String detail;
}
