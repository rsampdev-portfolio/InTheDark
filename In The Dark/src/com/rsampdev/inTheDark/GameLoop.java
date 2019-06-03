package com.rsampdev.inTheDark;

import java.util.Scanner;

class GameLoop {

	public static void main(String[] args) {

		Player player = new Player();

		Game game = new Game(player);

		GameLoop.gameLoop(game);

	}

	static void gameLoop(Game game) {
		Scanner terminal = new Scanner(System.in);
		String input = "";

		boolean running = true;

		game.prepare();

		while (running) {

			input = game.run(terminal);

			if (input.equals(Command.QUIT.getValue())) {
				running = false;
				quit();
				continue;
			}

			if (input.equals(Command.HELP.getValue())) {
				help();
				continue;
			}

		}

		terminal.close();
	}

	static void help() {
		System.out.println("HELP!\n");
	}

	static void quit() {
		System.out.println("QUIT!\n");
	}

}
