package com.rsampdev.inTheDark;

import java.util.ArrayList;
import java.util.Collections;

class Player extends Entity {

	private Level level;
	private double food = MAX_FOOD;
	private double drink = MAX_DRINK;
	private ArrayList<Item> inventory = new ArrayList<>();
	private ArrayList<Effect> effects = new ArrayList<>();

	private static final double MAX_FOOD = 100.0;
	private static final double MAX_DRINK = 100.0;

	Player() {
		setWeapon(Weapon.FIST);
		setExperience(0);
		setHealth(100);
		setLevel();
	}

	Player(int weaponID, double health, double experience, double food, double drink, ArrayList<Item> inventory) {
		setWeapon(Weapon.getWeapon(weaponID));
		setInventoryList(inventory);
		setExperience(experience);
		setHealth(health);
		setDrink(drink);
		setFood(food);
		setLevel();
	}

	Level getLevel() {
		return this.level;
	}

	void setLevel() {
		this.level = Level.getLevel(getExperience());
	}

	double getFood() {
		return food;
	}

	private void setFood(double food) {
		this.food = food;
	}

	double getDrink() {
		return drink;
	}

	private void setDrink(double drink) {
		this.drink = drink;
	}

	private void setInventoryList(ArrayList<Item> inventory) {
		for (Item item : inventory) {
			this.addItem(item);
		}
	}

	ArrayList<Item> getInventoryList() {
		return this.inventory;
	}

	void eat(double food) {
		if (this.food <= MAX_FOOD) {
			this.food += food;

			if (this.food > MAX_FOOD) {
				this.setHealth(getHealth() + (this.food - MAX_FOOD));
				this.food = MAX_FOOD;
			}
		}
	}

	void drink(double drink) {
		if (this.drink <= MAX_DRINK) {
			this.drink += drink;

			if (this.drink > MAX_DRINK) {
				this.setHealth(getHealth() + (this.drink - MAX_DRINK));
				this.drink = MAX_DRINK;
			}
		}
	}

	Item cook(Item item) {
		Item itemHolder = null;

		for (Item tempItem : inventory) {
			if (tempItem.getName().equals(item.getName())) {
				tempItem.decrement(1);
				itemHolder = Item.Cooker.cook(item);
				addItem(itemHolder);
				break;
			}
		}

		return itemHolder;
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
			if (item.getName().equals(name)) {
				tempItem = item;
				item.use(this);
			}
		}

		if (tempItem != null && tempItem.getStack() < 1) {
			inventory.remove(tempItem);
		}
	}

	String getInventoryString() {
		StringBuilder inventory = new StringBuilder("\nINVENTORY:");

		Collections.sort(this.inventory, new Item.SortItemsByName());

		ArrayList<Item> removables = new ArrayList<>();

		for (Item item : this.inventory) {
			if (item.getStack() < 1) {
				removables.add(item);
				continue;
			}

			if (item.getStack() == 1) {
				inventory.append("\n" + item.getStack() + " " + item.getName());
			}

			if (item.getStack() > 1) {
				inventory.append("\n" + item.getStack() + " " + item.getName() + "s");
			}
		}

		return inventory.toString();
	}

	String getCookableInventoryString() {
		StringBuilder inventory = new StringBuilder("\nCOOKABLE ITEMS IN INVENTORY:");

		Collections.sort(this.inventory, new Item.SortItemsByName());

		int check = 0;

		for (Item item : this.inventory) {
			if (item.getIsCookable()) {
				check++;
			}
		}

		if (check == 0) {
			inventory.replace(0, inventory.length() - 1, "");
			inventory.append("\nNO COOKABLE ITEMS IN INVENTORY!");
		}

		ArrayList<Item> removables = new ArrayList<>();

		for (Item item : this.inventory) {
			if (item.getStack() < 1) {
				removables.add(item);
				continue;
			}

			if (!item.getIsCookable()) {
				continue;
			}

			if (item.getStack() == 1) {
				inventory.append("\n" + item.getStack() + " " + item.getName());
			}

			if (item.getStack() > 1) {
				inventory.append("\n" + item.getStack() + " " + item.getName() + "s");
			}
		}

		return inventory.toString();
	}

	private void updateLevel() {
		this.setLevel();
	}

	private void updateEffects() {
		for (Effect effect : effects) {
			effect.effect.act(this);
		}
	}

	void update() {
		updateEffects();
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

		StringBuilder stats = new StringBuilder();

		stats.append("You are Lvl. " + getLevel().ordinal());
		stats.append(", have " + getHealth() + " HP, ");
		stats.append(getExperience() + " XP, ");
		stats.append(getFood() + "/" + MAX_FOOD + " food, ");
		stats.append(getDrink() + "/" + MAX_DRINK + " drink, ");
		stats.append("\nand are fighting with a(n) " + getWeapon().getDescription() + " that will deal " + getAttackDamage() + " damage");

		return stats.toString();
	}

}
