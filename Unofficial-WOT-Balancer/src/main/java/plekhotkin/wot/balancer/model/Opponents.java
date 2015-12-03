package plekhotkin.wot.balancer.model;

import java.util.List;

public final class Opponents {
	public final Clan clanA;
	public final Clan clanB;

	public Opponents(List<Clan> clanList) {
		if (clanList.size() != 2) {
			throw new IllegalArgumentException(
					"There should be only two opponents.");
		}
		this.clanA = clanList.get(0);
		this.clanB = clanList.get(1);
	}

}
