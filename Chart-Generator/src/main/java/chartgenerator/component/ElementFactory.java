package chartgenerator.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public final class ElementFactory {

	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 100;
	private static final int INITIAL_VALUE = 0;
	private static final int STEP = 1;

	private ElementFactory() {
		throw new UnsupportedOperationException("This is utility class.");
	}

	public static JSpinner createFrequencySpinner() {
		JSpinner frequencySpinner = new JSpinner(new SpinnerNumberModel(
				INITIAL_VALUE, MIN_VALUE, MAX_VALUE, STEP));
//		frequencySpinner.setPreferredSize(new Dimension(50, frequencySpinner
//				.getPreferredSize().height));
		return frequencySpinner;
	}

	public static JSpinner createNumberSpinner() {
		JSpinner numberSpinner = new JSpinner(new SpinnerNumberModel(
				INITIAL_VALUE, MIN_VALUE, Integer.MAX_VALUE, STEP));
		numberSpinner.setPreferredSize(new Dimension(75, numberSpinner
				.getPreferredSize().height));
		return numberSpinner;
	}

	public static JPanel createProperty(String label, JComponent input) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints labelConstraints = new GridBagConstraints();

		labelConstraints.anchor = GridBagConstraints.LINE_START;
		labelConstraints.weightx = 0;
		labelConstraints.insets = new Insets(0, 10, 0, 0);

		panel.add(new JLabel(label), labelConstraints);

		GridBagConstraints inputConstraints = new GridBagConstraints();
		inputConstraints.anchor = GridBagConstraints.LINE_START;
		inputConstraints.weightx = 1;
		panel.add(input, inputConstraints);

		return panel;
	}
	
	public static JPanel createSettingLoader(String label, JComponent input) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		

		GridBagConstraints labelConstraints = new GridBagConstraints();

		labelConstraints.anchor = GridBagConstraints.LINE_START;
		labelConstraints.weightx = 0;
		labelConstraints.insets = new Insets(0, 10, 0, 0);

		panel.add(new JLabel(label), labelConstraints);

		GridBagConstraints inputConstraints = new GridBagConstraints();
		inputConstraints.anchor = GridBagConstraints.LINE_START;
		inputConstraints.weightx = 1;
		panel.add(input, inputConstraints);

		return panel;
	}

	public static JPanel createFrequencyProperty(String label) {
		return createProperty(label, createFrequencySpinner());
	}

	public static JPanel createNumberProperty(String label) {
		return createProperty(label, createNumberSpinner());
	}
	
	public static JPanel createCheckBoxProperty(String label) {
		return createProperty(label, createCheckBox());
	}

	private static JCheckBox createCheckBox() {
		JCheckBox checkBox = new JCheckBox();
		return checkBox;
	}
}
