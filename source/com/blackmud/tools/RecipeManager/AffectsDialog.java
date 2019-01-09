package com.blackmud.tools.RecipeManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import com.blackmud.tools.RecipeManager.data.Affect;

public class AffectsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel buttonPanel = null;

	private JPanel mainPanel = null;

	private JButton addButton = null;

	private JButton cancelButton = null;

	private JLabel locationLabel = null;

	private JComboBox locationComboBox = null;

	private JLabel modifierLabel = null;

	private JFormattedTextField modifierTextField = null;
	
	private boolean okClicked = false;

	/**
	 * @param owner
	 */
	public AffectsDialog(Frame owner) {
		super(owner);
		initialize();
	}

	public AffectsDialog(Frame owner, Affect affect) {
		super(owner);
		initialize();
		this.setTitle("Edit Affect");
		getLocationComboBox().setSelectedIndex(affect.getLocation());
		getModifierTextField().setText(""+affect.getModifier());
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setTitle("Add Affect");
		this.setModal(true);
		this.setContentPane(getJContentPane());
		this.pack();
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
			buttonPanel.add(getAddButton(), null);
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
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.NONE;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints2.gridy = 1;
			modifierLabel = new JLabel();
			modifierLabel.setText("Modifier:");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			locationLabel = new JLabel();
			locationLabel.setText("Location:");
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			mainPanel.add(locationLabel, gridBagConstraints);
			mainPanel.add(getLocationComboBox(), gridBagConstraints1);
			mainPanel.add(modifierLabel, gridBagConstraints2);
			mainPanel.add(getModifierTextField(), gridBagConstraints3);
		}
		return mainPanel;
	}

	/**
	 * This method initializes addButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText("OK");
			addButton.setPreferredSize(new Dimension(73, 26));
			addButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					okClicked = true;
					setVisible(false);
				}
			});
		}
		return addButton;
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
					okClicked = false;
					setVisible(false);
				}
			});
		}
		return cancelButton;
	}

	/**
	 * This method initializes locationComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getLocationComboBox() {
		if (locationComboBox == null) {
			locationComboBox = new JComboBox();
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			for(int i = 0; i < Affect.AFFECTS.length; i++) {
				model.addElement(Affect.AFFECTS[i]);
			}
			locationComboBox.setModel(model);
		}
		return locationComboBox;
	}

	/**
	 * This method initializes modifierTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getModifierTextField() {
		if (modifierTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			modifierTextField = new JFormattedTextField(nf);
			modifierTextField.setColumns(10);
			modifierTextField.setValue(new Integer(0));
		}
		return modifierTextField;
	}
	
	public boolean okClicked() {
		return okClicked;
	}
	
	public int getAffectLocation() {
		return getLocationComboBox().getSelectedIndex();
	}
	
	public int getModifier() {
		return RecipeEditor.parseInt(getModifierTextField().getText());
	}
}
