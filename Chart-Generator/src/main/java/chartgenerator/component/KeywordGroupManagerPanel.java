package chartgenerator.component;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chartgenerator.inputs.KeywordGroupInputs;

public class KeywordGroupManagerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int TWO_BUTTONS_COUNT = 2;

	private JLabel titleLabel;
	private JLabel frequencyLabel;

	private JButton addGroupButton;
	private JButton removeLastGroupButton;

	private List<KeywordGroupPanel> keywordGroupList;

	public static void main(String args[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new KeywordGroupManagerPanel());
				frame.pack();
				frame.setVisible(true);
			}
		});

	}

	public KeywordGroupManagerPanel() {
		super();
		initComponents();
	}

	private void initComponents() {
		setAlignmentX(LEFT_ALIGNMENT);
		this.setLayout(new GridBagLayout());

		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.fill = GridBagConstraints.HORIZONTAL;
		labelConstraints.anchor = GridBagConstraints.LINE_START;
		labelConstraints.weightx = 0;
		labelConstraints.gridwidth = 2;

		labelConstraints.gridx = 0;
		labelConstraints.gridy = 0;
		titleLabel = new JLabel("Keyword groups to be used:");
		add(titleLabel, labelConstraints);

		labelConstraints.insets = new Insets(0, 10, 0, 0);
		labelConstraints.gridwidth = 1;
		labelConstraints.weightx = 1;
		labelConstraints.fill = GridBagConstraints.NONE;

		labelConstraints.gridx = 1;
		labelConstraints.gridy = 1;
		frequencyLabel = new JLabel("Usage Frequency");
		add(frequencyLabel, labelConstraints);

		initAddGroupButton();
		initRemoveLastGroupButton();

		keywordGroupList = new ArrayList<KeywordGroupPanel>();
	}

	private void initRemoveLastGroupButton() {
		removeLastGroupButton = new JButton("Remove last group");
		removeLastGroupButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				removeLastKeywordGroup();
			}
		});
		addRemoveLastGroupButton(2);

	}

	private void addRemoveLastGroupButton(int gridy) {
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.anchor = GridBagConstraints.LINE_START;
		buttonConstraints.gridx = 1;
		buttonConstraints.gridy = gridy;
		buttonConstraints.weightx = 1;
		add(removeLastGroupButton, buttonConstraints);
	}

	private void removeLastKeywordGroup() {
		if (!keywordGroupList.isEmpty()) {

			final int lastIndex = keywordGroupList.size() - 1;

			final KeywordGroupPanel keywordGroupLinkPanel = keywordGroupList
					.get(lastIndex);
			final Container parentContainer = keywordGroupLinkPanel.getParent();
			parentContainer.remove(keywordGroupLinkPanel);
			parentContainer.revalidate();
			parentContainer.repaint();

			keywordGroupList.remove(lastIndex);
			// packFrame();
		}

	}

	private void initAddGroupButton() {
		addGroupButton = new JButton("Add group");
		addGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addKeywordGroup();
			}
		});

		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraints.anchor = GridBagConstraints.LINE_START;
		buttonConstraints.weightx = 0;
		buttonConstraints.gridx = 0;
		buttonConstraints.gridy = 2;

		add(addGroupButton, buttonConstraints);
	}

	private void addKeywordGroup() {
		final KeywordGroupPanel keywordGroupLinkPanel = new KeywordGroupPanel(
				(JFrame) SwingUtilities.getRoot(this),
				keywordGroupList.size() + 1);

		GridBagConstraints panelConstraints = new GridBagConstraints();
		panelConstraints.weightx = 0;
		panelConstraints.fill = GridBagConstraints.HORIZONTAL;
		panelConstraints.anchor = GridBagConstraints.LINE_START;
		panelConstraints.gridwidth = 2;
		panelConstraints.gridx = 0;
		panelConstraints.gridy = keywordGroupList.size() + 2;
		add(keywordGroupLinkPanel, panelConstraints, getComponentCount()
				- TWO_BUTTONS_COUNT);

		panelConstraints.gridwidth = 1;

		panelConstraints.gridy = keywordGroupList.size() + 3;
		add(addGroupButton, panelConstraints);

		addRemoveLastGroupButton(keywordGroupList.size() + 3);

		revalidate();
		repaint();

		keywordGroupList.add(keywordGroupLinkPanel);
	}

	// TODO:
	public List<KeywordGroupInputs> getKeywordGroupInputsList() {

		final List<KeywordGroupInputs> inputsList = new ArrayList<KeywordGroupInputs>();
		for (KeywordGroupPanel keywordGroup : keywordGroupList) {
			KeywordGroupInputs inputs = keywordGroup.getInputs();
			inputsList.add(inputs);
		}

		return inputsList;
	}
}
