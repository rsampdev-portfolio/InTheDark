package com.rsampdev.inTheDark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class SaveGame {

	private static final String FILE_NAME = "InTheDarkSaveGame.txt";
	private static final String DIRECTORY = System.getProperty("user.home");
	private static final String ABSOLUTE_PATH = DIRECTORY + File.separator + FILE_NAME;

	static Game load() throws Exception {
		FileInputStream saveGameFile = new FileInputStream(ABSOLUTE_PATH);

		Game game = null;

		if (saveGameFile.available() != 0) {
			ObjectInputStream objectData = new ObjectInputStream(saveGameFile);
			game = (Game) objectData.readObject();
			objectData.close();
		}

		saveGameFile.close();

		if (game == null) {
			Player player = new Player();
			game = new Game(player);
		}

		return game;
	}

	static void save(Game game) throws Exception {
		FileOutputStream saveGameFile = new FileOutputStream(ABSOLUTE_PATH);
		saveGameFile.flush();
		ObjectOutputStream objectData = new ObjectOutputStream(saveGameFile);
		objectData.writeObject(game);
		objectData.close();
		saveGameFile.close();
	}

}
