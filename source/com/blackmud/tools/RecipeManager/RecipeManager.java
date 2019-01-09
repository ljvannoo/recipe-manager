package com.blackmud.tools.RecipeManager;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.blackmud.tools.RecipeManager.data.Item;
import com.blackmud.tools.RecipeManager.data.Recipe;
import com.blackmud.tools.RecipeManager.data.Server;
import com.blackmud.tools.RecipeManager.database.DBMediator;
import com.blackmud.tools.RecipeManager.database.DBResult;
import com.mysql.jdbc.UpdatableResultSet;

public class RecipeManager extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar mainMenuBar = null;
	private JSplitPane mainSplitPane = null;
	private JPanel statusPanel = null;
	private JTabbedPane leftTabbedPane = null;
	private JPanel recipeListPanel = null;
	private JMenu fileMenu = null;
	private JMenu helpMenu = null;
	private JScrollPane recipeListScrollPane = null;
	private JTree recipeTree = null;
	private JMenuItem fileConnectToAltDBMI = null;
	private JMenuItem fileExitMI = null;
	private JMenuItem helpAboutMI = null;
	private JTabbedPane rightTabbedPane = null;
	private DBMediator dbMediator = null;  //  @jve:decl-index=0:
	private DBMediator altDBMediator = null;
	private int busyCount = 0;
	private JLabel statusLabel = null;
	private JProgressBar progressBar = null;
	private JPopupMenu recipeListPopupMenu = null;  //  @jve:decl-index=0:visual-constraint="1124,315"
	private ItemManager itemManager = null;
	private NPCManager npcManager = null;
	private Configuration config = null; 
	private Server primaryServer = null;
	private Server alternateServer = null;

	/**
	 * This method initializes progressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
		}
		return progressBar;
	}

	/**
	 * This method initializes recipeListPopupMenu	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getRecipeListPopupMenu() {
		recipeListPopupMenu = new JPopupMenu();
		recipeListPopupMenu.add(new NewRecipeAction(this));
		if(getSelectedRecipe() != null) {
			recipeListPopupMenu.add(new EditRecipeAction());
			recipeListPopupMenu.add(new DeleteRecipeAction(this));
		}
		recipeListPopupMenu.add(new JSeparator());
		recipeListPopupMenu.add(new RefreshRecipesAction());

		return recipeListPopupMenu;
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final RecipeManager thisClass = new RecipeManager(args);
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setLocationRelativeTo(null);
				thisClass.setVisible(true);
				thisClass.showLoginDialog();
//				SwingUtilities.invokeLater(new Runnable() {
//					public void run() {
//						thisClass.connect();
//					}
//				});
			}
		});
	}

	protected void showLoginDialog() {
		final LoginDialog dialog = new LoginDialog(this);
		dialog.setTitle("BlackMUD Recipe Manager v"+getConfig().getProperty("version"));
		dialog.setLocationRelativeTo(this);
		
		Thread t0 = new Thread() {
			public void run() {
				busy(true);
				setStatus("Logging in...");
				String error = "temp";

				while(error != null) {
					dialog.setVisible(true);
					
					if(dialog.getButtonClicked() == LoginDialog.LOGIN_BUTTON) {
						primaryServer = dialog.getSelectedServer();
						setStatus("Connecting to database...");
						error = getDBMediator().getError();
						if(error != null) {
							if(error.startsWith("Access denied"))
								error = "Access denied";
							JOptionPane.showMessageDialog(dialog.getParent(), "Error connecting to database:\n"+error, "Connection Error", JOptionPane.ERROR_MESSAGE);
							dbMediator = null;
						}
					} else {
						// Cancel button clicked
						setVisible(false);
						dispose();
						System.exit(0);
					}
					dialog.reset();
				}
				
				setStatus("Loading recipe list...");
				loadRecipeList();
				setStatus("Loading object file...");
				getItemManager().loadObjectFile();
				setStatus("Loading NPC file...");
				getNPCManager().loadMobFile();

				setStatus("Ready.");
				busy(false);
			}
		};
		t0.start();
	}

	/**
	 * This is the default constructor
	 */
	public RecipeManager(String[] args) {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 for(int i=0; i<args.length; i++) {
            String arg = args[i];
            String values[] = new String[2];
            values = arg.split("=", 2);
            //System.out.println("Found argument: "+values[0]+" = "+values[1]);

            if(values[1].charAt(0)=='\'' || values[1].charAt(0)=='\"')
                values[1] = values[1].substring(1);
            if(values[1].charAt(values[1].length()-1)=='\'' || values[1].charAt(values[1].length()-1)=='\"')
                values[1] = values[1].substring(0, values[1].length()-1);
            config.setProperty(values[0], values[1]);
        }
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1200, 768);
		this.setJMenuBar(getMainMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Recipe Manager v"+getConfig().getProperty("version"));
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				if(getDBMediator() != null && getDBMediator().connected())
					getDBMediator().close();
				if(getAltDBMediator() != null && getAltDBMediator().connected())
					getAltDBMediator().close();
			}
		});
		setGlassPane(new GlassComponent());
	}

	/**
	 * This method initializes mainMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();
			mainMenuBar.add(getFileMenu());
			//mainMenuBar.add(getHelpMenu());
		}
		return mainMenuBar;
	}

	/**
	 * This method initializes mainSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getMainSplitPane() {
		if (mainSplitPane == null) {
			mainSplitPane = new JSplitPane();
			mainSplitPane.setLeftComponent(getLeftTabbedPane());
			mainSplitPane.setRightComponent(getRightTabbedPane());
			mainSplitPane.setDividerLocation(250);
		}
		return mainSplitPane;
	}

	/**
	 * This method initializes statusPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStatusPanel() {
		if (statusPanel == null) {
			statusLabel = new JLabel();
			statusLabel.setText("");
			statusPanel = new JPanel();
			statusPanel.setLayout(new BorderLayout());
			statusPanel.setPreferredSize(new Dimension(20, 20));
			statusPanel.add(statusLabel, BorderLayout.CENTER);
			statusPanel.add(getProgressBar(), BorderLayout.EAST);
		}
		return statusPanel;
	}

	/**
	 * This method initializes leftTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getLeftTabbedPane() {
		if (leftTabbedPane == null) {
			leftTabbedPane = new JTabbedPane();
			leftTabbedPane.addTab("Recipe List", null, getRecipeListPanel(), null);
		}
		return leftTabbedPane;
	}

	/**
	 * This method initializes recipeListPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getRecipeListPanel() {
		if (recipeListPanel == null) {
			recipeListPanel = new JPanel();
			recipeListPanel.setLayout(new BorderLayout());
			recipeListPanel.add(getRecipeListScrollPane(), BorderLayout.CENTER);
		}
		return recipeListPanel;
	}

	/**
	 * This method initializes fileMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getFileConnectToAltDBMI());
			fileMenu.add(new JSeparator());
			fileMenu.add(getFileExitMI());
		}
		return fileMenu;
	}

	/**
	 * This method initializes helpMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getHelpAboutMI());
		}
		return helpMenu;
	}

	/**
	 * This method initializes recipeListScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getRecipeListScrollPane() {
		if (recipeListScrollPane == null) {
			recipeListScrollPane = new JScrollPane();
			recipeListScrollPane.setViewportView(getRecipeTree());
		}
		return recipeListScrollPane;
	}

	/**
	 * This method initializes recipeList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JTree getRecipeTree() {
		if (recipeTree == null) {
			recipeTree = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode()));
			recipeTree.setRootVisible(false);
			recipeTree.setShowsRootHandles(true);
			recipeTree.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent evt) {    
					TreePath selectedPath = recipeTree.getPathForLocation(evt.getX(), evt.getY());
					recipeTree.setSelectionPath(selectedPath);
					if (evt.isPopupTrigger()) {
						getRecipeListPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
					}
					if(evt.getClickCount() == 2) {
						editSelectedRecipe();
					}
				}   
				public void mouseReleased(java.awt.event.MouseEvent evt) {
					TreePath selectedPath = recipeTree.getPathForLocation(evt.getX(), evt.getY());
					recipeTree.setSelectionPath(selectedPath);
					if (evt.isPopupTrigger()) {
						getRecipeListPopupMenu().show(evt.getComponent(), evt.getX(), evt.getY());
					}
				} 
			});
		}
		return recipeTree;
	}
	
	private JMenuItem getFileConnectToAltDBMI() {
		if (fileConnectToAltDBMI == null) {
			fileConnectToAltDBMI = new JMenuItem();
			fileConnectToAltDBMI.setText("Connect to Alt. DB...");
			fileConnectToAltDBMI.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					connectToAltDB();
				}
			});
		}
		return fileConnectToAltDBMI;
	}
	
	private class Semaphore {
		public boolean done = false;
	}

	protected void connectToAltDB() {
		final LoginDialog dialog = new LoginDialog(this);
		dialog.setTitle("Connect to Alternate Database");
		dialog.setLocationRelativeTo(this);
		final Semaphore flag = new Semaphore();
		
		for(;;) {
			dialog.setVisible(true);
			
			if(dialog.getButtonClicked() == LoginDialog.LOGIN_BUTTON) {
				if(dialog.getSelectedServer().getName().equals(primaryServer.getName())) {
					JOptionPane.showMessageDialog(this, "Primary and Alternate databases must be different.", "Connection Error", JOptionPane.ERROR_MESSAGE);
				} else {
					Thread t0 = new Thread() {
						public void run() {
							busy(true);
							setStatus("Logging in to alternate database...");
							
							Server tempServer = dialog.getSelectedServer();
							if(altDBMediator != null && altDBMediator.connected()) {
								altDBMediator.close();
								altDBMediator = null;
							}
							
							altDBMediator = new DBMediator();
							String result = altDBMediator.connect(tempServer.getURL(), tempServer.getName(), tempServer.getUsername(), tempServer.getPassword());
							if(result != null) {
								System.out.println("ERROR: "+result);
								if(result != null && result.startsWith("Access denied")) 
									result = "Access denied";
//								JOptionPane.showMessageDialog(dialog.getParent(), result, "Connection Error", JOptionPane.ERROR_MESSAGE);
								setStatus("CONNECTION ERROR: "+result);
								altDBMediator = null;
								dialog.reset();
							} else {
								alternateServer = tempServer;
								setTitle(getTitle()+" (Alt: "+alternateServer.getTitle()+")");
							}
							
//							String error = "temp";
//
//							if(dialog.getButtonClicked() == LoginDialog.LOGIN_BUTTON) {
//								primaryServer = dialog.getSelectedServer();
//								setStatus("Connecting to database...");
//								error = getDBMediator().getError();
//								if(error != null) {
//									if(error.startsWith("Access denied"))
//										error = "Access denied";
//									JOptionPane.showMessageDialog(dialog.getParent(), "Error connecting to database:\n"+error, "Connection Error", JOptionPane.ERROR_MESSAGE);
//									dbMediator = null;
//								}
//							} else {
//								// Cancel button clicked
//								setVisible(false);
//								dispose();
//								System.exit(0);
//							}
//							dialog.reset();
//							
//							setStatus("Loading recipe list...");
//							loadRecipeList();
//							setStatus("Loading object file...");
//							getItemManager().loadObjectFile();
//							setStatus("Loading NPC file...");
//							getNPCManager().loadMobFile();

							busy(false);
							flag.done = true;
						}
					};
					t0.start();
					while(!flag.done) {
						try {
							Thread.sleep(25);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					flag.done = false;
					if(altDBMediator != null)
						break;
				}
			} else {
				break;
			}
		}
		setStatus("Ready.");
		busy(false);
	}

	/**
	 * This method initializes fileExitMI	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileExitMI() {
		if (fileExitMI == null) {
			fileExitMI = new JMenuItem();
			fileExitMI.setText("Exit");
			fileExitMI.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
					dispose();
				}
			});
		}
		return fileExitMI;
	}

	/**
	 * This method initializes helpAboutMI	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getHelpAboutMI() {
		if (helpAboutMI == null) {
			helpAboutMI = new JMenuItem();
			helpAboutMI.setText("About...");
		}
		return helpAboutMI;
	}

	/**
	 * This method initializes rightTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getRightTabbedPane() {
		if (rightTabbedPane == null) {
			rightTabbedPane = new JTabbedPane();
			rightTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mousePressed(java.awt.event.MouseEvent evt) {
					maybeShowPopup(evt);
				}
				public void mouseReleased(java.awt.event.MouseEvent evt) {    
					maybeShowPopup(evt);
				}

				private void maybeShowPopup(java.awt.event.MouseEvent e) {
					if (e.isPopupTrigger()) {
						int tabcount = getRightTabbedPane().getTabCount();
						for(int i=0;i<tabcount;i++) {
							TabbedPaneUI tpu = getRightTabbedPane().getUI();
							Rectangle rect = tpu.getTabBounds(getRightTabbedPane(), i);
							int x = e.getX();
							int y = e.getY();
							if (x < rect.x  ||  x > rect.x+rect.width  ||  y < rect.y  ||  y > rect.y+rect.height)
								continue;

							getRightTabbedPane().setSelectedIndex(i);

							RecipeEditor editor = (RecipeEditor)rightTabbedPane.getSelectedComponent();
							editor.getTabPopupMenu().show(e.getComponent(), e.getX(), e.getY());
							break;
						}
					}
				}
			});
		}
		return rightTabbedPane;
	}

//	protected void connect() {
//		Thread t0 = new Thread() {
//			public void run() {
//				busy(true);
//				setStatus("Connecting to database...");
//				if(getDBMediator().connected()) {
//					setStatus("Loading recipe list...");
//					loadRecipeList();
//					setStatus("Loading object file...");
//					getItemManager().loadObjectFile();
//					setStatus("Loading NPC file...");
//					getNPCManager().loadMobFile();
//					setStatus("Ready.");
//				} else {
//					System.out.println("Error connecting to the database!");
//				}
//				busy(false);
//			}
//		};
//		t0.start();
//	}

	private void loadRecipeList() {
		DBResult result = getDBMediator().executeQuery("SELECT id, title, comments, runs, quantity, binds, vnum, name, short_desc, long_desc, value1, value2, value3, value4, value5, extra_flags, weight, type_flag, wear_flags, ego FROM bmud_recipes ORDER BY id");
		Recipe recipe = null;
		DefaultMutableTreeNode folderNode = null;
		DefaultMutableTreeNode recipeTreeRoot = new DefaultMutableTreeNode();
		DefaultTreeModel model = new DefaultTreeModel(recipeTreeRoot);
		Hashtable<String, DefaultMutableTreeNode> folderTable = new Hashtable<String, DefaultMutableTreeNode>();

		for(int i = 0; i < result.size(); i++) {
			System.out.println("Loading recipe #"+result.getInt(i, "id")+" - "+result.getString(i, "title"));
			recipe = new Recipe();
			recipe.setID(result.getInt(i, "id"));
			recipe.setTitle(result.getString(i, "title"));
			recipe.setComments(result.getString(i, "comments"));
			recipe.setRuns(result.getInt(i, "runs"));
			recipe.setQuantity(result.getInt(i, "quantity"));
			recipe.setBinds(result.getBool(i, "binds"));
			recipe.setVnum(result.getInt(i, "vnum"));
			recipe.setName(result.getString(i, "name"));
			recipe.setShortDesc(result.getString(i, "short_desc"));
			recipe.setLongDesc(result.getString(i, "long_desc"));
			recipe.setValue1(result.getInt(i, "value1"));
			recipe.setValue2(result.getInt(i, "value2"));
			recipe.setValue3(result.getInt(i, "value3"));
			recipe.setValue4(result.getInt(i, "value4"));
			recipe.setValue5(result.getInt(i, "value5"));
			recipe.setExtraFlags(result.getLong(i, "extra_flags"));
			recipe.setWeight(result.getInt(i, "weight"));
			recipe.setTypeFlag(result.getInt(i, "type_flag"));
			recipe.setWearFlags(result.getLong(i, "wear_flags"));
			recipe.setEgo(result.getInt(i, "ego"));
			if(!folderTable.containsKey(ItemManager.getTypeString(recipe.getTypeFlag()))) {
				folderNode = new DefaultMutableTreeNode(ItemManager.getTypeString(recipe.getTypeFlag()));
				folderTable.put(ItemManager.getTypeString(recipe.getTypeFlag()), folderNode);
			}
			folderTable.get(ItemManager.getTypeString(recipe.getTypeFlag())).add(new DefaultMutableTreeNode(recipe));
		}
		Enumeration<String> e = folderTable.keys();
		Vector<String> keys = new Vector<String>();
		while(e.hasMoreElements())
			keys.add(e.nextElement());
		
		Sorter sorter = new Sorter(keys, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		keys = sorter.sort(Sorter.QUICK_SORT);
		for(int i = 0; i < keys.size(); i++) {
			((DefaultMutableTreeNode)model.getRoot()).add(folderTable.get(keys.get(i)));
		}
		getRecipeTree().setModel(model);
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
			jContentPane.add(getMainSplitPane(), BorderLayout.CENTER);
			jContentPane.add(getStatusPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	public DBMediator getDBMediator() {
		if(dbMediator == null && primaryServer != null) {
			dbMediator = new DBMediator();
			String result = dbMediator.connect(primaryServer.getURL(), primaryServer.getName(), primaryServer.getUsername(), primaryServer.getPassword());
			if(result != null) {
				dbMediator.setError(result);
				System.out.println("ERROR: "+result);
			} else {
				setTitle(getTitle()+" - "+primaryServer.getTitle());
			}
		}
		return dbMediator;
	}
	
	public DBMediator getAltDBMediator() {
		return altDBMediator;
	}

	public void busy(boolean state) {
		if (state) {
			busyCount++;
			if (busyCount==1) {
				getGlassPane().setVisible(true);
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				SwingUtilities.invokeLater(new Runnable() {
					public void run()
					{
						progressBar.setIndeterminate(true);
					}
				});
			}
		}
		else {
			busyCount--;
			if (busyCount==0) {
				getGlassPane().setVisible(false);
				this.setCursor(Cursor.getDefaultCursor());
				SwingUtilities.invokeLater(new Runnable() {
					public void run()
					{
						progressBar.setIndeterminate(false);
					}
				});
			}
		}
	}

	public void setStatus(String status) {
		statusLabel.setText(status);
		System.out.println("STATUS: "+status);
	}

	public void clearStatus() {
		statusLabel.setText("");
	}
	
	private void editSelectedRecipe() {
		Recipe selectedRecipe = getSelectedRecipe();
		if(selectedRecipe != null) {
			RecipeEditor editor = new RecipeEditor(selectedRecipe, this);
			editor.setCloseAction(new CloseRecipeAction(editor));
			getRightTabbedPane().add(selectedRecipe.toString(), editor);
			getRightTabbedPane().setSelectedComponent(editor);
		}
	}
	
	private Recipe getSelectedRecipe(int x, int y) {
		if(recipeTree != null) {
			TreePath selectedPath = recipeTree.getPathForLocation(x, y);
			if(selectedPath != null) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectedPath.getLastPathComponent();
				if (selectedNode != null && selectedNode.getUserObject() instanceof Recipe) {
					return (Recipe)selectedNode.getUserObject();
				}
			}
		}
		return null;
	}
	
	private Recipe getSelectedRecipe() {
		if(recipeTree != null) {
			TreePath selectedPath = recipeTree.getSelectionPath();
			if(selectedPath != null) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectedPath.getLastPathComponent();
				if (selectedNode != null && selectedNode.getUserObject() instanceof Recipe) {
					return (Recipe)selectedNode.getUserObject();
				}
			}
		}
		return null;
	}
	
	public ItemManager getItemManager() {
		if(itemManager == null) {
			itemManager = new ItemManager();
		}
		return itemManager;
	}
	
	public NPCManager getNPCManager() {
		if(npcManager == null) {
			npcManager = new NPCManager();
		}
		return npcManager;
	}

	private class NewRecipeAction extends AbstractAction {
		RecipeManager manager = null;
		public NewRecipeAction(RecipeManager manager) {
			super("New Recipe...");
			this.manager = manager;
		}

		public void actionPerformed(ActionEvent arg0) {
			Recipe recipe = new Recipe();
			recipe.setID(-1);
			recipe.setTitle("New Recipe");
			RecipeEditor editor = new RecipeEditor(recipe, manager);
			editor.setCloseAction(new CloseRecipeAction(editor));
			getRightTabbedPane().add(recipe.toString(), editor);
			getRightTabbedPane().setSelectedComponent(editor);
		}
	}
	
	private class EditRecipeAction extends AbstractAction {
		public EditRecipeAction() {
			super("Edit Recipe...");
		}

		public void actionPerformed(ActionEvent arg0) {
			editSelectedRecipe();
		}
	}
	
	private class DeleteRecipeAction extends AbstractAction {
		private RecipeManager app = null;
		public DeleteRecipeAction(RecipeManager app) {
			super("Delete Recipe...");
			this.app = app; 
		}

		public void actionPerformed(ActionEvent e) {
			final Recipe selectedRecipe = getSelectedRecipe();
			if(selectedRecipe != null) {
				int result = JOptionPane.showConfirmDialog(app, "Are you sure you want to delete recipe "+selectedRecipe.toString()+"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					Thread t0 = new Thread() {
						public void run() {
							busy(true);
							setStatus("Deleting recipe #"+selectedRecipe.getID());
									
							DBMediator dbm = getDBMediator();
							int tabcount = getRightTabbedPane().getTabCount();
							for(int i = 0;i < tabcount; i++) {
								if(((RecipeEditor)getRightTabbedPane().getComponentAt(i)).getRecipeID() == selectedRecipe.getID()) {
									getRightTabbedPane().removeTabAt(i);
									break;
								}
							}
							//((DefaultListModel)getRecipeList().getModel()).removeElement(selectedRecipe);
							removeRecipeFromTree(selectedRecipe);
							
							String query = "DELETE FROM bmud_recipe_materials WHERE recipe_id = "+selectedRecipe.getID();
							dbm.executeUpdate(query);
		
							query = "DELETE FROM bmud_recipe_product_affects WHERE recipe_id = "+selectedRecipe.getID();
							dbm.executeUpdate(query);
		
							query = "DELETE FROM bmud_recipe_vendors WHERE recipe_id = "+selectedRecipe.getID();
							dbm.executeUpdate(query);
							
							query = "DELETE FROM bmud_recipes WHERE id = "+selectedRecipe.getID();
							dbm.executeUpdate(query);
							
							setStatus("Ready.");
							busy(false);
						}
					};
					t0.run();
				}
			}
		}
	}
	
	protected void removeRecipeFromTree(Recipe selectedRecipe) {
		Enumeration<DefaultMutableTreeNode> e = ((DefaultMutableTreeNode)getRecipeTree().getModel().getRoot()).breadthFirstEnumeration();
		DefaultMutableTreeNode curNode = null, parentNode = null;
		Recipe curRecipe = null;
		while(e.hasMoreElements()) {
			curNode = e.nextElement();
			if(curNode.getUserObject() instanceof Recipe) {
				curRecipe = (Recipe)curNode.getUserObject();
				if(curRecipe.getID() == selectedRecipe.getID()) {
					parentNode = (DefaultMutableTreeNode)curNode.getParent();
					((DefaultTreeModel)getRecipeTree().getModel()).removeNodeFromParent(curNode);
					if(parentNode.getChildCount() == 0)
						((DefaultTreeModel)getRecipeTree().getModel()).removeNodeFromParent(parentNode);
					break;
				}
			}
		}
	}

	private class CloseRecipeAction extends AbstractAction {
		private RecipeEditor editor = null;
		public CloseRecipeAction(RecipeEditor editor) {
			super("Close");
			this.editor = editor;
		}

		public void actionPerformed(ActionEvent arg0) {
			if(editor.close())
				getRightTabbedPane().remove(editor);
		}
	}
	
	private class RefreshRecipesAction extends AbstractAction {
		public RefreshRecipesAction() {
			super("Refresh Recipes");
		}

		public void actionPerformed(ActionEvent arg0) {
			Thread t0 = new Thread() {
				public void run() {
					busy(true);
					setStatus("Reloading recipe list...");
					getRightTabbedPane().removeAll();
					loadRecipeList();
					setStatus("Ready.");
					busy(false);
				}
			};
			t0.run();
		}
	}

	public void addRecipe(Recipe recipe) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)getRecipeTree().getModel().getRoot();
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(recipe), folderNode = null;
		String folder = ItemManager.getTypeString(recipe.getTypeFlag());
		Enumeration<DefaultMutableTreeNode> e = root.breadthFirstEnumeration();
		while(e.hasMoreElements()) {
			folderNode = e.nextElement();
			if(folderNode.getUserObject() instanceof String) {
				if(folderNode.getUserObject().equals(folder)) {
					System.out.println("Found it!");
					folderNode.add(node);
					((DefaultTreeModel)getRecipeTree().getModel()).nodeStructureChanged(folderNode);
					getRecipeTree().expandPath(new TreePath(folderNode.getPath()));
					return;
				}
			}
		}
		// Didn't find the appropriate folder, so create it
		folderNode = new DefaultMutableTreeNode(folder);
		folderNode.add(node);
		for(int i = 0; i < root.getChildCount(); i++) {
			if(((String)((DefaultMutableTreeNode)root.getChildAt(i)).getUserObject()).compareTo(folder) > 0) {
				root.insert(folderNode, i);
				((DefaultTreeModel)getRecipeTree().getModel()).nodeStructureChanged(root);
				getRecipeTree().expandPath(new TreePath(folderNode.getPath()));
				return;
			}
		}
		root.add(folderNode);
		((DefaultTreeModel)getRecipeTree().getModel()).nodeStructureChanged(root);
		getRecipeTree().expandPath(new TreePath(folderNode.getPath()));
	}

	public void updateRecipeList() {
		getRecipeTree().revalidate();
		getRecipeTree().repaint();
	}
	
	public Configuration getConfig() {
		if(config == null) {
			config = new Configuration("config", "/com/blackmud/tools/RecipeManager/properties", "com/blackmud/tools/RecipeManager/properties");
		}
		return config;
	}
}
