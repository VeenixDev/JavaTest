package de.veenix.jtest.server.http.components;

import de.veenix.jtest.server.http.HtmlElement;
import de.veenix.jtest.server.http.interfaces.Component;

import java.util.ArrayList;
import java.util.List;

public class MainComponent implements Component {

    private final List<HtmlElement> elements = new ArrayList<>();

    public MainComponent() {
        HtmlElement span = new HtmlElement();
        span.setElementName("span");
        span.setValue("Random garbage text!");
        span.addAttribute("class", "text");

        HtmlElement div = new HtmlElement();
        div.setElementName("div");
        div.addChildren(span);
        elements.add(div);
    }

    @Override
    public List<HtmlElement> getChildren() {
        return elements;
    }

    @Override
    public void registerElement(HtmlElement element) {
        this.elements.add(element);
    }

    @Override
    public void removeElement(HtmlElement element) {
        this.elements.remove(element);
    }

    @Override
    public String generateHtml() {
        StringBuilder sb = new StringBuilder();
        for (HtmlElement element : elements) {
            sb.append(element.toString());
        }

        return sb.toString();
    }
}
