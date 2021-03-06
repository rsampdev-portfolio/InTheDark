package com.rsampdev.inTheDark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

class Effect implements Serializable {

	private static final long serialVersionUID = 6312334522503877906L;

	private int turns;
	private String name;
	private transient Action effect;

	private static ArrayList<Effect> EFFECTS = new ArrayList<Effect>();

	Effect(String name, int turns, Action effect) {
		this.effect = effect;
		this.turns = turns;
		this.name = name;
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

	void effect() {
		if (turns >= 1) {
			effect.act();
			decrement(1);
		}
	}

	static Effect createCaveIllness() {
		Action action = new Action() {
			@Override
			public void act() {
				Game.PLAYER.setHealth(Game.PLAYER.getHealth() - 5);
			}
		};

		Effect effect = new Effect("Cave Illness", 5, action);

		return effect;
	}

	static Effect createUncookedFoodPoisoning() {
		Action action = new Action() {
			@Override
			public void act() {
				Game.PLAYER.setHealth(Game.PLAYER.getHealth() - (Tools.DICE.nextInt(2) + 1));
			}
		};

		Effect effect = new Effect("Uncooked Food Poisoning", 10, action);

		return effect;
	}

	static Effect createHealthRegeneration() {
		Action action = new Action() {
			@Override
			public void act() {
				Game.PLAYER.setHealth(Game.PLAYER.getHealth() + 5);
			}
		};

		Effect effect = new Effect("Health Regeneration", 5, action);

		return effect;
	}

	static void prepare() {
		Effect.EFFECTS.add(createCaveIllness());
		Effect.EFFECTS.add(createHealthRegeneration());
		Effect.EFFECTS.add(createUncookedFoodPoisoning());
	}

	static Effect getRandomEffect() throws Exception {
		int number = Tools.DICE.nextInt(EFFECTS.size());
		int index = 0;

		Effect randomEffect = null;

		for (Effect effect : EFFECTS) {
			if (index == number) {
				randomEffect = (Effect) effect.clone();
				break;
			} else {
				index++;
			}
		}

		return randomEffect;
	}

	static class SortEffectByName implements Comparator<Effect> {

		public int compare(Effect a, Effect b) {
			return a.getName().compareTo(b.getName());
		}

	}

}
