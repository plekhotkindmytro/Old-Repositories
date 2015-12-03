package com.dmytroplekhotkin.crawler.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AppSettings {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppSettings.class);
	private static final URL PROPS_PATH = AppSettings.class.getClassLoader()
			.getResource("settings/application.properties");

	private  final Properties defaultProperties;

	 {
		defaultProperties = new Properties();

		try (FileInputStream in = new FileInputStream(PROPS_PATH.toString())) {
			defaultProperties.load(in);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public String getProperty(String key) {
		return defaultProperties.getProperty(key);
	}

	public Properties getProperties() {
		return new Properties(defaultProperties);
	}
}
