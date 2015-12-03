package uwc.headinfo.model;

public class GithubEvent {
	private String type;
	private String repoName;

	public GithubEvent(String type, String repoName) {
		this.setType(type);
		this.setRepoName(repoName);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

}
