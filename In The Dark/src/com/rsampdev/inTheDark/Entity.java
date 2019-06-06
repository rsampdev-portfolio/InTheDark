package com.rsampdev.inTheDark;

abstract class Entity {

	private String name;
	private Weapon weapon;
	private double health;
	private double experience;

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

	void attack(Entity entity) {
		entity.setHealth(entity.getHealth() - getWeapon().getDamage());
	}

	abstract String getStats();

}
