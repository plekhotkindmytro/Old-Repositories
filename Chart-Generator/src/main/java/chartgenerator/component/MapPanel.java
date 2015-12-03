package chartgenerator.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	

	private JTextField textField;
	private JSpinner frequencySpinner;

	public MapPanel() {
		super();
		setLayout(new GridBagLayout());
		initComponents();
	}

	private void initComponents() {
		initTextField();
		initFrequencySpinner();
	}

	private void initTextField() {

		textField = new JTextField();
		textField.setColumns(10);
		GridBagConstraints firstElementConstraints = new GridBagConstraints();
		firstElementConstraints.fill = GridBagConstraints.HORIZONTAL;
		firstElementConstraints.anchor = GridBagConstraints.LINE_START;
		firstElementConstraints.weightx = 0;
		firstElementConstraints.insets = new Insets(0, 10, 0, 20);
		firstElementConstraints.gridx = 0;
		firstElementConstraints.gridx = 0;

		add(textField, firstElementConstraints);
	}
	
	public JTextField getTextField() {
		return textField;
	}

	public JSpinner getFrequencySpinner() {
		return frequencySpinner;
	}

	private void initFrequencySpinner() {
		frequencySpinner = ElementFactory.createFrequencySpinner();

		GridBagConstraints inputConstraints = new GridBagConstraints();

		inputConstraints.anchor = GridBagConstraints.LINE_START;

		inputConstraints.weightx = 1;
		inputConstraints.gridx = 0;
		inputConstraints.gridx = 1;

		add(frequencySpinner, inputConstraints);
	}
}
