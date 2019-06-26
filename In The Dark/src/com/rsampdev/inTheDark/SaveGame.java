package com.rsampdev.inTheDark;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

class SaveGame {

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

		Game game = SaveGame.parseSaveString(saveString);

		if (game == null) {
			Player player = new Player();
			game = new Game(player);
		}

		return game;
	}

	static void save(Game game) throws Exception {
		System.out.println("\nSaving game...\n");

		String saveString = SaveGame.generateSaveString(game);

		FileWriter fileWriter = new FileWriter(new File(ABSOLUTE_PATH));

		fileWriter.write(saveString);

		fileWriter.close();

		System.out.println("Game saved");
	}

	private static String generateSaveString(Game game) {
		Player player = game.getPlayer();

		StringBuilder save = new StringBuilder();

		save.append(game.getGameLevel().ordinal() + ":");

		save.append(player.getWeapon().ordinal() + ":");

		save.append(player.getHealth() + ":");

		save.append(player.getExperience() + ":");

		save.append(player.getFood() + ":");

		save.append(player.getDrink() + ":");

		for (Item item : player.getInventoryList()) {
			save = save.append(item.getName() + ",");
		}

		return save.toString();
	}

	private static Game parseSaveString(String saveString) {
		String[] saveData = saveString.split(":");

		Game game = null;

		int gameLevelID = Integer.parseInt(saveData[0]);

		if (saveData.length == 7) {
			int weaponID = Integer.parseInt(saveData[1]);

			double health = Double.parseDouble(saveData[2]);

			double experience = Double.parseDouble(saveData[3]);

			double food = Double.parseDouble(saveData[4]);

			double drink = Double.parseDouble(saveData[5]);

			String[] inventoryData = saveData[6].split(",");

			ArrayList<Item> inventory = new ArrayList<Item>();

			for (String itemName : inventoryData) {
				Item item = Item.getItem(itemName);

				if (item != null) {
					inventory.add(item);
				}
			}

			Player player = new Player(weaponID, health, experience, food, drink, inventory);

			game = new Game(gameLevelID, player);
		}

		return game;
	}

}
