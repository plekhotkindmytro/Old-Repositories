package chartgenerator.component;

import java.awt.Dialog.ModalityType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

public class GlobalSettingsDialog {
	private JDialog settingsDialog;
	private JFileChooser fileChooser;

	private JScrollPane scrollPane;
	private JPanel panel;

	public GlobalSettingsDialog(JFrame owner) {
		settingsDialog = new JDialog(owner);
		settingsDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		settingsDialog.setTitle("Campaign settings");

		initComponents();
		settingsDialog.pack();
	}

	private void initComponents() {
		fileChooser = new JFileChooser();

		scrollPane = new JScrollPane();
		panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 0;
		c.gridy = 0;
		createHeader("URL Formats", c);
		Map<String, Integer> urlFormats = new LinkedHashMap<String, Integer>();
		urlFormats.put("Put in CAPS:", 30);
		urlFormats.put("Add HTTP:", 10);
		c.gridx = 0;
		c.gridy++;
		createOptions(urlFormats, c);

		c.gridx = 0;
		c.gridy++;
		createHeader("Anchor Amounts", c);

		Map<String, Integer> anchorAmmonts = new LinkedHashMap<String, Integer>();
		anchorAmmonts.put("0:", 30);
		anchorAmmonts.put("1:", 20);
		anchorAmmonts.put("2:", 20);
		anchorAmmonts.put("3:", 20);
		anchorAmmonts.put("4:", 10);
		c.gridx = 0;
		c.gridy++;
		createOptions(anchorAmmonts, c);

		c.gridx = 0;
		c.gridy++;
		createHeader("Image Amounts", c);

		Map<String, Integer> imageAmmonts = new LinkedHashMap<String, Integer>();
		imageAmmonts.put("0:", 25);
		imageAmmonts.put("1:", 25);
		imageAmmonts.put("2:", 20);
		imageAmmonts.put("3:", 20);
		imageAmmonts.put("4:", 5);
		imageAmmonts.put("5:", 5);
		c.gridx = 0;
		c.gridy++;
		createOptions(imageAmmonts, c);

		c.gridx = 0;
		c.gridy++;
		createHeader("Audio Amounts", c);

		Map<String, Integer> audioAmmonts = new LinkedHashMap<String, Integer>();
		audioAmmonts.put("0:", 90);
		audioAmmonts.put("1:", 5);
		audioAmmonts.put("2:", 5);
		c.gridx = 0;
		c.gridy++;
		createOptions(audioAmmonts, c);

		c.gridx = 0;
		c.gridy++;
		createHeader("Video Amounts", c);

		Map<String, Integer> videoAmmonts = new LinkedHashMap<String, Integer>();
		videoAmmonts.put("0:", 60);
		videoAmmonts.put("1:", 30);
		videoAmmonts.put("2:", 7);
		videoAmmonts.put("3:", 3);
		c.gridx = 0;
		c.gridy++;
		createOptions(videoAmmonts, c);

		scrollPane.setViewportView(panel);
		settingsDialog.add(scrollPane);
	}

	private void createHeader(String title, GridBagConstraints c) {
		c.gridx = 0;
		c.gridwidth = 2;
		c.insets = new Insets(10, 20, 10, 20);
		JLabel titleLabel = new JLabel(title + "(%)");
		panel.add(titleLabel, c);
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 0, 0);

	}

	private void createOptions(Map<String, Integer> optionMap,
			GridBagConstraints c) {

		for (String option : optionMap.keySet()) {
			c.gridx = 0;

			JLabel optionLabel = new JLabel(option);
			panel.add(optionLabel, c);

			c.gridx++;
			c.insets = new Insets(0, 0, 0, 20);
			int frequency = optionMap.get(option);
			JSpinner frequencySpinner = ElementFactory.createFrequencySpinner();
			frequencySpinner.setValue(frequency);
			panel.add(frequencySpinner, c);
			c.gridy++;
			c.insets = new Insets(0, 0, 0, 0);
		}
	}

	public JDialog getDialog() {
		return settingsDialog;
	}
}
