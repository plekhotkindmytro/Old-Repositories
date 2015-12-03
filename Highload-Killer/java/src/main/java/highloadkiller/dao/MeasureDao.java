package highloadkiller.dao;

import java.sql.SQLException;

public interface MeasureDao {
	public void executeQuery(String query, String dbName) throws SQLException;
}
