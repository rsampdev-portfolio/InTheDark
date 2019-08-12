package com.rsampdev.inTheDark;

class GameLoop {

	public static void main(String[] args) throws Exception {
		Game game = SaveGame.load();

		GameLoop.prepare();

		GameLoop.gameLoop(game);
	}

	static void gameLoop(Game game) throws Exception {
		boolean running = true;

		while (running) {
			Tools.LISTENER = game.run();

			if (Tools.LISTENER.equals(Command.help.name())) {
				help();
			} else if (Tools.LISTENER.equals(Command.save.name())) {
				SaveGame.save(game);
			} else if (Tools.LISTENER.equals(Command.quit.name())) {
				running = quit(game);
			}

			if (Tools.LISTENER.equals(Command.give.name())) {
				Command.Operator.give();
			} else if (Tools.LISTENER.equals(Command.resetGame.name())) {
				Command.Operator.resetGame();
			} else if (Tools.LISTENER.equals(Command.resetPlayer.name())) {
				Command.Operator.resetPlayer();
			} else if (Tools.LISTENER.equals(Command.clearQuests.name())) {
				Command.Operator.clearQuests();
			} else if (Tools.LISTENER.equals(Command.clearInventory.name())) {
				Command.Operator.clearInventory();
			} else if (Tools.LISTENER.equals(Command.resetPlayerAndGame.name())) {
				Command.Operator.resetPlayerAndGame();
			}
		}

		Terminal.close();
	}

	static void prepare() throws Exception {
		Game.PLAYER.prepare();
		Effect.prepare();
		Enemy.prepare();
		Quest.prepare();
	}

	static void help() {
		System.out.println("\nHELP:");
		System.out.println("use: use an item");
		System.out.println("save: save the game");
		System.out.println("cook: cook a food item");
		System.out.println("stats: display your stats");
		System.out.println("level: display leveling progress");
		System.out.println("quests: display your quest progress");
		System.out.println("explore: explore deeper into the cave");
		System.out.println("effects: display the list of user effects");
		System.out.println("inventory: display the items in your inventory");
	}

	static boolean quit(Game game) throws Exception {
		boolean doNotQuitGame = true;

		while (!Tools.LISTENER.equals(Command.yes.name()) && !Tools.LISTENER.equals(Command.no.name())) {
			System.out.println("\nDo you want to save your game before quitting?");
			System.out.println("ENTER: yes or no\n");

			Tools.LISTENER = Terminal.getInput();

			if (Tools.LISTENER.equals(Command.yes.name())) {
				SaveGame.save(game);
			}
		}

		Tools.LISTENER = "";

		while (!Tools.LISTENER.equals(Command.yes.name()) && !Tools.LISTENER.equals(Command.no.name())) {
			System.out.println("\nAre you sure you want to quit your game?");
			System.out.println("ENTER: yes or no\n");

			Tools.LISTENER = Terminal.getInput();

			if (Tools.LISTENER.equals(Command.yes.name())) {
				doNotQuitGame = false;
			}
		}

		return doNotQuitGame;
	}

}
