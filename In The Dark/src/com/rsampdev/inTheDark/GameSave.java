package com.rsampdev.inTheDark;

import java.util.ArrayList;

class GameSave {

	static String generateSaveString(Game game) {
		Player player = game.getPlayer();

		String save = game.getGameLevel().ordinal() + ":";

		save = save.concat(player.getWeapon().ordinal() + ":");

		save = save.concat(player.getHealth() + ":");

		save = save.concat(player.getExperience() + ":");

		for (Item item : player.getInventoryList()) {
			save = save.concat(item.getName() + ",");
		}

		return save;
	}

	static Game parseSaveString(String saveString) {
		String[] saveData = saveString.split(":");

		Game game = null;

		int gameLevelID = Integer.parseInt(saveData[0]);

		if (saveData.length == 5) {
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

			game = new Game(gameLevelID, player);
		}

		return game;
	}

}
