package com.rsampdev.inTheDark;

import java.util.ArrayList;
import java.util.Random;

class Enemy extends Entity {

	static ArrayList<Enemy> ENEMIES = new ArrayList<Enemy>();

	private Enemy(String name, int health, Weapon weapon) {
		setName(name);
		setHealth(health);
		setWeapon(weapon);
	}

	private Enemy(Enemy enemy) {
		setName(enemy.getName());
		setHealth(enemy.getHealth());
		setWeapon(enemy.getWeapon());
	}

	private static Enemy createUndeadShambler() {
		return new Enemy("Undead Shambler", 10, Weapon.FIST);
	}

	private static Enemy createCaveGremlin() {
		return new Enemy("Cave Gremlin", 15, Weapon.STONE_SWORD);
	}

	static void prepare() {
		Enemy.ENEMIES.add(Enemy.createUndeadShambler());
		Enemy.ENEMIES.add(Enemy.createCaveGremlin());
	}

	static Enemy getRandomEnemy() {
		Random random = new Random();

		int number = random.nextInt(Enemy.ENEMIES.size());
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

}