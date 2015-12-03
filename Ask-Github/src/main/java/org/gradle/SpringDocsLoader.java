package org.gradle;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.gradle.util.ApidocsProperties;
import org.gradle.util.DbConnector;
import org.gradle.util.DbUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SpringDocsLoader {
	private static final int TIMEOUT = 6027;

	public static void loadDocsToDb() throws SQLException, InterruptedException {
		WebDriver driver = new FirefoxDriver();

		
		DbUtil.createDatabaseIfNotExist(DbConnector.getDbName());
		Connection connection = DbConnector.getConnection();
		connection.setCatalog(DbConnector.getDbName());
		createTableIfNotExist(connection);

		PreparedStatement preparedStatement = connection
				.prepareStatement("insert into " + DbConnector.getTableName()
						+ " (name, count) values(?,?)");
		driver.get(ApidocsProperties.getEndpoint()
				+ ApidocsProperties.getOverviewPage());

		Thread.sleep(TIMEOUT);

		List<WebElement> packages = driver.findElements(By
				.xpath(ApidocsProperties.getXpathPackageNames()));

		List<String> packageNameList = new ArrayList<String>();
		for (WebElement packageWebElement : packages) {
			String packageName = packageWebElement.getText();
			packageNameList.add(packageName);
		}

		for (String packageName : packageNameList) {
			driver.get(ApidocsProperties.getEndpoint()
					+ packageName.replace(".", "/")
					+ "/"+ApidocsProperties.getPackagePage());

			List<WebElement> objectList = driver.findElements(By
					.xpath(ApidocsProperties.getXpathPackageContent()));

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

	private static void createTableIfNotExist(Connection connection)
			throws SQLException {
		DatabaseMetaData md = connection.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		boolean docsExist = false;
		while (rs.next()) {
			String tableName = rs.getString(3);
			if (tableName.equals(DbConnector.getTableName())) {
				docsExist = true;
			}
		}

		if (!docsExist) {
			Statement createTable = connection.createStatement();
			createTable.executeUpdate("create table "
					+ DbConnector.getTableName() + "(name varchar(255), count int(11))");
			createTable.close();
		}

	}

	private static void loadDataFromGithub() throws InterruptedException,
			SQLException {
		WebDriver driver = new FirefoxDriver();
		Connection connection = DbConnector.getConnection(DbConnector.getDbName());
		
		PreparedStatement preparedStatement = connection
				.prepareStatement("update " + DbConnector.getTableName()
						+ " set count=? where name=?");
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select name, count from "
				+ DbConnector.getTableName());

		while (resultSet.next()) {
			String objectName = resultSet.getString(1);
			int count = resultSet.getInt(2);

			if (count == 0) {
				

				int number = getUsageCount(driver, "import+" + objectName);

				preparedStatement.setInt(1, number);
				preparedStatement.setString(2, objectName);
				preparedStatement.executeUpdate();
				System.out.println(objectName + " updated." + " Count: "
						+ number);
				
			}
		}

		preparedStatement.close();
		statement.close();

		// Close the browser
		driver.quit();
	}

	public static int getUsageCount(WebDriver driver, String objectName)
			throws InterruptedException {

		driver.get("https://github.com/search?l=java&q=\"" + objectName
				+ "\"&type=Code&utf8=✓");
		Thread.sleep(TIMEOUT);
		List<WebElement> numbers = driver
				.findElements(By
						.xpath("//nav[@class='menu']/a[@class='menu-item selected']/span[@class='counter']"));

		int usageCount = numbers.size() == 1 ? new Integer(numbers.get(0)
				.getText().replace(",", "")) : 0;

		return usageCount;
	}

	private static final boolean DATA_LOADED = false;

	public static void main(String[] args) throws InterruptedException,
			SQLException {
		if (!DATA_LOADED) {
			loadDocsToDb();
		}
		System.out.println("Load data from github.");
		loadDataFromGithub();

	}

}
