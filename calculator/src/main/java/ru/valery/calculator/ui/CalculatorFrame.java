package ru.valery.calculator.ui;


import ru.valery.math.Calculator;
import ru.valery.pluginloader.PluginLoader;

import javax.swing.JFrame;
import java.util.Properties;

class CalculatorFrame extends JFrame {
    public CalculatorFrame(Calculator calculator, PluginLoader pluginLoader, Properties properties) {
        setTitle("CalculatorUI");
        add(new CalculatorPanel(calculator, pluginLoader, properties));
        pack();
    }
}
