package chartgenerator.view;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import chartgenerator.component.MainMenuBar;

public class MainFrame {

	private JFrame mainFrame;

	private MainMenuBar menuBar;
	private TabbedPanel tabbedPanel;

	public MainFrame() {
		initComponents();
	}

	private void initComponents() {
		mainFrame = new JFrame();
		tabbedPanel = new TabbedPanel();
		mainFrame.getContentPane().add(tabbedPanel);

		menuBar = new MainMenuBar(mainFrame);
		mainFrame.setJMenuBar(menuBar);

	}

	public JFrame getFrame() {
		return mainFrame;
	}
}
