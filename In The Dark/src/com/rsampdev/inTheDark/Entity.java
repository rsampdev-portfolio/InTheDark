package com.rsampdev.inTheDark;

abstract class Entity {

	private int health;
	private String name;
	private Weapon weapon;
	private int experience;

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	int getHealth() {
		return health;
	}

	void setHealth(int health) {
		this.health = health;
	}

	int getExperience() {
		return this.experience;
	}

	void setExperience(int experience) {
		this.experience = experience;
	}

	void addExperience(int experience) {
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
