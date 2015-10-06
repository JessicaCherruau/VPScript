package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Class for the Graphical User Interface
 * Panel of the user interface containing the field for the authentication on the website
 * It uses the Grid Bag Layout
 * @author Jessica CHERRUAU
 *
 */
public class AuthenticationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPasswordField txtPassword;		// password field for the password
	private JTextField txtLogin;			// text field for the login (email address)
	private JTextField txtSaleNo;			/* text field for the sal number 
											(the number at the end of the URL of the sale on the website) */
	
	private int yPosition;		// incremental index to keep the count of the lines (gridy attribute)

	/**
	 * Constructor
	 * Put the fields and labels in the panel
	 */
	public AuthenticationPanel() {
		super();
		this.yPosition = 0;
		
		//define the layout
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[]{0, 0, 0};
		gbl_this.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		this.setLayout(gbl_this);
		
		txtLogin = this.addField("Login", false);
		txtPassword = (JPasswordField) this.addField("Mot de passe", true);
		txtSaleNo = this.addField("Numéro de vente", false);
	}
	
	/**
	 * Add to the panel a field (password or text) and its label
	 * 
	 * @param label text used for the field label
	 * @param isPasswordField true if JPasswordField has to be used, else false (JTextField) 
	 * @return the newly added field (can be JPasswordField or JTextField)
	 */
	public JTextField addField(String label, boolean isPasswordField){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 0);
		
		JLabel jLabel = new JLabel(label);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = this.yPosition;
		this.add(jLabel, gbc);
		
		JTextField txt = null; 
		if(isPasswordField)
			txt = new JPasswordField();
		else
			txt = new JTextField();
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = this.yPosition;
		this.add(txt, gbc);
		
		this.yPosition++;
		return txt;
	}
	
	/**
	 * @return text in the Login field
	 */
	public String getLogin(){
		return this.txtLogin.getText();
	}
	
	/**
	 * @return text in the Sale Number field
	 */
	public String getSaleNo(){
		return this.txtSaleNo.getText();
	}
	
	/**
	 * @return text in the Password Field
	 */
	protected String getPassword(){
		return new String(txtPassword.getPassword());
	}
}
