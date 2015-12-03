package com.dmytroplekhotkin.crawler.web.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Connector {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Connector.class);

	public static String get(final String urlString) {
		String result = null;
		final StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			final URL url = new URL(urlString);

			final URLConnection urlConnection = url.openConnection();

			bufferedReader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			result = stringBuilder.toString();
		} catch (IOException ioException) {
			LOGGER.error("Can't get data. Please enter CAPTCHA.", ioException);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException interruptedException) {
				LOGGER.error(interruptedException.getMessage(),
						interruptedException);
			}
			result = get(urlString);

		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}

		return result;
	}
}
