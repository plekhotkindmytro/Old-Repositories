package chartgenerator.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import chartgenerator.component.ElementFactory;
import chartgenerator.component.KeywordGroupManagerPanel;
import chartgenerator.component.MapManagerPanel;
import chartgenerator.component.TimeFramePanel;

public class BlogCommentsPanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel viewportView;
	private TimeFramePanel timeFramePanel;
	private KeywordGroupManagerPanel keygGroupManagerPanel;

	public BlogCommentsPanel() {
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

		viewportView
				.add(ElementFactory
						.createNumberProperty("Number of Blog Comments:"), c);

		// TODO: Blog comment sites to use (.txt file)
		c.weightx = 1;
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

}
