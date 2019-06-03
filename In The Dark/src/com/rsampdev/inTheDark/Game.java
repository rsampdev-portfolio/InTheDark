package com.rsampdev.inTheDark;

import java.util.Scanner;

class Game {

	private Player player;

	Game(Player player) {
		this.player = player;
	}

	String run(Scanner terminal) {
		String commandListener = "";
		String input = "";

		System.out.println("\nWhat do you want to do?");
		System.out.println("ENTER: explore, stats, help or quit\n");

		input = terminal.nextLine().toLowerCase().trim();

		if (input.equals(Command.EXPLORE.getValue())) {
			explore();
		} else if (input.equals(Command.STATS.getValue())) {
			stats();
		} else {
			commandListener = input;
		}

		return commandListener;
	}

	void explore() {
		System.err.println("EXPLORING!\n");
	}

	void stats() {
		System.err.println(player.getStats() + "\n");
	}

}
