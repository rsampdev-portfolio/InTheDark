package com.rsampdev.inTheDark;

import java.io.Serializable;

abstract class Entity implements Serializable {

	private static final long serialVersionUID = -5027268964715406888L;

	private String name;
	private Weapon weapon;
	private double health;
	private double experience;
	private transient DeathAction death;

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	double getHealth() {
		return health;
	}

	void setHealth(double health) {
		this.health = health;

		if (this.health <= 0) {
			this.death.death();
		}
	}

	double getExperience() {
		return this.experience;
	}

	void setExperience(double experience) {
		this.experience = experience;
	}

	void addExperience(double experience) {
		this.experience += experience;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	DeathAction getDeath() {
		return death;
	}

	void setDeath(DeathAction death) {
		this.death = death;
	}

	abstract void attack(Entity entity);

	abstract double getAttackDamage();

	abstract String getStats();

}
