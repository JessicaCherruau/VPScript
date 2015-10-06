package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ventePriveeScript.Main;

/**
 * Class for the Graphical User Interface
 * Entry point of the application
 * This frame is shown before the start of the script on the Vent Privee website
 * It allows to input the authentication parameters in order to execute the script
 * @author Jessica Cherruau
 *
 */
public class ConfigurationUI extends JFrame{

	private static final long serialVersionUID = 8036075562365859438L;
	private AuthenticationPanel authPanel; 		// mandatory fields to execute the script
	
	/**
	 * Start the configration frame
	 * @param args not used here
	 */
	public static void main(String[] args) {
		ConfigurationUI frame = new ConfigurationUI();
		frame.setVisible(true);
	}

	/**
	 * Constructor
	 * Uses a vertical Box Layout
	 */
	public ConfigurationUI(){
		//set attributes of the frame
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);
		
		// create the elements
		this.authPanel = new AuthenticationPanel();
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new StartListener());	// on click listener

		// add elements to the frame
		this.getContentPane().add(this.authPanel);
		this.getContentPane().add(btnStart);
	}
	
	/**
	 * On click listener for the Start button
	 * @author Jessica CHERRUAU
	 *
	 */
	private class StartListener implements ActionListener{

		@Override
		/**
		 * Start the script with the provided informations
		 */
		public void actionPerformed(ActionEvent arg0) {
			String email = authPanel.getLogin();
			String pwd = authPanel.getPassword();
			String saleNo = authPanel.getSaleNo();
			Main.startScript(email, pwd, saleNo);
		}
		
	}
}
