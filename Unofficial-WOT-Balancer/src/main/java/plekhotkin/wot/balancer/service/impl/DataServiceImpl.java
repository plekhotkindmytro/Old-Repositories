package plekhotkin.wot.balancer.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import plekhotkin.wot.balancer.model.Clan;
import plekhotkin.wot.balancer.model.ClanMember;
import plekhotkin.wot.balancer.model.Opponents;
import plekhotkin.wot.balancer.model.Tank;
import plekhotkin.wot.balancer.service.DataService;
import plekhotkin.wot.balancer.util.Connector;

public class DataServiceImpl implements DataService {

	private static final int TEAM_PLAYERS_COUNT = 15;
	private static final int TOP_TEN = 10;
	private static final int OPPONENTS_COUNT = 2;

	private final String apiEndpoint;
	private final String applicationId;

	public DataServiceImpl(final String applicationId, final String apiEndpoint) {
		this.applicationId = applicationId;
		this.apiEndpoint = apiEndpoint;
	}

	private JSONArray getTopClans(final String orderBy, final String mapId)
			throws IOException, JSONException {
		final StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(apiEndpoint).append("globalwar/top/")
				.append("?application_id=").append(applicationId)
				.append("&fields=clan_id,name,members_count")
				.append("&map_id=").append(mapId).append("&order_by=")
				.append(orderBy);
		final JSONObject response = Connector.get(urlBuilder.toString());
		final JSONArray clanArray = response.getJSONArray("data");
		return clanArray;
	}

	private List<Clan> getRandomOpponents(final JSONArray clanArray)
			throws JSONException, IOException {
		final Random random = new Random();
		List<Clan> randomClanList = new ArrayList<Clan>();

		Set<Integer> chosenIndexes = new HashSet<Integer>();
		while (randomClanList.size() != OPPONENTS_COUNT
				&& clanArray.length() != chosenIndexes.size()) {
			int randomIndex = random.nextInt(TOP_TEN);
			final JSONObject randomClan = clanArray.getJSONObject(randomIndex);
			final int membersCount = randomClan.getInt("members_count");
			final String clanId = randomClan.getString("clan_id");

			if (membersCount >= TEAM_PLAYERS_COUNT
					&& !chosenIndexes.contains(randomIndex)) {
				final String clanName = randomClan.getString("name");
				System.out.println("Analysing clan \"" + clanName + "\"");
				final Clan clan = new Clan(clanId, clanName);
				JSONObject clanInfoMap = getClanInfoMap(Arrays.asList(clanId));

				List<ClanMember> clanMembers = getRandomClanMembersByClanId(
						clanId, clanInfoMap);
				if (clanMembers.size() == TEAM_PLAYERS_COUNT) {
					clan.setMembers(clanMembers);
					randomClanList.add(clan);
					chosenIndexes.add(randomIndex);
				}
			}
		}

		return randomClanList;
	}

	@Override
	public Opponents getOpponents() throws JSONException, IOException {
		JSONArray clanArrayJson = getTopClans("provinces_count", "globalmap");
		List<Clan> clanList = getRandomOpponents(clanArrayJson);
		Opponents opponents = new Opponents(clanList);
		return opponents;
	}

	private JSONObject getClanInfoMap(List<String> clanIdList)
			throws JSONException, IOException {
		final String methodPath = "clan/info/";

		final String urlString = buildUrl(apiEndpoint, methodPath,
				"?application_id=", applicationId,
				"&fields=members,members.account_name", "&clan_id=",
				convertCollectionToString(clanIdList));

		final JSONObject response = Connector.get(urlString);
		final JSONObject clanInfo = response.getJSONObject("data");
		return clanInfo;
	}

	private List<ClanMember> getRandomClanMembersByClanId(String clanId,
			JSONObject clanInfo) throws JSONException, IOException {
		final JSONObject clanJson = clanInfo.getJSONObject(clanId);
		final JSONObject clanMembersJson = clanJson.getJSONObject("members");

		final List<ClanMember> fullMemberList = new ArrayList<ClanMember>();
		@SuppressWarnings("rawtypes")
		Iterator membersIterator = clanMembersJson.keys();

		final List<String> accountIdList = new ArrayList<String>();

		while (membersIterator.hasNext()) {

			final String accountId = membersIterator.next().toString();
			final JSONObject memberJson = clanMembersJson
					.getJSONObject(accountId);
			final String accountName = memberJson.getString("account_name");
			final ClanMember clanMember = new ClanMember(accountId, accountName);
			fullMemberList.add(clanMember);
			accountIdList.add(accountId);
		}

		// get tanks id
		final Set<String> tankIdSet = new HashSet<String>();
		JSONObject accountTanksMap = getAccountTanksMap(accountIdList);
		Iterator<ClanMember> memberIterator = fullMemberList.iterator();
		while (memberIterator.hasNext()) {
			ClanMember clanMember = memberIterator.next();
			List<Tank> tanks = getAccountTanks(clanMember.accountId,
					accountTanksMap);

			if (!tanks.isEmpty()) {
				for (Tank tank : tanks) {
					tankIdSet.add(tank.id);
				}
				clanMember.setTankList(tanks);
			} else {
				memberIterator.remove();
			}
		}

		JSONObject tankInfoJson = getTankInfo(tankIdSet);
		for (ClanMember clanMember : fullMemberList) {
			Iterator<Tank> tankIterator = clanMember.getTankList().iterator();
			while (tankIterator.hasNext()) {
				Tank tank = tankIterator.next();
				JSONObject tankInfo = tankInfoJson.getJSONObject(tank.id);
				final int level = tankInfo.getInt("level");
				// TODO: replace magic numbers
				if (level >= 4 && level <= 6) {
					final int gunDamageMin = tankInfo.getInt("gun_damage_min");
					final int gunDamageMax = tankInfo.getInt("gun_damage_max");
					final int maxHealth = tankInfo.getInt("max_health");
					final String name = tankInfo.getString("name_i18n");

					tank.setLevel(level);
					tank.setGunDamageMin(gunDamageMin);
					tank.setGunDamageMax(gunDamageMax);
					tank.setMaxHealth(maxHealth);
					tank.setName(name);

				} else {
					tankIterator.remove();
				}

			}
		}

		final List<ClanMember> randomClanMemberList = new ArrayList<ClanMember>();
		final Random random = new Random();
		while (randomClanMemberList.size() != TEAM_PLAYERS_COUNT
				&& !fullMemberList.isEmpty()) {
			int randomIndex = random.nextInt(fullMemberList.size());

			ClanMember member = fullMemberList.remove(randomIndex);

			if (!member.getTankList().isEmpty()) {
				randomClanMemberList.add(member);
			}
		}
		return randomClanMemberList;
	}

	private JSONObject getAccountTanksMap(final List<String> accountIdList)
			throws IOException, JSONException {
		final String methodPath = "account/tanks/";

		final String urlString = buildUrl(apiEndpoint, methodPath,
				"?application_id=", applicationId,
				"&fields=tank_id,mark_of_mastery", "&account_id=",
				convertCollectionToString(accountIdList));

		final JSONObject response = Connector.get(urlString);
		final JSONObject accountTanksMap = response.getJSONObject("data");
		return accountTanksMap;
	}

	private List<Tank> getAccountTanks(String accountId,
			JSONObject accountTanksMap) throws IOException, JSONException {
		final JSONArray tanksArray = accountTanksMap.getJSONArray(accountId);

		final List<Tank> tankList = new ArrayList<Tank>();

		for (int i = 0; i < tanksArray.length(); i++) {
			final JSONObject tankJson = tanksArray.getJSONObject(i);
			final int markOfMastery = tankJson.getInt("mark_of_mastery");
			final String tankId = tankJson.getString("tank_id");
			Tank tank = new Tank(tankId, markOfMastery);
			tankList.add(tank);
		}
		return tankList;
	}

	private JSONObject getTankInfo(final Set<String> tankIdSet)
			throws IOException, JSONException {
		final String methodPath = "encyclopedia/tankinfo/";

		final String urlString = buildUrl(
				apiEndpoint,
				methodPath,
				"?application_id=",
				applicationId,
				"&fields=nation_i18n,name_i18n,type_i18n,level,gun_damage_min,gun_damage_max,max_health",
				"&tank_id=", convertCollectionToString(tankIdSet));

		final JSONObject response = Connector.get(urlString.toString());

		return response.getJSONObject("data");
	}

	private static String buildUrl(final String apiEndpoint,
			final String methodPath, final String... parts) {

		final StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(apiEndpoint).append(methodPath);
		for (int i = 0; i < parts.length; i++) {
			urlBuilder.append(parts[i]);
		}

		return urlBuilder.toString();
	}

	private static <T> String convertCollectionToString(
			final Collection<T> collection) {
		final StringBuilder builder = new StringBuilder();
		Iterator<T> iterator = collection.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next());
			if (iterator.hasNext()) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

}
