package com.blackmud.tools.RecipeManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Enumeration;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.blackmud.tools.RecipeManager.data.Item;
import com.blackmud.tools.RecipeManager.data.Material;

public class MaterialsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel buttonPanel = null;
	private JPanel mainPanel = null;
	private JButton addButton = null;
	private JButton cancelButton = null;
	private boolean okClicked = false;
	private JLabel materialVnumLabel = null;
	private JFormattedTextField vnumTextField = null;
	private JLabel quantityLabel = null;
	private JFormattedTextField quantityTextField = null;
	private JButton vnumChooseButton = null;
	private RecipeManager manager = null;
	private JLabel qualityLabel = null;
	private JComboBox qualityComboBox = null;
	private long oldVnum = 0;

	/**
	 * @param owner
	 */
	public MaterialsDialog(RecipeManager manager) {
		super(manager);
		this.manager = manager;
		initialize();
	}
	
	public MaterialsDialog(RecipeManager manager, Material material) {
		super(manager);
		this.manager = manager;
		initialize();
		this.setTitle("Edit Material");
		getVnumTextField().setText(""+material.getMaterialVnum());
		resetFields(manager.getItemManager().getItem(material.getMaterialVnum()));
		if(material.getQuality() > 0)
			getQualityComboBox().setSelectedIndex(material.getQuality()-1);
		getQuantityTextField().setText(""+material.getQuantity());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setTitle("Add Material");
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
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.NONE;
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 1;
			qualityLabel = new JLabel();
			qualityLabel.setText("Quality:");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 2;
			gridBagConstraints5.insets = new Insets(5, 0, 5, 5);
			gridBagConstraints5.gridy = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.NONE;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints4.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.gridy = 2;
			quantityLabel = new JLabel();
			quantityLabel.setText("Quantity:");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.NONE;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridy = 0;
			materialVnumLabel = new JLabel();
			materialVnumLabel.setText("Material VNUM:");
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			mainPanel.add(materialVnumLabel, gridBagConstraints1);
			mainPanel.add(getVnumTextField(), gridBagConstraints2);
			mainPanel.add(quantityLabel, gridBagConstraints3);
			mainPanel.add(getQuantityTextField(), gridBagConstraints4);
			mainPanel.add(getVnumChooseButton(), gridBagConstraints5);
			mainPanel.add(qualityLabel, gridBagConstraints);
			mainPanel.add(getQualityComboBox(), gridBagConstraints6);
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
	private JFormattedTextField getVnumTextField() {
		if (vnumTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(new Integer(0));
			vnumTextField = new JFormattedTextField(nf);
			vnumTextField.setValue(new Integer(0));
			vnumTextField.setColumns(10);
			
			vnumTextField.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					resetFields(manager.getItemManager().getItem(RecipeEditor.parseLong(getVnumTextField().getText())));
				}
			});
			vnumTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					resetFields(manager.getItemManager().getItem(RecipeEditor.parseLong(getVnumTextField().getText())));
				}
			});
		}
		return vnumTextField;
	}
	
	protected void resetFields(Item selectedItem) {
		if(selectedItem != null) {
			if(oldVnum != selectedItem.getVnum()) {
				getVnumTextField().setText(""+selectedItem.getVnum());
				
				if(selectedItem.getType() == 35 && selectedItem.getValue4() > 0) {
					DefaultComboBoxModel model = new DefaultComboBoxModel();
					String[] qualityDescs = RecipeEditor.QUALITY_DESCS[selectedItem.getValue4()-1];
					for(int i = 0; i < qualityDescs.length; i++) {
						if(qualityDescs[i].length() > 0) {
							model.addElement(qualityDescs[i]);
						}
					}
					getQualityComboBox().setModel(model);
					getQualityComboBox().setEnabled(true);
				} else {
					getQualityComboBox().setEnabled(false);
					getQualityComboBox().setModel(new DefaultComboBoxModel());
				}
				oldVnum = selectedItem.getVnum();
			}
		} else {
			getVnumTextField().setText("-1");
			getQualityComboBox().setEnabled(false);
			getQualityComboBox().setModel(new DefaultComboBoxModel());
		}
	}

	/**
	 * This method initializes quantityTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getQuantityTextField() {
		if (quantityTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(new Integer(1));
			nf.setMaximum(new Integer(100));
			quantityTextField = new JFormattedTextField(nf);
			quantityTextField.setValue(new Integer(1));
			quantityTextField.setColumns(10);
		}
		return quantityTextField;
	}

	/**
	 * This method initializes vnumChooseButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getVnumChooseButton() {
		if (vnumChooseButton == null) {
			vnumChooseButton = new JButton();
			vnumChooseButton = new JButton();
			vnumChooseButton.setIcon(new ImageIcon(getClass().getResource("/com/blackmud/tools/RecipeManager/images/search.png")));
			vnumChooseButton.setPreferredSize(new Dimension(20, 20));
			vnumChooseButton.setMinimumSize(new Dimension(20, 20));
			vnumChooseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					chooseVnum(RecipeEditor.parseInt(getVnumTextField().getText()));
				}
			});
		}
		return vnumChooseButton;
	}
	
	protected void chooseVnum(long selectedVnum) {
		final JPopupMenu chooser = new JPopupMenu();
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300,300));
		panel.setMinimumSize(new Dimension(300,300));
		panel.setLayout(new BorderLayout());
		JScrollPane sp = new JScrollPane();
		panel.add(sp, BorderLayout.CENTER);
		
		final JTree itemTree = new JTree(manager.getItemManager().getItemTreeRoot());
		itemTree.setRootVisible(false);
		itemTree.setShowsRootHandles(true);
		itemTree.addMouseListener(new java.awt.event.MouseAdapter() {   
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				TreePath selectedPath = itemTree.getPathForLocation(evt.getX(), evt.getY());
				if(selectedPath != null) {
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectedPath.getLastPathComponent();
					if(evt.getClickCount() == 2 && selectedNode != null && selectedNode.getUserObject() instanceof Item) {
						Item selectedItem = (Item)selectedNode.getUserObject();
						getVnumTextField().setText(""+selectedItem.getVnum());
						resetFields(manager.getItemManager().getItem(selectedItem.getVnum()));
						chooser.setVisible(false);
					}
				}
			} 
		});
		if(selectedVnum != -1) {
			Enumeration e = ((DefaultMutableTreeNode)itemTree.getModel().getRoot()).breadthFirstEnumeration();
			DefaultMutableTreeNode curNode = null;
			while(e.hasMoreElements()) {
				curNode = (DefaultMutableTreeNode)e.nextElement();
				if(curNode.getUserObject() instanceof Item) {
					if(((Item)curNode.getUserObject()).getVnum() == selectedVnum) {
						final TreePath path = new TreePath(curNode.getPath());
						itemTree.expandPath(path);
						itemTree.setSelectionPath(path);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								itemTree.scrollPathToVisible(path);
							}
						});
					}
				}
			}
		}
		
		sp.setViewportView(itemTree);
		chooser.add(panel);
		chooser.show(getVnumChooseButton(), 0, getVnumChooseButton().getHeight());
	}

	/**
	 * This method initializes qualityTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JComboBox getQualityComboBox() {
		if (qualityComboBox == null) {
			qualityComboBox = new JComboBox();
			qualityComboBox.setEnabled(false);
		}
		return qualityComboBox;
	}
	
	public long getMaterialVnum() {
		return RecipeEditor.parseLong(getVnumTextField().getText());
	}
	
	public int getQuality() {
		Item selectedItem = manager.getItemManager().getItem(RecipeEditor.parseLong(getVnumTextField().getText()));
		if(selectedItem.getType() == 35 && selectedItem.getValue4() > 0)
			return getQualityComboBox().getSelectedIndex()+1;
		return 0;
	}
	
	public int getQuantity() {
		return RecipeEditor.parseInt(getQuantityTextField().getText());
	}
}
