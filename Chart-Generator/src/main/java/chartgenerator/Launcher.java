/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartgenerator;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import chartgenerator.view.MainFrame;

/**
 *
 * @author dmytroplekhotkin
 */
public class Launcher {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MainFrame frame = new MainFrame();
				frame.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getFrame().setVisible(true);
				frame.getFrame().pack();
			}
		});
	}
}
