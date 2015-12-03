package com.trashcssview.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public final class Connector {
	public static String get(final String urlString) throws IOException {

		final URL url = new URL(urlString);

		final URLConnection urlConnection = url.openConnection();

		final BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(urlConnection.getInputStream()));

		final StringBuilder stringBuilder = new StringBuilder("");
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}

		return stringBuilder.toString();
	}
}
