package com.rsampdev.inTheDark;

import java.util.ArrayList;
import java.util.Comparator;

class Effect {

	int turns;
	String name;
	Action effect;

	static ArrayList<Effect> EFFECTS = new ArrayList<Effect>();

	Effect(String name, int turns, Action effect) {
		this.effect = effect;
		this.turns = turns;
		this.name = name;
	}

	Effect(Effect effect) {
		this.effect = effect.effect;
		this.turns = effect.turns;
		this.name = effect.name;
	}

	int getTurns() {
		return this.turns;
	}

	String getName() {
		return this.name;
	}

	void increment(int by) {
		this.turns += by;
	}

	void decrement(int by) {
		this.turns -= by;
	}

	void effect(Player player) {
		if (turns >= 1) {
			effect.act(player);
			decrement(1);
		}
	}

	// create effects methods

	static void prepare() {
	}

	// Get effect by name
	static Effect getEffect(String effectName) {
		return null;
	}

	// Get a random effect
	static Effect getRandomEffect() {
		return null;
	}

	static class SortEffectByName implements Comparator<Effect> {

		public int compare(Effect a, Effect b) {
			return a.getName().compareTo(b.getName());
		}

	}

}
