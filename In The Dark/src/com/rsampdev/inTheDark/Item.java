package com.rsampdev.inTheDark;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

enum Item {

	// Foods

	EGG("Uncooked Egg", 1, true, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(2.5);
			Game.PLAYER.addEffect(Effect.createUncookedFoodPoisoning());
		}
	}), BOILED_EGG("Boiled Egg", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(5);
		}
	}), UNIDENTIFIED_MEAT("Can of Unidentified Uncooked Meat", 1, true, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(5);
			Game.PLAYER.addEffect(Effect.createUncookedFoodPoisoning());
		}
	}), UNIDENTIFIED_COOKED_MEAT("Can of Unidentified Cooked Meat", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(10);
		}
	}), RAW_SPIDER_JERKY("Raw Spider Jerky", 1, true, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(5);
		}
	}), SPIDER_JERKY("Spider Jerky", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(10);
		}
	}), RAW_MUTTON_CHOP("Raw Mutton Chop", 1, true, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(15);
			Game.PLAYER.addEffect(Effect.createUncookedFoodPoisoning());
		}
	}), MUTTON_CHOP("Mutton Chop", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(30);
		}
	}), RAW_PORK_CHOP("Raw Pork Chop", 1, true, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(20);
			Game.PLAYER.addEffect(Effect.createUncookedFoodPoisoning());
		}
	}), PORK_CHOP("Pork Chop", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(40);
		}
	}), RAW_BEEF("Raw Beef", 1, true, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(25);
			Game.PLAYER.addEffect(Effect.createUncookedFoodPoisoning());
		}
	}), STEAK("Steak", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.eat(50);
		}
	}),

	// Drinks

	UNFILTERED_WATER("Jar of Unfiltered Water", 1, true, new Action() {
		@Override
		public void act() {
			Game.PLAYER.drink(5);
		}
	}), CLEAN_WATER("Bottle of Clean Water", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.drink(15);
		}
	}),

	// Potions

	HEALTH_POTION("Health Potion", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.setHealth(Game.PLAYER.getHealth() + 15);
		}
	}), MEGA_HEALTH_POTION("Mega Health Potion", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.setHealth(Game.PLAYER.getHealth() + 30);
		}
	}), HEALTH_ELIXIR("Health Elixir", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.setHealth(Game.PLAYER.getHealth() + 50);
		}
	}), MEGA_HEALTH_ELIXIR("Mega Health Elixir", 1, false, new Action() {
		@Override
		public void act() {
			Game.PLAYER.setHealth(Game.PLAYER.getHealth() + 100);
		};
	});

	private int stack;
	private String name;
	private Action usage;
	private boolean isCookable;

	private static final List<Item> ITEMS = Collections.unmodifiableList(Arrays.asList(values()));

	private Item(String name, int stack, boolean isCookable, Action usage) {
		this.isCookable = isCookable;
		this.stack = stack;
		this.usage = usage;
		this.name = name;
	}

	String getName() {
		return this.name;
	}

	int getStack() {
		return this.stack;
	}

	boolean getIsCookable() {
		return this.isCookable;
	}

	void increment(int by) {
		this.stack += by;
	}

	void decrement(int by) {
		this.stack -= by;
	}

	void use() {
		if (stack >= 1) {
			usage.act();
			decrement(1);
		}
	}

	static Item getItem(String itemName) {
		Item item = null;

		for (Item tempItem : ITEMS) {
			if (tempItem.name.equals(itemName)) {
				item = tempItem;
			}
		}

		return item;
	}

	static Item getRandomItem() {
		int number = Tools.DICE.nextInt(Item.ITEMS.size());
		int index = 0;

		Item randomItem = null;

		for (Item item : ITEMS) {
			if (index == number) {
				randomItem = item;
				break;
			} else {
				index++;
			}
		}

		return randomItem;
	}

	static class Cooker {

		static Item cook(Item item) {
			Item tempItem = null;

			if (item.isCookable) {
				switch (item) {
				case EGG:
					tempItem = Item.BOILED_EGG;
					break;
				case UNFILTERED_WATER:
					tempItem = Item.CLEAN_WATER;
					break;
				case RAW_MUTTON_CHOP:
					tempItem = Item.MUTTON_CHOP;
					break;
				case RAW_PORK_CHOP:
					tempItem = Item.PORK_CHOP;
					break;
				case RAW_BEEF:
					tempItem = Item.STEAK;
					break;
				default:
					break;
				}
			}

			return tempItem;
		}

	}

	static class SortItemsByName implements Comparator<Item> {

		public int compare(Item a, Item b) {
			return a.getName().compareTo(b.getName());
		}

	}

}