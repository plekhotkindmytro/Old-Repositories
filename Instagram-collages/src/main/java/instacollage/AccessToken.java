package instacollage;

public final class AccessToken {

	private final String accessToken;
	private final User user;

	public String getAccessToken() {
		return accessToken;
	}

	public User getUser() {
		return user;
	}

	public AccessToken(String accessToken, String id, String username, String fullName, String profilePicture) {
		this.accessToken = accessToken;
		this.user = new User(id, username, fullName, profilePicture);
	}

	public static final class User {
		private final String id;
		private final String username;
		private final String fullName;
		private final String profilePicture;

		public String getId() {
			return id;
		}

		public String getUsername() {
			return username;
		}

		public String getFullName() {
			return fullName;
		}

		public String getProfilePicture() {
			return profilePicture;
		}

		private User(String id, String username, String fullName, String profilePicture) {
			this.id = id;
			this.username = username;
			this.fullName = fullName;
			this.profilePicture = profilePicture;
		}

		@Override
		public String toString() {
			return "User [id=" + id + ", username=" + username + ", fullName=" + fullName + ", profilePicture=" + profilePicture + "]";
		}

	}

	@Override
	public String toString() {
		return "AccessToken [accessToken=" + accessToken + ", user=" + user + "]";
	}

}
