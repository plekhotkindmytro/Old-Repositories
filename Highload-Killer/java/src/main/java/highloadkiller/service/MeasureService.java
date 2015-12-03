package highloadkiller.service;

import java.sql.SQLException;

public interface MeasureService {

	public long measureTime(String sqlQuery, String dbName, int iterations)
			throws SQLException;
}
