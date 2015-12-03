package org.gradle.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ApidocsProperties {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApidocsProperties.class);

	private static Properties prop;

	private static String endpoint;
	private static String overviewPage;
	private static String packagePage;
	private static String xpathPackageNames;
	private static String xpathPackageContent;

	static {
		try {
			setProperties("apidocs.properties");
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
				ApidocsProperties.class.getClassLoader().getResource(fileName)
						.toURI()));
		prop.load(fileInputStream);

		endpoint = prop.getProperty("docs.endpoint");
		LOGGER.debug("docs.endpoint={}", endpoint);

		overviewPage = prop.getProperty("docs.overview.page");
		LOGGER.debug("docs.overview.page={}", overviewPage);

		packagePage = prop.getProperty("docs.package.page");
		LOGGER.debug("docs.package.page", packagePage);

		xpathPackageNames = prop.getProperty("docs.xpath.package.names");
		LOGGER.debug("docs.xpath.package.names", xpathPackageNames);

		xpathPackageContent = prop.getProperty("docs.xpath.package.content");
		LOGGER.debug("docs.xpath.package.content", xpathPackageContent);

	}

	public static String getEndpoint() {
		return endpoint;
	}

	public static String getOverviewPage() {
		return overviewPage;
	}

	public static String getPackagePage() {
		return packagePage;
	}

	public static String getXpathPackageNames() {
		return xpathPackageNames;
	}

	public static String getXpathPackageContent() {
		return xpathPackageContent;
	}

}
