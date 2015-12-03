package com.dmytroplekhotkin.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.PortableServer.POA;

import com.dmytroplekhotkin.crawler.util.AppSettings;
import com.dmytroplekhotkin.crawler.web.service.GisApiService;

public class GisCrawler {

	public static void main(String[] args) throws JSONException {
		GisApiService service = new GisApiService();
		// preparation(service);

		URL fileUrl = GisCrawler.class.getClassLoader().getResource(
				"data/targetCities.2gis.csv");
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(fileUrl.toURI()), Charset.defaultCharset())) {

			String line;
			while ((line = reader.readLine()) != null) {

				String[] tokens = line.split(",");
				String response = service.getFirmList("эвакуатор", tokens[1]);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONObject projectListJson = new JSONObject(response);
				JSONArray result = projectListJson.getJSONArray("result");

				for (int i = 0; i < result.length(); i++) {
					JSONObject city = result.getJSONObject(i);
					String id = city.getString("id");
					String hash = city.getString("hash");
					String profile = service.getFirmProfile(id, hash);
					saveData(tokens[0], tokens[1], profile);
				}

			}
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void preparation(GisApiService service) throws JSONException {
		AppSettings appSettings = new AppSettings();

		String response = service.getProjectList(
				appSettings.getProperty("key"),
				appSettings.getProperty("version"));
		JSONObject projectListJson = new JSONObject(response);
		JSONArray result = projectListJson.getJSONArray("result");

		StringBuilder targetCityDataBuilder = new StringBuilder();

		URL fileUrl = GisCrawler.class.getClassLoader().getResource(
				"data/geoids.csv");
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(fileUrl.toURI()), Charset.defaultCharset())) {

			String line;
			while ((line = reader.readLine()) != null) {
				for (int i = 0; i < result.length(); i++) {
					JSONObject city = result.getJSONObject(i);
					String name = city.getString("name");
					String[] tokens = line.split(",");
					if (tokens[1].equals(name)) {
						targetCityDataBuilder.append(tokens[0]).append(",")
								.append(name).append(System.lineSeparator());
					}
				}
			}
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (PrintWriter writer = new PrintWriter("targetCities.2gis.csv")) {
			writer.write(targetCityDataBuilder.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void saveData(String country, String city, String data)
			throws JSONException {
		JSONObject result = new JSONObject(data);

		String name = result.getString("name");
		String cityName = result.getString("city_name");
		String address = result.optString("address");

		JSONArray contactsWrapper = result.getJSONArray("contacts");

		JSONArray contacts = contactsWrapper.getJSONObject(0).getJSONArray(
				"contacts");
		String phone = contacts.getJSONObject(0).getString("value");

		
		String morePhones = "";
		int morePhonesCount = 0;
		for (int i = 1; i < contacts.length(); i++) {

			JSONObject contact = contacts.getJSONObject(i);

			if (contact.getString("type").equals("phone")) {
				if (phone.length() >= 9) {
					if (morePhonesCount != 0) {
						morePhones += ",";
					}
					morePhones += validateAndUpdate(country,
							contact.getString("value"));
					morePhonesCount++;
				} else {
					phone = contact.getString("value");
				}
			}

		}
		phone = validateAndUpdate(country, phone);

		String desc = name;
		if (address != null && !address.isEmpty() && address.length() != 0) {
			desc += ", полный адресс: " + address;
		}

		if (!morePhones.equals("")) {
			desc += ", доп. тел.: " + morePhones;
		}

		String row = String.format("%s|%s|%s|%s", country, city, phone, desc);

		try (PrintWriter writer = new PrintWriter(new BufferedWriter(
				new FileWriter("result_2gis.csv", true)))) {
			writer.write(row + System.lineSeparator());

		} catch (FileNotFoundException e) {
			e.getStackTrace();
		} catch (IOException e) {
			e.getStackTrace();
		}

	}

	private static String validateAndUpdate(String country, String phone) {

		phone = phone.replaceAll("\\D", "");
		String countryCode = "";
		int endIndex = phone.length() - 7;
		int beginIndex = 0;
		switch (country) {
		case "Украина":
			countryCode = "380";
			beginIndex = endIndex - 2;

			break;
		case "Казахстан":
			countryCode = "7";
			beginIndex = endIndex - 3;
			break;
		case "Беларусь":
			countryCode = "375";
			beginIndex = endIndex - 2;
			break;
		default:
			break;
		}

		String secondPart = phone.substring(beginIndex, endIndex);
		String otherPart = phone.substring(endIndex);
		String part3 = otherPart.substring(0, 3);
		String part4 = otherPart.substring(3, 5);
		String part5 = otherPart.substring(5, 7);
		String template = String.format("+%s (%s) %s-%s-%s", countryCode,
				secondPart, part3, part4, part5);

		phone = template;
		return phone;
	}
}
