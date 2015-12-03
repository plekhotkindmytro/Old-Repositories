package org.gradle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Deprecated
public class DBConnector {

	private static Connection connection;

	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cocos2d", "root", "root");
		}
		return connection;
	}
}
