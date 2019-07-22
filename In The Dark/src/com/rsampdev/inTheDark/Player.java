package com.rsampdev.inTheDark;

import java.util.ArrayList;
import java.util.Collections;

class Player extends Entity {

	private Level level;
	private double food = MAX_FOOD;
	private double drink = MAX_DRINK;
	private double foodDeteriorationRate = 1.0;
	private double drinkDeteriorationRate = 1.0;
	private ArrayList<Quest> quests = new ArrayList<>();
	private ArrayList<Item> inventory = new ArrayList<>();
	private ArrayList<Effect> effects = new ArrayList<>();

	private static final double MAX_FOOD = 100.0;
	private static final double MAX_DRINK = 100.0;

	Player() {
		setWeapon(Weapon.FIST);
		setExperience(0);
		setHealth(100);
		setLevel();
		death();
	}

	Player(int weaponID, double health, double experience, double food, double drink, ArrayList<Item> inventory) {
		setWeapon(Weapon.getWeapon(weaponID));
		setInventoryList(inventory);
		setExperience(experience);
		setHealth(health);
		setDrink(drink);
		setFood(food);
		setLevel();
		death();
	}

	private void death() {
		this.setDeath(new DeathAction() {
			@Override
			public void death() {
				QuestEvent.CURRENT_EVENT = QuestEvent.PLAYER_DEATH;
			}
		});
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

	double getFoodDeteriorationRate() {
		return foodDeteriorationRate;
	}

	void setFoodDeteriorationRate(double foodDeteriorationRate) {
		this.foodDeteriorationRate = foodDeteriorationRate;
	}

	double getDrinkDeteriorationRate() {
		return drinkDeteriorationRate;
	}

	void setDrinkDeteriorationRate(double drinkDeteriorationRate) {
		this.drinkDeteriorationRate = drinkDeteriorationRate;
	}

	private void setInventoryList(ArrayList<Item> inventory) {
		for (Item item : inventory) {
			this.addItem(item);
		}
	}

	ArrayList<Item> getInventoryList() {
		return this.inventory;
	}

	ArrayList<Quest> getQuests() {
		return this.quests;
	}

	void addQuest(Quest quest) {
		this.quests.add(quest);
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

	void addItem(Item item) {
		if (this.inventory.size() < 1) {
			this.inventory.add(item);
		} else {
			boolean check = true;

			for (Item tempItem : this.inventory) {
				if (tempItem.getName().equals(item.getName())) {
					tempItem.increment(1);
					check = false;
					break;
				}
			}

			if (check) {
				this.inventory.add(item);
			}
		}

		Collections.sort(this.inventory, new Item.SortItemsByName());
	}

	void removeItem(Item item) {
		int index = -1;

		for (Item tempItem : inventory) {
			index++;

			if (tempItem.getName().equals(item.getName())) {
				tempItem.decrement(1);
				break;
			}
		}

		if (item.getStack() < 1) {
			this.inventory.remove(index);
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

	String getQuestsString() {
		StringBuilder quests = new StringBuilder("\nQUESTS:");

		Collections.sort(this.quests, new Quest.SortQuestsByName());

		for (Quest quest : this.quests) {
			quests.append("\n" + quest.getName() + ": " + quest.getCompletionPercentage() + "%");
		}

		return quests.toString();
	}

	void addEffect(Effect effect) {
		boolean check = true;

		for (Effect tempEffect : effects) {
			if (tempEffect.getName().equals(effect.getName())) {
				tempEffect.increment(effect.getTurns());
				check = false;
				break;
			}
		}

		if (check) {
			this.effects.add(effect);
		}

	}

	String getEffectsString() {
		StringBuilder effects = new StringBuilder("\nEFFECTS:");

		Collections.sort(this.effects, new Effect.SortEffectByName());

		if (!(this.effects.size() < 1))
			for (Effect effect : this.effects) {
				effects.append("\n" + effect.getName() + "\n");
			}
		else {
			effects.append("\nNONE");
		}

		return effects.toString();
	}

	private void updateLevel() {
		this.setLevel();
	}

	private void updateQuests() {
		for (Quest quest : quests) {
			quest.checkForCompletion();

			if (quest.isCompleted()) {
				quest.doleOutRewardsTo(this);
			}
		}
	}

	private void updateEffects() {
		for (Effect effect : effects) {
			effect.effect(this);
		}
	}

	void turnUpdate() {
		setDrink(getDrink() - drinkDeteriorationRate);
		setFood(getFood() - foodDeteriorationRate);
		updateEffects();
		updateQuests();
	}

	void continuousUpdate() {
		updateLevel();
	}

	@Override
	double getAttackDamage() {
		return getWeapon().getDamage() * getLevel().getDamageMultiplier();
	}

	@Override
	void attack(Entity entity) {
		entity.setHealth(entity.getHealth() - getAttackDamage());
		continuousUpdate();
	}

	@Override
	String getStats() {
		continuousUpdate();

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
