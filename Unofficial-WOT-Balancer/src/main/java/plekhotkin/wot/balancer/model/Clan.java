package plekhotkin.wot.balancer.model;

import java.util.List;

public final class Clan {
	public final String id;
	public final String name;
	private List<ClanMember> memberList;

	public Clan(final String id, final String name) {
		this.id = id;
		this.name = name;
	}

	public List<ClanMember> getMembers() {
		return memberList;
	}

	public void setMembers(final List<ClanMember> members) {
		memberList = members;
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
		Clan other = (Clan) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public void printStats() {
		int masterySum = 0;
		int gunDamageMinSum = 0;
		int gunDamageMaxSum = 0;
		int maxHealthSum = 0;

		for (ClanMember clanMember : memberList) {
			final Tank tank = clanMember.getTankForGame();

			masterySum += tank.markOfMastery;
			gunDamageMinSum += tank.gunDamageMin;
			gunDamageMaxSum += tank.gunDamageMax;
			maxHealthSum += tank.maxHealth;
		}

		System.out.println("Clan" + name + ": " + "mastery (sum) = "
				+ masterySum + ", gun damage min (sum) = " + gunDamageMinSum
				+ ", gun damage max (sum) = " + gunDamageMaxSum
				+ ", max health (sum) = " + maxHealthSum);
	}

}
