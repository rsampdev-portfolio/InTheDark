package com.rsampdev.inTheDark;

import java.util.ArrayList;

class Enemy extends Entity {

	private double damageMultiplier;
	
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

	private double getDamageMultiplier() {
		return this.damageMultiplier;
	}

	private void setDamageMultiplier(double damageMultiplier) {
		this.damageMultiplier = damageMultiplier;
	}

	void modifyForGameLevel(GameLevel level) {
		setHealth(getHealth() * level.getHealthMultiplier());
		setDamageMultiplier(level.getDamageMultiplier());
	}

	private static Enemy createUndeadShambler() {
		return new Enemy("Undead Shambler", 10, 50, Weapon.FIST);
	}

	private static Enemy createCaveGremlin() {
		return new Enemy("Cave Gremlin", 15, 75, Weapon.STONE_SWORD);
	}

	private static Enemy createCaveOrc() {
		return new Enemy("Cave Orc", 20, 100, Weapon.IRON_REINFORCED_CLUB);
	}

	private static Enemy createCaveTroll() {
		return new Enemy("Cave Troll", 40, 125, Weapon.WOODEN_CLUB);
	}

	static void prepare() {
		Enemy.ENEMIES.add(Enemy.createUndeadShambler());
		Enemy.ENEMIES.add(Enemy.createCaveGremlin());
		Enemy.ENEMIES.add(Enemy.createCaveOrc());
		Enemy.ENEMIES.add(Enemy.createCaveTroll());
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
	double getAttackDamage() {
		return getWeapon().getDamage() * getDamageMultiplier();
	}

	@Override
	void attack(Entity entity) {
		entity.setHealth(entity.getHealth() - getAttackDamage());
	}

	@Override
	String getStats() {
		String stats = "The " + getName() + " has " + getHealth() + " HP and is fighting with a(n) " + getWeapon().getStats();
		return stats;
	}

}
