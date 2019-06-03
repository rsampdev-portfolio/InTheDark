package com.rsampdev.inTheDark;

import java.util.Scanner;

class GameLoop {

	public static void main(String[] args) {

		gameLoop();

	}

	static void gameLoop() {
		Scanner terminal = new Scanner(System.in);
		String input = "";

		boolean running = true;

		while (running) {

			input = terminal.nextLine();

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

	}

	static void quit() {

	}

}
