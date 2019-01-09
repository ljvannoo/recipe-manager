package com.blackmud.tools.RecipeManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;

import com.blackmud.tools.RecipeManager.data.Server;

public class LoginDialog extends JDialog {
	public static final int LOGIN_BUTTON = 0;
	public static final int CANCEL_BUTTON = 1;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel buttonPanel = null;
	private JPanel mainPanel = null;
	private JButton loginButton = null;
	private JButton cancelButton = null;
	
	private RecipeManager app = null;
	private JPanel titlePanel = null;
	private JLabel titleLabel = null;
	private JLabel usernameLabel = null;
	private JLabel passwordLabel = null;
	private JLabel databaseLabel = null;
	private JTextField usernameTextField = null;
	private JPasswordField passwordTextField = null;
	private JComboBox databaseComboBox = null;
	
	private int buttonClicked = CANCEL_BUTTON;
	
	public LoginDialog(RecipeManager app) {
		super(app);
		this.app = app;
		initialize();
	}
	
	private void initialize() {
		this.setSize(400, 175);
		this.setTitle("Login");
		this.setModal(true);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
//		this.pack();
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
			jContentPane.add(getMainPanel(), BorderLayout.CENTER);
			jContentPane.add(getTitlePanel(), BorderLayout.NORTH);
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			buttonPanel.add(getLoginButton(), null);
			buttonPanel.add(getCancelButton(), null);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes mainPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.NONE;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints2.gridy = 2;
			databaseLabel = new JLabel();
			databaseLabel.setText("Database:");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridy = 1;
			passwordLabel = new JLabel();
			passwordLabel.setText("Password:");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			usernameLabel = new JLabel();
			usernameLabel.setText("Username:");
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			mainPanel.add(usernameLabel, gridBagConstraints);
			mainPanel.add(passwordLabel, gridBagConstraints1);
			mainPanel.add(databaseLabel, gridBagConstraints2);
			mainPanel.add(getUsernameTextField(), gridBagConstraints3);
			mainPanel.add(getPasswordTextField(), gridBagConstraints4);
			mainPanel.add(getDatabaseComboBox(), gridBagConstraints5);
		}
		return mainPanel;
	}
	
	/**
	 * This method initializes addButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLoginButton() {
		if (loginButton == null) {
			loginButton = new JButton();
			loginButton.setText("Login");
			loginButton.setPreferredSize(new Dimension(73, 26));
			loginButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					buttonClicked = LOGIN_BUTTON;
					setVisible(false);
				}
			});
		}
		return loginButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.setPreferredSize(new Dimension(73, 26));
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					buttonClicked = CANCEL_BUTTON;
					setVisible(false);
				}
			});
		}
		return cancelButton;
	}

	/**
	 * This method initializes titlePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTitlePanel() {
		if (titlePanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			
			titlePanel = new JPanel();
			titlePanel.setLayout(flowLayout);
			titlePanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			titlePanel.setBackground(Color.darkGray);
			titlePanel.add(getTitleLabel(), null);
		}
		return titlePanel;
	}

	/**
	 * This method initializes usernameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getUsernameTextField() {
		if (usernameTextField == null) {
			usernameTextField = new JTextField();
			usernameTextField.setColumns(20);
			usernameTextField.setPreferredSize(new Dimension(224, 20));
			usernameTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getLoginButton().doClick();
				}
			});
		}
		return usernameTextField;
	}

	/**
	 * This method initializes passwordTextField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getPasswordTextField() {
		if (passwordTextField == null) {
			passwordTextField = new JPasswordField();
			passwordTextField.setColumns(20);
			passwordTextField.setPreferredSize(new Dimension(224, 20));
			passwordTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getLoginButton().doClick();
				}
			});
		}
		return passwordTextField;
	}

	/**
	 * This method initializes databaseComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getDatabaseComboBox() {
		if (databaseComboBox == null) {
			databaseComboBox = new JComboBox();
			databaseComboBox.setPreferredSize(new Dimension(224, 20));

			DefaultComboBoxModel model = new DefaultComboBoxModel();
			int i = 0;
			int selectedIndex = 0;
			String temp = null;
			Server serverInfo = null;
			
			while((temp = app.getConfig().getProperty("server"+i+".title")) != null) {
				serverInfo = new Server(temp);
				serverInfo.setName(app.getConfig().getProperty("server"+i+".name"));
				serverInfo.setURL(app.getConfig().getProperty("server"+i+".url"));
				temp = app.getConfig().getProperty("server"+i+".default");
				if(temp != null && temp.equals("true")) {
					selectedIndex = i;
				}
				model.addElement(serverInfo);
				i++;
			}
			databaseComboBox.setModel(model);
			databaseComboBox.setSelectedIndex(selectedIndex);
		}
		return databaseComboBox;
	}
	
	public int getButtonClicked() {
		return buttonClicked;
	}

	public Server getSelectedServer() {
		Server server = (Server)getDatabaseComboBox().getSelectedItem();
		server.setUsername(getUsernameTextField().getText());
		server.setPassword(new String(passwordTextField.getPassword()));
		
		return server;
	}
	
	public void reset() {
		getPasswordTextField().setText("");
		getUsernameTextField().requestFocus();
		getUsernameTextField().selectAll();
	}
	
	public void setTitle(String title) {
		super.setTitle(title);
		getTitleLabel().setText(title);
	}
	
	private JLabel getTitleLabel() {
		if(titleLabel == null) {
			titleLabel = new JLabel();
			titleLabel.setText("BlackMUD Recipe Manager - Login");
			titleLabel.setForeground(Color.white);
			titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		}
		return titleLabel;
	}
}  //  @jve:decl-index=0:visual-constraint="-4,10"
