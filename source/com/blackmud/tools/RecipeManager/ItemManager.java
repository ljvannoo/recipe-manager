package com.blackmud.tools.RecipeManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import com.blackmud.tools.RecipeManager.data.Item;

public class ItemManager {
	private Hashtable<Long, Item> itemTable = null;
	private DefaultMutableTreeNode itemTreeRoot = null;

	public ItemManager() {
	}
	
	public void loadObjectFile() {
		File objFile = new File(getClass().getResource("/com/blackmud/tools/RecipeManager/data/world.obj").getFile());
		BufferedReader input = null;
		InputStream is = null;
		long lastVnum = 0l;
		
		if(objFile.exists()) {
			System.out.println("INFO: Trying to load obj file...");
			try {
				input = new BufferedReader(new FileReader(objFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("INFO: File not found, loading from jar...");
			is = getClass().getResourceAsStream("/com/blackmud/tools/RecipeManager/data/world.obj");
			input = new BufferedReader(new InputStreamReader(is));
		}
		
		if(input != null) {
			String line = null;
			Item tempItem = null;
			DefaultMutableTreeNode folderNode = null;
			Hashtable<String, DefaultMutableTreeNode> folderTable = new Hashtable<String, DefaultMutableTreeNode>();
			itemTreeRoot = new DefaultMutableTreeNode();
			
			try {
				while((line = input.readLine().trim()) != null) {
					if(line.length() > 0 && line.charAt(0) == '#') {
						if(tempItem != null)
							lastVnum = tempItem.getVnum();
						tempItem = new Item();
						
						tempItem.setVnum(RecipeEditor.parseLong(line.substring(1).trim()));
						
//						if((tempItem.getVnum() - lastVnum) > 1) {
//							System.out.println("Gap between #"+lastVnum+" and #"+tempItem.getVnum()+" = "+(tempItem.getVnum()-lastVnum));
//						}
						if((line = input.readLine().trim()) != null) {
							if(line.charAt(0) == '$')
								break;
							
							tempItem.setName(line.substring(0, line.length()-1));
							
							line = input.readLine().trim();
							tempItem.setShortDesc(line.substring(0, line.length()-1));
							
							line = input.readLine().trim();
							while(line.charAt(line.length()-1) != '~')
								line = line+input.readLine().trim();
							tempItem.setLongDesc(line.substring(0, line.length()-1));
							
							// Get type, extra flags, wear flags
							line = input.readLine().trim().replaceAll("  ", " "); 
							
							if(line.contains("~"))			// Throw away the action description
								line = input.readLine().trim().replaceAll("  ", " ");
							String[] values = line.split(" ");
							if(values.length > 0) {
								tempItem.setType(Integer.parseInt(values[0].trim()));
								if(tempItem.getType() < 0 ||tempItem.getType() > RecipeEditor.TYPES.length) {
									tempItem.setType(-1);
								}
							}
							if(values.length > 1) {
								tempItem.setExtraFlags(Long.parseLong(values[1].trim()));
							}
							if(values.length > 2) {
								tempItem.setWearFlags(Long.parseLong(values[2].trim()));
							}
							
							// Get values
							line = input.readLine().trim().replaceAll("  ", " ");
							values = line.split(" ");
							for(int val = 0; val < values.length && val < 5; val++) {
								tempItem.setValue(val, Integer.parseInt(values[val].trim()));
							}
							
							// Get weight, value, ego
							line = input.readLine().trim().replaceAll("  ", " ");
							values = line.split(" ");
							if(values.length > 0) {
								tempItem.setWeight(Integer.parseInt(values[0].trim()));
							}
							if(values.length > 1) {
								tempItem.setValue(Long.parseLong(values[1].trim()));
							}
							if(values.length > 2) {
								tempItem.setValue(Integer.parseInt(values[2].trim()));
							}
							
							getItemTable().put(tempItem.getVnum(), tempItem);
							
							if(!folderTable.containsKey(getTypeString(tempItem.getType()))) {
								folderNode = new DefaultMutableTreeNode(getTypeString(tempItem.getType()));
								//itemTreeRoot.add(folderNode);
								folderTable.put(getTypeString(tempItem.getType()), folderNode);
							}
							folderTable.get(getTypeString(tempItem.getType())).add(new DefaultMutableTreeNode(tempItem));
						}
					}
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
					itemTreeRoot.add(folderTable.get(keys.get(i)));
				}
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("INFO: Loaded "+getItemTable().size()+" items from the object file");
		} else {
			System.out.println("ERROR: Failed to load obj file!");
		}
	}
	
	public static String getTypeString(int type) {
		if(type >= 0 && type < RecipeEditor.TYPES.length)
			return RecipeEditor.TYPES[type];
		
		return "UNDEFINED";
	}

	private Hashtable<Long, Item> getItemTable() {
		if(itemTable == null) {
			itemTable = new Hashtable<Long, Item>();
		}
		return itemTable;
	}
	
	public Item getItem(long vnum) {
		return getItemTable().get(vnum);
	}

	public DefaultMutableTreeNode getItemTreeRoot() {
		return itemTreeRoot;
	}
}
