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
		int roll = dice.nextInt(6);

		if (roll <= 1) {
			System.out.println("\nYou have encountered nothing. But the tunnel continues onward...");
		}

		if (roll == 2) {
			intersection(terminal);
		}

		if (roll == 3) {
			foundItem(terminal);
		}

		if (roll == 4) {
			foundWeapon(terminal);
		}

		if (roll == 5) {
			combat(terminal);
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
		System.out.println(entity.getStats());
	}

	private void statsEndWithNewLine(Entity entity) {
		System.out.println(entity.getStats() + "\n");
	}

	private void statsStartWithNewLine(Entity entity) {
		System.out.println("\n" + entity.getStats());
	}

	private void statsStartAndEndWithNewLine(Entity entity) {
		System.out.println("\n" + entity.getStats() + "\n");
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

			if (input.equals(Command.YES.getCommand())) {
				player.useItem(item.getName());
			}
		}
	}

	private void foundWeapon(Scanner terminal) {
		String input = "";

		Weapon weapon = Weapon.getRandomWeapon();

		if (player.getWeapon() == weapon) {
			explore(terminal);
		} else {

			System.out.println("\nYou have found a(n) " + weapon.getStats() + "\n");

			while (!input.equals(Command.YES.getCommand()) && !input.equals(Command.NO.getCommand())) {

				System.out.println("Your current weapon is a(n) " + player.getWeapon().getStats() + "\n");
				System.out.println("Do you want to pick up the " + weapon.getStats() + "\n");
				System.out.println("ENTER: " + Command.YES.getCommand() + " or " + Command.NO.getCommand() + "\n");

				input = terminal.nextLine().toLowerCase().trim();

				if (input.equals(Command.YES.getCommand())) {
					player.setWeapon(weapon);
				}
			}
		}
	}

	private void combat(Scanner terminal) {
		String input = "";

		Enemy enemy = Enemy.getRandomEnemy();

		System.out.println("\nYou have encountered a(n) " + enemy.getName() + " wielding a(n) " + enemy.getWeapon().getDescription() + "\n");

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
					System.out.println("\n" + "You have dealt " + player.getWeapon().getDamage() + " damage to the " + enemy.getName() + "\n");

					if (enemy.getHealth() <= 0) {
						System.out.println("You have killed the " + enemy.getName() + "\n");
						player.addExperience(enemy.getExperience());
						System.out.println("You have gained " + enemy.getExperience() + " XP from killing the " + enemy.getName() + "\n");
						stats(player);
						continueFight = false;
						break;
					} else {
						enemy.attack(player);
						System.out.println("The " + enemy.getName() + " has dealt " + enemy.getWeapon().getDamage() + " damage to you" + "\n");
					}
				} else if (number == 1) {
					enemy.attack(player);
					System.out.println("\n" + "The " + enemy.getName() + " has dealt " + enemy.getWeapon().getDamage() + " damage to you" + "\n");

					if (player.getHealth() <= 0) {
						System.out.println("\n" + "You have died. Game Over." + "\n");
						input = Command.QUIT.getCommand();
						continueFight = false;
						break;
					} else {
						player.attack(enemy);
						System.out.println("You have dealt " + player.getWeapon().getDamage() + " damage to the " + enemy.getName() + "\n");
					}
				}

				statsEndWithNewLine(player);
				statsEndWithNewLine(enemy);

				while (!input.equals(Command.YES.getCommand()) && !input.equals(Command.NO.getCommand())) {
					if (enemy.getHealth() <= 0) {
						System.out.println("You have killed the " + enemy.getName() + "\n");
						player.addExperience(enemy.getExperience());
						System.out.println("You have gained " + enemy.getExperience() + " XP from killing the " + enemy.getName() + "\n");
						stats(player);
						continueFight = false;
						break;
					} else if (player.getHealth() <= 0) {
						System.out.println("\n" + "You have died. Game Over." + "\n");
						input = Command.QUIT.getCommand();
						continueFight = false;
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
