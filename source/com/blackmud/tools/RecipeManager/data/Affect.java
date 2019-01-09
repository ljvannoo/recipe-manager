package com.blackmud.tools.RecipeManager.data;

public class Affect {
	public static final String[] AFFECTS = {"NONE", //0
		"STR",
		"DEX",
		"INT",
		"WIS",
		"CON",
		"CHR",
		"SEX",
		"LEVEL",
		"AGE",
		"CHAR_WEIGHT", //10
		"CHAR_HEIGHT",
		"MANA",
		"HIT",
		"MOVE",
		"GOLD",
		"EXP",
		"ARMOR",
		"HITROLL",
		"DAMROLL", //20
		"SAVING_PARA",
		"SAVING_ROD",
		"SAVING_PETRI",
		"SAVING_BREATH",
		"SAVING_SPELL",
		"SAVE_ALL",
		"IMMUNE",
		"SUSC",
		"M_IMMUNE",
		"SPELL", //30
		"WEAPON_SPELL",
		"EAT_SPELL",
		"BACKSTAB",
		"KICK",
		"SNEAK",
		"HIDE",
		"BASH",
		"PICK",
		"STEAL",
		"TRACK", //40
		"HITNDAM",
		"SPELLFAIL",
		"ATTACKS",
		"HASTE",
		"SLOW",
		"BV2",
		"FIND_TRAPS",
		"RIDE",
		"RACE_SLAYER",
		"ALIGN_SLAYER",
		"MANA_REGEN",
		"HIT_REGEN",
		"MOVE_REGEN",
		"MOVE_BONUS",
		"INTRINSIC"		
	};
	
	private int location = 0;
	private int modifier = 0;
	
	public Affect() {
		
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
	
	public String toString() {
		return AFFECTS[getLocation()]+" by "+getModifier();
	}
}
