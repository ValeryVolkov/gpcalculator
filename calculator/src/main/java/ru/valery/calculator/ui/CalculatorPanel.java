package ru.valery.calculator.ui;


import ru.valery.calculator.ui.listeners.RemoveButtonActionListener;
import ru.valery.math.Calculator;
import ru.valery.math.Operations;
import ru.valery.pluginloader.PluginLoader;
import ru.valery.plugins.Plugin;
import ru.valery.plugins.UnaryPlugin;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * Панель калькулятора
 */
class CalculatorPanel extends JPanel {
	private static String pluginPath;
	private final static String[] buttons = {
			"7", "8", "9",
			"4", "5", "6",
			"1", "2", "3",
			"0", "."
	};
	private JButton display;
	private JButton accumulator;
	private boolean start = false;

	private Calculator calculator;
	private PluginLoader pluginLoader;
	private JPanel operationPanel;

	public CalculatorPanel(Calculator calculator, PluginLoader pluginLoader, Properties properties) {
		pluginPath = properties.getProperty("plugins.path");
		this.calculator = calculator;
		this.pluginLoader = pluginLoader;

		display = new JButton("0");
		display.setEnabled(false);
		display.setHorizontalAlignment(SwingConstants.RIGHT);

		accumulator = new JButton("");
		accumulator.setEnabled(false);
		accumulator.setHorizontalAlignment(SwingConstants.LEFT);

		setLayout(new BorderLayout());
		// Панель дисплея
		add(display, BorderLayout.NORTH);
		// Состояние аккумулятора
		add(accumulator, BorderLayout.SOUTH);
		// Числовая панель
		add(createNumberPanel(), BorderLayout.WEST);
		// Панель операций над выражениями
		// Располагается по-центру
		operationPanel = createOperationPanel();
		add(operationPanel, BorderLayout.CENTER);

		add(new FileTree(new File(pluginPath), pluginLoader) {
			@Override
			public void pluginUpdated() {
				CalculatorPanel.this.remove(operationPanel);
				operationPanel.revalidate();

				operationPanel = createOperationPanel();
				CalculatorPanel.this.add(operationPanel, BorderLayout.CENTER);
				operationPanel.revalidate();

			}
		}, BorderLayout.EAST);

	}

	private JPanel createOperationPanel() {
		ActionListener command = new CommandAction();

		JPanel commandPanel = new JPanel() {
			public Dimension getMinimumSize() {
				return new Dimension(200, 400);
			}

			public Dimension getPreferredSize() {
				return new Dimension(400, 400);
			}

		};

		commandPanel.setLayout(new GridLayout(9, 1));

		commandPanel.add(addButton("AC", command));
		commandPanel.add(addButton( "(", command));
		commandPanel.add(addButton( ")", command));
		commandPanel.add(addButton( "=", command));

		pluginLoader.getPlugins().entrySet()
				.forEach(plugin -> commandPanel.add(createOperationButton(command, commandPanel, plugin)));
		return commandPanel;
	}

	private JButton createOperationButton(ActionListener command, JPanel commandPanel, Map.Entry<String, Plugin> plugin) {
		JButton button = addButton(plugin.getValue().operatorName(), command);
		commandPanel.add(button);

		JButton removeButton = new JButton();
		removeButton.setText("x");
		removeButton.setHorizontalAlignment(SwingConstants.CENTER);
		removeButton.setVerticalAlignment(SwingConstants.CENTER);
		removeButton.addActionListener(new RemoveButtonActionListener(commandPanel, pluginLoader, button));
		button.add(removeButton);
		return button;
	}

	private JPanel createNumberPanel() {
		JPanel numberPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(4, 3);
		numberPanel.setLayout(gridLayout);

		ActionListener insert = new InsertAction();
		Arrays.stream(buttons).forEach(name -> numberPanel.add(addButton(name, insert)));
		return numberPanel;
	}

	private JButton addButton(String label, ActionListener listener) {
		JButton button = new JButton(label);
		button.addActionListener(listener);
		return button;
	}

	private class InsertAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String input = event.getActionCommand();
			accumulator.setText(accumulator.getText() + input);
			if (!start) {
				display.setText(input);
				start = true;
				return;
			}
			display.setText(display.getText() + input);
		}

	}

	private class CommandAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String command = event.getActionCommand();

			switch (command) {
				case "AC":
					calculator.reset();
					display.setText("0");
					accumulator.setText("");
					start = false;
					break;
				case "(":
					calculator.addOperation(Operations.OpenBrace.getPlugin());

					display.setText("");
					accumulator.setText(accumulator.getText() + "(");
					break;
				case ")":
					calculator.addValue(parseDouble(display.getText()));
					calculator.addOperation(Operations.CloseBrace.getPlugin());

					display.setText("");
					accumulator.setText(accumulator.getText() + ")");

					break;
				case "=":
					if (!"".equals(display.getText())) {

						calculator.addValue(parseDouble(display.getText()));

					}
					Double result = calculator.calculate();
					display.setText("" + result);
					accumulator.setText("" + result);
					start = false;

					break;
				default:
					if (!"".equals(display.getText())) {
						calculator.addValue(parseDouble(display.getText()));
					}
					Plugin plugin = pluginLoader.getPlugins().get(command);
					calculator.addOperation(plugin);
					if (plugin instanceof UnaryPlugin) {
						display.setText("");
					}

					accumulator.setText(accumulator.getText() + command);
					start = false;

			}

		}

		private Double parseDouble(String text) {
			try {
				return Double.valueOf(text);
			} catch (NumberFormatException e) {
				accumulator.setText("");
				display.setText("0");
				calculator.reset();
				start = false;
				JOptionPane.showMessageDialog(CalculatorPanel.this, "Не могу преобразовать строку в вещественное число", "Ошибка", JOptionPane.ERROR_MESSAGE);
				return 0.0;
			}
		}

	}

}
