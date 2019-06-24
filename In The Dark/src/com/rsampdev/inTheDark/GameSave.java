package com.rsampdev.inTheDark;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

class GameSave {

	private static final String FILE_NAME = "InTheDarkSaveGame.txt";
	private static final String DIRECTORY = System.getProperty("user.home");
	private static final String ABSOLUTE_PATH = DIRECTORY + File.separator + FILE_NAME;

	static Game load() throws Exception {
		File file = new File(ABSOLUTE_PATH);

		FileReader fileReader = new FileReader(file);

		String saveString = "";

		int data = fileReader.read();

		while (data != -1) {
			char symbol = (char) data;

			saveString = saveString.concat(symbol + "");

			data = fileReader.read();
		}

		fileReader.close();

		Game game = GameSave.parseSaveString(saveString);

		if (game == null) {
			Player player = new Player();
			game = new Game(player);
		}
		
		return game;
	}

	static void save(Game game) throws Exception {
		System.out.println("\nSaving game...\n");

		String saveString = GameSave.generateSaveString(game);

		FileWriter fileWriter = new FileWriter(new File(ABSOLUTE_PATH));

		fileWriter.write(saveString);

		fileWriter.close();

		System.out.println("Game saved");
	}

	private static String generateSaveString(Game game) {
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

	private static Game parseSaveString(String saveString) {
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
