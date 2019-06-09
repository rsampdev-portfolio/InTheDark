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

		save = save.concat("|");

		return save;
	}

	static Game parseSaveString() {
		int weaponID = -1;
		double health = -1;
		double experience = -1;
		ArrayList<Item> inventory = null;

		Player player = new Player(weaponID, health, experience, inventory);

		Game game = new Game(player);

		return game;
	}

}
