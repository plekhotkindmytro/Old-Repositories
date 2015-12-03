package org.gradle.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DbUtil {

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

	public static void createDatabaseIfNotExist(final String dbName)
			throws SQLException {
		final Connection connection = DbConnector.getConnection();
		final ResultSet resultSet = connection.getMetaData().getCatalogs();

		boolean exist = false;
		while (resultSet.next()) {
			final String databaseName = resultSet.getString(1);
			if (databaseName.equals(dbName)) {
				exist = true;
				break;
			}
		}
		resultSet.close();
		connection.close();

		if (!exist) {
			createDatabase(dbName);
		}
	}

	private static void createDatabase(final String name) throws SQLException {
		final Connection connection = DbConnector.getConnection();

		Statement createStatement = connection.createStatement();
		createStatement.executeUpdate("CREATE DATABASE " + name);
		createStatement.close();

		DbConnector.closeConnection(connection);
	}
}
