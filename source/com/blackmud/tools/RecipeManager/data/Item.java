package com.blackmud.tools.RecipeManager.data;

import com.blackmud.tools.RecipeManager.RecipeEditor;

public class Item {
	private long vnum = 0;
	private String name = null;
	private String shortDesc = null;
	private String longDesc = null;
	private int type = 0;
	private long extraFlags = 0;
	private long wearFlags = 0;
	private int[] values = new int[5];
	private int weight = 0;
	private long value = 0;
	private int ego = 0;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Item() {
		
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

	public int getValue1() {
		return values[0];
	}

	public void setValue1(int value1) {
		this.values[0] = value1;
	}

	public int getValue2() {
		return values[1];
	}

	public void setValue2(int value2) {
		this.values[1] = value2;
	}

	public int getValue3() {
		return values[2];
	}

	public void setValue3(int value3) {
		this.values[2] = value3;
	}

	public int getValue4() {
		return values[3];
	}

	public void setValue4(int value4) {
		this.values[3] = value4;
	}
	
	public int getValue5() {
		return values[4];
	}

	public void setValue5(int value5) {
		this.values[4] = value5;
	}
	
	public void setValue(int valueIndex, int value) {
		this.values[valueIndex] = value;
	}
	
	public int getValue(int valueIndex) {
		return values[valueIndex];
	}
	
	public static String transformDesc(String desc, int quality, int qualitySet) {
		String tempDesc = new String(desc);
		if(quality > 0 && qualitySet > 0) {
			String vowels = "aeiou";
			String tempString = RecipeEditor.QUALITY_DESCS[qualitySet-1][quality-1];
			if(vowels.contains(""+tempString.charAt(0)))
				tempString = "an "+tempString;
			else
				tempString = "a "+tempString; 
			
			tempDesc = tempDesc.replaceAll("%a", tempString);
			
			tempString = tempString.substring(0, 1).toUpperCase()+tempString.substring(1);
			tempDesc = tempDesc.replaceAll("%A", tempString);
		}
		return tempDesc;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
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

	public long getWearFlags() {
		return wearFlags;
	}

	public void setWearFlags(long wearFlags) {
		this.wearFlags = wearFlags;
	}
}
