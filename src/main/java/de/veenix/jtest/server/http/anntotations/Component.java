package de.veenix.jtest.server.http.anntotations;

import de.veenix.jtest.server.http.HtmlElement;

import java.util.List;

public interface Component {

    List<HtmlElement> getChildren();

    void registerElement(HtmlElement element);

    void removeElement(HtmlElement element);

    String generateHtml();

}
