package com.rsampdev.inTheDark;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum Weapon {

	// Basic; 0-1

	NONE(0, "literally nothing"), FIST(5, "Fist"),

	// Swords: 2-5

	WOODEN_SWORD(10, "Wooden Sword"), STONE_SWORD(20, "Stone Sword"), IRON_SWORD(30, "Iron Sword"), STEEL_SWORD(40, "Steel Sword"),

	// Clubs: 6-7

	WOODEN_CLUB(20, "Wooden Club"), IRON_REINFORCED_CLUB(30, "Iron Reinforced Club");

	// Animals: 8 ->

	// Coming soon...

	private double damage;
	private String description;

	private static final List<Weapon> WEAPONS = Collections.unmodifiableList(Arrays.asList(values()));
	private static final List<Weapon> BASIC_WEAPONS = WEAPONS.subList(0, 2);
	private static final List<Weapon> SWORD_WEAPONS = WEAPONS.subList(2, 6);
	private static final List<Weapon> CLUB_WEAPONS = WEAPONS.subList(6, 8);

	// private static final List<Weapon> ANIMAL_WEAPONS = WEAPONS.subList(0, 2);

	private static final int WEAPONS_SIZE = WEAPONS.size();
	private static final int BASIC_WEAPONS_SIZE = WEAPONS.size();
	private static final int SWORD_WEAPONS_SIZE = WEAPONS.size();
	private static final int CLUB_WEAPONS_SIZE = WEAPONS.size();

	// private static final int ANIMAL_WEAPONS_SIZE = WEAPONS.size();

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
		return WEAPONS.get(Tools.DICE.nextInt(WEAPONS_SIZE - 2) + 2);
	}

	static Weapon getRandomBasicWeapon() {
		return BASIC_WEAPONS.get(Tools.DICE.nextInt(BASIC_WEAPONS_SIZE));
	}

	static Weapon getRandomSwordWeapon() {
		return SWORD_WEAPONS.get(Tools.DICE.nextInt(SWORD_WEAPONS_SIZE));
	}

	static Weapon getRandomClubWeapon() {
		return CLUB_WEAPONS.get(Tools.DICE.nextInt(CLUB_WEAPONS_SIZE));
	}

	String getStats() {
		String stats = getDescription() + " that deals " + getDamage() + " damage";
		return stats;
	}

}
