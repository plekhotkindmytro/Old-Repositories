package org.gradle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class SearchGithub {

	private static final int TIMEOUT = 6027;

	private WebDriver driver;

	public SearchGithub() throws IOException {
		driver = new FirefoxDriver();
	}

	public int getUsageCount(String objectName) throws InterruptedException {

		driver.get("https://github.com/search?l=javascript&q=\"" + objectName
				+ "\"&type=Code&utf8=✓");
		Thread.sleep(TIMEOUT);
		List<WebElement> numbers = driver
				.findElements(By
						.xpath("//nav[@class='menu']/a[@class='menu-item selected']/span[@class='counter']"));

		int usageCount = numbers.size() == 1 ? new Integer(numbers.get(0)
				.getText().replace(",", "")) : 0;

		return usageCount;
	}

	public static void main(String[] args) throws URISyntaxException,
			IOException, InterruptedException {
		SearchGithub searchGithub = new SearchGithub();

		ObjectsReader reader = new ObjectsReader();
		List<String> nameList = reader.readObjectNames();

		for (String name : nameList) {
			int count = searchGithub.getUsageCount(name);
			System.out.println("(" + name + ", " + count + "),");
		}
		searchGithub.driver.quit();
	}
}
