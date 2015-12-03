package com.dmytroplekhotkin.crawler.web.service;

import com.dmytroplekhotkin.crawler.web.client.Connector;

public class GisApiService {

	public String getProjectList(String key, String version) {
		return getProjectList(key, version, OutputFormat.JSON);
	}

	public String getProjectList(String key, String version, OutputFormat format) {
		final String baseUrlString = "http://catalog.api.2gis.ru/project/list?key=rudcgu3317&version=1.3";

//		final String projectListUrl = String.format(
//				"%s?key=%s&version=%s&format=%s", baseUrlString, key, version,
//				format);

		String response = Connector.get(baseUrlString);
		return response;
	}

	public String getFirmList(String what,
			String where) {
		final String baseUrlString = "http://catalog.api.2gis.ru/search?key=rudcgu3317&version=1.3&pagesize=50";

		final String firmListUrl = String.format(
				"%s&what=%s&where=%s",baseUrlString, what, where);

		String response = Connector.get(firmListUrl);
		return response;
	}

	public String getFirmProfile(String id, String hash) {
		final String baseUrlString = "http://catalog.api.2gis.ru/profile?key=rudcgu3317&version=1.3";

		final String firmProfileUrl = String.format(
				"%s&id=%s&hash=%s",baseUrlString, id, hash);

		String response = Connector.get(firmProfileUrl);
		return response;
		
	}

}
