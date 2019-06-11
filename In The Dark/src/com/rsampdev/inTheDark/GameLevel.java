package com.rsampdev.inTheDark;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum GameLevel {

	LEVEL_ZERO(0, 1.0, 1.0), LEVEL_ONE(1, 1.1, 1.1), LEVEL_TWO(2, 1.2, 1.2), LEVEL_THREE(3, 1.3, 1.3), LEVEL_FOUR(4, 1.4, 1.4), LEVEL_FIVE(5, 1.5, 1.5), LEVEL_SIX(6, 1.6, 1.6),
	LEVEL_SEVEN(7, 1.7, 1.7), LEVEL_EIGHT(8, 1.8, 1.8), LEVEL_NINE(9, 1.9, 1.9), LEVEL_TEN(10, 2.0, 2.0);

	private static final List<GameLevel> GAME_LEVELS = Collections.unmodifiableList(Arrays.asList(values()));

	private int id;
	private double healthMultiplier;
	private double damageMultiplier;

	private GameLevel(int id, double healthMultiplier, double damageMultiplier) {
		this.healthMultiplier = this.damageMultiplier = damageMultiplier;
		this.id = id;
	}

	int getID() {
		return this.id;
	}

	double getHealthMultiplier() {
		return this.healthMultiplier;
	}

	double getDamageMultiplier() {
		return this.damageMultiplier;
	}

	static GameLevel getLevel(int gameLevelID) {
		return GAME_LEVELS.get(gameLevelID);
	}

	static GameLevel nextLevel(GameLevel level) {
		GameLevel gameLevel = level;

		if (!(gameLevel == GameLevel.LEVEL_TEN)) {
			int id = gameLevel.getID() + 1;

			for (GameLevel tempGameLevel : GAME_LEVELS) {
				if (id == tempGameLevel.getID()) {
					gameLevel = tempGameLevel;
					break;
				}
			}
		}

		return gameLevel;
	}

	@Override
	public String toString() {
		return "ID: " + getID() + ", Health Multiplier: " + getHealthMultiplier() + ", Damage Multiplier: " + getDamageMultiplier();
	}

}
