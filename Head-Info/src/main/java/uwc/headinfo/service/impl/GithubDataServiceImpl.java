package uwc.headinfo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uwc.headinfo.model.GithubEvent;
import uwc.headinfo.model.GithubUser;
import uwc.headinfo.service.GithubDataService;
import uwc.headinfo.util.ApiConnector;
import uwc.headinfo.util.GithubProperties;

public class GithubDataServiceImpl implements GithubDataService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GithubDataServiceImpl.class);

	private static final String EVENTS_URL = "/users/:username/events";
	private static final int MAX_PAGE_NUMBER = 10;
	private static final int MAX_EVENTS_COUNT_PER_PAGE = 30;

	public List<GithubUser> getUserInfoList(String searchTerm,
			Object accessToken) {

		String accessTokenParam = accessToken == null ? "" : "access_token="
				+ accessToken;
		List<GithubUser> userList = new ArrayList<GithubUser>();

		try {
			JSONObject response = ApiConnector.getJsonObject(GithubProperties
					.getApiEndpoint()
					+ "/search/users?q="
					+ searchTerm
					+ "&"
					+ accessTokenParam);
			JSONArray dataArray = response.getJSONArray("items");
			for (int i = 0; i < dataArray.length(); i++) {
				JSONObject userJson = dataArray.getJSONObject(i);

				String login = userJson.getString("login");
				String avatarUrl = userJson.getString("avatar_url");
				GithubUser user = new GithubUser(avatarUrl, login);

				// setPersonalData(user, accessTokenParam);
				setUserEventList(user, accessTokenParam);

				userList.add(user);
			}
		} catch (JSONException e) {
			LOGGER.error("JSON structure of user list is wrong.", e);
		} catch (IOException e) {
			LOGGER.error("Error while reading users info.", e);
		}

		return userList;
	}

	private void setUserEventList(GithubUser githubUser, String accessTokenParam) {
		List<GithubEvent> eventList = getGithubEventsByLogin(
				githubUser.getLogin(), accessTokenParam);
		githubUser.setEventList(eventList);
	}

	private List<GithubEvent> getGithubEventsByLogin(String login,
			String accessTokenParam) {
		final List<GithubEvent> eventList = new ArrayList<GithubEvent>();

		for (int i = 1; i <= MAX_PAGE_NUMBER; i++) {
			List<GithubEvent> eventListPerPage = getCommitsCountByLoginByPage(
					login, accessTokenParam, i);

			eventList.addAll(eventListPerPage);
			if (eventListPerPage.size() < MAX_EVENTS_COUNT_PER_PAGE) {
				break;
			}
		}

		return eventList;
	}

	private List<GithubEvent> getCommitsCountByLoginByPage(String login,
			String accessTokenParam, int pageNumber) {
		final List<GithubEvent> eventList = new ArrayList<GithubEvent>();
		final String eventUrl = EVENTS_URL.replace(":username", login);
		try {
			JSONArray response = ApiConnector.getJsonArray(GithubProperties
					.getApiEndpoint()
					+ eventUrl
					+ "?page="
					+ pageNumber
					+ "&"
					+ accessTokenParam);

			for (int i = 0; i < response.length(); i++) {
				JSONObject eventJson = response.getJSONObject(i);
				String type = eventJson.getString("type");
				JSONObject repo = eventJson.getJSONObject("repo");
				String repoName = repo.getString("name");
				GithubEvent event = new GithubEvent(type, repoName);
				eventList.add(event);
			}

		} catch (JSONException e) {
			LOGGER.error("JSON structure of user list is wrong.", e);
		} catch (IOException e) {
			LOGGER.error("Error while reading users info.", e);
		}
		return eventList;
	}

}
