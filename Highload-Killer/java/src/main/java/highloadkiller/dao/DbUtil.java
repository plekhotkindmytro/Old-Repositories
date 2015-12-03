package highloadkiller.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.init.ScriptUtils;

final class DbUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbUtil.class);

	private DbUtil() {
		throw new UnsupportedOperationException();
	}

	public static void replaceDatabaseIfExist(final String dbName)
			throws SQLException {
		dropDatabaseIfExist(dbName);
		createDatabase(dbName);
	}

	private static void dropDatabaseIfExist(final String dbName)
			throws SQLException {
		final Connection connection = DbConnector.getConnection();
		final ResultSet resultSet = connection.getMetaData().getCatalogs();

		while (resultSet.next()) {
			final String databaseName = resultSet.getString(1);
			if (databaseName.equals(dbName)) {
				final Statement dropStatement = connection.createStatement();
				dropStatement.executeUpdate("DROP DATABASE " + dbName);
				dropStatement.close();
				break;
			}
		}
		resultSet.close();
		connection.close();
	}

	private static void createDatabase(final String name) throws SQLException {
		final Connection connection = DbConnector.getConnection();

		Statement createStatement = connection.createStatement();
		createStatement.executeUpdate("CREATE DATABASE " + name);
		createStatement.close();

		connection.setCatalog(name);
		DbConnector.closeConnection(connection);
	}

	public static void executeScript(final String databaseName,
			final String scriptName) throws SQLException, IOException {
		final Connection connection = DbConnector.getConnection(databaseName);
		final List<String> statements = getStatementsFromScript(scriptName);
		for (String statement : statements) {
			if (statement.startsWith("CREATE") || statement.startsWith("ALTER")) {
				final Statement stmt = connection.createStatement();
				stmt.executeUpdate(statement);
				stmt.close();
			}
		}
		DbConnector.closeConnection(connection);
	}

	private static List<String> getStatementsFromScript(final String scriptName)
			throws IOException {
		final List<String> statements = new ArrayList<String>();
		final String sriptBody = readSqlScript(scriptName);
		ScriptUtils.splitSqlScript(sriptBody, ';', statements);
		return statements;
	}

	private static String readSqlScript(String scriptName) throws IOException {
		final BufferedReader bufferedReader = new BufferedReader(
				new FileReader(new File(scriptName)));

		final StringBuilder scriptBuilder = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			scriptBuilder.append(line);
			scriptBuilder.append(System.lineSeparator());
		}
		bufferedReader.close();

		return scriptBuilder.toString();
	}

	public static void exportDatabase(String dbName) {

		Runtime rt = Runtime.getRuntime();

		PrintStream ps;

		try {
			File file = new File(dbName + ".sql");
			Process child = rt
					.exec(DbConnector.getDumpCommand() + " " + dbName);
			ps = new PrintStream(file);
			InputStream in = child.getInputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				ps.write(ch);
			}

			BufferedReader err = new BufferedReader(new InputStreamReader(
					child.getErrorStream()));
			StringBuilder errorBuilder = new StringBuilder();
			String line;
			while ((line = err.readLine()) != null) {
				errorBuilder.append(line);
			}
			if (errorBuilder.length() != 0) {
				LOGGER.error(errorBuilder.toString());
			}
		} catch (Exception e) {
			LOGGER.error("Error while generating database dump", e);
		}
	}

}
