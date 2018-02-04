package ru.valery.calculator.ui;

import ru.valery.math.Calculator;
import ru.valery.math.MathCalculator;
import ru.valery.pluginloader.PluginLoader;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class CalculatorUI {
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("Первый параметр для приложения - полный путь к конфигу calculator.properties");
			System.out.println("java -jar calculator-1.0-SNAPSHOT.jar /Users/valery/calculator.properties");
		}
		Properties properties = new Properties();
		InputStream in = new FileInputStream(args[0]);
		properties.load(in);
		in.close();

		Calculator calculator = new MathCalculator();
		PluginLoader pluginLoader = new PluginLoader();

		EventQueue.invokeLater(() -> {
			CalculatorFrame frame = new CalculatorFrame(calculator, pluginLoader, properties);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}

