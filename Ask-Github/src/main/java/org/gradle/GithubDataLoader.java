package org.gradle;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GithubDataLoader {

	// xpath for docs:
	// //ul[@class='blockList']/li[@class='blockList']/table[@class='packageSummary']/tbody[2]/tr/td/a
	private static List<String> objects = new ArrayList<String>();

	public static final String PACKAGE = "java.security";

	public static void main(String[] args) throws InterruptedException,
			IOException {
		loadDataFromGithub();
	}

	

	private static void loadDataFromGithub() throws InterruptedException,
			IOException {
		loadObjects();

		WebDriver driver = new FirefoxDriver();
		for (int j = 0; j < objects.size(); j++) {
			driver.get("https://github.com/search?q=%22import+" + PACKAGE + "."
					+ objects.get(j) + "%22&type=Code&ref=searchresults");
			Thread.sleep(6021);
			List<WebElement> numbers = driver
					.findElements(By
							.xpath("//div[@class='menu-container']/ul[@class='menu']/li[2]/a[@class='selected']/span[@class='counter']"));

			int number = numbers.size() == 1 ? new Integer(numbers.get(0)
					.getText().replace(",", "")) : 0;
			String objectName = PACKAGE + "." + objects.get(j);
			// save to db.
		}

		// Close the browser
		driver.quit();
	}

	private static void loadObjects() throws IOException {

		Path path = Paths.get("data/data.csv");
		objects = Files.readAllLines(path, Charset.defaultCharset());

	}
}
