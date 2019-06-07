package com.rsampdev.inTheDark;

import java.util.Scanner;

class Game {

	private Player player;
	private static String COMMAND_LISTENER = "";

	Game(Player player) {
		this.player = player;
	}

	void prepare() {
		Item.prepare();
		Enemy.prepare();
	}

	String run(Scanner terminal) {
		String input = "";

		System.out.println("\nWhat do you want to do?");
		System.out.println("ENTER: explore, inventory, use, stats, help or quit\n");

		input = terminal.nextLine().toLowerCase().trim();

		COMMAND_LISTENER = "";

		if (input.equals(Command.EXPLORE.getCommand())) {
			explore(terminal);
		} else if (input.equals(Command.INVENTORY.getCommand())) {
			inventory();
		} else if (input.equals(Command.USE.getCommand())) {
			useItem(terminal);
		} else if (input.equals(Command.STATS.getCommand())) {
			statsStartWithNewLine(player);
		} else {
			COMMAND_LISTENER = input;
		}

		return COMMAND_LISTENER;
	}

	private void explore(Scanner terminal) {
		int roll = Tools.DICE.nextInt(6);

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
		System.out.println(this.player.getInventoryString());
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

	private void intersection(Scanner terminal) {
		String input = "";

		while (!input.equals("left") && !input.equals("right")) {
			System.out.println("\nYou have come to an intersection, do you go left of right?\n");
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
			startCombat(terminal, enemy);
		} else {
			System.out.println("\n" + "You attempt to escape the " + enemy.getName());

			int roll = Tools.DICE.nextInt(4);

			if (roll <= 2) {
				System.out.println("\n" + "You have successfully escaped from the " + enemy.getName());
			} else if (roll == 3) {
				System.out.println("\n" + "You have failed to escape from the " + enemy.getName());
				startCombat(terminal, enemy);
			}
		}
	}

	private void startCombat(Scanner terminal, Enemy enemy) {
		String input = "";

		boolean continueFight = true;

		while (continueFight) {
			input = "";

			int roll = Tools.DICE.nextInt(2);

			if (roll == 0) {
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
			} else if (roll == 1) {
				enemy.attack(player);
				System.out.println("\n" + "The " + enemy.getName() + " has dealt " + enemy.getWeapon().getDamage() + " damage to you" + "\n");

				if (player.getHealth() <= 0) {
					System.out.println("You have died. Game Over.");
					input = Command.QUIT.getCommand();
					continueFight = false;
					break;
				} else {
					player.attack(enemy);
					System.out.println("You have dealt " + player.getWeapon().getDamage() + " damage to the " + enemy.getName() + "\n");
				}
			}

			player.update();

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
					System.out.println("You have died. Game Over.");
					COMMAND_LISTENER = Command.QUIT.getCommand();
					continueFight = false;
					break;
				}

				System.out.println("Do you want to continue the fight with the " + enemy.getName() + "?");
				System.out.println("ENTER: " + Command.YES.getCommand() + " or " + Command.NO.getCommand() + "\n");

				input = terminal.nextLine().toLowerCase().trim();

				if (input.equals(Command.NO.getCommand())) {
					System.out.println("\n" + "You attempt to escape the " + enemy.getName());

					int secondRoll = Tools.DICE.nextInt(4);

					if (secondRoll <= 2) {
						System.out.println("\n" + "You have successfully escaped from the " + enemy.getName());
						continueFight = false;
					} else if (secondRoll == 3) {
						System.out.println("\n" + "You have failed to escape from the " + enemy.getName());
						startCombat(terminal, enemy);
					}
				}
			}
		}
	}

}
