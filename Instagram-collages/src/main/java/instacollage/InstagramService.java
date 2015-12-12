package instacollage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class InstagramService {

	private final String authEndpoint;
	private final String clientId;
	private final String redirectUri;

	private final String clientSecret;
	private final String accessTokenUrl;

	private final String usersMediaUrl;

	public InstagramService() throws IOException {
		Properties properties = new Properties();
		InputStream propertiesInputStream = CollageController.class.getClassLoader().getResourceAsStream("instagram.properties");
		properties.load(propertiesInputStream);
		propertiesInputStream.close();

		authEndpoint = properties.getProperty("auth.endpoint");
		clientId = properties.getProperty("auth.clientId");
		redirectUri = properties.getProperty("auth.redirectUri");

		accessTokenUrl = properties.getProperty("auth.accessToken.url");
		clientSecret = properties.getProperty("auth.clientSecret");
		usersMediaUrl = properties.getProperty("users.media.recent");

	}

	public String getAuthorizationUrl() {
		StringBuilder builder = new StringBuilder();
		builder.append(authEndpoint).append("?client_id=").append(clientId);
		builder.append("&redirect_uri=").append(redirectUri);
		builder.append("&response_type=code");
		return builder.toString();

	}

	// {
	// "code": 400,
	// "error_type": "OAuthException",
	// "error_message": "You must provide a client_secret"
	// }
	public AccessToken exchangeCode(String code) {
		StringBuilder bodyBuilder = new StringBuilder();
		bodyBuilder.append("client_id=").append(clientId);
		bodyBuilder.append("&client_secret=").append(clientSecret);
		bodyBuilder.append("&grant_type=authorization_code");
		bodyBuilder.append("&redirect_uri=").append(redirectUri);
		bodyBuilder.append("&code=").append(code);
		try {
			HttpResponse<JsonNode> response = Unirest.post(accessTokenUrl).body(bodyBuilder.toString()).asJson();
			int responseStatus = response.getStatus();
			if (responseStatus != 200) {
				throw new ServiceException(response.getStatusText());
			}
			JsonNode responseBody = response.getBody();
			JSONObject json = responseBody.getObject();

			String accessTokenString = json.getString("access_token");
			JSONObject userObject = json.getJSONObject("user");
			String userId = userObject.getString("id");
			String username = userObject.getString("username");
			String fullName = userObject.getString("full_name");
			String profilePicture = userObject.getString("profile_picture");

			return new AccessToken(accessTokenString, userId, username, fullName, profilePicture);

		} catch (UnirestException e) {
			throw new ServiceException("Server error during auth", e);
		}
	}

	public Page<Image> getUserImages(String url, String accessToken) {
		if (url == null) {
			StringBuilder builder = new StringBuilder();
			builder.append(usersMediaUrl);
			builder.append("?access_token=").append(accessToken);
			url = builder.toString();
		}

		try {

			HttpResponse<JsonNode> response = Unirest.get(url).asJson();

			int responseStatus = response.getStatus();
			if (responseStatus != 200) {
				throw new ServiceException(response.getStatusText());
			}

			JsonNode responseBody = response.getBody();
			JSONObject json = responseBody.getObject();

			JSONArray dataJsonArray = json.getJSONArray("data");
			List<Image> imageList = new ArrayList<Image>();
			for (int i = 0; i < dataJsonArray.length(); i++) {
				JSONObject dataEntry = dataJsonArray.getJSONObject(i);
				if ("image".equals(dataEntry.getString("type"))) {
					JSONObject imagesJson = dataEntry.getJSONObject("images");
					JSONObject thumbnail = imagesJson.getJSONObject("thumbnail");
					JSONObject standardResolution = imagesJson.getJSONObject("standard_resolution");
					JSONObject lowResolution = imagesJson.getJSONObject("low_resolution");
					Image image = new Image();
					image.setId(dataEntry.getString("id"));
					image.setThumbnailUrl(thumbnail.getString("url"));
					image.setLowUrl(lowResolution.getString("url"));
					image.setStandardUrl(standardResolution.getString("url"));
					imageList.add(image);
				}
			}
			Page<Image> imagesPage = new Page<Image>(imageList);

			JSONObject paginationJson = json.getJSONObject("pagination");
			if (paginationJson.has("next_url")) {
				String nextUrl = paginationJson.getString("next_url");
				// String nextMaxId = paginationJson.getString("next_max_id");
				imagesPage.setNextUrl(nextUrl);
			}

			return imagesPage;
		} catch (UnirestException e) {
			throw new ServiceException("Cannot get user images", e);
		}
	}

}
