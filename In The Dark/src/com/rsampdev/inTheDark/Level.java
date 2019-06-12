package com.rsampdev.inTheDark;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum Level {

	LEVEL_ZERO(0, 1.0, 0, 200), LEVEL_ONE(1, 1.05, 200, 400), LEVEL_TWO(2, 1.1, 400, 600), LEVEL_THREE(3, 1.15, 600, 800), LEVEL_FOUR(4, 1.2, 800, 1000), LEVEL_FIVE(5, 1.25, 1000, 1200), LEVEL_SIX(6, 1.3, 1200, 1400),
	LEVEL_SEVEN(7, 1.35, 1400, 1600), LEVEL_EIGHT(8, 1.4, 1600, 1800), LEVEL_NINE(9, 1.45, 1800, 2000), LEVEL_TEN(10, 1.5, 2000, Integer.MAX_VALUE);

	private int id;
	private double damageMultiplier;
	private int lowerExperienceBound;
	private int upperExperienceBound;

	private static final List<Level> LEVELS = Collections.unmodifiableList(Arrays.asList(values()));

	static final int NUMBER_OF_LEVELS = LEVELS.size();

	private Level(int id, double damageMultiplier, int lowerExperienceBound, int upperExperienceBound) {
		this.lowerExperienceBound = lowerExperienceBound;
		this.upperExperienceBound = upperExperienceBound;
		this.damageMultiplier = damageMultiplier;
		this.id = id;
	}

	int getID() {
		return this.id;
	}

	double getLowerExperienceBound() {
		return this.lowerExperienceBound;
	}

	double getUpperExperienceBound() {
		return this.upperExperienceBound;
	}

	double getDamageMultiplier() {
		return this.damageMultiplier;
	}

	private boolean isInExperienceRange(double experience) {
		boolean isInExperienceRange = false;

		if (experience < upperExperienceBound && experience >= lowerExperienceBound) {
			isInExperienceRange = true;
		}

		return isInExperienceRange;
	}

	static Level getLevelFromID(int levelID) {
		return LEVELS.get(levelID);
	}
	
	static Level getLevel(double experience) {
		Level level = LEVEL_ZERO;

		for (Level tempLevel : LEVELS) {
			if (tempLevel.isInExperienceRange(experience)) {
				level = tempLevel;
				break;
			}
		}

		return level;
	}

}
