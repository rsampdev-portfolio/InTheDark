package com.rsampdev.inTheDark;

import java.util.Random;
import java.util.Scanner;

class Game {

	private Random dice = new Random();
	private Player player;

	Game(Player player) {
		this.player = player;
	}

	void prepare() {
		Item.prepare();
	}

	String run(Scanner terminal) {
		String commandListener = "";
		String input = "";

		System.out.println("\nWhat do you want to do?");
		System.out.println("ENTER: explore, stats, help or quit\n");

		input = terminal.nextLine().toLowerCase().trim();

		if (input.equals(Command.EXPLORE.getValue())) {
			explore(terminal);
		} else if (input.equals(Command.STATS.getValue())) {
			stats();
		} else {
			commandListener = input;
		}

		return commandListener;
	}

	private void explore(Scanner terminal) {
		int number = dice.nextInt(6);

		if (number <= 1) {
			System.out.println("You have encountered nothing. But the tunnel continues onward...");
		}

		if (number == 2) {
			intersection(terminal);
		}

		if (number == 3) {
			foundItem(terminal);
		}

		if (number == 4) {
			fight(terminal);

			// Enemies and monsters
		}

		if (number == 5) {
			System.out.println("Another path...");

			// Another path...
		}

	}

	private void intersection(Scanner terminal) {
		String input = "";

		while (!input.equals("left") && !input.equals("right")) {
			System.out.println("You have come to an intersection, do you go left of right?");
			input = terminal.nextLine().toLowerCase().trim();
		}

		explore(terminal);
	}

	private void foundItem(Scanner terminal) {
		String input = "";

		Item item = Item.getRandomItem();
		player.addItem(item);

		System.out.println("\nYou have found a(n) " + item.getName() + "\n");
		System.out.println("ENTER: yes or no");

		while (!input.equals(Command.YES.getValue()) && !input.equals(Command.NO.getValue())) {
			System.out.println("Do you want to use the " + item.getName() + " now?\n");

			input = terminal.nextLine().toLowerCase().trim();

			if (input.equals("yes")) {
				player.useItem(item.getName());
			}
		}
	}

	private void fight(Scanner terminal) {
		System.out.println("You have encountered an enemy.");
	}

	private void stats() {
		System.out.println(player.getStats() + "\n");
	}

}
