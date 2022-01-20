package de.veenix.jtest.server.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlElement {

    private String elementName;
    private String value;
    private final Map<String, String> attributes = new HashMap<>();
    private final List<String> toggles = new ArrayList<>();
    private final List<HtmlElement> children = new ArrayList<>();

    public void addChildren(HtmlElement element) {
        children.add(element);
    }

    public List<HtmlElement> getChildren() {
        return children;
    }

    public HtmlElement getChild(int index) {
        return children.get(index);
    }

    public void addToggle(String toggle) {
        if(!toggles.contains(toggle)) {
            toggles.add(toggle);
        }
    }

    public void removeToggle(String toggle) {
        toggles.remove(toggle);
    }

    public void addAttribute(String attribute, String value) {
        attributes.put(attribute, value);
    }

    public void removeAttribute(String attribute) {
        attributes.remove(attribute);
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<String> getToggles() {
        return toggles;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (HtmlElement element : children) {
            sb.append(element.toString());
        }

        return "<" + elementName + ">" + value + sb + "</" + elementName + ">";
    }
}
