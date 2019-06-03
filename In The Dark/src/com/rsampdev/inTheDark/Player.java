package com.rsampdev.inTheDark;

import java.util.ArrayList;

class Player {

	private int health = 100;
	private int experience = 0;
	private ArrayList<Item> inventory = new ArrayList<>();

	// void attack() {}

	// void use() {}

	// void retreat() {}

	int getHealth() {
		return health;
	}

	void setHealth(int health) {
		this.health = health;
	}

	int getExperience() {
		return experience;
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
		String stats = "You have " + getHealth() + " HP and " + getExperience() + " XP";
		return stats;
	}

}
