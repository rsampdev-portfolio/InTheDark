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
	private static Effect createCaveIllness() {
		Action action = new Action() {
			@Override
			public void act(Player player) {
				player.setHealth(player.getHealth() - 5);
			}
		};

		Effect effect = new Effect("Cave Illness", 5, action);

		return effect;
	}

	private static Effect createHealthRegeneration() {
		Action action = new Action() {
			@Override
			public void act(Player player) {
				player.setHealth(player.getHealth() + 5);
			}
		};

		Effect effect = new Effect("Health Regeneration", 5, action);

		return effect;
	}

	static void prepare() {
		Effect.EFFECTS.add(createCaveIllness());
		Effect.EFFECTS.add(createHealthRegeneration());
	}

	static Effect getEffect(String effectName) {
		Effect effect = null;

		if (effectName.equals("Cave Illness")) {
			effect = createCaveIllness();
		}

		if (effectName.equals("Health Regeneration")) {
			effect = createHealthRegeneration();
		}

		return effect;
	}

	static Effect getRandomEffect() {
		int number = Tools.DICE.nextInt(EFFECTS.size());
		int index = 0;

		Effect randomEffect = null;

		for (Effect effect : EFFECTS) {
			if (index == number) {
				randomEffect = new Effect(effect);
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
