package com.rsampdev.inTheDark;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum Level {

	LEVEL_ONE(1, 0, 100), LEVEL_TWO(2, 100, 200), LEVEL_THREE(3, 200, 300), LEVEL_FOUR(4, 300, 400), LEVEL_FIVE(5, 400, 500), LEVEL_SIX(6, 500, 600), LEVEL_SEVEN(7, 600, 700),
	LEVEL_EIGHT(8, 700, 800), LEVEL_NINE(9, 800, 900), LEVEL_TEN(10, 900, Integer.MAX_VALUE);

	private int id;
	private int lowerExperienceBound;
	private int upperExperienceBound;

	private static final List<Level> LEVELS = Collections.unmodifiableList(Arrays.asList(values()));

	private Level(int id, int lowerExperienceBound, int upperExperienceBound) {
		this.lowerExperienceBound = lowerExperienceBound;
		this.upperExperienceBound = upperExperienceBound;
		this.id = id;
	}

	int getID() {
		return this.id;
	}

	private boolean isInExperienceRange(double experience) {
		boolean isInExperienceRange = false;

		if (experience < upperExperienceBound && experience >= lowerExperienceBound) {
			isInExperienceRange = true;
		}

		return isInExperienceRange;
	}

	static Level getLevel(double experience) {
		Level level = LEVEL_ONE;

		for (Level tempLevel : LEVELS) {
			if (tempLevel.isInExperienceRange(experience)) {
				level = tempLevel;
				break;
			}
		}

		return level;
	}

}
