package highloadkiller.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DbConnector {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DbConnector.class);

	private static Properties prop;

	private static String driverClass;
	private static String urlString;
	private static String userName;
	private static String password;
	private static String dumpCommand;

	static {
		try {
			setProperties("db.properties");
		} catch (FileNotFoundException e) {
			LOGGER.error("Cannot find db.properties file.", e);
		} catch (InvalidPropertiesFormatException e) {
			LOGGER.error("db.properties format is invalid.", e);
		} catch (IOException e) {
			LOGGER.error("Error while reading db.properties file.", e);
		} catch (URISyntaxException e) {
			LOGGER.error("Bad file db.properties URI.", e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Cannot load driver class.", e);
		}
	}

	private static void setProperties(String fileName)
			throws FileNotFoundException, IOException,
			InvalidPropertiesFormatException, URISyntaxException,
			ClassNotFoundException {
		prop = new Properties();
		final FileInputStream fileInputStream = new FileInputStream(new File(
				DbConnector.class.getClassLoader().getResource(fileName)
						.toURI()));
		prop.load(fileInputStream);

		driverClass = prop.getProperty("db.driver_class");
		Class.forName("com.mysql.jdbc.Driver");
		LOGGER.debug("db.driver_class={}", driverClass);

		urlString = prop.getProperty("db.url");
		LOGGER.debug("db.url={}", urlString);

		userName = prop.getProperty("db.username");
		LOGGER.debug("db.username={}", userName);

		password = prop.getProperty("db.password");
		LOGGER.debug("db.password={}", password);

		dumpCommand = prop.getProperty("db.dump.command");
		LOGGER.debug("db.dump.command={}", dumpCommand);

	}

	public static Connection getConnection() throws SQLException {
		final Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		final Connection connection = DriverManager.getConnection(urlString,
				connectionProps);

		return connection;
	}

	public static Connection getConnection(final String database)
			throws SQLException {
		final Connection connection = getConnection();
		connection.setCatalog(database);

		LOGGER.debug("Connected to database: {}", database);
		return connection;
	}

	public static void closeConnection(final Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {
			printSQLException(sqle);
		}
	}

	private static void printSQLException(SQLException ex) {
		while (ex != null) {
			LOGGER.error("SQLState: {}", ex.getSQLState());
			LOGGER.error("Error Code: {}", ex.getErrorCode());
			LOGGER.error("Message: {}", ex.getMessage());
			Throwable t = ex.getCause();
			while (t != null) {
				LOGGER.error("Cause: {}", t);
				t = t.getCause();
			}
			ex = ex.getNextException();
		}
	}

	public static String getDumpCommand() {
		return dumpCommand;
	}
}