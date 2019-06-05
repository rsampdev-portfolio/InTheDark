package com.rsampdev.inTheDark;

enum Weapon {

	NONE(0, "literally nothing"), FIST(5, "Fist"), WOODEN_SWORD(10, "Wooden Sword"), STONE_SWORD(20, "Stone Sword"), IRON_SWORD(30, "Iron Sword"), STEEL_SWORD(40, "Steel Sword");

	private int damage;
	private String description;

	private Weapon(int damage, String description) {
		this.damage = damage;
		this.description = description;
	}

	int getDamage() {
		return this.damage;
	}

	String getDescription() {
		return this.description;
	}

	String getStats() {
		String stats = getDescription() + " that deals " + getDamage() + " damage";
		return stats;
	}

}
