package com.blackmud.tools.RecipeManager.data;

public class Recipe {
	private int id = -1;
	private String title = null;
	private String comments = null;
	private int runs = 1;
	private int quantity = 1;
	private boolean binds = false;
	private long vnum = -1;
	private String name = null;
	private String shortDesc = null;
	private String longDesc = null;
	private int value1 = 0;
	private int value2 = 0;
	private int value3 = 0;
	private int value4 = 0;
	private int value5 = 0;
	private long extraFlags = 0;
	private int weight = 0;
	private int typeFlag = 0;
	private long wearFlags = 0;
	private int ego = 0;
	
	public Recipe() {
		
	}
	
	public String getComments() {
		if(comments == null)
			return "";
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getEgo() {
		return ego;
	}

	public void setEgo(int ego) {
		this.ego = ego;
	}

	public long getExtraFlags() {
		return extraFlags;
	}

	public void setExtraFlags(long extraFlags) {
		this.extraFlags = extraFlags;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getRuns() {
		return runs;
	}

	public void setRuns(int runs) {
		this.runs = runs;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(int typeFlag) {
		this.typeFlag = typeFlag;
	}

	public int getValue1() {
		return value1;
	}

	public void setValue1(int value1) {
		this.value1 = value1;
	}

	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}

	public int getValue3() {
		return value3;
	}

	public void setValue3(int value3) {
		this.value3 = value3;
	}

	public int getValue4() {
		return value4;
	}

	public void setValue4(int value4) {
		this.value4 = value4;
	}

	public int getValue5() {
		return value5;
	}

	public void setValue5(int value5) {
		this.value5 = value5;
	}

	public long getVnum() {
		return vnum;
	}

	public void setVnum(long vnum) {
		this.vnum = vnum;
	}

	public long getWearFlags() {
		return wearFlags;
	}

	public void setWearFlags(long wearFlags) {
		this.wearFlags = wearFlags;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public String toString() {
		return "#"+getID()+" - "+getTitle();
	}

	public boolean isBinds() {
		return binds;
	}

	public void setBinds(boolean binds) {
		this.binds = binds;
	}
}
