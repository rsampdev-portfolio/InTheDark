package com.rsampdev.inTheDark;

import java.util.Scanner;

class Game {

	private Player player;
	private GameLevel gameLevel = GameLevel.LEVEL_ZERO;

	Game(Player player) {
		this.player = player;
		prepare();
	}

	Game(int gameLevelID, Player player) {
		this.gameLevel = GameLevel.getLevel(gameLevelID);
		this.player = player;
		prepare();
	}

	void prepare() {
		Enemy.prepare();
		Effect.prepare();
	}

	Player getPlayer() {
		return this.player;
	}

	GameLevel getGameLevel() {
		return this.gameLevel;
	}

	String run(Scanner terminal) {
		Tools.LISTENER = "";

		System.out.println("\nWhat do you want to do?");
		System.out.println("ENTER: explore, inventory, use, cook, stats, level, help, save or quit\n");

		Tools.LISTENER = Tools.getInputFrom(terminal);

		if (Tools.LISTENER.equals(Command.explore.name())) {
			explore(terminal);
		} else if (Tools.LISTENER.equals(Command.inventory.name())) {
			inventory();
		} else if (Tools.LISTENER.equals(Command.use.name())) {
			useItem(terminal);
		} else if (Tools.LISTENER.equals(Command.cook.name())) {
			cookItem(terminal);
		} else if (Tools.LISTENER.equals(Command.level.name())) {
			level();
		} else if (Tools.LISTENER.equals(Command.stats.name())) {
			statsStartWithNewLine(player);
		}

		boolean update = !(Tools.LISTENER.equals(Command.inventory.name()) || Tools.LISTENER.equals(Command.use.name()) || Tools.LISTENER.equals(Command.cook.name())
				|| Tools.LISTENER.equals(Command.level.name()) || Tools.LISTENER.equals(Command.stats.name()));

		if (update) {
			player.turnUpdate();
		}

		return Tools.LISTENER;
	}

	private void explore(Scanner terminal) {
		int roll = Tools.DICE.nextInt(7);

		if (roll <= 1) {
			System.out.println("\nYou have encountered nothing. But the tunnel continues onward...");
		} else if (roll == 2) {
			intersection(terminal);
		} else if (roll == 3) {
			foundItem(terminal);
		} else if (roll == 4) {
			foundWeapon(terminal);
		} else if (roll == 5) {
			combat(terminal);
		} else if (roll == 6) {
			ascend(terminal);
		}
	}

	private void inventory() {
		System.out.println(this.player.getInventoryString());
	}

	private void cookableInventory() {
		System.out.println(this.player.getCookableInventoryString());
	}

	private void useItem(Scanner terminal) {
		while (!Tools.LISTENER.equals(Command.cancel.name())) {
			System.out.println("\nENTER: the name of the item in your inventory you want to use or cancel");
			inventory();
			System.out.println();

			Tools.LISTENER = Tools.getInputFrom(terminal);

			for (Item item : player.getInventoryList()) {
				if (item.getName().toLowerCase().equals(Tools.LISTENER.toLowerCase())) {
					Tools.LISTENER = Command.cancel.name();
					item.use(player);
				}
			}
		}
	}

	private void cookItem(Scanner terminal) {
		while (!Tools.LISTENER.equals(Command.cancel.name())) {
			System.out.println("\nENTER: the name of the food item in your inventory you want to cook or cancel");
			cookableInventory();
			System.out.println();

			Tools.LISTENER = Tools.getInputFrom(terminal);

			Item food = null;
			Item itemToRemove = null;

			for (Item tempItem : player.getInventoryList()) {
				if (tempItem.getName().toLowerCase().equals(Tools.LISTENER.toLowerCase())) {
					food = Item.Cooker.cook(tempItem);
					itemToRemove = tempItem;
				}
			}

			if (food != null && itemToRemove != null) {
				player.addItem(food);
				player.removeItem(itemToRemove);

				while (!Tools.LISTENER.equals(Command.yes.name()) && !Tools.LISTENER.equals(Command.no.name()) && !Tools.LISTENER.equals(Command.cancel.name())) {

					System.out.println("\nDo you want to eat the " + food.getName() + " now?");
					System.out.println("ENTER: yes or no\n");

					Tools.LISTENER = Tools.getInputFrom(terminal);

					if (Tools.LISTENER.equals(Command.yes.name()) || Tools.LISTENER.equals(Command.no.name())) {
						if (Tools.LISTENER.equals(Command.yes.name())) {
							player.useItem(food.getName());
						}

						Tools.LISTENER = Command.cancel.name();
					}
				}
			}
		}
	}

	private void level() {
		int ordinal = this.player.getLevel().ordinal();

		String levelData = "You are Lvl. " + ordinal + ", with " + this.player.getExperience() + " XP";

		if (ordinal < Level.NUMBER_OF_LEVELS - 1) {
			Level nextLevel = Level.getLevelFromOrdinal(ordinal + 1);
			double levelXPGap = nextLevel.getLowerExperienceBound() - player.getExperience();
			levelData = levelData.concat(", and are " + levelXPGap + " XP from Lvl. " + nextLevel.ordinal());
		} else {
			levelData = levelData.concat(", and are max level");
		}

		System.out.println("\n" + levelData);
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
		while (!Tools.LISTENER.equals("left") && !Tools.LISTENER.equals("right")) {
			System.out.println("\nYou have come to an intersection, do you go left of right?\n");
			Tools.LISTENER = Tools.getInputFrom(terminal);
		}

		explore(terminal);
	}

	private void foundItem(Scanner terminal) {
		Item item = Item.getRandomItem();
		player.addItem(item);

		System.out.println("\nYou have found a(n) " + item.getName() + "\n");

		while (!Tools.LISTENER.equals(Command.yes.name()) && !Tools.LISTENER.equals(Command.no.name())) {
			System.out.println("Do you want to use or eat the " + item.getName() + " now?\n");
			System.out.println("ENTER: " + Command.yes.name() + " or " + Command.no.name() + "\n");

			Tools.LISTENER = Tools.getInputFrom(terminal);

			if (Tools.LISTENER.equals(Command.yes.name())) {
				player.useItem(item.getName());
			}
		}
	}

	private void foundWeapon(Scanner terminal) {
		Weapon weapon = Weapon.getRandomWeapon();

		if (player.getWeapon() == weapon) {
			explore(terminal);
		} else {
			System.out.println("\nYou have found a(n) " + weapon.getStats() + "\n");

			while (!Tools.LISTENER.equals(Command.yes.name()) && !Tools.LISTENER.equals(Command.no.name())) {
				System.out.println("Your current weapon is a(n) " + player.getWeapon().getStats() + "\n");
				System.out.println("Do you want to pick up the " + weapon.getStats() + "\n");
				System.out.println("ENTER: " + Command.yes.name() + " or " + Command.no.name() + "\n");

				Tools.LISTENER = Tools.getInputFrom(terminal);

				if (Tools.LISTENER.equals(Command.yes.name())) {
					player.setWeapon(weapon);
				}
			}
		}
	}

	private void combat(Scanner terminal) {
		Enemy enemy = Enemy.getRandomEnemy();
		enemy.modifyForGameLevel(gameLevel);

		System.out.println("\nYou have encountered a(n) " + enemy.getName() + " wielding a(n) " + enemy.getWeapon().getDescription() + "\n");

		while (!Tools.LISTENER.equals(Command.yes.name()) && !Tools.LISTENER.equals(Command.no.name())) {
			System.out.println("Do you want to fight the " + enemy.getName() + "?");
			System.out.println("ENTER: " + Command.yes.name() + " or " + Command.no.name() + "\n");

			Tools.LISTENER = Tools.getInputFrom(terminal);
		}

		if (Tools.LISTENER.equals(Command.yes.name())) {
			startCombat(terminal, enemy);
		} else if (Tools.LISTENER.equals(Command.no.name())) {
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
		boolean continueFight = true;

		while (continueFight) {
			Tools.LISTENER = "";

			int roll = Tools.DICE.nextInt(2);

			if (roll == 0) {
				player.attack(enemy);
				System.out.println("\n" + "You have dealt " + player.getAttackDamage() + " damage to the " + enemy.getName() + "\n");

				if (enemy.getHealth() <= 0) {
					System.out.println("You have killed the " + enemy.getName() + "\n");
					player.addExperience(enemy.getExperience());
					System.out.println("You have gained " + enemy.getExperience() + " XP from killing the " + enemy.getName() + "\n");
					stats(player);
					continueFight = false;
					break;
				} else {
					enemy.attack(player);
					System.out.println("The " + enemy.getName() + " has dealt " + enemy.getAttackDamage() + " damage to you" + "\n");
				}
			} else if (roll == 1) {
				enemy.attack(player);
				System.out.println("\n" + "The " + enemy.getName() + " has dealt " + enemy.getAttackDamage() + " damage to you" + "\n");

				if (player.getHealth() <= 0) {
					System.out.println("You have died. Game Over.");
					Tools.LISTENER = Command.quit.name();
					continueFight = false;
					break;
				} else {
					player.attack(enemy);
					System.out.println("You have dealt " + player.getAttackDamage() + " damage to the " + enemy.getName() + "\n");
				}
			}

			player.continuousUpdate();

			statsEndWithNewLine(player);
			statsEndWithNewLine(enemy);

			while (!Tools.LISTENER.equals(Command.yes.name()) && !Tools.LISTENER.equals(Command.no.name())) {
				if (enemy.getHealth() <= 0) {
					System.out.println("You have killed the " + enemy.getName() + "\n");
					player.addExperience(enemy.getExperience());
					System.out.println("You have gained " + enemy.getExperience() + " XP from killing the " + enemy.getName() + "\n");
					stats(player);
					continueFight = false;
					break;
				} else if (player.getHealth() <= 0) {
					System.out.println("You have died. Game Over.");
					Tools.LISTENER = Command.quit.name();
					continueFight = false;
					break;
				}

				System.out.println("Do you want to continue the fight with the " + enemy.getName() + "?");
				System.out.println("ENTER: " + Command.yes.name() + " or " + Command.no.name() + "\n");

				Tools.LISTENER = Tools.getInputFrom(terminal);

				if (Tools.LISTENER.equals(Command.no.name())) {
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

	private void ascend(Scanner terminal) {
		if (this.gameLevel.ordinal() < GameLevel.GAME_LEVELS.get(GameLevel.GAME_LEVELS.size() - 1).ordinal()) {
			while (!Tools.LISTENER.equals(Command.yes.name()) && !Tools.LISTENER.equals(Command.no.name())) {
				System.out.println("\n" + "You have found an entrance to a higher level");
				System.out.println("Do you ascend? Careful, the monsters will have more health and do more damage");
				System.out.println("ENTER: " + Command.yes.name() + " or " + Command.no.name() + "\n");

				Tools.LISTENER = Tools.getInputFrom(terminal);
			}

			if (Tools.LISTENER.equals(Command.yes.name())) {
				gameLevel = GameLevel.nextLevel(gameLevel);

				System.out.println("\n" + "You're now one step closer to the surface...");
			} else if (Tools.LISTENER.equals(Command.no.name())) {
				System.out.println("\n" + "You ignore the entrance, continuing on your way...");
			}
		} else {
			explore(terminal);
		}
	}

}
