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
import chartgenerator.inputs.GlobalSettings;
import chartgenerator.inputs.SubdomainBlogInputs;
import chartgenerator.outputs.SubdomainBlogOutput;
import chartgenerator.process.SubdomainBlogsProcessor;

public class SubdomainBlogsPanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SubdomainBlogsPanel.class);
	private JPanel viewportView;

	private NumberProperty totalBlogNumber;
	private NumberProperty totalArticleNumber;

	private NumberProperty minArticleNumber;
	private NumberProperty maxArticleNumber;

	private NumberProperty minImageNumber;
	private NumberProperty maxImageNumber;

	private NumberProperty minAudioNumber;
	private NumberProperty maxAudioNumber;

	private NumberProperty minVideoNumber;
	private NumberProperty maxVideoNumber;

	private MapManagerPanel subdomainBlogSitesPanel;
	private TimeFramePanel timeFramePanel;
	private KeywordGroupManagerPanel keywordGroupManagerPanel;

	public SubdomainBlogsPanel() {
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

		totalBlogNumber = new NumberProperty(
				"Number of Subdomain Blogs To Create:");
		viewportView.add(totalBlogNumber, c);

		c.gridy++;
		totalArticleNumber = new NumberProperty(
				"Number of Total Articles to be used on Subdomain Blogs:");
		viewportView.add(totalArticleNumber, c);

		c.gridy++;
		maxArticleNumber = new NumberProperty(
				"Max Number of Articles per Subdomain Blogs:");
		viewportView.add(maxArticleNumber, c);

		c.gridy++;
		minArticleNumber = new NumberProperty(
				"Min Number of Articles per Subdomain Blogs:");
		viewportView.add(minArticleNumber, c);

		c.gridy++;
		maxImageNumber = new NumberProperty(
				"Max Number of Images per Article per Subdomain Blogs:");
		viewportView.add(maxImageNumber, c);

		c.gridy++;
		minImageNumber = new NumberProperty(
				"Min Number of Images per Article per Subdomain Blogs:");
		viewportView.add(minImageNumber, c);

		c.gridy++;
		maxAudioNumber = new NumberProperty(
				"Max Number of Videos per Article per Subdomain Blogs:");
		viewportView.add(maxAudioNumber, c);

		c.gridy++;
		minAudioNumber = new NumberProperty(
				"Min Number of Videos per Article per Subdomain Blogs:");
		viewportView.add(minAudioNumber, c);

		c.gridy++;
		maxVideoNumber = new NumberProperty(
				"Max Number of Audio per Article per Subdomain Blogs:");
		viewportView.add(maxVideoNumber, c);

		c.gridy++;
		minVideoNumber = new NumberProperty(
				"Min Number of Audio per Article per Subdomain Blogs:");
		viewportView.add(minVideoNumber, c);

		c.weightx = 1;
		c.gridy++;
		subdomainBlogSitesPanel = new MapManagerPanel("Subdomain Blog Sites");
		viewportView.add(subdomainBlogSitesPanel, c);

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

				final SubdomainBlogInputs inputs = new SubdomainBlogInputs(
						totalBlogNumber.getNumber(), totalArticleNumber
								.getNumber(), minArticleNumber.getNumber(),
						maxArticleNumber.getNumber(), minImageNumber
								.getNumber(), maxImageNumber.getNumber(),
						minVideoNumber.getNumber(), maxVideoNumber.getNumber(),
						minAudioNumber.getNumber(), maxAudioNumber.getNumber(),
						subdomainBlogSitesPanel.getFrequencyMap(), startDate,
						endDate, keywordGroupManagerPanel
								.getKeywordGroupInputsList());
				GlobalSettings settings = new GlobalSettings("CAR", Arrays
						.asList(0.5, 0.3, 0.15, 0.05), Arrays.asList(0.5, 0.3,
						0.15, 0.05), Arrays.asList(0.5, 0.3, 0.15, 0.05),
						Arrays.asList(0.5, 0.3, 0.15, 0.05), Arrays.asList(10,
								30, 25, 25, 10));
				SubdomainBlogsProcessor processor = new SubdomainBlogsProcessor(
						inputs, settings);
				List<SubdomainBlogOutput> outputList = processor.getOutputs();
				final DateTimeFormatter dateTimeFormatter = DateTimeFormat
						.forPattern("dd_MM_yyyy");
				for (LocalDate date = startDate; date.isBefore(endDate); date = date
						.plusDays(1)) {

					for (SubdomainBlogOutput output : outputList) {
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
