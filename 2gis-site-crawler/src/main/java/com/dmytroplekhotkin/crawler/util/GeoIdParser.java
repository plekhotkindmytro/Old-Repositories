package com.dmytroplekhotkin.crawler.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoIdParser {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GeoIdParser.class);

	public static void main(String[] args) throws JSONException {
		// saveAllToCsv();
		savePartToCsv(Arrays.asList("187", "149", "159"));
	}

	private static void savePartToCsv(List<String> parentIds)
			throws JSONException {
		String data = getGeoIds("geoIdList.ru.json");
		JSONArray json = new JSONArray(data);

		try (PrintWriter writer = new PrintWriter(new BufferedWriter(
				new FileWriter("geoid_ukr_kz_bl.ru.csv", true)))) {
			JSONObject element = null;
			for (int i = 0; i < json.length(); i++) {
				element = json.getJSONObject(i);
				String parentId = element.getString("parentId");
				if (parentIds.contains(parentId)) {
					String name = element.getString("name");
					String id = element.getString("id");
					String csvLine = String.format("%s,%s,%s", parentId, name,
							id);

					writer.println(csvLine);
				}
			}

		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	private static String getGeoIds(String fileName) {
		StringBuilder builder = new StringBuilder();
		URL fileUrl = GeoIdParser.class.getClassLoader().getResource(fileName);

		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileUrl.toURI()))));
			while ((line = reader.readLine()) != null) {
				builder.append(line);

			}
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);

		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}

		return builder.toString();
	}
}
