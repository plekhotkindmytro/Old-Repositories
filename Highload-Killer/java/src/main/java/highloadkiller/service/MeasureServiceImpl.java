package highloadkiller.service;

import highloadkiller.dao.MeasureDao;
import highloadkiller.dao.MeasureDaoImpl;

import java.sql.SQLException;

public class MeasureServiceImpl implements MeasureService {

	private MeasureDao measureDao;

	public MeasureServiceImpl() {
		measureDao = new MeasureDaoImpl();
	}

	public long measureTime(String sqlQuery, String dbName, int iterations)
			throws SQLException {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < iterations; i++) {
			measureDao.executeQuery(sqlQuery, dbName);
		}

		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		return duration;
	}
}
