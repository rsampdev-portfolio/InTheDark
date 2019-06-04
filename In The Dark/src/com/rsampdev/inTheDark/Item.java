package com.rsampdev.inTheDark;

import java.util.ArrayList;
import java.util.Random;

interface Action {
	abstract Player act(Player player);
}

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

	Player use(Player player) {
		if (stack >= 1) {
			usage.act(player);
			stack--;
		}

		return player;
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

	private static Item createHealthPotion() {
		Action action = new Action() {
			@Override
			public Player act(Player player) {
				player.setHealth(player.getHealth() + 10);
				return player;
			}
		};

		Item item = new Item("Health Potion", 1, action);

		return item;
	}

	private static Item createHealthElixir() {
		Action action = new Action() {
			@Override
			public Player act(Player player) {
				player.setHealth(player.getHealth() + 100);
				return player;
			}
		};

		Item item = new Item("Health Elixir", 1, action);

		return item;
	}

	static void prepare() {
		Item.ITEMS.add(Item.createHealthPotion());
		Item.ITEMS.add(Item.createHealthElixir());
	}

	static Item getRandomItem() {
		Random random = new Random();

		int number = random.nextInt(Item.ITEMS.size());
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

}
