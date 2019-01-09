package com.blackmud.tools.RecipeManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.blackmud.tools.RecipeManager.data.NPC;
import com.blackmud.tools.RecipeManager.data.Vendor;

public class VendorDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel buttonPanel = null;

	private JPanel mainPanel = null;

	private JButton addButton = null;

	private JButton cancelButton = null;

	private boolean okClicked = false;

	private RecipeManager manager = null;

	private JLabel vendorVnumLabel = null;

	private JTextField vnumTextField = null;

	private JLabel recipeCostLabel = null;

	private JTextField recipeCostTextField = null;

	private JLabel minLevelLabel = null;

	private JTextField minLevelTextField = null;

	private JLabel allowedClassesLabel = null;

	private JTextField allowedClassesTextField = null;

	private JButton vnumChooseButton = null;

	private JButton classChooseButton = null;

	/**
	 * @param owner
	 */
	public VendorDialog(RecipeManager manager) {
		super(manager);
		this.manager = manager;
		initialize();
	}
	
	public VendorDialog(RecipeManager manager, Vendor vendor) {
		super(manager);
		this.manager = manager;
		initialize();
		this.setTitle("Edit Vendor");
//		getVnumTextField().setText(""+material.getMaterialVnum());
//		getQuantityTextField().setText(""+material.getQuantity());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setTitle("Add Vendor");
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
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 2;
			gridBagConstraints9.insets = new Insets(5, 0, 5, 5);
			gridBagConstraints9.gridy = 3;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.insets = new Insets(5, 0, 5, 5);
			gridBagConstraints8.gridy = 0;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.NONE;
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints6.gridy = 3;
			allowedClassesLabel = new JLabel();
			allowedClassesLabel.setText("Allowed classes:");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.NONE;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints4.gridy = 2;
			minLevelLabel = new JLabel();
			minLevelLabel.setText("Minimum level:");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.NONE;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints2.gridy = 1;
			recipeCostLabel = new JLabel();
			recipeCostLabel.setText("Recipe cost:");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.NONE;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.gridy = 0;
			vendorVnumLabel = new JLabel();
			vendorVnumLabel.setText("Vendor VNUM:");
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			mainPanel.add(vendorVnumLabel, gridBagConstraints);
			mainPanel.add(getVnumTextField(), gridBagConstraints1);
			mainPanel.add(recipeCostLabel, gridBagConstraints2);
			mainPanel.add(getRecipeCostTextField(), gridBagConstraints3);
			mainPanel.add(minLevelLabel, gridBagConstraints4);
			mainPanel.add(getMinLevelTextField(), gridBagConstraints5);
			mainPanel.add(allowedClassesLabel, gridBagConstraints6);
			mainPanel.add(getAllowedClassesTextField(), gridBagConstraints7);
			mainPanel.add(getVnumChooseButton(), gridBagConstraints8);
			mainPanel.add(getClassChooseButton(), gridBagConstraints9);
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

	public boolean okClicked() {
		return okClicked;
	}

	/**
	 * This method initializes vnumTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getVnumTextField() {
		if (vnumTextField == null) {
			vnumTextField = new JTextField();
			vnumTextField.setText("0");
			vnumTextField.setColumns(10);
		}
		return vnumTextField;
	}

	/**
	 * This method initializes recipeCostTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getRecipeCostTextField() {
		if (recipeCostTextField == null) {
			recipeCostTextField = new JTextField();
			recipeCostTextField.setText("0");
			recipeCostTextField.setColumns(10);
		}
		return recipeCostTextField;
	}

	/**
	 * This method initializes minLevelTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMinLevelTextField() {
		if (minLevelTextField == null) {
			minLevelTextField = new JTextField();
			minLevelTextField.setText("1");
			minLevelTextField.setColumns(10);
		}
		return minLevelTextField;
	}

	/**
	 * This method initializes allowedClassesTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getAllowedClassesTextField() {
		if (allowedClassesTextField == null) {
			allowedClassesTextField = new JTextField();
			allowedClassesTextField.setColumns(10);
		}
		return allowedClassesTextField;
	}

	/**
	 * This method initializes vnumChooseButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getVnumChooseButton() {
		if (vnumChooseButton == null) {
			vnumChooseButton = new JButton();
			vnumChooseButton.setIcon(new ImageIcon(getClass().getResource("/com/blackmud/tools/RecipeManager/images/search.png")));
			vnumChooseButton.setPreferredSize(new Dimension(20, 20));
			vnumChooseButton.setMinimumSize(new Dimension(20, 20));
			vnumChooseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					chooseVendor(RecipeEditor.parseLong(getVnumTextField().getText()));
				}
			});
		}
		return vnumChooseButton;
	}

	/**
	 * This method initializes classChooseButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getClassChooseButton() {
		if (classChooseButton == null) {
			classChooseButton = new JButton();
			classChooseButton.setIcon(new ImageIcon(getClass().getResource("/com/blackmud/tools/RecipeManager/images/search.png")));
			classChooseButton.setPreferredSize(new Dimension(20, 20));
			classChooseButton.setMinimumSize(new Dimension(20, 20));
			classChooseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					chooseClasses(getAllowedClassesTextField().getText());
				}
			});
		}
		return classChooseButton;
	}
	
	protected void chooseVendor(long selectedVnum) {
		final JPopupMenu chooser = new JPopupMenu();
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300,300));
		panel.setMinimumSize(new Dimension(300,300));
		panel.setLayout(new BorderLayout());
		JScrollPane sp = new JScrollPane();
		panel.add(sp, BorderLayout.CENTER);
		
		final JList mobList = new JList();
		DefaultListModel model = new DefaultListModel();
		Vector<NPC> npcList = manager.getNPCManager().getNPCList();
		for(int i = 0; i < npcList.size(); i++) {
			model.addElement(npcList.get(i));
		}
		mobList.setModel(model);
//		final JTree itemTree = new JTree(manager.getItemManager().getItemTreeRoot());
//		itemTree.setRootVisible(false);
//		itemTree.setShowsRootHandles(true);
//		itemTree.addMouseListener(new java.awt.event.MouseAdapter() {   
//			public void mouseReleased(java.awt.event.MouseEvent evt) {
//				TreePath selectedPath = itemTree.getPathForLocation(evt.getX(), evt.getY());
//				if(selectedPath != null) {
//					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectedPath.getLastPathComponent();
//					if(evt.getClickCount() == 2 && selectedNode != null && selectedNode.getUserObject() instanceof Item) {
//						Item selectedItem = (Item)selectedNode.getUserObject();
//						resetFields(selectedItem);
//						chooser.setVisible(false);
//					}
//				}
//			} 
//		});
//		if(selectedVnum != -1) {
//			Enumeration e = ((DefaultMutableTreeNode)itemTree.getModel().getRoot()).breadthFirstEnumeration();
//			DefaultMutableTreeNode curNode = null;
//			while(e.hasMoreElements()) {
//				curNode = (DefaultMutableTreeNode)e.nextElement();
//				if(curNode.getUserObject() instanceof Item) {
//					if(((Item)curNode.getUserObject()).getVnum() == selectedVnum) {
//						final TreePath path = new TreePath(curNode.getPath());
//						itemTree.expandPath(path);
//						itemTree.setSelectionPath(path);
//						SwingUtilities.invokeLater(new Runnable() {
//							public void run() {
//								itemTree.scrollPathToVisible(path);
//							}
//						});
//					}
//				}
//			}
//		}
//		
		sp.setViewportView(mobList);
		chooser.add(panel);
		chooser.show(getVnumChooseButton(), 0, getVnumChooseButton().getHeight());
	}
	
	protected void chooseClasses(String selectedClasses) {
		
	}
	
//	public long getMaterialVnum() {
//		return Long.parseLong(getVnumTextField().getText());
//	}
//	
//	public int getQuality() {
//		return Integer.parseInt(getQualityTextField().getText());
//	}
//	
//	public int getQuantity() {
//		return Integer.parseInt(getQuantityTextField().getText());
//	}
}
