package org.gradle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class OracleDocsDataLoader {

	private static final String BASE_URL = "http://selenium.googlecode.com/git/docs/api/java/";

	private static final String OVERVIEW = "overview-summary.html";
	private static final int TIMEOUT = 6027;

	private static final boolean DATA_LOADED = true;

	public static void main(String[] args) throws InterruptedException,
			SQLException {
		if (!DATA_LOADED) {
			loadDataFromOracleDocs();
		}
		System.out.println("Load data from github.");
		loadDataFromGithub();

	}

	private static void loadDataFromGithub() throws InterruptedException,
			SQLException {
		WebDriver driver = new FirefoxDriver();
		Connection connection = DBConnector.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("update selenium set count=? where name=?");
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("select name, count from selenium");

		while (resultSet.next()) {
			String objectName = resultSet.getString(1);
			int count = resultSet.getInt(2);

			if (count == 0) {
				driver.get("https://github.com/search?q=%22import+"
						+ objectName + "%22&type=Code&ref=searchresults");
				Thread.sleep(TIMEOUT);
				List<WebElement> numbers = driver
						.findElements(By
								.xpath("//div[@class='menu-container']/ul[@class='menu']/li[2]/a[@class='selected']/span[@class='counter']"));

				int number = numbers.size() == 1 ? new Integer(numbers.get(0)
						.getText().replace(",", "")) : 0;

				preparedStatement.setInt(1, number);
				preparedStatement.setString(2, objectName);
				int updatedRowsCount = preparedStatement.executeUpdate();
				if (updatedRowsCount != 1) {
					throw new SQLException("Updated " + updatedRowsCount
							+ " rows. Must be 1 row updated. Object name: "
							+ objectName + ". Count: " + number);
				}
				System.out.println(objectName + " updated." + " Count: " + number);
			}
		}
		
		preparedStatement.close();
		statement.close();

		// Close the browser
		driver.quit();
	}

	private static void loadDataFromOracleDocs() throws SQLException,
			InterruptedException {
		WebDriver driver = new FirefoxDriver();

		Connection connection = DBConnector.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("insert into selenium (name, count) values(?,?)");
		driver.get(BASE_URL + OVERVIEW);

		Thread.sleep(TIMEOUT);

		List<WebElement> packages = driver
				.findElements(By
						.xpath("//table[@class='overviewSummary']/tbody[2]/tr/td[@class='colFirst']/a"));

		List<String> packageNameList = new ArrayList<String>();
		for (WebElement packageWebElement : packages) {
			String packageName = packageWebElement.getText();
			packageNameList.add(packageName);
		}

		for (String packageName : packageNameList) {
			driver.get(BASE_URL + packageName.replace(".", "/")
					+ "/package-summary.html");

			List<WebElement> objectList = driver
					.findElements(By
							.xpath("//ul[@class='blockList']/li[@class='blockList']/table[@class='packageSummary']/tbody[2]/tr/td/a"));

			for (WebElement objectWebElement : objectList) {
				String objectName = objectWebElement.getText();
				preparedStatement.setString(1, packageName + "." + objectName);
				preparedStatement.setInt(2, 0);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
		preparedStatement.close();

		// Close the browser
		driver.quit();
	}
}