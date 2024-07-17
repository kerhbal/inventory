package domainapp.modules.simple.dom.inventory.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RuleDetail {
    List<RuleUnit> input;
    List<RuleUnit> output;

    public RuleDetail(List<RuleUnit> input, List<RuleUnit> output) {
        this.input = input;
        this.output = output;
    }

    public static RuleDetail getEmptyDetailObj() {
        RuleDetail ruleDetail = new RuleDetail();
        ruleDetail.setInput(new ArrayList<>());
        ruleDetail.setOutput(new ArrayList<>());
        return ruleDetail;
    }

    @Override
    public String toString() {
        return "input: [ " + input.stream().map(RuleUnit::toString).collect(Collectors.joining(",")) + " ], " +
                "output: [ " + output.stream().map(RuleUnit::toString).collect(Collectors.joining(",")) + " ]";
    }
}


