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

import com.blackmud.tools.RecipeManager.data.NPC;

public class NPCManager {
	private Hashtable<Long, NPC> npcTable = null;
	
	public NPCManager() {
	}
	
	public void loadMobFile() {
		File objFile = new File(getClass().getResource("/com/blackmud/tools/RecipeManager/data/world.mob").getFile());
		BufferedReader input = null;
		InputStream is = null;
		
		if(objFile.exists()) {
			System.out.println("INFO: Trying to load mob file...");
			try {
				input = new BufferedReader(new FileReader(objFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("INFO: File not found, loading from jar...");
			is = getClass().getResourceAsStream("/com/blackmud/tools/RecipeManager/data/world.mob");
			input = new BufferedReader(new InputStreamReader(is));
		}
		
		if(input != null) {
			String line = null;
			NPC tempNPC = null;
			
			try {
				while((line = input.readLine().trim()) != null) {
					if(line.length() > 0 && line.charAt(0) == '#') {
						tempNPC = new NPC();
						tempNPC.setVnum(Long.parseLong(line.substring(1).trim()));
						if((line = input.readLine().trim()) != null) {
							if(line.charAt(0) == '$')
								break;
							
							tempNPC.setName(line.substring(0, line.length()-1));
							
							line = input.readLine().trim();
							tempNPC.setShortDesc(line.substring(0, line.length()-1));
							
							line = input.readLine().trim();
							tempNPC.setLongDesc(line.substring(0, line.length()-1));
							
							getNPCTable().put(tempNPC.getVnum(), tempNPC);
						}
					}
				}
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("INFO: Loaded "+getNPCTable().size()+" NPCs from the mob file");
		} else {
			System.out.println("ERROR: Failed to load mob file!");
		}
	}

	private Hashtable<Long, NPC> getNPCTable() {
		if(npcTable == null) {
			npcTable = new Hashtable<Long, NPC>();
		}
		return npcTable;
	}
	
	public NPC getNPC(long vnum) {
		return getNPCTable().get(vnum);
	}
	
	public Vector<NPC> getNPCList() {
		Vector<NPC> npcList = new Vector<NPC>(getNPCTable().size());
		Enumeration<Long> keys = getNPCTable().keys();
		
		while(keys.hasMoreElements()) {
			npcList.add(getNPCTable().get(keys.nextElement()));
		}
		Sorter sorter = new Sorter(npcList, new Comparator<NPC>() {
			public int compare(NPC o1, NPC o2) {
				Long vnum1 = o1.getVnum();
				Long vnum2 = o2.getVnum();
				return vnum1.compareTo(vnum2);
			}
		});
		npcList = sorter.sort(Sorter.QUICK_SORT);
		
		return npcList;
	}
}
