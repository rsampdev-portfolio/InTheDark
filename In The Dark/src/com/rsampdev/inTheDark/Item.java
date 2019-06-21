package com.rsampdev.inTheDark;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

enum Item {

	HEALTH_POTION("Health Potion", 1, new Action() {
		@Override
		public void act(Player player) {
			player.setHealth(player.getHealth() + 15);
		}
	}), MEGA_HEALTH_POTION("Mega Health Potion", 1, new Action() {
		@Override
		public void act(Player player) {
			player.setHealth(player.getHealth() + 30);
		}
	}), HEALTH_ELIXIR("Health Elixir", 1, new Action() {
		@Override
		public void act(Player player) {
			player.setHealth(player.getHealth() + 50);
		}
	}), MEGA_HEALTH_ELIXIR("Mega Health Elixir", 1, new Action() {
		@Override
		public void act(Player player) {
			player.setHealth(player.getHealth() + 100);
		}
	});

	private int stack;
	private String name;
	private Action usage;

	private static final List<Item> ITEMS = Collections.unmodifiableList(Arrays.asList(values()));

	private Item(String name, int stack, Action usage) {
		this.stack = stack;
		this.usage = usage;
		this.name = name;
	}

	String getName() {
		return name;
	}

	int getStack() {
		return stack;
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

	static class SortItemsByName implements Comparator<Item> {

		public int compare(Item a, Item b) {
			return a.getName().compareTo(b.getName());
		}

	}

}
