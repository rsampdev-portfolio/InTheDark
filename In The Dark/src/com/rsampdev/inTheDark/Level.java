package com.rsampdev.inTheDark;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum Level {

	LEVEL_ZERO(1, 1.0, 0, 100), LEVEL_ONE(1, 1.1, 100, 200), LEVEL_TWO(2, 1.2, 200, 300), LEVEL_THREE(3, 1.3, 300, 400), LEVEL_FOUR(4, 1.4, 400, 500), LEVEL_FIVE(5, 1.5, 500, 600),
	LEVEL_SIX(6, 1.6, 600, 700), LEVEL_SEVEN(7, 1.7, 700, 800), LEVEL_EIGHT(8, 1.8, 800, 900), LEVEL_NINE(9, 1.9, 900, 1000), LEVEL_TEN(10, 2.0, 1000, Integer.MAX_VALUE);

	private int id;
	private double damageMultiplier;
	private int lowerExperienceBound;
	private int upperExperienceBound;

	private static final List<Level> LEVELS = Collections.unmodifiableList(Arrays.asList(values()));

	private Level(int id, double damageMultiplier, int lowerExperienceBound, int upperExperienceBound) {
		this.lowerExperienceBound = lowerExperienceBound;
		this.upperExperienceBound = upperExperienceBound;
		this.damageMultiplier = damageMultiplier;
		this.id = id;
	}

	int getID() {
		return this.id;
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
