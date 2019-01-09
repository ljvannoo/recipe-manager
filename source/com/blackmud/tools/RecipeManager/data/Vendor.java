package com.blackmud.tools.RecipeManager.data;

public class Vendor {
	private long vendorVnum = 0;
	private long cost = 0;
	private int minLevel = 0;
	private String allowedClasses = null;
	private String vendorShortDesc = null;
	
	public Vendor() {
		
	}

	public String getAllowedClasses() {
		return allowedClasses;
	}

	public void setAllowedClasses(String allowedClasses) {
		this.allowedClasses = allowedClasses;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public String getVendorShortDesc() {
		return vendorShortDesc;
	}

	public void setVendorShortDesc(String vendorShortDesc) {
		this.vendorShortDesc = vendorShortDesc;
	}

	public long getVendorVnum() {
		return vendorVnum;
	}

	public void setVendorVnum(long vendorVnum) {
		this.vendorVnum = vendorVnum;
	}
	
	public String toString() {
		return ""+getCost()+" coins from "+getVendorShortDesc()+" (Lvl: "+getMinLevel()+", Cls: "+getAllowedClasses()+")";
	}
}
