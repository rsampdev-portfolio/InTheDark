package com.rsampdev.inTheDark;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum Weapon {

	NONE(0, "literally nothing"), FIST(5, "Fist"), WOODEN_SWORD(10, "Wooden Sword"), STONE_SWORD(20, "Stone Sword"), IRON_SWORD(30, "Iron Sword"), STEEL_SWORD(40, "Steel Sword"),
	WOODEN_CLUB(20, "Wooden Club"), IRON_REINFORCED_CLUB(30, "Iron Reinforced Club");

	private double damage;
	private String description;

	private static final List<Weapon> WEAPONS = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = WEAPONS.size();

	private Weapon(double damage, String description) {
		this.description = description;
		this.damage = damage;
	}

	double getDamage() {
		return this.damage;
	}

	String getDescription() {
		return this.description;
	}

	static Weapon getWeapon(int ordinal) {
		return WEAPONS.get(ordinal);
	}

	static Weapon getRandomWeapon() {
		return WEAPONS.get(2 + Tools.DICE.nextInt(SIZE - 2));
	}

	String getStats() {
		String stats = getDescription() + " that deals " + getDamage() + " damage";
		return stats;
	}

}
