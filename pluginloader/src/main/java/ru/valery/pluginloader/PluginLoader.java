package ru.valery.pluginloader;

import ru.valery.plugins.BinaryPlugin;
import ru.valery.plugins.Plugin;
import ru.valery.plugins.UnaryPlugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PluginLoader {
    private static final String pluginClassName = "ru.valery.plugins.UserPlugin";
    private static final String pluginJarPath = "/Users/valery/IdeaProjects/calculator-plugins";

    private Map<String, Plugin> plugins;

    public PluginLoader() {
        plugins = new HashMap<>();
    }


    public void putPlugin(File jar) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        URL jarURL = jar.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});

        Class clazz = classLoader.loadClass(pluginClassName);
        plugins.put(((Plugin)clazz.newInstance()).operatorName(),((Plugin)clazz.newInstance()));
    }

    /**
     * Test only
     * @param args
     */
    public static void main(String[] args) {
        PluginLoader loader = new PluginLoader();
        for(Map.Entry<String, Plugin> plugin : loader.plugins.entrySet()){
            System.out.println(String.format("%s:%s",plugin.getKey(), plugin.getValue().operatorName()));
            if(plugin.getValue() instanceof BinaryPlugin){
                System.out.println((((BinaryPlugin) plugin.getValue())).calculate(33.2,33.1));
            }
            if(plugin.getValue() instanceof UnaryPlugin){
                System.out.println((((UnaryPlugin) plugin.getValue())).calculate(33.2));
            }
        }

    }

    public Map<String, Plugin> getPlugins() {
        return plugins;
    }
    public void remove(String name){
        //Выгрузку из памяти, как и защиту от небезопасного плагина тут не реализуем
        plugins.remove(name);
    }
}
