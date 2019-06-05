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
		Enemy.prepare();
	}

	String run(Scanner terminal) {
		String commandListener = "";
		String input = "";

		System.out.println("\nWhat do you want to do?");
		System.out.println("ENTER: explore, inventory, use, stats, help or quit\n");

		input = terminal.nextLine().toLowerCase().trim();

		if (input.equals(Command.EXPLORE.getCommand())) {
			explore(terminal);
		} else if (input.equals(Command.INVENTORY.getCommand())) {
			inventory();
		} else if (input.equals(Command.USE.getCommand())) {
			useItem(terminal);
		} else if (input.equals(Command.STATS.getCommand())) {
			stats(player);
		} else {
			commandListener = input;
		}

		return commandListener;
	}

	private void explore(Scanner terminal) {
		int number = dice.nextInt(6);

		if (number <= 1) {
			System.out.println("\nYou have encountered nothing. But the tunnel continues onward...");
		}

		if (number == 2) {
			intersection(terminal);
		}

		if (number == 3) {
			foundItem(terminal);
		}

		if (number == 4) {
			combat(terminal);
		}

		if (number == 5) {
			System.out.println("\nAnother path...");

			// Another path...
		}
	}

	private void inventory() {
		System.out.println(this.player.getInventoryString() + "\n");
	}

	private void useItem(Scanner terminal) {
		System.out.println("\nENTER: the name of the item in your inventory you want to use\n");

		String input = "";

		input = terminal.nextLine().trim();

		for (Item item : player.getInventoryList()) {
			if (item.getName().equals(input)) {
				item.use(player);
			}
		}
	}

	private void stats(Entity entity) {
		System.out.println(entity.getStats() + "\n");
	}

	private void intersection(Scanner terminal) {
		String input = "";

		while (!input.equals("left") && !input.equals("right")) {
			System.out.println("\nYou have come to an intersection, do you go left of right?");
			input = terminal.nextLine().toLowerCase().trim();
		}

		explore(terminal);
	}

	private void foundItem(Scanner terminal) {
		String input = "";

		Item item = Item.getRandomItem();
		player.addItem(item);

		System.out.println("\nYou have found a(n) " + item.getName() + "\n");

		while (!input.equals(Command.YES.getCommand()) && !input.equals(Command.NO.getCommand())) {
			System.out.println("Do you want to use the " + item.getName() + " now?\n");
			System.out.println("ENTER: " + Command.YES.getCommand() + " or " + Command.NO.getCommand() + "\n");

			input = terminal.nextLine().toLowerCase().trim();

			if (input.equals("yes")) {
				player.useItem(item.getName());
			}
		}
	}

	private void combat(Scanner terminal) {
		String input = "";

		Enemy enemy = Enemy.getRandomEnemy();

		System.out.println("\nYou have encountered a(n) " + enemy.getName() + " wielding " + enemy.getWeapon().getDescription() + "\n");

		while (!input.equals(Command.YES.getCommand()) && !input.equals(Command.NO.getCommand())) {
			System.out.println("Do you want to fight the " + enemy.getName() + "?");
			System.out.println("ENTER: " + Command.YES.getCommand() + " or " + Command.NO.getCommand() + "\n");

			input = terminal.nextLine().toLowerCase().trim();
		}

		if (input.equals(Command.YES.getCommand())) {
			input = "";

			boolean continueFight = true;

			while (continueFight) {
				input = "";

				int number = dice.nextInt(2);

				if (number == 0) {
					player.attack(enemy);
					enemy.attack(player);
				} else if (number == 1) {
					enemy.attack(player);
					player.attack(enemy);
				}

				stats(player);
				stats(enemy);

				System.out.println("\nYou have dealt " + player.getWeapon().getDamage() + " to the " + enemy.getName());
				System.out.println("The " + enemy.getName() + " has dealt " + enemy.getWeapon().getDamage() + " to you\n");

				while (!input.equals(Command.YES.getCommand()) && !input.equals(Command.NO.getCommand())) {
					if (player.getHealth() <= 0 || enemy.getHealth() <= 0) {
						continueFight = false;

						if (player.getHealth() <= 0) {
							System.out.println("\nYou have died.");
							input = Command.QUIT.getCommand();
						} else if (enemy.getHealth() <= 0) {
							System.out.println("\nYou have killed the " + enemy.getName());
							player.addExperience(enemy.getExperience());
							System.out.println("You have gained " + enemy.getExperience() + " XP from killing the " + enemy.getName());
							stats(player);
						}

						break;
					}

					System.out.println("Do you want to continue the fight with the " + enemy.getName() + "?");
					System.out.println("ENTER: " + Command.YES.getCommand() + " or " + Command.NO.getCommand() + "\n");

					input = terminal.nextLine().toLowerCase().trim();

					if (input.equals(Command.NO.getCommand())) {
						continueFight = false;
					}
				}
			}
		} else {
			// chance of escape
		}
	}

}
