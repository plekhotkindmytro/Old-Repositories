package chartgenerator.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeFramePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().add(new TimeFramePanel());

		frame.pack();
		frame.setVisible(true);
	}

	// Format for output
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("dd/MM/yyyy");
	private JLabel timeFrameLabel;
	private JLabel startLabel;
	private JLabel endLabel;

	private JFormattedTextField startField;
	private JFormattedTextField endField;

	public TimeFramePanel() {
		super();
		initComponents();
	}

	private void initComponents() {
		setAlignmentX(LEFT_ALIGNMENT);
		setLayout(new GridBagLayout());

		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.fill = GridBagConstraints.HORIZONTAL;
		labelConstraints.weightx = 0;
		labelConstraints.gridwidth = 2;
		labelConstraints.anchor = GridBagConstraints.LINE_START;

		GridBagConstraints inputConstraints = new GridBagConstraints();
		inputConstraints.anchor = GridBagConstraints.LINE_START;
		inputConstraints.weightx = 1;

		labelConstraints.gridx = 0;
		labelConstraints.gridy = 0;
		timeFrameLabel = new JLabel("Timeframe: ");
		add(timeFrameLabel, labelConstraints);

		labelConstraints.gridwidth = 1;
		labelConstraints.insets = new Insets(0, 10, 0, 0);

		labelConstraints.gridx = 0;
		labelConstraints.gridy = 1;
		// c.fill = GridBagConstraints.NONE;
		startLabel = new JLabel("Start");
		add(startLabel, labelConstraints);

		inputConstraints.gridx = 1;
		inputConstraints.gridy = 1;
		final SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
				"dd/MM/yyyy");
		startField = new JFormattedTextField(defaultDateFormat);
		add(startField, inputConstraints);

		labelConstraints.gridx = 0;
		labelConstraints.gridy = 2;
		endLabel = new JLabel("End");
		add(endLabel, labelConstraints);

		inputConstraints.gridx = 1;
		inputConstraints.gridy = 2;
		endField = new JFormattedTextField(defaultDateFormat);
		add(endField, inputConstraints);

		final String defaultDateValue = defaultDateFormat.format(new Date());
		startField.setText(defaultDateValue);
		endField.setText(defaultDateValue);

	}

	public LocalDate getStartDate() {
		final String startDateString = startField.getText();
		return DATE_TIME_FORMATTER.parseLocalDate(startDateString);
	}

	public LocalDate getEndDate() {
		final String endDateString = endField.getText();
		return DATE_TIME_FORMATTER.parseLocalDate(endDateString);
	}

}
