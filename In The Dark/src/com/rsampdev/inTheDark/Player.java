package com.rsampdev.inTheDark;

import java.util.ArrayList;
import java.util.Collections;

class Player extends Entity {

	private Level level;
	private ArrayList<Item> inventory = new ArrayList<>();

	// void use() {}

	// void retreat() {}

	Player() {
		this.setHealth(100);
		this.setExperience(0);
		this.setWeapon(Weapon.FIST);
		this.setLevel();
	}

	Player(int weaponID, double health, double experience, ArrayList<Item> inventory) {
		this.setWeapon(Weapon.getWeapon(weaponID));
		this.setHealth(health);
		this.setExperience(experience);
		this.setLevel();
		this.setInventoryList(inventory);
	}

	Level getLevel() {
		return this.level;
	}

	void setLevel() {
		this.level = Level.getLevel(getExperience());
	}

	private void setInventoryList(ArrayList<Item> inventory) {
		for (Item item : inventory) {
			this.addItem(item);
		}
	}

	ArrayList<Item> getInventoryList() {
		return this.inventory;
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

	private void updateLevel() {
		this.setLevel();
	}

	void update() {
		updateLevel();
	}

	@Override
	double getAttackDamage() {
		return getWeapon().getDamage() * getLevel().getDamageMultiplier();
	}

	@Override
	void attack(Entity entity) {
		entity.setHealth(entity.getHealth() - getAttackDamage());
		update();
	}

	@Override
	String getStats() {
		update();
		String stats = "You are Lvl. " + getLevel().getID() + ", have " + getHealth() + " HP, " + getExperience() + " XP, and are fighting with a(n) " + getWeapon().getDescription() + " that will deal " + getAttackDamage() + " damage";
		return stats;
	}

}
