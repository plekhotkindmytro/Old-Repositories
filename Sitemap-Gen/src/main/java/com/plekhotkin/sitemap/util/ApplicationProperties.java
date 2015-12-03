package com.plekhotkin.sitemap.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);
    private static Properties prop = new Properties();;

    static {
        try {
            loadProperties("application.properties");
            loadProperties("mail.properties");
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

    private static void loadProperties(String fileName) throws FileNotFoundException, IOException, InvalidPropertiesFormatException, URISyntaxException {
        final FileInputStream fileInputStream = new FileInputStream(new File(ApplicationProperties.class.getClassLoader().getResource(fileName).toURI()));
        prop.load(fileInputStream);
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

}
