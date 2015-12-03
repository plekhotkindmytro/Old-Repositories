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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmytroplekhotkin.crawler.PhoneLoader;

public class GeoIdParser {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GeoIdParser.class);

	private static final int UKRAINE_GEO_ID = 187;
	public static void main(String[] args) throws JSONException {
		String data = getGeoIdJson("geoIdList.ru.json");
		JSONArray json = new JSONArray(data);
		
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(
				new FileWriter("geoIdList.ru.csv", true)))) {
			JSONObject element = null;
			for(int i = 0;i< json.length();i++){
				element = json.getJSONObject(i);
				String parentId = element.getString("parentId");
				String name = element.getString("name");
				String id = element.getString("id");
				String csvLine = String.format("%s,%s,%s", parentId, name,id);
				
				writer.println(csvLine);
			}

		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
	}

	private static String getGeoIdJson(String fileName) {
		StringBuilder builder = new StringBuilder();
		URL fileUrl = PhoneLoader.class.getClassLoader().getResource(fileName);

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
