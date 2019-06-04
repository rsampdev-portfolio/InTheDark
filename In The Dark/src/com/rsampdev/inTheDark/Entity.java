package com.rsampdev.inTheDark;

abstract class Entity {

	private int health = 0;
	private String name = "Entity";
	private Weapon weapon = Weapon.NONE;

	int getHealth() {
		return health;
	}

	void setHealth(int health) {
		this.health = health;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
