/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartgenerator.component;

import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
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
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chartgenerator.model.AnchorType;
import chartgenerator.model.KeywordData;
import chartgenerator.model.LinkType;

/**
 *
 * @author dmytroplekhotkin
 */
public class KeywordGroupSettingsDialog {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(KeywordGroupSettingsDialog.class);
	private JDialog settingsDialog;
	private JFileChooser keywordsFileChooser;

	private List<KeywordTypeProperty> anchorPropertyList;
	private List<KeywordTypeProperty> linkPropertyList;

	public KeywordGroupSettingsDialog() {
		super();
	}

	public KeywordGroupSettingsDialog(String groupName, JFrame owner) {
		settingsDialog = new JDialog(owner);
		settingsDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		settingsDialog.setTitle("Keyword " + groupName + " data");
		initComponents();
	}

	private void initComponents() {
		keywordsFileChooser = new JFileChooser();
		settingsDialog.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 1;
		c.gridy = 0;
		c.gridx = 0;
		JPanel anchorsHeader = createHeaderRow("Data", "Usage frequency(%)",
				"Keyword file(.txt)");
		settingsDialog.add(anchorsHeader, c);

		anchorPropertyList = new ArrayList<KeywordTypeProperty>();

		for (AnchorType anchorType : AnchorType.values()) {
			c.gridy++;
			final KeywordTypeProperty anchor = new KeywordTypeProperty(
					anchorType.toString(), keywordsFileChooser);
			settingsDialog.add(anchor, c);
			anchorPropertyList.add(anchor);
		}

		c.gridy++;
		JPanel linksHeader = createHeaderRow("URLs", "Usage frequency(%)",
				"URLs file(.txt)");
		settingsDialog.add(linksHeader, c);
		linkPropertyList = new ArrayList<KeywordTypeProperty>();

		for (LinkType linkType : LinkType.values()) {
			c.gridy++;
			final KeywordTypeProperty link = new KeywordTypeProperty(
					linkType.toString(), keywordsFileChooser);
			settingsDialog.add(link, c);
			linkPropertyList.add(link);
		}

	
		settingsDialog.pack();

	}

	private JPanel createHeaderRow(String dataTitle, String frequencyTitle,
			String fileTitle) {
		GridBagConstraints c = new GridBagConstraints();
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		c.insets = new Insets(0, 10, 0, 10);

		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		panel.add(new JLabel(dataTitle), c);

		c.weightx = 0.1;
		c.anchor = GridBagConstraints.CENTER;
		panel.add(new JLabel(frequencyTitle), c);

		c.weightx = 0.4;
		c.anchor = GridBagConstraints.LINE_START;
		panel.add(new JLabel(fileTitle), c);

		return panel;
	}

	public Dialog getDialog() {
		return settingsDialog;
	}

	public List<KeywordData> getAnchorData() {
		final List<KeywordData> anchorData = new ArrayList<KeywordData>();
		for (KeywordTypeProperty anchorProperty : anchorPropertyList) {
			final KeywordData data = anchorProperty.getKeywordData();
			anchorData.add(data);
		}

		return anchorData;
	}

	public List<KeywordData> getLinkData() {
		final List<KeywordData> linkData = new ArrayList<KeywordData>();
		for (KeywordTypeProperty linkProperty : linkPropertyList) {
			final KeywordData data = linkProperty.getKeywordData();
			linkData.add(data);
		}

		return linkData;
	}
}
