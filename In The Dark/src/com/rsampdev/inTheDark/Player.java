package com.rsampdev.inTheDark;

import java.util.ArrayList;

class Player extends Entity {

	private int experience = 0;
	private ArrayList<Item> inventory = new ArrayList<>();

	// void use() {}

	// void retreat() {}

	Player() {
		this.setHealth(100);
		this.setWeapon(Weapon.FIST);
	}

	int getExperience() {
		return this.experience;
	}

	void setExperience(int experience) {
		this.experience = experience;
	}

	void addItem(Item item) {
		this.inventory.add(item);
	}

	void useItem(String name) {
		Item tempItem = null;

		for (Item item : inventory) {
			if (item.getName() == name) {
				tempItem = item;
				item.use(this);
			}
		}

		if (tempItem != null && tempItem.getUses() < 1) {
			inventory.remove(tempItem);
		}
	}

	String getStats() {
		String stats = "You have " + getHealth() + " HP, " + getExperience() + " XP and are fighting with "
				+ getWeapon().getDescription();
		return stats;
	}

}
