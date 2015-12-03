package com.dmytroplekhotkin.crawler;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmytroplekhotkin.crawler.model.CompanyData;
import com.dmytroplekhotkin.crawler.util.Connector;

public class PhoneLoader {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PhoneLoader.class);
	private static final String YANDEX_URL_FORMAT = "http://ojfak1.sdc1vps1.owl.e.s48.ru.wbprx.com/cmd?cmd=ajax_search&page_size=50&name=эвакуатор&geo_id=%s";
	private static final int MIN_SLEEP_TIME = 100;
	private static final Random random = new Random();

	private static final Map<String, String> COUNTRY_TO_EXPLORE = new HashMap<>();
	private static final String UKRAINE_GEO_ID = "187";
	private static final String BELARUS_GEO_ID = "149";
	private static final String KARAKHSTAN_GEO_ID = "159";
	static {
		COUNTRY_TO_EXPLORE.put(UKRAINE_GEO_ID, "Украина");
		COUNTRY_TO_EXPLORE.put(BELARUS_GEO_ID, "Беларусь");
		COUNTRY_TO_EXPLORE.put(KARAKHSTAN_GEO_ID, "Казахстан");
	}

	public static void main(String[] args) throws IOException, JSONException,
			InterruptedException {
		if (args.length != 1) {
			LOGGER.error("Enter file name with region data as parameter. Nothing parsed.");
		} else {
			String fileName = args[0];
			start(fileName);
		}
	}

	private static String getResponse(String geoId) {
		int sleepTime = random.nextInt(100) + MIN_SLEEP_TIME;
		String url = String.format(YANDEX_URL_FORMAT, geoId);

		String response = Connector.get(url);
		// I don't want to be banned :)
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private static List<CompanyData> parceResponse(String response,
			String country) throws JSONException {
		List<CompanyData> parsedData = new ArrayList<CompanyData>();
		JSONObject responseJson = new JSONObject(response);
		JSONArray results = responseJson.getJSONArray("result");
		String total = responseJson.getString("total");
		LOGGER.trace("Total rows found: " + total);
		if (!total.equals("0")) {
			for (int i = 0; i < results.length(); i++) {
				String htmlString = results.getString(i);
				Element data = Jsoup.parseBodyFragment(htmlString);
				Elements resultItems = data.getElementsByClass("b-card");

				for (Element element : resultItems) {
					String title = element.select(".b-card__header-link")
							.text();
					String address = element.select(".b-card__address").text();
					String phones = element.select(".b-card__phones").text()
							.replace("&nbsp;", " ");
					List<String> phoneNumberList = Arrays.asList(phones
							.split(","));
					String site = element.select(".b-card__site").text();
					CompanyData companyData = new CompanyData(country,
							phoneNumberList, address, site, title);
					LOGGER.info(companyData.toString());
					parsedData.add(companyData);
				}

			}
		}
		return parsedData;
	}

	private static void start(String fileName) throws JSONException {
		final URL fileUrl = PhoneLoader.class.getClassLoader().getResource(
				fileName);

		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileUrl.toURI()))));
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");

				if (tokens.length == 3) {
					final String parentId = tokens[0];
					final String name = tokens[1];
					final String geoId = tokens[2];

					if (COUNTRY_TO_EXPLORE.containsKey(parentId)) {
						String countryName = COUNTRY_TO_EXPLORE.get(parentId);
						String response = getResponse(geoId);
						List<CompanyData> resultList = parceResponse(response,
								countryName);
						saveData(resultList);
						LOGGER.info(name + " saved.");
					}
				}
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
	}

	private static void saveData(List<CompanyData> data) {
		if (data.size() > 0) {
			try (PrintWriter writer = new PrintWriter(new BufferedWriter(
					new FileWriter("result_yandex.csv", true)))) {
				for (CompanyData companyData : data) {
					writer.write(companyData.toString()
							+ System.lineSeparator());
				}
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}

		}
	}
}