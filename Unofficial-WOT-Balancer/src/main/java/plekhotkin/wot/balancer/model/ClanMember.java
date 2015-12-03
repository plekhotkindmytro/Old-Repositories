package plekhotkin.wot.balancer.model;

import java.util.List;

public final class ClanMember {
	public final String accountId;
	public final String accountName;

	private Tank tankForGame;
	private List<Tank> tankList;

	public ClanMember(final String accountId, final String accountName) {
		this.accountId = accountId;
		this.accountName = accountName;
	}

	public List<Tank> getTankList() {
		return tankList;
	}

	public void setTankList(final List<Tank> tankList) {
		if (tankList == null || tankList.size() == 0) {
			throw new IllegalArgumentException("Clan member " + accountName
					+ " must have at least one tank.");
		}
		this.tankList = tankList;
	}

	public Tank getTankForGame() {
		return tankForGame;
	}

	public void setTankForGame(Tank tankForGame) {
		this.tankForGame = tankForGame;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
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
		ClanMember other = (ClanMember) obj;
		if (accountId == null) {
			if (other.accountId != null) {
				return false;
			}
		} else if (!accountId.equals(other.accountId)) {
			return false;
		}
		return true;
	}

}
