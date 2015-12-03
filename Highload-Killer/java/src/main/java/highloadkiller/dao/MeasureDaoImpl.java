package highloadkiller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MeasureDaoImpl implements MeasureDao {
	public void executeQuery(String query, String dbName) throws SQLException {
		final Connection connection = DbConnector.getConnection(dbName);
		final Statement statement = connection.createStatement();
		statement.execute(query);
		DbConnector.closeConnection(connection);
	}
}
