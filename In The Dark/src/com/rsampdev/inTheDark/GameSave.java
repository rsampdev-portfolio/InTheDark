package com.rsampdev.inTheDark;

import java.util.ArrayList;

class GameSave {

	private Game game;

	public GameSave(Game game) {
		this.game = game;
	}

	String generateSaveString() {
		Player player = game.getPlayer();

		String save = game.getGameLevel().getID() + "|";

		save = save.concat(player.getWeapon().getID() + "|");

		save = save.concat(player.getHealth() + "|");

		save = save.concat(player.getExperience() + "|");

		for (Item item : player.getInventoryList()) {
			save = save.concat(item.getName() + ",");
		}

		return save;
	}

	static Game parseSaveString(String saveString) {
		String[] saveData = saveString.split("|");

		int weaponID = Integer.parseInt(saveData[1]);

		double health = Double.parseDouble(saveData[2]);

		double experience = Double.parseDouble(saveData[3]);

		String[] inventoryData = saveData[4].split(",");

		ArrayList<Item> inventory = new ArrayList<Item>();

		for (String itemName : inventoryData) {
			Item item = Item.getItem(itemName);

			if (item != null) {
				inventory.add(item);
			}
		}

		Player player = new Player(weaponID, health, experience, inventory);

		Game game = new Game(player);

		return game;
	}

}
