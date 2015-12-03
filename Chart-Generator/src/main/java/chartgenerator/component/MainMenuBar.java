package chartgenerator.component;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu settingsMenu;
	private GlobalSettingsDialog settingsDialog;

	public MainMenuBar(JFrame owner) {
		super();
		initComponents(owner);
	}

	private void initComponents(JFrame owner) {
		settingsDialog = new GlobalSettingsDialog(owner);

		settingsMenu = new JMenu("File");
		JMenuItem menuItem = new JMenuItem("Settings");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsDialog.getDialog().setVisible(true);

			}
		});

		settingsMenu.add(menuItem);

		add(settingsMenu);
	}
}
