package de.veenix.jtest.plugin;

public class PluginMain extends PluginInterface {
    @Override
    public void setup() {
        addParameter("uwu", "sixty nine");
        addParameter("owo", 420);

        System.out.println(getParameter("uwu"));
        System.out.println(getParameter("owo"));
    }

    @Override
    public void getParameterHook(String parameterName) {
        System.out.println("Your parameter has the name: " + parameterName);
    }
}
