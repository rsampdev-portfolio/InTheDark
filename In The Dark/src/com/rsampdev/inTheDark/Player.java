package com.rsampdev.inTheDark;

import java.util.ArrayList;
import java.util.Collections;

class Player extends Entity {

	private ArrayList<Item> inventory = new ArrayList<>();

	// void use() {}

	// void retreat() {}

	Player() {
		this.setHealth(100);
		this.setExperience(0);
		this.setWeapon(Weapon.FIST);
	}

	void addItem(Item item) {
		ArrayList<Item> toAddInventory = new ArrayList<>();

		if (inventory.size() < 1) {
			this.inventory.add(item);
		} else {
			for (Item tempItem : inventory) {
				if (item.getName().equals(tempItem.getName())) {
					tempItem.increment(1);
					break;
				}

				if (tempItem.equals(inventory.get(inventory.size() - 1))) {
					toAddInventory.add(item);
				}
			}

			for (Item itemToAdd : toAddInventory) {
				this.inventory.add(itemToAdd);
			}
		}
	}

	void useItem(String name) {
		Item tempItem = null;

		for (Item item : inventory) {
			if (item.getName() == name) {
				tempItem = item;
				item.use(this);
			}
		}

		if (tempItem != null && tempItem.getStack() < 1) {
			inventory.remove(tempItem);
		}
	}

	ArrayList<Item> getInventoryList() {
		return this.inventory;
	}

	String getInventoryString() {
		String inventory = "\nINVENTORY:";

		Collections.sort(this.inventory, new Item.SortItemsByName());

		ArrayList<Item> removables = new ArrayList<>();

		for (Item item : this.inventory) {
			if (item.getStack() < 1) {
				removables.add(item);
				continue;
			}

			if (item.getStack() == 1) {
				inventory = inventory + "\n" + item.getStack() + " " + item.getName();
			}

			if (item.getStack() > 1) {
				inventory = inventory + "\n" + item.getStack() + " " + item.getName() + "s";
			}
		}

		return inventory;
	}

	@Override
	String getStats() {
		String stats = "You have " + getHealth() + " HP, " + getExperience() + " XP, and are fighting with a(n) " + getWeapon().getStats();
		return stats;
	}

}
