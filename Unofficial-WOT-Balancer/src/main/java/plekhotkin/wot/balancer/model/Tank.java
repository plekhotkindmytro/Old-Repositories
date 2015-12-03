package plekhotkin.wot.balancer.model;

public final class Tank {
	public final String id;
	public final int markOfMastery;

	public int level;
	public int maxHealth;
	public int gunDamageMin;
	public int gunDamageMax;
	public String name;
	public String type;
	public String nation;

	public Tank(final String id, final int markOfMastery) {
		this.id = id;
		this.markOfMastery = markOfMastery;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getGunDamageMin() {
		return gunDamageMin;
	}

	public void setGunDamageMin(int gunDamageMin) {
		this.gunDamageMin = gunDamageMin;
	}

	public int getGunDamageMax() {
		return gunDamageMax;
	}

	public void setGunDamageMax(int gunDamageMax) {
		this.gunDamageMax = gunDamageMax;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tank other = (Tank) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
