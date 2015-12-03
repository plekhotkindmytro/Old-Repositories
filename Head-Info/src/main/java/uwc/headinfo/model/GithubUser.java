package uwc.headinfo.model;

import java.util.List;

public final class GithubUser {

	private String avatarUrl;
	private String login;

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	private String name;
	private String email;
	private List<GithubEvent> eventList;

	public GithubUser(String avatarUrl, String login) {
		this.avatarUrl = avatarUrl;
		this.login = login;
		this.setName(name);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GithubEvent> getEventList() {
		return eventList;
	}

	public void setEventList(List<GithubEvent> eventList) {
		this.eventList = eventList;
	}
}
