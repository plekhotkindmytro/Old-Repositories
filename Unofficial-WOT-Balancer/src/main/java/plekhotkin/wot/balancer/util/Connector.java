package plekhotkin.wot.balancer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class Connector {
	public static JSONObject get(final String urlString) throws IOException,
			JSONException {

		final URL url = new URL(urlString);

		final URLConnection urlConnection = url.openConnection();

		final BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(urlConnection.getInputStream()));

		final StringBuilder stringBuilder = new StringBuilder("");
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}

		final JSONObject response = new JSONObject(stringBuilder.toString());

		return response;
	}
}
