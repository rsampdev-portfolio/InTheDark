package com.rsampdev.inTheDark;

import java.util.Scanner;

class GameLoop {

	public static void main(String[] args) throws Exception {
		Game game = GameSave.load();

		GameLoop.gameLoop(game);
	}

	static void gameLoop(Game game) throws Exception {
		Scanner terminal = new Scanner(System.in);
		String input = "";

		boolean running = true;

		while (running) {
			input = game.run(terminal);

			if (input.equals(Command.help.name())) {
				help();
			} else if (input.equals(Command.save.name())) {
				GameSave.save(game);
			} else if (input.equals(Command.quit.name())) {
				running = quit(terminal, game);
			}
		}

		terminal.close();
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

	static boolean quit(Scanner terminal, Game game) throws Exception {
		boolean doNotQuitGame = true;

		String input = "";

		while (!input.equals(Command.yes.name()) && !input.equals(Command.no.name())) {
			System.out.println("\nDo you want to save your game before quitting?");
			System.out.println("ENTER: yes or no\n");

			input = terminal.nextLine().toLowerCase().trim();

			if (input.equals(Command.yes.name())) {
				GameSave.save(game);
			}
		}

		input = "";

		while (!input.equals(Command.yes.name()) && !input.equals(Command.no.name())) {
			System.out.println("\nAre you sure you want to quit your game?");
			System.out.println("ENTER: yes or no\n");

			input = terminal.nextLine().toLowerCase().trim();

			if (input.equals(Command.yes.name())) {
				doNotQuitGame = false;
			}
		}

		return doNotQuitGame;
	}

}
