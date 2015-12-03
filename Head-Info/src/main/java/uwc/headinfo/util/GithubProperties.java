package uwc.headinfo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GithubProperties {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GithubProperties.class);

	private static Properties prop;

	private static String apiEndpoint;
	private static String clientId;
	private static String clientSecret;
	private static String authUrl;
	private static String accessTokenUrl;

	static {
		try {
			setProperties("github.properties");
		} catch (FileNotFoundException e) {
			LOGGER.error("Cannot find properties file.", e);
		} catch (InvalidPropertiesFormatException e) {
			LOGGER.error("Properties format is invalid.", e);
		} catch (IOException e) {
			LOGGER.error("Error while reading properties file.", e);
		} catch (URISyntaxException e) {
			LOGGER.error("Bad file properties URI.", e);
		}
	}

	private static void setProperties(String fileName)
			throws FileNotFoundException, IOException,
			InvalidPropertiesFormatException, URISyntaxException {
		prop = new Properties();
		final FileInputStream fileInputStream = new FileInputStream(new File(
				GithubProperties.class.getClassLoader().getResource(fileName)
						.toURI()));
		prop.load(fileInputStream);

		apiEndpoint = prop.getProperty("github.api.endpoint");
		LOGGER.debug("github.api.endpoint={}", apiEndpoint);

		clientId = prop.getProperty("github.clientId");
		LOGGER.debug("github.clientId={}", clientId);

		clientSecret = prop.getProperty("github.clientSecret");
		LOGGER.debug("github.clientSecret", clientSecret);

		authUrl = prop.getProperty("github.authUrl");
		LOGGER.debug("github.authUrl", authUrl);

		accessTokenUrl = prop.getProperty("github.accessTokenUrl");
		LOGGER.debug("github.accessTokenUrl", accessTokenUrl);

	}

	public static String getApiEndpoint() {
		return apiEndpoint;
	}

	public static String getClientId() {
		return clientId;
	}

	public static String getClientSecret() {
		return clientSecret;
	}

	public static String getAuthUrl() {
		return authUrl;
	}

	public static String getAccessTokenUrl() {
		return accessTokenUrl;
	}

}
