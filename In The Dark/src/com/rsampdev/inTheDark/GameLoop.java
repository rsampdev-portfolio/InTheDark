package com.rsampdev.inTheDark;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

class GameLoop {

	private static String DIRECTORY = System.getProperty("user.home");
	private static String FILE_NAME = "InTheDarkSaveGame.txt";
	private static String ABSOLUTE_PATH = DIRECTORY + File.separator + FILE_NAME;

	public static void main(String[] args) throws Exception {
		Game game = load();

		if (game == null) {
			Player player = new Player();
			game = new Game(player);
		}

		GameLoop.gameLoop(game);
	}

	static void gameLoop(Game game) throws Exception {
		Scanner terminal = new Scanner(System.in);
		String input = "";

		boolean running = true;

		game.prepare();

		while (running) {

			input = game.run(terminal);

			if (input.equals(Command.QUIT.getCommand())) {
				running = quit();
				continue;
			}

			if (input.equals(Command.HELP.getCommand())) {
				help();
				continue;
			}

			if (input.equals(Command.SAVE.getCommand())) {
				save(game);
				continue;
			}

		}

		terminal.close();
	}

	static Game load() throws Exception {
		// Test: 1:3:69:420:Health Potion,Health Elixir,Health Potion,

		File file = new File(ABSOLUTE_PATH);

		String saveString = "";

		FileReader fileReader = new FileReader(file);

		int data = fileReader.read();

		while (data != -1) {
			char symbol = (char) data;
			saveString = saveString.concat(symbol + "");
			data = fileReader.read();
		}

		fileReader.close();

		Game game = GameSave.parseSaveString(saveString);

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

	static void help() {
		System.out.println("\nHELP:");
		System.out.println("use: use an item");
		System.out.println("save: save the game");
		System.out.println("stats: display your stats");
		System.out.println("level: display leveling progress");
		System.out.println("explore: explore deeper into the cave");
		System.out.println("inventory: display the items in your inventory");
	}

	static boolean quit() {
		return false;
	}

}
