package com.rsampdev.inTheDark;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

enum Item {

	// Foods

	UNIDENTIFIED_MEAT("Can of Unidentified Meat", 1, true, new Action() {
		@Override
		public void act(Player player) {
			player.eat(5);
		}
	}), UNIDENTIFIED_COOKED_MEAT("Can of Unidentified Cooked Meat", 1, false, new Action() {
		@Override
		public void act(Player player) {
			player.eat(10);
		}
	}), UNCOOKED_MUTTON("Uncooked Mutton", 1, true, new Action() {
		@Override
		public void act(Player player) {
			player.eat(15);
		}
	}), MUTTON("Mutton", 1, false, new Action() {
		@Override
		public void act(Player player) {
			player.eat(30);
		}
	}), UNCOOKED_PORKCHOP("Uncooked Porkchop", 1, true, new Action() {
		@Override
		public void act(Player player) {
			player.eat(20);
		}
	}), PORKCHOP("Porkchop", 1, false, new Action() {
		@Override
		public void act(Player player) {
			player.eat(40);
		}
	}), UNCOOKED_BEEF("Uncooked Beef", 1, true, new Action() {
		@Override
		public void act(Player player) {
			player.eat(25);
		}
	}), STEAK("Steak", 1, false, new Action() {
		@Override
		public void act(Player player) {
			player.eat(50);
		}
	}),

	// Drinks

	UNFILTERED_WATER("Unfiltered Water Jar", 1, true, new Action() {
		@Override
		public void act(Player player) {
			player.drink(5);
		}
	}), CLEAN_WATER("Clean Water Bottle", 1, false, new Action() {
		@Override
		public void act(Player player) {
			player.drink(15);
		}
	}),

	// Potions

	HEALTH_POTION("Health Potion", 1, false, new Action() {
		@Override
		public void act(Player player) {
			player.setHealth(player.getHealth() + 15);
		}
	}), MEGA_HEALTH_POTION("Mega Health Potion", 1, false, new Action() {
		@Override
		public void act(Player player) {
			player.setHealth(player.getHealth() + 30);
		}
	}), HEALTH_ELIXIR("Health Elixir", 1, false, new Action() {
		@Override
		public void act(Player player) {
			player.setHealth(player.getHealth() + 50);
		}
	}), MEGA_HEALTH_ELIXIR("Mega Health Elixir", 1, false, new Action() {
		@Override
		public void act(Player player) {
			player.setHealth(player.getHealth() + 100);
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

	void increment(int by) {
		this.stack += by;
	}

	void decrement(int by) {
		this.stack -= by;
	}

	void use(Player player) {
		if (stack >= 1) {
			usage.act(player);
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

		Item cook(Item item) {
			Item tempItem = null;

			if (item.isCookable) {
				switch (item) {
				case UNFILTERED_WATER:
					tempItem = Item.CLEAN_WATER;
					break;
				case UNCOOKED_MUTTON:
					tempItem = Item.MUTTON;
					break;
				case UNCOOKED_PORKCHOP:
					tempItem = Item.PORKCHOP;
					break;
				case UNCOOKED_BEEF:
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