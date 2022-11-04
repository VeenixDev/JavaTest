package de.veenix.jtest.plugin;

import java.util.HashMap;

public abstract class PluginInterface implements IPluginInterface {
    private final HashMap<String, Object> data = new HashMap<>();

    @Override
    public void addParameter(String key, Object value) {
        data.put(key, value);
    }

    @Override
    public void removeParameter(String key) {
        data.remove(key);
    }

    @Override
    public Object getParameter(String key) {
        getParameterHook(key);
        return data.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }
}
