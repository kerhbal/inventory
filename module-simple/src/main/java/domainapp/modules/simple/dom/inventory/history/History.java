package domainapp.modules.simple.dom.inventory.history;

import domainapp.modules.simple.dom.inventory.ObjectParent;
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
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.Title;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Named("simple.History")
@DomainObject(nature = Nature.VIEW_MODEL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        propOrder = {
                "uuid",
                "user",
                "action",
                "detail"
        }
)
public class History extends ObjectParent {
    @Title@Property
    String uuid;

    @Property
    String user;

    @Property
    String action;

    @Property
    String detail;

    @Property
    LocalDateTime createdTime;
}


