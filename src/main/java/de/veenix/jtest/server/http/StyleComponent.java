package de.veenix.jtest.server.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StyleComponent {

    private final Map<String, List<String>> rules = new HashMap<>();

    public void addRule(String selector, String ruleName, String ruleValue) {
        if(rules.containsKey(selector)) {
            List<String> rules = this.rules.get(selector);
            rules.add(ruleName + ": " + ruleValue);
            return;
        }
        List<String> rules = new ArrayList<>();
        rules.add(ruleName + ": " + ruleValue);
        this.rules.put(selector, rules);
    }

    public void removeRule(String selector, String ruleName) {
        if(!rules.containsKey(selector)) {
            return;
        }

        rules.get(selector).remove(ruleName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        rules.forEach((k,v) -> {
            sb.append(k).append(": {");
            v.forEach(e -> sb.append(e).append(";"));
            sb.append("}");
        });

        return sb.toString();
    }
}
