package ru.valery.calculator.ui.listeners;

import ru.valery.pluginloader.PluginLoader;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveButtonActionListener implements ActionListener {
	private JPanel panel;
	private PluginLoader pluginLoader;
	private JButton button;

	public RemoveButtonActionListener(JPanel panel, PluginLoader pluginLoader, JButton button) {
		this.panel = panel;
		this.pluginLoader = pluginLoader;
		this.button = button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pluginLoader.remove(button.getText());
		panel.remove(button);
		panel.revalidate();
		panel.repaint();
	}
}
