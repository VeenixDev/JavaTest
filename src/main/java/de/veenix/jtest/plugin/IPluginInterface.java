package de.veenix.jtest.plugin;

public interface IPluginInterface {

    void setup();

    void addParameter(String key, Object value);
    void removeParameter(String key);
    Object getParameter(String key);
    boolean containsKey(String key);
    boolean containsValue(Object value);

    void getParameterHook(String parameterName);

}
