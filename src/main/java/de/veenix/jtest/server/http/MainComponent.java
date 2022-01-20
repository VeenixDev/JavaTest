package de.veenix.jtest.server.http;

import java.util.ArrayList;
import java.util.List;

public class MainComponent {

    private final List<HtmlElement> elements = new ArrayList<>();

    public MainComponent() {
        HtmlElement element = new HtmlElement();
        element.setValue("Test Test");
        element.addAttribute("class", "text");
        element.setElementName("span");
        elements.add(element);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (HtmlElement element : elements) {
            sb.append(element.toString());
        }

        return sb.toString();
    }
}
