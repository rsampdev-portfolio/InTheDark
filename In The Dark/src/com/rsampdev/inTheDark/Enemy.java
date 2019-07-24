package com.rsampdev.inTheDark;

import java.util.ArrayList;

class Enemy extends Entity implements Cloneable {

	private static final long serialVersionUID = -2659066575595252824L;

	private double damageMultiplier;

	static ArrayList<Enemy> ENEMIES = new ArrayList<Enemy>();

	private Enemy(String name, int health, int experience, Weapon weapon, Action deathAction) {
		setName(name);
		setHealth(health);
		setWeapon(weapon);
		setDeath(deathAction);
		setExperience(experience);
	}

	private Enemy(Enemy enemy) {
		setName(enemy.getName());
		setDeath(enemy.getDeath());
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

	static Enemy createSpider() {
		return new Enemy("Spider", 10, 50, Weapon.FIST, new Action() {
			@Override
			public void act() {
				QuestEvent.CURRENT_EVENT = QuestEvent.SPIDER_KILLED;
			}
		});
	}

	static Enemy createUndeadShambler() {
		return new Enemy("Undead Shambler", 10, 50, Weapon.FIST, new Action() {
			@Override
			public void act() {
				QuestEvent.CURRENT_EVENT = QuestEvent.UNDEAD_SHAMBLER_KILLED;
			}
		});
	}

	static Enemy createCaveGremlin() {
		return new Enemy("Cave Gremlin", 15, 75, Weapon.STONE_SWORD, new Action() {
			@Override
			public void act() {
				QuestEvent.CURRENT_EVENT = QuestEvent.CAVE_GREMLIN_KILLED;
			}
		});
	}

	static Enemy createCaveOrc() {
		return new Enemy("Cave Orc", 20, 100, Weapon.IRON_REINFORCED_CLUB, new Action() {
			@Override
			public void act() {
				QuestEvent.CURRENT_EVENT = QuestEvent.CAVE_ORC_KILLED;
			}
		});
	}

	static Enemy createSkeletonWarrior() {
		return new Enemy("Skeleton Warrior", 25, 125, Weapon.IRON_SWORD, new Action() {
			@Override
			public void act() {
				QuestEvent.CURRENT_EVENT = QuestEvent.SKELETON_WARRIOR_KILLED;
			}
		});
	}

	static Enemy createCaveTroll() {
		return new Enemy("Cave Troll", 40, 200, Weapon.WOODEN_CLUB, new Action() {
			@Override
			public void act() {
				QuestEvent.CURRENT_EVENT = QuestEvent.CAVE_TROLL_KILLED;
			}
		});
	}

	static void prepare() throws Exception {
		Enemy.ENEMIES.add((Enemy) Enemy.createSkeletonWarrior().clone());
		Enemy.ENEMIES.add((Enemy) Enemy.createUndeadShambler().clone());
		Enemy.ENEMIES.add((Enemy) Enemy.createCaveGremlin().clone());
		Enemy.ENEMIES.add((Enemy) Enemy.createCaveTroll().clone());
		Enemy.ENEMIES.add((Enemy) Enemy.createCaveOrc().clone());
		Enemy.ENEMIES.add((Enemy) Enemy.createSpider().clone());
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
