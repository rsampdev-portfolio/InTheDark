package com.rsampdev.inTheDark;

enum Weapon {

	NONE(0, "literally nothing"), FIST(5, "fists"), WOODEN_SWORD(10, "a wooden sword"),
	STONE_SWORD(20, "a stone sword"), IRON_SWORD(30, "an iron sword"), STEEL_SWORD(40, "a steel sword");

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

}
