package com.rsampdev.inTheDark;

import java.util.Scanner;

class GameLoop {

	public static void main(String[] args) {

		gameLoop();

	}

	static void gameLoop() {
		Scanner terminal = new Scanner(System.in);
		String input = "";

		Player player = new Player();
		Game game = new Game(player);

		boolean running = true;

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
		System.err.println("HELP!\n");
	}

	static void quit() {
		System.err.println("QUIT!\n");
	}

}
