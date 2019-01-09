package com.blackmud.tools.RecipeManager.data;

public class NPC {
	private long vnum = 0;
	private String name = null;
	private String shortDesc = null;
	private String longDesc = null;
	
	public NPC() {
		
	}

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public long getVnum() {
		return vnum;
	}

	public void setVnum(long vnum) {
		this.vnum = vnum;
	}
	
	public String toString() {
		return "#"+getVnum()+" - "+getShortDesc();
	}
}
