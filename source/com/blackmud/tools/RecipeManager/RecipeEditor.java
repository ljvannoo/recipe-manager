package com.blackmud.tools.RecipeManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.blackmud.tools.RecipeManager.data.Affect;
import com.blackmud.tools.RecipeManager.data.Item;
import com.blackmud.tools.RecipeManager.data.Material;
import com.blackmud.tools.RecipeManager.data.Recipe;
import com.blackmud.tools.RecipeManager.data.Vendor;
import com.blackmud.tools.RecipeManager.database.DBMediator;
import com.blackmud.tools.RecipeManager.database.DBResult;

public class RecipeEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final String[] TYPES = {
		"NONE",
		"LIGHT",
		"SCROLL",
		"WAND",
		"STAFF",
		"WEAPON",
		"FIREWEAPON",
		"MISSILE",
		"TREASURE",
		"ARMOR",
		"POTION",
		"WORN",
		"OTHER",
		"TRASH",
		"TRAP",
		"CONTAINER",
		"NOTE",
		"DRINKCON",
		"KEY",
		"FOOD",
		"MONEY",
		"PEN",
		"BOAT",
		"AUDIO",
		"BOARD",
		"TREE",
		"ROCK",
		"HEAD",
		"TICKET",
		"TACK",
		"POISON",
		"SKIN",
		"DIE",
		"GODSTONE",
		"RECIPE",
		"MATERIAL"
	};
	private static final String[][] VALUE_LABELS = {
		{"Unused", "Unused", "Unused", "Unused"},                       // NONE       
		{"Unused", "Unused", "Life", "Unused"},                         // LIGHT      
		{"Spell level", "Spell", "Spell", "Spell"},                     // SCROLL     
		{"Spell level", "Max charges", "Charges left", "Spell"},        // WAND       
		{"Spell level", "Max charges", "Charges left", "Spell"},        // STAFF      
		{"Unused", "Dice count", "Dice size", "Weapon type"},           // WEAPON     
		{"Arrow type", "Min strength", "Hitroll", "Damroll"},           // FIREWEAPON 
		{"Arrow type", "Dice count", "Dice size", "Unused"},            // MISSILE    
		{"Unused", "Unused", "Unused", "Unused"},                       // TREASURE   
		{"AC affect", "Unused", "Unused", "Unused"},                    // ARMOR      
		{"Spell level", "Spell", "Spell", "Spell"},                     // POTION     
		{"Unused", "Unused", "Unused", "Unused"},                       // WORN       
		{"Unused", "Unused", "Unused", "Unused"},                       // OTHER      
		{"Unused", "Unused", "Unused", "Unused"},                       // TRASH      
		{"Trigger type", "Damage type", "Level", "Charges"},            // TRAP       
		{"Max contains", "Container flags", "Key", "Unused"},           // CONTAINER  
		{"Unused", "Unused", "Unused", "Unused"},                       // NOTE       
		{"Max units", "Units left", "Drink type", "Poisoned/Eternal"},  // DRINKCON   
		{"Type", "Unused", "Unused", "Unused"},                         // KEY        
		{"Hours", "Unused", "Unused", "Poisoned"},                      // FOOD       
		{"Amount", "Unused", "Unused", "Unused"},                       // MONEY      
		{"Unused", "Unused", "Unused", "Unused"},                       // PEN        
		{"Unused", "Unused", "Unused", "Unused"},                       // BOAT       
		{"Unused", "Unused", "Unused", "Unused"},                       // AUDIO      
		{"Unused", "Unused", "Unused", "Unused"},                       // BOARD      
		{"Unused", "Unused", "Unused", "Unused"},                       // TREE       
		{"Unused", "Unused", "Unused", "Unused"},                       // ROCK       
		{"Unused", "Unused", "Unused", "Unused"},                       // HEAD       
		{"Unused", "Unused", "Unused", "Unused"},                       // TICKET     
		{"Unused", "Unused", "Unused", "Unused"},                       // TACK       
		{"Unused", "Unused", "Unused", "Unused"},                       // POISON     
		{"Unused", "Unused", "Unused", "Unused"},                       // SKIN       
		{"Sides", "Flippable", "Weighted factor", "Weighted side"},     // DIE        
		{"Destination", "Use charges", "Attunement charges", "Unused"}, // GODSTONE   
		{"Recipe ID", "Runs", "Binds", "Unused"},                       // RECIPE
		{"Quality", "Unused", "Unused", "Quality Set"}                  // Material     
	};
	
	private static final String[] EXTRA_FLAGS = {"GLOW",
		"HUM",
		"METAL",
		"ANTI_DRUID",
		"ORGANIC",
		"INVISIBLE",
		"MAGIC",
		"NODROP",
		"BLESS",
		"ANTI_GOOD",
		"ANTI_EVIL",
		"ANTI_NEUTRAL",
		"ANTI_CLERIC",
		"ANTI_MAGE",
		"ANTI_THIEF",
		"ANTI_FIGHTER",
		"BRITTLE",
		"RESISTANT",
		"ANTI_MONK",
		"ANTI_MEN",
		"ANTI_WOMEN",
		"ANTI_SUN",
		"INSET",
		"FIGURINE",
		"WARPED",
		"RESTRICTED",
		"TWO_HANDED",
		"ARMED",
		"ARTIFACT",
		"PROTECTED",
		"POLEARM"
	};
	
	private static final String[] WEAR_FLAGS = {"TAKE",
		"FINGER",
		"NECK",
		"BODY",
		"HEAD",
		"LEGS",
		"FEET",
		"HANDS",
		"ARMS",
		"SHIELD",
		"ABOUT BODY",
		"WAIST",
		"WRIST",
		"WIELD",
		"HOLD",
		"FACE",
		"ABOUT LEGS",
		"HUNG",
		"STRAPPED"
	};

	public static final String QUALITY_DESCS[][] = {
	  {"ruined", "fair", "excellent", "", ""},
	  {"flawed", "flawless", "", "", ""},
	  {"coarse", "smooth", "perfect", "", ""},
	  {"vibrant", "glittering", "shimmering", "", ""},
	  {"faint", "glowing", "radiating", "", ""},
	  {"static", "placid", "violent", "unstable", ""},
	  {"small", "medium", "large", "", ""},
	  {"lesser", "greater", "", "", ""},
	  {"rotting", "infected", "plagued", "", ""},
	  {"flawed", "pristine", "", "", ""},
	  {"pristine", "weak", "powerful", "", ""},
	  {"blue-colored", "yellow-colored", "pale", "tattooed", ""},
	  {"broken", "worn", "", "", ""},
	  {"weak", "strong", "", "", ""},
	  {"broken", "cracked", "flawless", "", ""},
	  {"faded", "glowing", "", "", ""}
	};
	
	private JPanel recipePanel = null;
	private JPanel productPanel = null;
	private JLabel idLabel = null;
	private JLabel idValueLabel = null;
	private JLabel titleLabel = null;
	private JTextField titleTextField = null;
	private JPanel mainPanel = null;
	private JToolBar mainToolbar = null;

	private Recipe recipe = null;
	private JPopupMenu tabPopupMenu = null;
	private JLabel runsLabel = null;
	private JFormattedTextField runsTextField = null;
	private JLabel quantityLabel = null;
	private JFormattedTextField quantityTextField = null;
	private JPanel commentsPanel = null;
	private JTextArea commentsTextArea = null;
	private JScrollPane commentsScrollPane = null;
	private JLabel vnumLabel = null;
	private JFormattedTextField vnumTextField = null;
	private JLabel nameLabel = null;
	private JTextField nameTextField = null;
	private JLabel shortDescLabel = null;
	private JTextField shortDescTextField = null;
	private JLabel longDescLabel = null;
	private JTextField longDescTextField = null;
	private JPanel valuesPanel = null;
	private JButton vnumChooseButton = null;
	private JButton vnumClearButton = null;
	private JLabel typeFlagLabel = null;
	private JComboBox typeFlagComboBox = null;
	private JLabel weightLabel = null;
	private JFormattedTextField weightTextField = null;
	private JLabel extraFlagsLabel = null;
	private JFormattedTextField extraFlagsTextField = null;
	private JButton extraFlagsChooseButton = null;
	private JButton extraFlagsClearButton = null;
	private JLabel wearFlagsLabel = null;
	private JFormattedTextField wearFlagsTextField = null;
	private JButton wearFlagsChooseButton = null;
	private JButton wearFlagsClearButton = null;
	private JLabel egoLabel = null;
	private JFormattedTextField egoTextField = null;
	private JButton egoClearButton = null;
	private JLabel value5Label = null;
	private JFormattedTextField value5TextField = null;
	private JButton value5ClearButton = null;
	private JLabel value1Label = null;
	private JFormattedTextField value1TextField = null;
	private JLabel value2Label = null;
	private JFormattedTextField value2TextField = null;
	private JLabel value3Label = null;
	private JFormattedTextField value3TextField = null;
	private JLabel value4Label = null;
	private JFormattedTextField value4TextField = null;
	private JPanel materialsPanel = null;
	private JList affectsList = null;
	private JPanel affectsPanel = null;
	private JPanel vendorsPanel = null;
	private JList vendorList = null;
	private JList materialsList = null;
	
	private RecipeManager manager = null;
	private JScrollPane materialsListScrollPane = null;
	private JScrollPane affectsListScrollPane = null;
	private JLabel bindsLabel = null;
	private JCheckBox bindsCheckBox = null;
	private JScrollPane vendorScrollPane = null;
	/**
	 * This is the default constructor
	 */
	public RecipeEditor(Recipe recipe, RecipeManager manager) {
		super();
		this.recipe = recipe;
		this.manager = manager;
		initialize();
		setValues();
	}
	
	private void setValues() {
		Thread t0 = new Thread() {
			public void run() {
				DBMediator dbm = manager.getDBMediator();
				
				manager.busy(true);
				manager.setStatus("Setting recipe fields...");
				
				idValueLabel.setText(""+recipe.getID());
				getRunsTextField().setText(""+recipe.getRuns());
				getQuantityTextField().setText(""+recipe.getQuantity());
				getBindsCheckBox().setSelected(recipe.isBinds());
				getTitleTextField().setText(recipe.getTitle());
				getCommentsTextArea().setText((recipe.getComments().equals("null")?"":recipe.getComments()));
				
				getVnumTextField().setText(""+recipe.getVnum());
				enableFields((recipe.getVnum() == -1));
				getNameTextField().setText(recipe.getName());
				getShortDescTextField().setText(recipe.getShortDesc());
				getLongDescTextField().setText(recipe.getLongDesc());
				getTypeFlagComboBox().setSelectedIndex(recipe.getTypeFlag());
				getWeightTextField().setText(""+recipe.getWeight());
				getExtraFlagsTextField().setText(""+recipe.getExtraFlags());
				getWearFlagsTextField().setText(""+recipe.getWearFlags());
				getEgoTextField().setText(""+recipe.getEgo());
				getValue1TextField().setText(""+recipe.getValue1());
				getValue2TextField().setText(""+recipe.getValue2());
				getValue3TextField().setText(""+recipe.getValue3());
				getValue4TextField().setText(""+recipe.getValue4());
				getValue5TextField().setText(""+recipe.getValue5());
				if(dbm.connected() && recipe.getID() > 0) {
					manager.setStatus("Loading material list...");
					
					DBResult result = dbm.executeQuery("SELECT material_vnum, quantity, quality FROM bmud_recipe_materials WHERE recipe_id = "+recipe.getID()+" ORDER BY material_vnum");
					Material material = null;
					DefaultListModel model = new DefaultListModel();
					Item item = null;

					for(int i = 0; i < result.size(); i++) {
						material = new Material();
						material.setMaterialVnum(result.getLong(i, "material_vnum"));
						material.setQuality(result.getInt(i, "quality"));
						material.setQuantity(result.getInt(i, "quantity"));
						material.setMaterialShortDesc(manager.getItemManager().getItem(material.getMaterialVnum()).getShortDesc());
						if(material.getQuality() != -1) {
							item = manager.getItemManager().getItem(material.getMaterialVnum());
							material.setMaterialShortDesc(Item.transformDesc(material.getMaterialShortDesc(), material.getQuality(), item.getValue4()));
						}
						
						model.addElement(material);
//						System.out.println("Loading recipe #"+result.getInt(i, "id")+" - "+result.getString(i, "title"));
					}
					getMaterialsList().setModel(model);
					
					manager.setStatus("Loading affects list...");
					
					result = dbm.executeQuery("SELECT location, modifier FROM bmud_recipe_product_affects WHERE recipe_id = "+recipe.getID()+" ORDER BY location");
					Affect affect = null;
					model = new DefaultListModel();

					for(int i = 0; i < result.size(); i++) {
						affect = new Affect();
						affect.setLocation(result.getInt(i, "location"));
						affect.setModifier(result.getInt(i, "modifier"));
						
						model.addElement(affect);
//						System.out.println("Loading recipe #"+result.getInt(i, "id")+" - "+result.getString(i, "title"));
					}
					getAffectsList().setModel(model);
					
					manager.setStatus("Loading vendors list...");
					
					result = dbm.executeQuery("SELECT vendor_vnum, cost, min_level, allowed_classes FROM bmud_recipe_vendors WHERE recipe_id = "+recipe.getID()+" ORDER BY vendor_vnum");
					Vendor vendor = null;
					model = new DefaultListModel();

					for(int i = 0; i < result.size(); i++) {
						vendor = new Vendor();
						try {
							vendor.setVendorVnum(result.getLong(i, "vendor_vnum"));
							vendor.setCost(result.getLong(i, "cost"));
							vendor.setMinLevel(result.getInt(i, "min_level"));
							vendor.setAllowedClasses(result.getString(i, "allowed_classes"));

								vendor.setVendorShortDesc(manager.getNPCManager().getNPC(vendor.getVendorVnum()).getShortDesc());


							model.addElement(vendor);
						} catch(NullPointerException e) {
							System.out.println("ERROR: Failed to load vendor #" + vendor.getVendorVnum());
						}
//						System.out.println("Loading recipe #"+result.getInt(i, "id")+" - "+result.getString(i, "title"));
					}
					getVendorList().setModel(model);
				} else if(recipe.getID() <= 0) {
					System.out.println("INFO: Creating new recipe");
				} else {
					System.out.println("Error connecting to the database!");
				}
				manager.setStatus("Ready.");
				manager.busy(false);
			}
		};
		t0.start();
	}

	public static int parseInt(String number) {
		NumberFormat nf = NumberFormat.getInstance();
		Number n = null;
		int result = -1;
		try {
			n = nf.parse(number);
			result = n.intValue();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Parse Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static double parseDouble(String number) {
		NumberFormat nf = NumberFormat.getInstance();
		Number n = null;
		double result = -1.0;
		try {
			n = nf.parse(number);
			result = n.doubleValue();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Parse Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static long parseLong(String number) {
		NumberFormat nf = NumberFormat.getInstance();
		Number n = null;
		long result = -1l;
		try {
			n = nf.parse(number);
			result = n.longValue();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Parse Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return result;
	}
	
	protected void updateValueLabels() {
		int i = getTypeFlagComboBox().getSelectedIndex();

		if(i != -1) {
			value1Label.setText(VALUE_LABELS[i][0]+":");
			if(value1Label.getText().equals("Unused:")) {
				getValue1TextField().setText("0");
				getValue1TextField().setVisible(false);
				value1Label.setVisible(false);
			} else {
				getValue1TextField().setVisible(true);
				value1Label.setVisible(true);
			}
			
			value2Label.setText(VALUE_LABELS[i][1]+":");
			if(value2Label.getText().equals("Unused:")) {
				getValue2TextField().setText("0");
				getValue2TextField().setVisible(false);
				value2Label.setVisible(false);
			} else {
				getValue2TextField().setVisible(true);
				value2Label.setVisible(true);
			}
			
			value3Label.setText(VALUE_LABELS[i][2]+":");
			if(value3Label.getText().equals("Unused:")) {
				getValue3TextField().setText("0");
				getValue3TextField().setVisible(false);
				value3Label.setVisible(false);
			} else {
				getValue3TextField().setVisible(true);
				value3Label.setVisible(true);
			}
			
			value4Label.setText(VALUE_LABELS[i][3]+":");
			if(value4Label.getText().equals("Unused:")) {
				getValue4TextField().setText("0");
				getValue4TextField().setVisible(false);
				value4Label.setVisible(false);
			} else {
				getValue4TextField().setVisible(true);
				value4Label.setVisible(true);
			}
		} else {
			getValue1TextField().setText("0");
			getValue1TextField().setVisible(false);
			getValue2TextField().setText("0");
			getValue2TextField().setVisible(false);
			getValue3TextField().setText("0");
			getValue3TextField().setVisible(false);
			getValue4TextField().setText("0");
			getValue4TextField().setVisible(false);
			getValue5TextField().setText("0");
			getValue5TextField().setVisible(false);
		}
	}

	protected void enableFields(boolean b) {
//		getVnumTextField().setEnabled(!b);
		getVnumClearButton().setEnabled(!b);
		getNameTextField().setEnabled(b);
		getShortDescTextField().setEnabled(b);
		getLongDescTextField().setEnabled(b);
		getValue1TextField().setEnabled(b);
		getValue2TextField().setEnabled(b);
		getValue3TextField().setEnabled(b);
		getValue4TextField().setEnabled(b);
		getValue5TextField().setEnabled(b);
		getValue5ClearButton().setEnabled(b);
		getExtraFlagsTextField().setEnabled(b);
		//getExtraFlagsChooseButton().setEnabled(b);
		getExtraFlagsClearButton().setEnabled(b);
		getWearFlagsTextField().setEnabled(b);
		//getWearFlagsChooseButton().setEnabled(b);
		getWearFlagsClearButton().setEnabled(b);
		getEgoTextField().setEnabled(b);
		getEgoClearButton().setEnabled(b);
		getTypeFlagComboBox().setEnabled(b);
		getWeightTextField().setEnabled(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(664, 635);
		this.setLayout(new BorderLayout());
		this.add(getMainPanel(), BorderLayout.CENTER);
		this.add(getMainToolbar(), BorderLayout.NORTH);
	}

	/**
	 * This method initializes recipePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getRecipePanel() {
		if (recipePanel == null) {
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.gridx = 7;
			gridBagConstraints71.anchor = GridBagConstraints.WEST;
			gridBagConstraints71.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints71.weightx = 1.0D;
			gridBagConstraints71.gridy = 0;
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.gridx = 6;
			gridBagConstraints61.anchor = GridBagConstraints.WEST;
			gridBagConstraints61.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints61.gridy = 0;
			bindsLabel = new JLabel();
			bindsLabel.setText("Binds:");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridwidth = 8;
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints5.gridy = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.NONE;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.insets = new Insets(5, 5, 5, 25);
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridx = 5;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 4;
			gridBagConstraints31.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints31.weightx = 0.0D;
			gridBagConstraints31.gridy = 0;
			quantityLabel = new JLabel();
			quantityLabel.setText("Quantity:");
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.NONE;
			gridBagConstraints21.gridy = 0;
			gridBagConstraints21.weightx = 0.0D;
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.insets = new Insets(5, 5, 5, 25);
			gridBagConstraints21.gridx = 3;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints11.gridy = 0;
			runsLabel = new JLabel();
			runsLabel.setText("Runs:");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.gridwidth = 7;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints2.gridy = 1;
			titleLabel = new JLabel();
			titleLabel.setText("Title:");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 25);
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridy = 0;
			idValueLabel = new JLabel();
			idValueLabel.setText("-1");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			idLabel = new JLabel();
			idLabel.setText("ID:");
			recipePanel = new JPanel();
			recipePanel.setLayout(new GridBagLayout());
			recipePanel.setBorder(BorderFactory.createTitledBorder(null, "Recipe Information", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			recipePanel.add(idLabel, gridBagConstraints);
			recipePanel.add(idValueLabel, gridBagConstraints1);
			recipePanel.add(titleLabel, gridBagConstraints2);
			recipePanel.add(getTitleTextField(), gridBagConstraints3);
			recipePanel.add(runsLabel, gridBagConstraints11);
			recipePanel.add(getRunsTextField(), gridBagConstraints21);
			recipePanel.add(quantityLabel, gridBagConstraints31);
			recipePanel.add(getQuantityTextField(), gridBagConstraints4);
			recipePanel.add(getCommentsPanel(), gridBagConstraints5);
			recipePanel.add(bindsLabel, gridBagConstraints61);
			recipePanel.add(getBindsCheckBox(), gridBagConstraints71);
		}
		return recipePanel;
	}

	/**
	 * This method initializes productPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getProductPanel() {
		if (productPanel == null) {
			weightLabel = new JLabel();
			weightLabel.setText("Weight:");
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 3;
			gridBagConstraints17.insets = new Insets(5, 1, 5, 1);
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridy = 0;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 2;
			gridBagConstraints16.insets = new Insets(5, 1, 5, 1);
			gridBagConstraints16.gridy = 0;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.fill = GridBagConstraints.BOTH;
			gridBagConstraints15.gridwidth = 4;
			gridBagConstraints15.weighty = 1.0D;
			gridBagConstraints15.gridy = 4;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints14.gridy = 3;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints14.gridwidth = 3;
			gridBagConstraints14.gridx = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.gridy = 3;
			longDescLabel = new JLabel();
			longDescLabel.setText("Long description:");
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.gridy = 2;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints12.gridwidth = 3;
			gridBagConstraints12.gridx = 1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 2;
			shortDescLabel = new JLabel();
			shortDescLabel.setText("Short description:");
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints9.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints9.gridwidth = 3;
			gridBagConstraints9.gridx = 1;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridy = 1;
			nameLabel = new JLabel();
			nameLabel.setText("Keywords:");
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.NONE;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints7.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.weighty = 0.0D;
			gridBagConstraints6.gridy = 0;
			vnumLabel = new JLabel();
			vnumLabel.setText("VNUM:");
			productPanel = new JPanel();
			productPanel.setLayout(new GridBagLayout());
			productPanel.setBorder(BorderFactory.createTitledBorder(null, "Product Information", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			productPanel.add(vnumLabel, gridBagConstraints6);
			productPanel.add(getVnumTextField(), gridBagConstraints7);
			productPanel.add(nameLabel, gridBagConstraints8);
			productPanel.add(getNameTextField(), gridBagConstraints9);
			productPanel.add(shortDescLabel, gridBagConstraints10);
			productPanel.add(getShortDescTextField(), gridBagConstraints12);
			productPanel.add(longDescLabel, gridBagConstraints13);
			productPanel.add(getLongDescTextField(), gridBagConstraints14);
			productPanel.add(getValuesPanel(), gridBagConstraints15);
			productPanel.add(getVnumChooseButton(), gridBagConstraints16);
			productPanel.add(getVnumClearButton(), gridBagConstraints17);
		}
		return productPanel;
	}

	/**
	 * This method initializes titleTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTitleTextField() {
		if (titleTextField == null) {
			titleTextField = new JTextField();
			titleTextField.setDocument(new FixedLengthPlainDocument(80));
			
		}
		return titleTextField;
	}

	/**
	 * This method initializes mainPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			mainPanel.add(getProductPanel(), BorderLayout.CENTER);
			mainPanel.add(getRecipePanel(), BorderLayout.NORTH);
		}
		return mainPanel;
	}

	/**
	 * This method initializes mainToolbar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getMainToolbar() {
		if (mainToolbar == null) {
			mainToolbar = new JToolBar();
			mainToolbar.add(new SaveAction());
			if(manager.getAltDBMediator() != null && manager.getAltDBMediator().connected() && recipe.getID() != -1) {
				mainToolbar.add(new CopyAction());
			}
			mainToolbar.add(new ExportProductAction(manager));
		}
		return mainToolbar;
	}
	
	public void setCloseAction(AbstractAction closeAction) {
		getMainToolbar().add(closeAction);
		getTabPopupMenu().add(closeAction);
	}
	
	public boolean close() {
		return true;
	}

	/**
	 * This method initializes tabPopupMenu	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	public JPopupMenu getTabPopupMenu() {
		if (tabPopupMenu == null) {
			tabPopupMenu = new JPopupMenu();
		}
		return tabPopupMenu;
	}
	
	private class SaveAction extends AbstractAction {
		public SaveAction() {
			super("Save");
			//this.putValue(AbstractAction.SMALL_ICON, new ImageIcon(getClass().getResource("/images/icons/toolbar/hierarchic_layout.png")));
		}

		public void actionPerformed(ActionEvent arg0) {
			Thread t0 = new Thread() {
				public void run() {
					String query = null;
					Vector values = null;
					manager.busy(true);
					DBMediator dbm = manager.getDBMediator();
					
					manager.setStatus("Setting values...");
					recipe.setTitle(getTitleTextField().getText());
					recipe.setComments(getCommentsTextArea().getText());
					recipe.setRuns(RecipeEditor.parseInt(getRunsTextField().getText()));
					recipe.setQuantity(RecipeEditor.parseInt(getQuantityTextField().getText()));
					recipe.setBinds(getBindsCheckBox().isSelected());
					recipe.setVnum(RecipeEditor.parseLong(getVnumTextField().getText()));
					recipe.setName(getNameTextField().getText());
					recipe.setShortDesc(getShortDescTextField().getText());
					recipe.setLongDesc(getLongDescTextField().getText());
					recipe.setValue1(RecipeEditor.parseInt(getValue1TextField().getText()));
					recipe.setValue2(RecipeEditor.parseInt(getValue2TextField().getText()));
					recipe.setValue3(RecipeEditor.parseInt(getValue3TextField().getText()));
					recipe.setValue4(RecipeEditor.parseInt(getValue4TextField().getText()));
					recipe.setValue5(RecipeEditor.parseInt(getValue5TextField().getText()));
					recipe.setExtraFlags(RecipeEditor.parseLong(getExtraFlagsTextField().getText()));
					recipe.setWeight(RecipeEditor.parseInt(getWeightTextField().getText()));
					recipe.setTypeFlag(getTypeFlagComboBox().getSelectedIndex());
					recipe.setWearFlags(RecipeEditor.parseLong(getWearFlagsTextField().getText()));
					recipe.setEgo(RecipeEditor.parseInt(getEgoTextField().getText()));
					
					values = new Vector();
					values.add(recipe.getTitle());
					values.add((recipe.getComments().length()>0?recipe.getComments():null));
					values.add(recipe.getRuns());
					values.add(recipe.getQuantity());
					values.add((recipe.isBinds()?1:0));
					values.add(recipe.getVnum());
					values.add(recipe.getName());
					values.add(recipe.getShortDesc());
					values.add(recipe.getLongDesc());
					values.add(recipe.getValue1());
					values.add(recipe.getValue2());
					values.add(recipe.getValue3());
					values.add(recipe.getValue4());
					values.add(recipe.getValue5());
					values.add(recipe.getExtraFlags());
					values.add(recipe.getWeight());
					values.add(recipe.getTypeFlag());
					values.add(recipe.getWearFlags());
					values.add(recipe.getEgo());
					
					if(idValueLabel.getText().equals("-1")) {
						manager.setStatus("Inserting recipe...");
						query = "INSERT INTO bmud_recipes (title, comments, runs, quantity, binds, vnum, name, short_desc, long_desc, "+
							"value1, value2, value3, value4, value5, extra_flags, weight, type_flag, wear_flags, ego) VALUES "+
							"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
						recipe.setID(dbm.executeInsert(query, values));
						idValueLabel.setText(""+recipe.getID());
						
						if(recipe.getID() == -1) {
							dbm.getError();
							JOptionPane.showMessageDialog(manager, "There was an error adding your recipe to the database.\nError: "+dbm.getError(), "Database Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("ERROR: Error adding recipe to database: "+dbm.getError());
						} else {
							manager.setStatus("Inserting recipe materials...");
							DefaultListModel model = (DefaultListModel)getMaterialsList().getModel();
							Material mat = null;
							for(int i = 0; i < model.size(); i++) {
								mat = (Material)model.getElementAt(i);
								query = "INSERT INTO bmud_recipe_materials (recipe_id, material_vnum, quantity, quality) "+
									"VALUES ("+recipe.getID()+", "+mat.getMaterialVnum()+", "+mat.getQuantity()+", "+mat.getQuality()+")";
								
								dbm.executeUpdate(query);
							}
							
							manager.setStatus("Inserting product affects...");
							model = (DefaultListModel)getAffectsList().getModel();
							Affect aff = null;
							for(int i = 0; i < model.size(); i++) {
								aff = (Affect)model.getElementAt(i);
								query = "INSERT INTO bmud_recipe_product_affects (recipe_id, location, modifier) "+
									"VALUES ("+recipe.getID()+", "+aff.getLocation()+", "+aff.getModifier()+")";
								
								dbm.executeUpdate(query);
							}
							
							manager.addRecipe(recipe);
						}
					} else {
						manager.setStatus("Updating recipe...");
						query = "UPDATE bmud_recipes SET title = ?, comments = ?, runs = ?, quantity = ?, binds = ?, vnum = ?, "+
							"name = ?, short_desc = ?, long_desc = ?, value1 = ?, value2 = ?, value3 = ?, value4 = ?, value5 = ?, "+
							"extra_flags = ?, weight = ?, type_flag = ?, wear_flags = ?, ego = ? WHERE id = "+recipe.getID();
						
						dbm.executeUpdate(query, values);
						
						manager.setStatus("Updating recipe materials...");
						query = "DELETE FROM bmud_recipe_materials WHERE recipe_id = "+recipe.getID();
						dbm.executeUpdate(query);
						
						DefaultListModel model = (DefaultListModel)getMaterialsList().getModel();
						Material mat = null;
						for(int i = 0; i < model.size(); i++) {
							mat = (Material)model.getElementAt(i);
							query = "INSERT INTO bmud_recipe_materials (recipe_id, material_vnum, quantity, quality) "+
								"VALUES ("+recipe.getID()+", "+mat.getMaterialVnum()+", "+mat.getQuantity()+", "+mat.getQuality()+")";
							
							dbm.executeUpdate(query);
						}
						
						manager.setStatus("Updating product affects...");
						query = "DELETE FROM bmud_recipe_product_affects WHERE recipe_id = "+recipe.getID();
						dbm.executeUpdate(query);
						
						model = (DefaultListModel)getAffectsList().getModel();
						Affect aff = null;
						for(int i = 0; i < model.size(); i++) {
							aff = (Affect)model.getElementAt(i);
							query = "INSERT INTO bmud_recipe_product_affects (recipe_id, location, modifier) "+
								"VALUES ("+recipe.getID()+", "+aff.getLocation()+", "+aff.getModifier()+")";
							
							dbm.executeUpdate(query);
						}
						
						manager.updateRecipeList();
					}
					manager.setStatus("Ready.");
					manager.busy(false);
				}
			};
			t0.run();
		}
	}
	
	private class CopyAction extends AbstractAction {
		public CopyAction() {
			super("Copy");
			//this.putValue(AbstractAction.SMALL_ICON, new ImageIcon(getClass().getResource("/images/icons/toolbar/hierarchic_layout.png")));
		}

		public void actionPerformed(ActionEvent arg0) {
			Thread t0 = new Thread() {
				public void run() {
					String query = null;
					Vector values = null;
					DBMediator dbm = manager.getAltDBMediator();
					DBResult result = null;
					int auto_increment = 0, newID = -1;
					Vector<Object> idList = new Vector<Object>();
					HashSet<Integer> idSet = new HashSet<Integer>();
					Object chosenID = null;
					
					manager.busy(true);
					manager.setStatus("Compiling valid ID list...");
					
					query = "SHOW TABLE STATUS LIKE 'bmud_recipes'";
					result = dbm.executeQuery(query);
					auto_increment = result.getInt(0, 10);
					
					query = "SELECT id FROM bmud_recipes ORDER BY id";
					result = dbm.executeQuery(query);
					
					for(int i = 0; i < result.size(); i++) {
						idSet.add(result.getInt(i, 0));
					}
					
					for(int i = 1; i < auto_increment; i++) {
						if(!idSet.contains(i))
							idList.add(i);
					}
					
					idList.add("Auto");
					
					manager.setStatus("Choosing new ID...");
					query = "SELECT id FROM bmud_recipes WHERE title = '"+recipe.getTitle()+"'";
					result = dbm.executeQuery(query);
					
					if(result.size() == 1) {
						chosenID = JOptionPane.showConfirmDialog(manager, "A recipe with the same title exists. Overwrite?", "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
						if((Integer)chosenID == JOptionPane.YES_OPTION) {
							chosenID = result.getInt(0, "id");
							newID = (Integer)chosenID;
							query = "DELETE FROM bmud_recipes WHERE id = "+chosenID;
							dbm.executeUpdate(query);
						} else {
							chosenID = null;
						}
					}
					if(chosenID == null) {
						for(;;) {
							chosenID = JOptionPane.showInputDialog(manager, "Select new recipe ID:", "Select Recipe ID", JOptionPane.INFORMATION_MESSAGE, null, idList.toArray(), "Auto");
	//						newID = JOptionPane.showInputDialog(manager, "Enter new recipe ID:", idList);
	
							if(chosenID == null)
								return;
							
							if(!chosenID.toString().equals("Auto"))
								newID = RecipeEditor.parseInt(chosenID.toString());
							
							if(newID != -1) {
								query = "SELECT count(*) FROM bmud_recipes WHERE id = "+newID;
								result = dbm.executeQuery(query);
								if(result.getInt(0, 0) != 0) {
									JOptionPane.showMessageDialog(manager, "That ID already exists in the alternate database.", "Copy Failure", JOptionPane.ERROR_MESSAGE);
								} else {
									break;
								}
							} else {
								break;
							}
						}
					}
					
					System.out.println("INFO: Using recipe ID: "+newID);
					
					manager.setStatus("Setting values...");

					values = new Vector();
					if(newID != -1)
						values.add(newID);
					values.add(recipe.getTitle());
					values.add((recipe.getComments().length()>0?recipe.getComments():null));
					values.add(recipe.getRuns());
					values.add(recipe.getQuantity());
					values.add((recipe.isBinds()?1:0));
					values.add(recipe.getVnum());
					values.add(recipe.getName());
					values.add(recipe.getShortDesc());
					values.add(recipe.getLongDesc());
					values.add(recipe.getValue1());
					values.add(recipe.getValue2());
					values.add(recipe.getValue3());
					values.add(recipe.getValue4());
					values.add(recipe.getValue5());
					values.add(recipe.getExtraFlags());
					values.add(recipe.getWeight());
					values.add(recipe.getTypeFlag());
					values.add(recipe.getWearFlags());
					values.add(recipe.getEgo());
					
					manager.setStatus("Inserting recipe...");
					if(newID != -1) {
						query = "INSERT INTO bmud_recipes (id, title, comments, runs, quantity, binds, vnum, name, short_desc, long_desc, "+
							"value1, value2, value3, value4, value5, extra_flags, weight, type_flag, wear_flags, ego) VALUES "+
							"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						dbm.executeInsert(query, values);
					} else {
						query = "INSERT INTO bmud_recipes (title, comments, runs, quantity, binds, vnum, name, short_desc, long_desc, "+
							"value1, value2, value3, value4, value5, extra_flags, weight, type_flag, wear_flags, ego) VALUES "+
							"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						newID = dbm.executeInsert(query, values);
					}
					
					manager.setStatus("Inserting recipe materials...");
					query = "DELETE FROM bmud_recipe_materials WHERE recipe_id = "+newID;
					dbm.executeUpdate(query);
					
					DefaultListModel model = (DefaultListModel)getMaterialsList().getModel();
					Material mat = null;
					for(int i = 0; i < model.size(); i++) {
						mat = (Material)model.getElementAt(i);
						query = "INSERT INTO bmud_recipe_materials (recipe_id, material_vnum, quantity, quality) "+
							"VALUES ("+newID+", "+mat.getMaterialVnum()+", "+mat.getQuantity()+", "+mat.getQuality()+")";
						
						dbm.executeUpdate(query);
					}
					
					manager.setStatus("Inserting product affects...");
					query = "DELETE FROM bmud_recipe_product_affects WHERE recipe_id = "+newID;
					dbm.executeUpdate(query);
					
					model = (DefaultListModel)getAffectsList().getModel();
					Affect aff = null;
					for(int i = 0; i < model.size(); i++) {
						aff = (Affect)model.getElementAt(i);
						query = "INSERT INTO bmud_recipe_product_affects (recipe_id, location, modifier) "+
							"VALUES ("+newID+", "+aff.getLocation()+", "+aff.getModifier()+")";
						
						dbm.executeUpdate(query);
					}
					
					manager.setStatus("Ready.");
					manager.busy(false);
				}
			};
			t0.run();
		}
	}
	
	public String toString() {
		return this.getClass().getName()+"["+recipe.toString()+"]";
	}

	/**
	 * This method initializes runsTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getRunsTextField() {
		if (runsTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(new Integer(-1));
			runsTextField = new JFormattedTextField(nf);
			runsTextField.setValue(new Integer(1));
			runsTextField.setColumns(5);
		}
		return runsTextField;
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
			quantityTextField = new JFormattedTextField(nf);
			quantityTextField.setColumns(5);
			quantityTextField.setValue(new Integer(1));
		}
		return quantityTextField;
	}

	/**
	 * This method initializes commentsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCommentsPanel() {
		if (commentsPanel == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(5);
			borderLayout.setVgap(5);
			commentsPanel = new JPanel();
			commentsPanel.setLayout(borderLayout);
			commentsPanel.setBorder(BorderFactory.createTitledBorder(null, "Comments", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			commentsPanel.add(getCommentsScrollPane(), BorderLayout.CENTER);
		}
		return commentsPanel;
	}
	
	private JScrollPane getCommentsScrollPane() {
		if(commentsScrollPane == null) {
			commentsScrollPane = new JScrollPane();
			commentsScrollPane.setViewportView(getCommentsTextArea());
		}
		return commentsScrollPane;
	}

	/**
	 * This method initializes commentsTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getCommentsTextArea() {
		if (commentsTextArea == null) {
			commentsTextArea = new JTextArea();
			commentsTextArea.setRows(5);
			commentsTextArea.setDocument(new FixedLengthPlainDocument(512));
		}
		return commentsTextArea;
	}

	/**
	 * This method initializes vnumTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getVnumTextField() {
		if (vnumTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(new Integer(-1));
			vnumTextField = new JFormattedTextField(nf);
			vnumTextField.setValue(new Integer(-1));
			vnumTextField.setColumns(10);
			vnumTextField.setPreferredSize(new Dimension(115, 20));
			vnumTextField.setMinimumSize(new Dimension(115, 20));
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

	/**
	 * This method initializes nameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new JTextField();
			nameTextField.setDocument(new FixedLengthPlainDocument(128));
		}
		return nameTextField;
	}

	/**
	 * This method initializes shortDescTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getShortDescTextField() {
		if (shortDescTextField == null) {
			shortDescTextField = new JTextField();
			shortDescTextField.setDocument(new FixedLengthPlainDocument(128));
		}
		return shortDescTextField;
	}

	/**
	 * This method initializes longDescTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLongDescTextField() {
		if (longDescTextField == null) {
			longDescTextField = new JTextField();
			longDescTextField.setDocument(new FixedLengthPlainDocument(256));
		}
		return longDescTextField;
	}

	/**
	 * This method initializes valuesPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getValuesPanel() {
		if (valuesPanel == null) {
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.gridx = 4;
			gridBagConstraints48.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints48.fill = GridBagConstraints.BOTH;
			gridBagConstraints48.gridwidth = 4;
			gridBagConstraints48.gridy = 5;
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.gridx = 0;
			gridBagConstraints47.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints47.fill = GridBagConstraints.BOTH;
			gridBagConstraints47.weighty = 1.0D;
			gridBagConstraints47.gridwidth = 4;
			gridBagConstraints47.gridy = 5;
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.gridx = 8;
			gridBagConstraints46.gridheight = 6;
			gridBagConstraints46.weightx = 1.0D;
			gridBagConstraints46.fill = GridBagConstraints.BOTH;
			gridBagConstraints46.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints46.gridy = 0;
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.fill = GridBagConstraints.NONE;
			gridBagConstraints45.gridy = 4;
			gridBagConstraints45.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints45.anchor = GridBagConstraints.NORTH;
			gridBagConstraints45.gridx = 5;
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.gridx = 4;
			gridBagConstraints44.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints44.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints44.gridy = 4;
			value4Label = new JLabel();
			value4Label.setText("Value 4:");
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.fill = GridBagConstraints.NONE;
			gridBagConstraints43.gridy = 4;
			gridBagConstraints43.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints43.anchor = GridBagConstraints.NORTH;
			gridBagConstraints43.gridx = 1;
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.gridx = 0;
			gridBagConstraints42.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints42.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints42.gridy = 4;
			value3Label = new JLabel();
			value3Label.setText("Value 3:");
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.fill = GridBagConstraints.NONE;
			gridBagConstraints41.gridy = 3;
			gridBagConstraints41.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints41.gridx = 5;
			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.gridx = 4;
			gridBagConstraints40.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints40.anchor = GridBagConstraints.WEST;
			gridBagConstraints40.gridy = 3;
			value2Label = new JLabel();
			value2Label.setText("Value 2:");
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.fill = GridBagConstraints.NONE;
			gridBagConstraints39.gridy = 3;
			gridBagConstraints39.anchor = GridBagConstraints.CENTER;
			gridBagConstraints39.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints39.gridx = 1;
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			gridBagConstraints38.gridx = 0;
			gridBagConstraints38.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints38.anchor = GridBagConstraints.WEST;
			gridBagConstraints38.gridy = 3;
			value1Label = new JLabel();
			value1Label.setText("Value 1:");
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.gridx = 6;
			gridBagConstraints37.insets = new Insets(5, 1, 5, 1);
			gridBagConstraints37.gridy = 2;
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.fill = GridBagConstraints.NONE;
			gridBagConstraints36.gridy = 2;
			gridBagConstraints36.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints36.gridx = 5;
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.gridx = 4;
			gridBagConstraints35.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints35.anchor = GridBagConstraints.WEST;
			gridBagConstraints35.gridy = 2;
			value5Label = new JLabel();
			value5Label.setText("Durability:");
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.gridx = 2;
			gridBagConstraints34.insets = new Insets(5, 1, 5, 1);
			gridBagConstraints34.gridy = 2;
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.fill = GridBagConstraints.NONE;
			gridBagConstraints33.gridy = 2;
			gridBagConstraints33.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints33.anchor = GridBagConstraints.WEST;
			gridBagConstraints33.gridx = 1;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.gridx = 0;
			gridBagConstraints32.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints32.anchor = GridBagConstraints.WEST;
			gridBagConstraints32.gridy = 2;
			egoLabel = new JLabel();
			egoLabel.setText("Ego:");
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.gridx = 7;
			gridBagConstraints30.insets = new Insets(5, 1, 5, 1);
			gridBagConstraints30.anchor = GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 1;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.gridx = 6;
			gridBagConstraints29.insets = new Insets(5, 1, 5, 1);
			gridBagConstraints29.gridy = 1;
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.fill = GridBagConstraints.NONE;
			gridBagConstraints28.gridy = 1;
			gridBagConstraints28.anchor = GridBagConstraints.WEST;
			gridBagConstraints28.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints28.gridx = 5;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.gridx = 4;
			gridBagConstraints27.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints27.anchor = GridBagConstraints.WEST;
			gridBagConstraints27.gridy = 1;
			wearFlagsLabel = new JLabel();
			wearFlagsLabel.setText("Wear flags:");
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.gridx = 3;
			gridBagConstraints26.insets = new Insets(5, 1, 5, 25);
			gridBagConstraints26.anchor = GridBagConstraints.WEST;
			gridBagConstraints26.gridy = 1;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridx = 2;
			gridBagConstraints25.insets = new Insets(5, 1, 5, 1);
			gridBagConstraints25.gridy = 1;
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.fill = GridBagConstraints.NONE;
			gridBagConstraints24.gridy = 1;
			gridBagConstraints24.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints24.anchor = GridBagConstraints.WEST;
			gridBagConstraints24.gridx = 1;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridx = 0;
			gridBagConstraints23.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints23.anchor = GridBagConstraints.WEST;
			gridBagConstraints23.gridy = 1;
			extraFlagsLabel = new JLabel();
			extraFlagsLabel.setText("Extra flags:");
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.fill = GridBagConstraints.NONE;
			gridBagConstraints22.gridy = 0;
			gridBagConstraints22.insets = new Insets(5, 5, 5, 0);
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.gridx = 5;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 4;
			gridBagConstraints20.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints20.anchor = GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 0;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints19.gridy = 0;
			gridBagConstraints19.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.gridx = 1;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints18.anchor = GridBagConstraints.WEST;
			gridBagConstraints18.gridy = 0;
			typeFlagLabel = new JLabel();
			typeFlagLabel.setText("Type:");
			valuesPanel = new JPanel();
			valuesPanel.setLayout(new GridBagLayout());
			valuesPanel.add(typeFlagLabel, gridBagConstraints18);
			valuesPanel.add(getTypeFlagComboBox(), gridBagConstraints19);
			valuesPanel.add(weightLabel, gridBagConstraints20);
			valuesPanel.add(getWeightTextField(), gridBagConstraints22);
			valuesPanel.add(extraFlagsLabel, gridBagConstraints23);
			valuesPanel.add(getExtraFlagsTextField(), gridBagConstraints24);
			valuesPanel.add(getExtraFlagsChooseButton(), gridBagConstraints25);
			valuesPanel.add(getExtraFlagsClearButton(), gridBagConstraints26);
			valuesPanel.add(wearFlagsLabel, gridBagConstraints27);
			valuesPanel.add(getWearFlagsTextField(), gridBagConstraints28);
			valuesPanel.add(getWearFlagsChooseButton(), gridBagConstraints29);
			valuesPanel.add(getAffectsPanel(), gridBagConstraints47);
			valuesPanel.add(getWearFlagsClearButton(), gridBagConstraints30);
			valuesPanel.add(egoLabel, gridBagConstraints32);
			valuesPanel.add(getEgoTextField(), gridBagConstraints33);
			valuesPanel.add(getEgoClearButton(), gridBagConstraints34);
			valuesPanel.add(value5Label, gridBagConstraints35);
			valuesPanel.add(getValue5TextField(), gridBagConstraints36);
			valuesPanel.add(getValue5ClearButton(), gridBagConstraints37);
			valuesPanel.add(value1Label, gridBagConstraints38);
			valuesPanel.add(getValue1TextField(), gridBagConstraints39);
			valuesPanel.add(value2Label, gridBagConstraints40);
			valuesPanel.add(getValue2TextField(), gridBagConstraints41);
			valuesPanel.add(value3Label, gridBagConstraints42);
			valuesPanel.add(getValue3TextField(), gridBagConstraints43);
			valuesPanel.add(value4Label, gridBagConstraints44);
			valuesPanel.add(getValue4TextField(), gridBagConstraints45);
			valuesPanel.add(getMaterialsPanel(), gridBagConstraints46);
			valuesPanel.add(getVendorsPanel(), gridBagConstraints48);
		}
		return valuesPanel;
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
					chooseProduct(RecipeEditor.parseLong(getVnumTextField().getText()));
				}
			});
		}
		return vnumChooseButton;
	}

	protected void chooseProduct(long selectedVnum) {
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
						resetFields(selectedItem);
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
	
	protected void chooseExtraFlags(long selectedFlags) {
		final JPopupMenu chooser = new JPopupMenu();
		final JPanel panel = new JPanel();
		JCheckBox tempCB = null;
		GridBagConstraints tempGBC = null;
		panel.setLayout(new GridBagLayout());
		
		int i = 0;
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 8; y++) {
				if(i >= EXTRA_FLAGS.length)
					break;
				tempCB = new JCheckBox(EXTRA_FLAGS[i]);
				if((selectedFlags&(1l<<i)) == (1l<<i)) {
					tempCB.setSelected(true);
					tempCB.setForeground(Color.red);
				}
				
				tempCB.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						JCheckBox cb = (JCheckBox)e.getSource();
						if(cb.isSelected())
							cb.setForeground(Color.red);
						else
							cb.setForeground(Color.black);
					}
				});
				tempCB.setEnabled(getExtraFlagsTextField().isEnabled());
				tempGBC = new GridBagConstraints();
				tempGBC.gridx = x;
				tempGBC.gridy = y;
				tempGBC.anchor = GridBagConstraints.WEST;
				panel.add(tempCB, tempGBC);
				i++;
			}
		}
		chooser.add(panel);
		chooser.pack();
		chooser.addPopupMenuListener(new PopupMenuListener() {

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) { }

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { }

			public void popupMenuCanceled(PopupMenuEvent e) {
				long value = 0;
				for(int i = 0; i < panel.getComponentCount(); i++) {
					if(panel.getComponent(i) instanceof JCheckBox) {
						if(((JCheckBox)panel.getComponent(i)).isSelected()) {
							value += (1l<<i);
						}
					}
				}
				getExtraFlagsTextField().setText(""+value);		
			}
			
		});
		chooser.show(getExtraFlagsChooseButton(), 0, getExtraFlagsChooseButton().getHeight());
	}
	
	protected void chooseWearFlags(long selectedFlags) {
		final JPopupMenu chooser = new JPopupMenu();
		final JPanel panel = new JPanel();
		JCheckBox tempCB = null;
		GridBagConstraints tempGBC = null;
		panel.setLayout(new GridBagLayout());
		
		int i = 0;
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 5; y++) {
				if(i >= WEAR_FLAGS.length)
					break;
				tempCB = new JCheckBox(WEAR_FLAGS[i]);
				if((selectedFlags&(1l<<i)) == (1l<<i)) {
					tempCB.setSelected(true);
					tempCB.setForeground(Color.red);
				}
				
				tempCB.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						JCheckBox cb = (JCheckBox)e.getSource();
						if(cb.isSelected())
							cb.setForeground(Color.red);
						else
							cb.setForeground(Color.black);
					}
				});
				tempCB.setEnabled(getWearFlagsTextField().isEnabled());
				tempGBC = new GridBagConstraints();
				tempGBC.gridx = x;
				tempGBC.gridy = y;
//				tempGBC.insets = new Insets(5,5,5,5);
				tempGBC.anchor = GridBagConstraints.WEST;
				panel.add(tempCB, tempGBC);
				i++;
			}
		}
		chooser.add(panel);
		chooser.pack();
		chooser.addPopupMenuListener(new PopupMenuListener() {

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) { }

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { }

			public void popupMenuCanceled(PopupMenuEvent e) {
				long value = 0;
				for(int i = 0; i < panel.getComponentCount(); i++) {
					if(panel.getComponent(i) instanceof JCheckBox) {
						if(((JCheckBox)panel.getComponent(i)).isSelected()) {
							value += (1l<<i);
						}
					}
				}
				getWearFlagsTextField().setText(""+value);		
			}
			
		});
		chooser.show(getWearFlagsChooseButton(), 0, getWearFlagsChooseButton().getHeight());
	}
	
	protected void resetFields(Item selectedItem) {
		if(selectedItem != null) {
			getVnumTextField().setText(""+selectedItem.getVnum());
			
			getNameTextField().setText(selectedItem.getName());
			getShortDescTextField().setText(selectedItem.getShortDesc());
			getLongDescTextField().setText(selectedItem.getLongDesc());
			
			getTypeFlagComboBox().setSelectedIndex(selectedItem.getType());
			getWeightTextField().setText(""+selectedItem.getWeight());
			getExtraFlagsTextField().setText(""+selectedItem.getExtraFlags());
			getWearFlagsTextField().setText(""+selectedItem.getWearFlags());
			getEgoTextField().setText(""+selectedItem.getEgo());
			getValue1TextField().setText(""+selectedItem.getValue1());
			getValue2TextField().setText(""+selectedItem.getValue2());
			getValue3TextField().setText(""+selectedItem.getValue3());
			getValue4TextField().setText(""+selectedItem.getValue4());
			getValue5TextField().setText(""+selectedItem.getValue5());
			
			enableFields(false);
		} else {
			getVnumTextField().setText("-1");
//			getNameTextField().setText("");
//			getShortDescTextField().setText("");
//			getLongDescTextField().setText("");
			
			enableFields(true);
		}
	}

	/**
	 * This method initializes vnumClearButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getVnumClearButton() {
		if (vnumClearButton == null) {
			vnumClearButton = new JButton();
			vnumClearButton.setPreferredSize(new Dimension(20, 20));
			vnumClearButton.setMinimumSize(new Dimension(20, 20));
			vnumClearButton.setIcon(new ImageIcon(getClass().getResource("/com/blackmud/tools/RecipeManager/images/none.png")));
			vnumClearButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					resetFields(null);
					getNameTextField().setText("");
					getShortDescTextField().setText("");
					getLongDescTextField().setText("");
				}
			});
		}
		return vnumClearButton;
	}

	/**
	 * This method initializes typeFlagComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTypeFlagComboBox() {
		if (typeFlagComboBox == null) {
			typeFlagComboBox = new JComboBox();
			typeFlagComboBox.setPreferredSize(new Dimension(115, 20));
			typeFlagComboBox.setMinimumSize(new Dimension(115, 20));
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			for(int i = 0; i < TYPES.length; i++) {
				model.addElement(TYPES[i]);
			}
			typeFlagComboBox.setModel(model);
			typeFlagComboBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					updateValueLabels();
				}
			});
		}
		return typeFlagComboBox;
	}

	/**
	 * This method initializes weightTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getWeightTextField() {
		if (weightTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(new Integer(0));
			weightTextField = new JFormattedTextField(nf);
			weightTextField.setColumns(10);
			weightTextField.setPreferredSize(new Dimension(115, 20));
			weightTextField.setMinimumSize(new Dimension(115, 20));
			weightTextField.setValue(new Integer(0));
		}
		return weightTextField;
	}

	/**
	 * This method initializes extraFlagsTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getExtraFlagsTextField() {
		if (extraFlagsTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(new Integer(0));
			extraFlagsTextField = new JFormattedTextField(nf);
			extraFlagsTextField.setColumns(10);
			extraFlagsTextField.setPreferredSize(new Dimension(115, 20));
			extraFlagsTextField.setMinimumSize(new Dimension(115, 20));
			extraFlagsTextField.setValue(new Integer(0));
		}
		return extraFlagsTextField;
	}

	/**
	 * This method initializes extraFlagsChooseButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getExtraFlagsChooseButton() {
		if (extraFlagsChooseButton == null) {
			extraFlagsChooseButton = new JButton();
			extraFlagsChooseButton.setPreferredSize(new Dimension(20, 20));
			extraFlagsChooseButton.setMinimumSize(new Dimension(20, 20));
			extraFlagsChooseButton.setIcon(new ImageIcon(getClass().getResource("/com/blackmud/tools/RecipeManager/images/search.png")));
			extraFlagsChooseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					chooseExtraFlags(RecipeEditor.parseLong(getExtraFlagsTextField().getText()));
				}
			});
		}
		return extraFlagsChooseButton;
	}

	/**
	 * This method initializes extraFlagsClearButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getExtraFlagsClearButton() {
		if (extraFlagsClearButton == null) {
			extraFlagsClearButton = new JButton();
			extraFlagsClearButton.setPreferredSize(new Dimension(20, 20));
			extraFlagsClearButton.setMinimumSize(new Dimension(20, 20));
			extraFlagsClearButton.setIcon(new ImageIcon(getClass().getResource("/com/blackmud/tools/RecipeManager/images/none.png")));
			extraFlagsClearButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getExtraFlagsTextField().setText("0");
				}
			});
		}
		return extraFlagsClearButton;
	}

	/**
	 * This method initializes wearFlagsTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getWearFlagsTextField() {
		if (wearFlagsTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(new Integer(0));
			wearFlagsTextField = new JFormattedTextField(nf);
			wearFlagsTextField.setColumns(10);
			wearFlagsTextField.setPreferredSize(new Dimension(115, 20));
			wearFlagsTextField.setMinimumSize(new Dimension(115, 20));
			wearFlagsTextField.setValue(new Integer(0));
		}
		return wearFlagsTextField;
	}

	/**
	 * This method initializes wearFlagsChooseButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getWearFlagsChooseButton() {
		if (wearFlagsChooseButton == null) {
			wearFlagsChooseButton = new JButton();
			wearFlagsChooseButton.setPreferredSize(new Dimension(20, 20));
			wearFlagsChooseButton.setMinimumSize(new Dimension(20, 20));
			wearFlagsChooseButton.setIcon(new ImageIcon(getClass().getResource("/com/blackmud/tools/RecipeManager/images/search.png")));
			wearFlagsChooseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					chooseWearFlags(RecipeEditor.parseLong(getWearFlagsTextField().getText()));
				}
			});
		}
		return wearFlagsChooseButton;
	}

	/**
	 * This method initializes wearFlagsClearButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getWearFlagsClearButton() {
		if (wearFlagsClearButton == null) {
			wearFlagsClearButton = new JButton();
			wearFlagsClearButton.setPreferredSize(new Dimension(20, 20));
			wearFlagsClearButton.setMinimumSize(new Dimension(20, 20));
			wearFlagsClearButton.setIcon(new ImageIcon(getClass().getResource("/com/blackmud/tools/RecipeManager/images/none.png")));
			wearFlagsClearButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getWearFlagsTextField().setText("0");
				}
			});
		}
		return wearFlagsClearButton;
	}

	/**
	 * This method initializes egoTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getEgoTextField() {
		if (egoTextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(new Integer(-1));
			nf.setMaximum(new Integer(1000));
			egoTextField = new JFormattedTextField(nf);
			egoTextField.setColumns(10);
			egoTextField.setPreferredSize(new Dimension(115, 20));
			egoTextField.setMinimumSize(new Dimension(115, 20));
			egoTextField.setValue(new Integer(0));
		}
		return egoTextField;
	}

	/**
	 * This method initializes egoClearButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getEgoClearButton() {
		if (egoClearButton == null) {
			egoClearButton = new JButton();
			egoClearButton.setPreferredSize(new Dimension(20, 20));
			egoClearButton.setMinimumSize(new Dimension(20, 20));
			egoClearButton.setIcon(new ImageIcon(getClass().getResource("/com/blackmud/tools/RecipeManager/images/none.png")));
			egoClearButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getEgoTextField().setText("0");
				}
			});
		}
		return egoClearButton;
	}

	/**
	 * This method initializes value5TextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getValue5TextField() {
		if (value5TextField == null) {
			NumberFormatter nf = new NumberFormatter();
			nf.setMinimum(new Integer(-1));
			nf.setMaximum(new Integer(100));
			value5TextField = new JFormattedTextField(nf);
			value5TextField.setColumns(10);
			value5TextField.setPreferredSize(new Dimension(115, 20));
			value5TextField.setMinimumSize(new Dimension(115, 20));
			value5TextField.setValue(new Integer(0));
		}
		return value5TextField;
	}

	/**
	 * This method initializes value5ClearButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getValue5ClearButton() {
		if (value5ClearButton == null) {
			value5ClearButton = new JButton();
			value5ClearButton.setPreferredSize(new Dimension(20, 20));
			value5ClearButton.setMinimumSize(new Dimension(20, 20));
			value5ClearButton.setIcon(new ImageIcon(getClass().getResource("/com/blackmud/tools/RecipeManager/images/none.png")));
			value5ClearButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getValue5TextField().setText("0");
				}
			});
		}
		return value5ClearButton;
	}

	/**
	 * This method initializes value1TextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getValue1TextField() {
		if (value1TextField == null) {
			NumberFormatter nf = new NumberFormatter();
			value1TextField = new JFormattedTextField(nf);
			value1TextField.setColumns(10);
			value1TextField.setPreferredSize(new Dimension(115, 20));
			value1TextField.setMinimumSize(new Dimension(115, 20));
			value1TextField.setValue(new Integer(0));
		}
		return value1TextField;
	}

	/**
	 * This method initializes value2TextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getValue2TextField() {
		if (value2TextField == null) {
			NumberFormatter nf = new NumberFormatter();
			value2TextField = new JFormattedTextField(nf);
			value2TextField.setColumns(10);
			value2TextField.setPreferredSize(new Dimension(115, 20));
			value2TextField.setMinimumSize(new Dimension(115, 20));
			value2TextField.setValue(new Integer(0));
		}
		return value2TextField;
	}

	/**
	 * This method initializes value3TextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getValue3TextField() {
		if (value3TextField == null) {
			NumberFormatter nf = new NumberFormatter();
			value3TextField = new JFormattedTextField(nf);
			value3TextField.setColumns(10);
			value3TextField.setPreferredSize(new Dimension(115, 20));
			value3TextField.setMinimumSize(new Dimension(115, 20));
			value3TextField.setValue(new Integer(0));
		}
		return value3TextField;
	}

	/**
	 * This method initializes value4TextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getValue4TextField() {
		if (value4TextField == null) {
			NumberFormatter nf = new NumberFormatter();
			value4TextField = new JFormattedTextField();
			value4TextField.setColumns(10);
			value4TextField.setPreferredSize(new Dimension(115, 20));
			value4TextField.setMinimumSize(new Dimension(115, 20));
			value4TextField.setValue(new Integer(0));
		}
		return value4TextField;
	}

	/**
	 * This method initializes materialsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMaterialsPanel() {
		if (materialsPanel == null) {
			materialsPanel = new JPanel();
			materialsPanel.setLayout(new BorderLayout());
			materialsPanel.setBorder(BorderFactory.createTitledBorder(null, "Materials", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			materialsPanel.add(getMaterialsListScrollPane(), BorderLayout.CENTER);
		}
		return materialsPanel;
	}

	/**
	 * This method initializes affectsList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getAffectsList() {
		if (affectsList == null) {
			affectsList = new JList(new DefaultListModel());
			affectsList.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mousePressed(java.awt.event.MouseEvent evt) {
					int selRow = getAffectsList().locationToIndex(new Point(evt.getX(),evt.getY()));
					if(selRow != -1)
						getAffectsList().setSelectedIndex(selRow);
					if (evt.isPopupTrigger()) {
						getAffectsPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
					}
				}   
				public void mouseReleased(java.awt.event.MouseEvent evt) {
					int selRow = getAffectsList().locationToIndex(new Point(evt.getX(),evt.getY()));
					if(selRow != -1)
						getAffectsList().setSelectedIndex(selRow);
					if (evt.isPopupTrigger()) {
						getAffectsPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
					}
				} 
			});
		}
		return affectsList;
	}

	/**
	 * This method initializes affectsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAffectsPanel() {
		if (affectsPanel == null) {
			affectsPanel = new JPanel();
			affectsPanel.setLayout(new BorderLayout());
			affectsPanel.setBorder(BorderFactory.createTitledBorder(null, "Affects", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			affectsPanel.add(getAffectsListScrollPane(), BorderLayout.CENTER);
		}
		return affectsPanel;
	}

	/**
	 * This method initializes vendorsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getVendorsPanel() {
		if (vendorsPanel == null) {
			vendorsPanel = new JPanel();
			vendorsPanel.setLayout(new BorderLayout());
			vendorsPanel.setBorder(BorderFactory.createTitledBorder(null, "Vendors", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			vendorsPanel.add(getVendorScrollPane(), BorderLayout.CENTER);
		}
		return vendorsPanel;
	}

	/**
	 * This method initializes vendorList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getVendorList() {
		if (vendorList == null) {
			vendorList = new JList();
//			vendorList.addMouseListener(new java.awt.event.MouseAdapter() {   
//				public void mousePressed(java.awt.event.MouseEvent evt) {
//					int selRow = getVendorList().locationToIndex(new Point(evt.getX(),evt.getY()));
//					if(selRow != -1)
//						getVendorList().setSelectedIndex(selRow);
//					if (evt.isPopupTrigger()) {
//						getVendorPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
//					}
//				}   
//				public void mouseReleased(java.awt.event.MouseEvent evt) {
//					int selRow = getVendorList().locationToIndex(new Point(evt.getX(),evt.getY()));
//					if(selRow != -1)
//						getVendorList().setSelectedIndex(selRow);
//					if (evt.isPopupTrigger()) {
//						getVendorPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
//					}
//				} 
//			});
		}
		return vendorList;
	}
	
	/**
	 * This method initializes vendorScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getVendorScrollPane() {
		if (vendorScrollPane == null) {
			vendorScrollPane = new JScrollPane();
			vendorScrollPane.setViewportView(getVendorList());
		}
		return vendorScrollPane;
	}

	/**
	 * This method initializes materialsList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getMaterialsList() {
		if (materialsList == null) {
			materialsList = new JList(new DefaultListModel());
			materialsList.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mousePressed(java.awt.event.MouseEvent evt) {
					int selRow = getMaterialsList().locationToIndex(new Point(evt.getX(),evt.getY()));
					if(selRow != -1)
						getMaterialsList().setSelectedIndex(selRow);
					if (evt.isPopupTrigger()) {
						getMaterialsPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
					}
				}   
				public void mouseReleased(java.awt.event.MouseEvent evt) {
					int selRow = getMaterialsList().locationToIndex(new Point(evt.getX(),evt.getY()));
					if(selRow != -1)
						getMaterialsList().setSelectedIndex(selRow);
					if (evt.isPopupTrigger()) {
						getMaterialsPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
					}
				} 
			});
		}
		return materialsList;
	}

	/**
	 * This method initializes materialsListScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getMaterialsListScrollPane() {
		if (materialsListScrollPane == null) {
			materialsListScrollPane = new JScrollPane();
			materialsListScrollPane.setViewportView(getMaterialsList());
		}
		return materialsListScrollPane;
	}

	/**
	 * This method initializes affectsListScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getAffectsListScrollPane() {
		if (affectsListScrollPane == null) {
			affectsListScrollPane = new JScrollPane();
			affectsListScrollPane.setViewportView(getAffectsList());
		}
		return affectsListScrollPane;
	}

	/**
	 * This method initializes bindsCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getBindsCheckBox() {
		if (bindsCheckBox == null) {
			bindsCheckBox = new JCheckBox();
		}
		return bindsCheckBox;
	}
	
	public int getRecipeID() {
		return recipe.getID();
	}

	/**
	 * This method initializes affectsPopupMenu	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getAffectsPopupMenu() {
		JPopupMenu affectsPopupMenu = new JPopupMenu();
		if(getAffectsList().getModel().getSize() < 5) {
			affectsPopupMenu.add(new AddAffectAction());
		}
		Affect selectedAffect = (Affect)getAffectsList().getSelectedValue();
		if(selectedAffect != null) {
			affectsPopupMenu.add(new EditAffectAction(selectedAffect));
			affectsPopupMenu.add(new DeleteAffectAction(selectedAffect));
		}
		return affectsPopupMenu;
	}
	
	private class AddAffectAction extends AbstractAction {
		public AddAffectAction() {
			super("Add affect...");
		}

		public void actionPerformed(ActionEvent e) {
			AffectsDialog dialog = new AffectsDialog(manager);
			dialog.setLocationRelativeTo(manager);
			dialog.setVisible(true);
			if(dialog.okClicked()) {
				Affect affect = new Affect();
				affect.setLocation(dialog.getAffectLocation());
				affect.setModifier(dialog.getModifier());
				((DefaultListModel)getAffectsList().getModel()).addElement(affect);
			}
		}
	}
	private class EditAffectAction extends AbstractAction {
		private Affect selectedAffect = null;
		public EditAffectAction(Affect selectedAffect) {
			super("Edit affect...");
			this.selectedAffect = selectedAffect;
		}

		public void actionPerformed(ActionEvent e) {
			AffectsDialog dialog = new AffectsDialog(manager, selectedAffect);
			dialog.setLocationRelativeTo(manager);
			dialog.setVisible(true);
			if(dialog.okClicked()) {
				selectedAffect.setLocation(dialog.getAffectLocation());
				selectedAffect.setModifier(dialog.getModifier());
				
				getAffectsList().revalidate();
				getAffectsList().repaint();
			}
		}
	}
	private class DeleteAffectAction extends AbstractAction {
		private Affect selectedAffect = null;
		public DeleteAffectAction(Affect selectedAffect) {
			super("Delete affect");
			this.selectedAffect = selectedAffect;
		}

		public void actionPerformed(ActionEvent e) {
			((DefaultListModel)getAffectsList().getModel()).removeElement(selectedAffect);
		}
	}
	
	private JPopupMenu getMaterialsPopupMenu() {
		JPopupMenu materialsPopupMenu = new JPopupMenu();
		
		materialsPopupMenu.add(new AddMaterialAction());
		
		Material selectedMaterial = (Material)getMaterialsList().getSelectedValue();
		if(selectedMaterial != null) {
			materialsPopupMenu.add(new EditMaterialAction(selectedMaterial));
			materialsPopupMenu.add(new DeleteMaterialAction(selectedMaterial));
		}
		return materialsPopupMenu;
	}
	
	private class AddMaterialAction extends AbstractAction {
		public AddMaterialAction() {
			super("Add material...");
		}

		public void actionPerformed(ActionEvent e) {
			MaterialsDialog dialog = new MaterialsDialog(manager);
			dialog.setLocationRelativeTo(manager);
			dialog.setVisible(true);
			if(dialog.okClicked() && dialog.getMaterialVnum() > 0 && manager.getItemManager().getItem(dialog.getMaterialVnum()) != null) {
				Material material = new Material();
				Item item = manager.getItemManager().getItem(dialog.getMaterialVnum());
				material.setMaterialVnum(dialog.getMaterialVnum());
				material.setMaterialShortDesc(Item.transformDesc(item.getShortDesc(), dialog.getQuality(), item.getValue4()));
				material.setQuality(dialog.getQuality());
				material.setQuantity(dialog.getQuantity());

				((DefaultListModel)getMaterialsList().getModel()).addElement(material);
			}
		}
	}
	private class EditMaterialAction extends AbstractAction {
		private Material selectedMaterial = null;
		public EditMaterialAction(Material selectedMaterial) {
			super("Edit material...");
			this.selectedMaterial = selectedMaterial;
		}

		public void actionPerformed(ActionEvent e) {
			MaterialsDialog dialog = new MaterialsDialog(manager, selectedMaterial);
			dialog.setLocationRelativeTo(manager);
			dialog.setVisible(true);
			if(dialog.okClicked() && dialog.getMaterialVnum() > 0 && manager.getItemManager().getItem(dialog.getMaterialVnum()) != null) {
				Item item = manager.getItemManager().getItem(dialog.getMaterialVnum());
				selectedMaterial.setMaterialVnum(dialog.getMaterialVnum());
				selectedMaterial.setMaterialShortDesc(Item.transformDesc(item.getShortDesc(), dialog.getQuality(), item.getValue4()));
				selectedMaterial.setQuality(dialog.getQuality());
				selectedMaterial.setQuantity(dialog.getQuantity());

//				((DefaultListModel)getMaterialsList().getModel()).addElement(selectedMaterial);
				getMaterialsList().revalidate();
				getMaterialsList().repaint();
			}
		}
	}
	private class DeleteMaterialAction extends AbstractAction {
		private Material selectedMaterial = null;
		public DeleteMaterialAction(Material selectedMaterial) {
			super("Delete material");
			this.selectedMaterial = selectedMaterial;
		}

		public void actionPerformed(ActionEvent e) {
			((DefaultListModel)getMaterialsList().getModel()).removeElement(selectedMaterial);
		}
	}
	
	private class ExportProductAction extends AbstractAction {
		private RecipeManager manager = null;
		public ExportProductAction(RecipeManager manager) {
			super("Export Product");
			this.manager = manager;
		}

		public void actionPerformed(ActionEvent e) {
			String result = "#";
			result += getVnumTextField().getText()+"\n";
			result += getNameTextField().getText()+"~\n";
			result += getShortDescTextField().getText()+"~\n";
			result += getLongDescTextField().getText()+"~\n";
			result += "~\n";
			result += getTypeFlagComboBox().getSelectedIndex()+" "+RecipeEditor.parseLong(getExtraFlagsTextField().getText())+" "+RecipeEditor.parseLong(getWearFlagsTextField().getText())+"\n";
			result += RecipeEditor.parseInt(getValue1TextField().getText())+" "+RecipeEditor.parseInt(getValue2TextField().getText())+" "+RecipeEditor.parseInt(getValue3TextField().getText())+" "+RecipeEditor.parseInt(getValue4TextField().getText())+" "+RecipeEditor.parseInt(getValue5TextField().getText())+"\n";
			result += RecipeEditor.parseInt(getWeightTextField().getText())+" 100 "+RecipeEditor.parseInt(getEgoTextField().getText())+"\n";
			
			DefaultListModel model = (DefaultListModel)getAffectsList().getModel();
			Affect aff = null;
			for(int i = 0; (i < model.size() && i <= 5); i++) {
				aff = (Affect)model.getElementAt(i);
				result += "A\n"+aff.getLocation()+" "+aff.getModifier()+"\n";
			}
			JOptionPane.showMessageDialog(manager, new JTextArea(result), "Export Product", JOptionPane.INFORMATION_MESSAGE);
		}
	}
//	
//	private JPopupMenu getVendorPopupMenu() {
//		JPopupMenu vendorPopupMenu = new JPopupMenu();
//		
//		vendorPopupMenu.add(new AddVendorAction());
//		
////		Material selectedMaterial = (Material)getMaterialsList().getSelectedValue();
////		if(selectedMaterial != null) {
////			vendorPopupMenu.add(new EditMaterialAction(selectedMaterial));
////			vendorPopupMenu.add(new DeleteMaterialAction(selectedMaterial));
////		}
//		return vendorPopupMenu;
//	}
//	
//	private class AddVendorAction extends AbstractAction {
//		public AddVendorAction() {
//			super("Add vendor...");
//		}
//
//		public void actionPerformed(ActionEvent e) {
//			VendorDialog dialog = new VendorDialog(manager);
//			dialog.setLocationRelativeTo(manager);
//			dialog.setVisible(true);
//		}
//	}
	
	private class FixedLengthPlainDocument extends PlainDocument {

		private int maxLength;

		public FixedLengthPlainDocument(int maxLength) {
			this.maxLength = maxLength;
		}

		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
			if (getLength() + str.length() > maxLength) {

				Toolkit.getDefaultToolkit().beep();
			} else {
				super.insertString(offset, str, a);
			}
		}
	} 

}  //  @jve:decl-index=0:visual-constraint="10,10"
