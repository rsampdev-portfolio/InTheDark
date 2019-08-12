package com.rsampdev.inTheDark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class SaveGame {

	private static final String FOLDER = "InTheDark";
	private static final String DIRECTORY = System.getProperty("user.home") + File.separator + FOLDER;

	private static final String GAME_FILE_NAME = "Game.itd";
	private static final String PLAYER_FILE_NAME = "Player.itd";

	private static final String ABSOLUTE_GAME_PATH = DIRECTORY + File.separator + GAME_FILE_NAME;
	private static final String ABSOLUTE_PLAYER_PATH = DIRECTORY + File.separator + PLAYER_FILE_NAME;

	static Game load() throws Exception {
		FileInputStream saveGameFile = new FileInputStream(ABSOLUTE_GAME_PATH);

		Game game = null;

		if (saveGameFile.available() != 0) {
			ObjectInputStream objectData = new ObjectInputStream(saveGameFile);
			game = (Game) objectData.readObject();

			Game.PLAYER = loadPlayer();

			objectData.close();
		}

		saveGameFile.close();

		if (game == null) {
			Player player = new Player();
			game = new Game(player);
		}

		return game;
	}

	private static Player loadPlayer() throws Exception {
		FileInputStream saveGameFile = new FileInputStream(ABSOLUTE_PLAYER_PATH);

		Player player = null;

		if (saveGameFile.available() != 0) {
			ObjectInputStream objectData = new ObjectInputStream(saveGameFile);
			player = (Player) objectData.readObject();
			objectData.close();
		}

		saveGameFile.close();

		if (player == null) {
			player = new Player();
		}

		return player;
	}

	static void save(Game game) throws Exception {
		FileOutputStream saveGameFile = new FileOutputStream(ABSOLUTE_GAME_PATH);
		saveGameFile.flush();
		ObjectOutputStream objectData = new ObjectOutputStream(saveGameFile);
		objectData.writeObject(game);
		objectData.close();
		saveGameFile.close();
		savePlayer();
	}

	private static void savePlayer() throws Exception {
		FileOutputStream saveGameFile = new FileOutputStream(ABSOLUTE_PLAYER_PATH);
		saveGameFile.flush();
		ObjectOutputStream objectData = new ObjectOutputStream(saveGameFile);
		objectData.writeObject(Game.PLAYER);
		objectData.close();
		saveGameFile.close();
	}

}
