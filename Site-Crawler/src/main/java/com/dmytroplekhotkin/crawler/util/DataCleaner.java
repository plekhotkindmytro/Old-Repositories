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
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONException;

import com.dmytroplekhotkin.crawler.PhoneLoader;

public class DataCleaner {

	public static void main(String[] args) throws JSONException {
		clean("result/full_result_yandex.csv");
	}

	private static void clean(String fileName) throws JSONException {
		final URL fileUrl = PhoneLoader.class.getClassLoader().getResource(
				fileName);
		Set<String> dataSet = new TreeSet<String>();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileUrl.toURI()))));
			while ((line = reader.readLine()) != null) {
				dataSet.add(line);
				
			}
			saveData(dataSet);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void saveData(Set<String> data) {

		try (PrintWriter writer = new PrintWriter(new BufferedWriter(
				new FileWriter("clean_result_yandex.csv", true)))) {
			for (String string : data) {
				writer.write(string + System.lineSeparator());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
