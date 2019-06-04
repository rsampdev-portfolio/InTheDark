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

			if (input.equals(Command.QUIT.getCommand())) {
				running = quit();
				continue;
			}

			if (input.equals(Command.HELP.getCommand())) {
				help();
				continue;
			}

		}

		terminal.close();
	}

	static void help() {
		System.out.println("HELP!\n");
	}

	static boolean quit() {
		System.out.println("QUIT!\n");
		return false;
	}

}
