package com.rsampdev.inTheDark;

enum Command {

	// basic commands

	help, quit, save, yes, no, cancel, explore, inventory, quests, effects, use, cook, level, stats,

	// operator commands

	give, resetPlayer, resetGame, resetPlayerAndGame, clearQuests, clearInventory;

	static class Operator {

		static void give() {

		}

		static void resetPlayer() {

		}

		static void resetGame() {

		}

		static void resetPlayerAndGame() {

		}

		static void clearQuests() {

		}

		static void clearInventory() {

		}

	}

}
