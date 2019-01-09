package com.blackmud.tools.RecipeManager.data;

public class Material {
	private long materialVnum = 0;
	private int quality = 0;
	private int quantity = 0;
	private String materialShortDesc = null;
	
	public Material() {
		
	}

	public String getMaterialShortDesc() {
		return materialShortDesc;
	}

	public void setMaterialShortDesc(String materialShortDesc) {
		this.materialShortDesc = materialShortDesc;
	}

	public long getMaterialVnum() {
		return materialVnum;
	}

	public void setMaterialVnum(long materialVnum) {
		this.materialVnum = materialVnum;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String toString() {
		return ""+getQuantity()+"x '"+getMaterialShortDesc()+"' (#"+getMaterialVnum()+")";
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}
}
