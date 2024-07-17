package domainapp.modules.simple.dom.inventory.rule;

import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domainapp.modules.simple.dom.inventory.ObjectParent;
import jakarta.inject.Named;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.Title;

@Getter @Setter
@NoArgsConstructor
public class Rule extends ObjectParent {
    String name;

    String ruleDetail;

    public RuleDetail getRuleDetailObj (String ruleDetail) throws JsonProcessingException, UnsupportedEncodingException {
        if (ruleDetail == null || ruleDetail.isEmpty()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        // URL decode the string
        String decodedAttributes = java.net.URLDecoder.decode(ruleDetail, "UTF-8");
        // Deserialize the JSON string into a Map
        return objectMapper.readValue(decodedAttributes, RuleDetail.class);
    }
}
