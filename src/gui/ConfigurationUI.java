package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ventePriveeScript.Main;

public class ConfigurationUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8036075562365859438L;
	private JPasswordField txtPassword;
	private JTextField txtLogin;
	
	public static void main(String[] args) {

		ConfigurationUI frame = new ConfigurationUI();
		frame.setVisible(true);

	}

	public ConfigurationUI(){
		setBounds(100, 100, 450, 300);
		JPanel content = new JPanel();
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[]{0, 0, 0};
		gbl_this.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		content.setLayout(gbl_this);
		
		JLabel lblBienvenue = new JLabel("Bienvenue");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		content.add(lblBienvenue, gbc);
		
		JLabel lblLogin = new JLabel("Login");
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		content.add(lblLogin, gbc);
		
		txtLogin = new JTextField();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		content.add(txtLogin, gbc);
		txtLogin.setColumns(10);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 2;
		content.add(lblMotDePasse, gbc);
		
		txtPassword = new JPasswordField();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 2;
		content.add(txtPassword, gbc);
		
		JButton btnStart = new JButton("Start");
		gbc.gridx = 1;
		gbc.gridy = 3;
		content.add(btnStart, gbc);
		
		btnStart.addActionListener(new StartListener());
		setContentPane(content);
	}
	
	private class StartListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String email = txtLogin.getText();
			String pwd = new String(txtPassword.getPassword());
			Main.startScript(email, pwd);
		}
		
	}
}
