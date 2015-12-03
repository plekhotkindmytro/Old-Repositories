package chartgenerator.component;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class NumberProperty extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MIN_VALUE = 0;
	private static final int INITIAL_VALUE = 0;
	private static final int STEP = 1;

	private JSpinner numberSpinner;

	public NumberProperty(String labelString) {
		super();
		setLayout(new GridBagLayout());

		initLabel(labelString);
		initInput();

	}

	private void initLabel(String labelString) {
		final GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.anchor = GridBagConstraints.LINE_START;
		labelConstraints.weightx = 0;
		labelConstraints.insets = new Insets(0, 10, 0, 0);

		final JLabel label = new JLabel(labelString);
		add(label, labelConstraints);
	}

	private void initInput() {
		GridBagConstraints inputConstraints = new GridBagConstraints();
		inputConstraints.anchor = GridBagConstraints.LINE_START;
		inputConstraints.weightx = 1;
		numberSpinner = new JSpinner(new SpinnerNumberModel(INITIAL_VALUE,
				MIN_VALUE, Integer.MAX_VALUE, STEP));
		numberSpinner.setPreferredSize(new Dimension(75, numberSpinner
				.getPreferredSize().height));
		add(numberSpinner, inputConstraints);
	}

	public int getNumber() {
		final String value = String.valueOf(numberSpinner.getValue());
		return Integer.parseInt(value);
	}

}
