package com.rsampdev.inTheDark;

import java.util.ArrayList;

class Enemy extends Entity {

	static ArrayList<Enemy> ENEMIES = new ArrayList<Enemy>();

	private Enemy(String name, int health, int experience, Weapon weapon) {
		setName(name);
		setHealth(health);
		setWeapon(weapon);
		setExperience(experience);
	}

	private Enemy(Enemy enemy) {
		setName(enemy.getName());
		setHealth(enemy.getHealth());
		setWeapon(enemy.getWeapon());
		setExperience(enemy.getExperience());
	}

	private static Enemy createUndeadShambler() {
		return new Enemy("Undead Shambler", 10, 50, Weapon.FIST);
	}

	private static Enemy createCaveGremlin() {
		return new Enemy("Cave Gremlin", 15, 100, Weapon.STONE_SWORD);
	}

	static void prepare() {
		Enemy.ENEMIES.add(Enemy.createUndeadShambler());
		Enemy.ENEMIES.add(Enemy.createCaveGremlin());
	}

	static Enemy getRandomEnemy() {
		int number = Tools.DICE.nextInt(ENEMIES.size());
		int index = 0;

		Enemy randomEnemy = null;

		for (Enemy enemy : ENEMIES) {
			if (index == number) {
				randomEnemy = new Enemy(enemy);
				break;
			} else {
				index++;
			}
		}

		return randomEnemy;
	}

	@Override
	String getStats() {
		String stats = "The " + getName() + " has " + getHealth() + " HP and is fighting with a(n) " + getWeapon().getStats();
		return stats;
	}

}
