package chartgenerator.component;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chartgenerator.model.KeywordData;

public class KeywordTypeProperty extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(KeywordTypeProperty.class);

	private final JFileChooser keywordsFileChooser;

	private JSpinner frequencySpinner;
	private List<String> keywordList;

	public KeywordTypeProperty(String labelString,
			final JFileChooser keywordsFileChooser) {
		super();
		setLayout(new GridBagLayout());

		this.keywordsFileChooser = keywordsFileChooser;
		initLabel(labelString);
		initInput();
		initBrowseButton();
	}

	private void initLabel(String labelString) {
		final GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.anchor = GridBagConstraints.LINE_START;
		labelConstraints.weightx = 0.5;
		labelConstraints.insets = new Insets(0, 10, 0, 0);

		final JLabel label = new JLabel(labelString);
		label.setPreferredSize(new Dimension(200,
				label.getPreferredSize().height));
		add(label, labelConstraints);
	}

	private void initInput() {
		GridBagConstraints inputConstraints = new GridBagConstraints();

		inputConstraints.anchor = GridBagConstraints.CENTER;
		inputConstraints.weightx = 0.1;
		frequencySpinner = ElementFactory.createFrequencySpinner();
		frequencySpinner.setPreferredSize(new Dimension(75, frequencySpinner
				.getPreferredSize().height));
		add(frequencySpinner, inputConstraints);
	}

	private void initBrowseButton() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		final JButton browseButton = new JButton("Browse...");
		final JLabel fileLabel = new JLabel("File is not chosen.");
		fileLabel.setPreferredSize(new Dimension(200, fileLabel
				.getPreferredSize().height));

		browseButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				LOGGER.debug("Event: {}", e.paramString());
				final int returnVal = keywordsFileChooser
						.showOpenDialog(KeywordTypeProperty.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = keywordsFileChooser.getSelectedFile();
					keywordList = getKeywordListFromFile(file);
					fileLabel.setText(file.getName());
				}
			}
		});

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;

		gridBagConstraints.weightx = 0.1;
		add(browseButton, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.weightx = 0.3;
		add(fileLabel, gridBagConstraints);

	}

	private List<String> getKeywordListFromFile(File file) {
		LOGGER.debug("Entering getKeywordListFromFile: file={}", file.getName());
		final List<String> keywordList = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null && !line.isEmpty()) {
				LOGGER.debug("Keyword from file: {}", line);
				keywordList.add(line.trim());
				line = reader.readLine();
			}
		} catch (IOException e) {
			LOGGER.error("Failed to read keyword list from file.", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				LOGGER.error("Failed to close file reader.", e);
			}
		}
		LOGGER.debug("Exit from getKeywordListFromFile: keywordList.size()={}",
				keywordList.size());
		return keywordList;
	}

	public KeywordData getKeywordData() {
		final String value = String.valueOf(frequencySpinner.getValue());
		int frequency = Integer.parseInt(value);
		return new KeywordData(frequency, keywordList);
	}
}
