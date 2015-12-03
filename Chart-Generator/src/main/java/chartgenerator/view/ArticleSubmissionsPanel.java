package chartgenerator.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chartgenerator.component.KeywordGroupManagerPanel;
import chartgenerator.component.MapManagerPanel;
import chartgenerator.component.NumberProperty;
import chartgenerator.component.TimeFramePanel;
import chartgenerator.inputs.ArticleSubmissionInputs;
import chartgenerator.inputs.GlobalSettings;
import chartgenerator.outputs.ArticleSubmissionOutput;
import chartgenerator.outputs.TLDWebsiteOutput;
import chartgenerator.process.ArticleSubmissionsProcessor;

public class ArticleSubmissionsPanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ArticleSubmissionsPanel.class);

	private JPanel viewportView;

	private NumberProperty articleSubmissionsNumber;

	private MapManagerPanel articleSubmissionSitesPanel;
	private TimeFramePanel timeFramePanel;
	private KeywordGroupManagerPanel keywordGroupManagerPanel;

	public ArticleSubmissionsPanel() {
		super();
		initComponents();

	}

	private void initComponents() {
		setAlignmentX(LEFT_ALIGNMENT);
		viewportView = new JPanel();

		viewportView.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		c.gridx = 0;

		articleSubmissionsNumber = new NumberProperty(
				"Number of Article Submissions:");
		viewportView.add(articleSubmissionsNumber, c);

		c.weightx = 1;
		c.gridy++;
		articleSubmissionSitesPanel = new MapManagerPanel(
				"Article Submission Sites");
		viewportView.add(articleSubmissionSitesPanel, c);

		c.gridy++;
		timeFramePanel = new TimeFramePanel();
		viewportView.add(timeFramePanel, c);

		c.gridy++;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		keywordGroupManagerPanel = new KeywordGroupManagerPanel();
		viewportView.add(keywordGroupManagerPanel, c);

		JButton button = createProccessButton();
		viewportView.add(button);

		setViewportView(viewportView);

	}

	private JButton createProccessButton() {
		JButton button = new JButton("Process");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				final LocalDate startDate = timeFramePanel.getStartDate();
				final LocalDate endDate = timeFramePanel.getEndDate();

				final ArticleSubmissionInputs inputs = new ArticleSubmissionInputs(
						articleSubmissionsNumber.getNumber(),
						articleSubmissionSitesPanel.getFrequencyMap(),
						startDate, endDate, keywordGroupManagerPanel
								.getKeywordGroupInputsList());
				GlobalSettings settings = new GlobalSettings("CAR", Arrays
						.asList(0.5, 0.3, 0.15, 0.05), Arrays.asList(0.5, 0.3,
						0.15, 0.05), Arrays.asList(0.5, 0.3, 0.15, 0.05),
						Arrays.asList(0.5, 0.3, 0.15, 0.05), Arrays.asList(10,
								30, 25, 25, 10));
				ArticleSubmissionsProcessor processor = new ArticleSubmissionsProcessor(
						inputs, settings);
				List<ArticleSubmissionOutput> outputList = processor
						.getOutputs();
				final DateTimeFormatter dateTimeFormatter = DateTimeFormat
						.forPattern("dd_MM_yyyy");
				for (LocalDate date = startDate; date.isBefore(endDate); date = date
						.plusDays(1)) {

					for (ArticleSubmissionOutput output : outputList) {
						PrintWriter out = null;
						try {
							out = new PrintWriter(new BufferedWriter(
									new FileWriter(dateTimeFormatter
											.print(date) + ".csv", true)));

							LOGGER.debug(output.getCsvString(date));
							out.println(output.getCsvString(date));

						} catch (FileNotFoundException e1) {
							LOGGER.error(
									"Cannot save processing result to file.",
									e1);
						} catch (IOException e1) {
							LOGGER.error(
									"Cannot save processing result to file.",
									e1);
						} finally {
							if (out != null) {
								out.close();
							}
						}
					}
				}

			}
		});
		return button;
	}

}
