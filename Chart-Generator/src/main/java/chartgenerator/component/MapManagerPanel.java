package chartgenerator.component;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapManagerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int TWO_BUTTONS_COUNT = 2;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MapManagerPanel.class);

	private JLabel titleLabel;
	private JLabel frequencyLabel;
	private JButton addButton;
	private JButton removeButton;

	private List<MapPanel> mapPanelList;

	public static void main(String args[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new MapManagerPanel("Platform"));
				frame.pack();
				frame.setVisible(true);
			}
		});

	}

	public MapManagerPanel(String name) {
		super();

		initComponents(name);

		mapPanelList = new ArrayList<MapPanel>();
	}

	private void initComponents(String name) {
		setAlignmentX(LEFT_ALIGNMENT);
		this.setLayout(new GridBagLayout());

		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.fill = GridBagConstraints.HORIZONTAL;
		labelConstraints.anchor = GridBagConstraints.LINE_START;
		labelConstraints.weightx = 0;
		labelConstraints.gridwidth = 2;

		labelConstraints.gridx = 0;
		labelConstraints.gridy = 0;
		titleLabel = new JLabel(name + " to use:");
		add(titleLabel, labelConstraints);

		labelConstraints.insets = new Insets(0, 10, 0, 0);
		labelConstraints.gridwidth = 1;
		labelConstraints.weightx = 1;
		labelConstraints.fill = GridBagConstraints.NONE;

		labelConstraints.gridx = 1;
		labelConstraints.gridy = 1;
		frequencyLabel = new JLabel("Usage Frequency");
		add(frequencyLabel, labelConstraints);

		initAddButton();

		initRemoveLastButton();
	}

	private void initRemoveLastButton() {
		removeButton = new JButton("Remove last element");
		removeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				removeLastElement();
			}
		});
		addRemoveLastButton(2);

	}

	private void addRemoveLastButton(int gridy) {
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.anchor = GridBagConstraints.LINE_START;
		buttonConstraints.gridx = 1;
		buttonConstraints.gridy = gridy;
		buttonConstraints.weightx = 1;
		add(removeButton, buttonConstraints);
	}

	private void removeLastElement() {

		if (getComponentCount() > 4) {
			final Component mapPanel = getComponent(getComponentCount() - 3);
			final Container parentContainer = mapPanel.getParent();
			parentContainer.remove(mapPanel);
			parentContainer.revalidate();
			parentContainer.repaint();
			mapPanelList.remove(mapPanelList.size() - 1);
			// packFrame();
		}

	}

	private void initAddButton() {
		addButton = new JButton("Add element");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addElement();
			}
		});

		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraints.anchor = GridBagConstraints.LINE_START;
		buttonConstraints.weightx = 0;
		buttonConstraints.gridx = 0;
		buttonConstraints.gridy = 2;

		add(addButton, buttonConstraints);
	}

	private void addElement() {
		final MapPanel mapPanel = new MapPanel();
		getComponentCount();
		GridBagConstraints panelConstraints = new GridBagConstraints();
		panelConstraints.weightx = 0;
		panelConstraints.fill = GridBagConstraints.HORIZONTAL;
		panelConstraints.anchor = GridBagConstraints.LINE_START;
		panelConstraints.gridwidth = 2;
		panelConstraints.gridx = 0;
		panelConstraints.gridy = getComponentCount();
		add(mapPanel, panelConstraints, getComponentCount() - TWO_BUTTONS_COUNT);
		mapPanelList.add(mapPanel);

		panelConstraints.gridwidth = 1;

		panelConstraints.gridy = getComponentCount();
		add(addButton, panelConstraints);

		addRemoveLastButton(getComponentCount());

		revalidate();
		repaint();
	}

	public Map<String, Integer> getFrequencyMap() {
		Map<String, Integer> frequencyMap = new HashMap<String, Integer>();

		for (MapPanel mapPanel : mapPanelList) {
			String key = mapPanel.getTextField().getText();
			int value = Integer.parseInt(String.valueOf(mapPanel
					.getFrequencySpinner().getValue()));

			LOGGER.debug("MapPanel: {}, {}", key, value);
			frequencyMap.put(key, value);
		}
		return frequencyMap;
	}
}
