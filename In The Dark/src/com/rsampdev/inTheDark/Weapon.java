package com.rsampdev.inTheDark;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum Weapon {

	NONE(0, 0, "literally nothing"), FIST(1, 5, "Fist"), WOODEN_SWORD(2, 10, "Wooden Sword"), STONE_SWORD(3, 20, "Stone Sword"), IRON_SWORD(4, 30, "Iron Sword"),
	STEEL_SWORD(5, 40, "Steel Sword");

	private int id;
	private int damage;
	private String description;

	private static final List<Weapon> WEAPONS = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = WEAPONS.size();

	private Weapon(int id, int damage, String description) {
		this.damage = damage;
		this.description = description;
	}

	int getID() {
		return this.id;
	}

	int getDamage() {
		return this.damage;
	}

	String getDescription() {
		return this.description;
	}

	static Weapon getRandomWeapon() {
		return WEAPONS.get(2 + Tools.DICE.nextInt(SIZE - 2));
	}

	String getStats() {
		String stats = getDescription() + " that deals " + getDamage() + " damage";
		return stats;
	}

}
