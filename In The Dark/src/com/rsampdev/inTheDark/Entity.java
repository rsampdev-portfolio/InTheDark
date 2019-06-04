package com.rsampdev.inTheDark;

class Entity {

	private int health = 0;
	private Weapon weapon = Weapon.NONE;

	int getHealth() {
		return health;
	}

	void setHealth(int health) {
		this.health = health;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

}
