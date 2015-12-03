package chartgenerator.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import chartgenerator.component.ElementFactory;
import chartgenerator.component.KeywordGroupManagerPanel;
import chartgenerator.component.MapManagerPanel;
import chartgenerator.component.TimeFramePanel;

public class T2Panel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel viewportView;

	private MapManagerPanel videoSubmissionSitesPanel;
	private MapManagerPanel bookmarkSitesPanel;
	private MapManagerPanel subdomainBlogSitesPanel;
	private MapManagerPanel prDistributionSitesPanel;
	private TimeFramePanel timeFramePanel;

	private KeywordGroupManagerPanel keygGroupManagerPanel;

	public T2Panel() {
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

		viewportView.add(ElementFactory
				.createNumberProperty("Number of T2 Sites per T1 Site:"), c);
		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Number of Total Articles to be used on T2 Sites:"),
						c);
		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Max Number of Articles per Subdomain Blogs:"),
						c);
		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Min Number of Articles per Subdomain Blogs:"),
						c);
		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Max Number of Images per Article per Subdomain Blogs:"),
						c);
		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Min Number of Images per Article per Subdomain Blogs:"),
						c);

		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Max Number of Videos per Article per Subdomain Blogs:"),
						c);
		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Min Number of Videos per Article per Subdomain Blogs:"),
						c);
		c.gridy++;
		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Max Number of Audio per Article per Subdomain Blogs:"),
						c);
		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Min Number of Audio per Article per Subdomain Blogs:"),
						c);

		// Authority package
		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Authority Package for T1 Subdomain Blogs?"),
						c);

		c.gridy++;
		viewportView
				.add(ElementFactory
						.createNumberProperty("Authority Package for T1 TLD Sites?"),
						c);

		createAuthorityPackageFields(
				Arrays.asList(
						"Max Size of each PBN:",
						"Min Size of each PBN:",
						"Number of Total Articles to be used in all Authority Packages:",
						"Max Number of Articles per PBN Blog:",
						"Min Number of Articles per PBN Blog:",
						"Max Number of Images per Article per PBN Blog:",
						"Min Number of Images per Article per PBN Blog:",
						"Max Number of Videos per Article per PBN Blog:",
						"Min Number of Videos per Article per PBN Blog:",
						"Max Number of Audio per Article per PBN Blog:",
						"Min Number of Audio per Article per PBN Blog:",
						"Max Number of Press Release Distributions:",
						"Min Number of Press Release Distributions:",
						"Max Number of Bookmarks:", "Min Number of Bookmarks:",
						"Max Number of Video Distribution:",
						"Min Number of Video Distribution:"), c);
		c.weightx = 1;
		c.gridy++;
		videoSubmissionSitesPanel = new MapManagerPanel(
				"Video Submission Sites");
		viewportView.add(videoSubmissionSitesPanel, c);

		c.gridy++;
		bookmarkSitesPanel = new MapManagerPanel("Bookmark Sites");
		viewportView.add(bookmarkSitesPanel, c);

		c.gridy++;
		subdomainBlogSitesPanel = new MapManagerPanel("Subdomain Blog Sites");
		viewportView.add(subdomainBlogSitesPanel, c);

		c.gridy++;
		prDistributionSitesPanel = new MapManagerPanel("PR Distribution Sites");
		viewportView.add(prDistributionSitesPanel, c);

		c.gridy++;
		timeFramePanel = new TimeFramePanel();
		viewportView.add(timeFramePanel, c);

		c.gridy++;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		keygGroupManagerPanel = new KeywordGroupManagerPanel();
		viewportView.add(keygGroupManagerPanel, c);

		setViewportView(viewportView);

	}

	private void createAuthorityPackageFields(List<String> labelList,
			GridBagConstraints c) {
		for (String label : labelList) {
			viewportView.add(ElementFactory.createNumberProperty(label), c);
			c.gridy++;
		}
	}

}
