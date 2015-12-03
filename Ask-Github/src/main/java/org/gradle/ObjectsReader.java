package org.gradle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ObjectsReader {

	List<String> readObjectNames() throws URISyntaxException, IOException {
		List<String> nameList = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new FileReader(new File(
				ObjectsReader.class.getClassLoader().getResource("objects.csv")
						.toURI())));

		String line;
		while ((line = reader.readLine()) != null) {
			if (!line.isEmpty()) {
				nameList.add(line);
			}
		}

		reader.close();

		return nameList;
	}

	public static void main(String... args) throws URISyntaxException,
			IOException {
		ObjectsReader reader = new ObjectsReader();
		List<String> objectNames = reader.readObjectNames();
		for (String name : objectNames) {
			System.out.println(name);
		}
	}
}
