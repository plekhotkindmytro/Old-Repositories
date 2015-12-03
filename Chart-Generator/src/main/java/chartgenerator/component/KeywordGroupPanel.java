package chartgenerator.component;

import chartgenerator.inputs.KeywordGroupInputs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dialog.ModalityType;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class KeywordGroupPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private KeywordGroupSettingsDialog keywordGroupDialog;
	private JLabel groupNameLabel;
	private JSpinner frequencySpinner;

	private final int index;
	private final String groupLabel;
	private final String groupName;
	private final JFrame owner;

	public KeywordGroupPanel(JFrame owner, int groupIndex) {
		super();
		setLayout(new GridBagLayout());
		index = groupIndex;
		groupLabel = "Group " + index;
		groupName = "KG" + index;
		this.owner = owner;
		initComponents();
	}

	private void initComponents() {
		initKeywordGroupDialog();
		initGroupNameLabel();
		initFrequencySpinner();
	}

	private void initKeywordGroupDialog() {
		keywordGroupDialog = new KeywordGroupSettingsDialog(groupLabel, owner);
	}

	private void initGroupNameLabel() {

		groupNameLabel = new JLabel(groupLabel);
		groupNameLabel
				.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		groupNameLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keywordGroupDialog.getDialog().setVisible(true);
			}
		});

		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.fill = GridBagConstraints.HORIZONTAL;
		labelConstraints.anchor = GridBagConstraints.LINE_START;
		labelConstraints.weightx = 0;
		labelConstraints.insets = new Insets(0, 10, 0, 80);
		labelConstraints.gridx = 0;
		labelConstraints.gridx = 0;

		add(groupNameLabel, labelConstraints);
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

	public KeywordGroupInputs getInputs() {
		final String value = String.valueOf(frequencySpinner.getValue());
		int frequency = Integer.parseInt(value);
		KeywordGroupInputs inputs = new KeywordGroupInputs(groupName,
				frequency, keywordGroupDialog.getAnchorData(),
				keywordGroupDialog.getLinkData());

		return inputs;
	}

}
