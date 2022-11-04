package de.veenix.jtest.plugin;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        JarFile extensionFile = new JarFile(new File("out/artifacts/Plugin", "Plugin.jar"));

        JarEntry pluginProperties = extensionFile.getJarEntry("plugin.properties");

        System.out.println("Entry: " + pluginProperties.getName());

        InputStream inStream = extensionFile.getInputStream(pluginProperties);
        File outFile = new File("temp/Plugin/plugin.properties");
        if(outFile.exists()) {
            outFile.delete();
        }
        if(!outFile.exists()) {
            outFile.getParentFile().mkdirs();
            outFile.createNewFile();
        }

        FileOutputStream outStream = new FileOutputStream("temp/Plugin/plugin.properties");
        while(inStream.available() != 0) {
            outStream.write(inStream.read());
        }
        outStream.close();
        inStream.close();

        Properties properties = new Properties();
        properties.load(new FileReader(outFile));

        String pluginMain = properties.getProperty("mainClass");
        String pluginName = properties.getProperty("name");

        System.out.println("Loaded " + pluginMain + " from " + pluginName);

        URL[] urls = { new URL("jar:file:" + "out/artifacts/Plugin/Plugin.jar" + "!/") };
        URLClassLoader classLoader = URLClassLoader.newInstance(urls);

        Class<? extends PluginInterface> pluginMainClass = (Class<? extends PluginInterface>) classLoader.loadClass(pluginMain);
        System.out.println("Plugin main loaded! Classname: " + pluginMainClass.getName());
        System.out.println();

        PluginInterface plInterface = (PluginInterface) pluginMainClass.getConstructors()[0].newInstance();
        plInterface.setup();
    }

}
