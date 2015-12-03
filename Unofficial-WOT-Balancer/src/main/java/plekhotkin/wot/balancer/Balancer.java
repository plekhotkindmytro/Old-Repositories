package plekhotkin.wot.balancer;

import java.util.List;
import java.util.Random;

import plekhotkin.wot.balancer.model.Clan;
import plekhotkin.wot.balancer.model.ClanMember;
import plekhotkin.wot.balancer.model.Opponents;
import plekhotkin.wot.balancer.model.Tank;

public final class Balancer {

	public static void balance(Opponents opponents) {
		final Random random = new Random();

		for (ClanMember member : opponents.clanA.getMembers()) {
			final List<Tank> tankList = member.getTankList();

			int randomIndex = random.nextInt(tankList.size());
			Tank tank = tankList.get(randomIndex);

			member.setTankForGame(tank);
			findSimillarTankForOtherClan(tank, opponents.clanB);
		}
	}

	private static void findSimillarTankForOtherClan(Tank tank, Clan other) {

		int masteryDelta = Integer.MAX_VALUE;
		int gunDamageDelta = Integer.MAX_VALUE;
		int maxHealthDelta = Integer.MAX_VALUE;
		Tank otherTankForGame = null;
		ClanMember memberWaitingForTank = null;

		for (ClanMember member : other.getMembers()) {
			if (gunDamageDelta == 0 && maxHealthDelta == 0) {
				break;
			}
			if (member.getTankForGame() == null) {
				for (Tank otherTank : member.getTankList()) {

					if (masteryDelta == 0 && gunDamageDelta == 0
							&& maxHealthDelta == 0) {
						break;
					}

					int masteryDiff = tank.markOfMastery
							- otherTank.markOfMastery;
					int gunDamageMinDiff = tank.gunDamageMin
							- otherTank.gunDamageMin;
					int gunDamageMaxDiff = tank.gunDamageMax
							- otherTank.gunDamageMax;
					int gunDamageDiff = Math.abs(gunDamageMaxDiff
							+ gunDamageMinDiff);

					int maxHealthDiff = Math.abs(tank.maxHealth
							- otherTank.maxHealth);

					if (masteryDiff <= masteryDelta
							&& gunDamageDiff <= gunDamageDelta
							&& maxHealthDiff <= maxHealthDelta) {
						otherTankForGame = otherTank;
						memberWaitingForTank = member;
						masteryDelta = masteryDiff;
						gunDamageDelta = gunDamageDiff;
						maxHealthDelta = maxHealthDiff;
					} else if (Math.abs(masteryDiff - masteryDelta) <= 2
							&& (gunDamageDiff < gunDamageDelta || maxHealthDiff < maxHealthDelta)) {
						int delta = Math.abs(gunDamageDiff - maxHealthDiff);
						if (delta < Math.abs(gunDamageDelta - maxHealthDelta)) {
							masteryDelta = masteryDiff;
							gunDamageDelta = gunDamageDiff;
							maxHealthDelta = maxHealthDiff;
							otherTankForGame = otherTank;
							memberWaitingForTank = member;
						}
					}

				}
			}
		}

		memberWaitingForTank.setTankForGame(otherTankForGame);

	}
}
