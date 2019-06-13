package com.rsampdev.inTheDark;

import java.util.ArrayList;
import java.util.Comparator;

class Item {

	private int stack;
	private String name;
	private Action usage;

	static ArrayList<Item> ITEMS = new ArrayList<Item>();

	private Item(String name, int stack, Action usage) {
		this.name = name;
		this.stack = stack;
		this.usage = usage;
	}

	private Item(Item item) {
		this.name = item.name;
		this.stack = item.stack;
		this.usage = item.usage;
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

	private static Item createHealthPotion() {
		Action action = new Action() {
			@Override
			public Player act(Player player) {
				player.setHealth(player.getHealth() + 5);
				return player;
			}
		};

		Item item = new Item("Health Potion", 1, action);

		return item;
	}
	
	private static Item createMegaHealthPotion() {
		Action action = new Action() {
			@Override
			public Player act(Player player) {
				player.setHealth(player.getHealth() + 25);
				return player;
			}
		};

		Item item = new Item("Mega Health Potion", 1, action);

		return item;
	}

	private static Item createHealthElixir() {
		Action action = new Action() {
			@Override
			public Player act(Player player) {
				player.setHealth(player.getHealth() + 50);
				return player;
			}
		};

		Item item = new Item("Health Elixir", 1, action);

		return item;
	}

	private static Item createMegaHealthElixir() {
		Action action = new Action() {
			@Override
			public Player act(Player player) {
				player.setHealth(player.getHealth() + 100);
				return player;
			}
		};

		Item item = new Item("Mega Health Elixir", 1, action);

		return item;
	}

	static void prepare() {
		Item.ITEMS.add(Item.createHealthPotion());
		Item.ITEMS.add(Item.createMegaHealthPotion());
		Item.ITEMS.add(Item.createHealthElixir());
		Item.ITEMS.add(Item.createMegaHealthElixir());
	}

	static Item getItem(String itemName) {
		Item item = null;

		if (itemName.equals("Health Potion")) {
			item = createHealthPotion();
		}

		if (itemName.equals("Health Elixir")) {
			item = createHealthElixir();
		}

		return item;
	}

	static Item getRandomItem() {
		int number = Tools.DICE.nextInt(Item.ITEMS.size());
		int index = 0;

		Item randomItem = null;

		for (Item item : ITEMS) {
			if (index == number) {
				randomItem = new Item(item);
				break;
			} else {
				index++;
			}
		}

		return randomItem;
	}

	interface Action {
		abstract Player act(Player player);
	}

	static class SortItemsByName implements Comparator<Item> {

		public int compare(Item a, Item b) {
			return a.getName().compareTo(b.getName());
		}

	}

}
