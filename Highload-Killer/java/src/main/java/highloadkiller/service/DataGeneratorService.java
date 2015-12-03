package highloadkiller.service;

import java.io.IOException;
import java.sql.SQLException;

public interface DataGeneratorService {

	public void generateDBForTesting(String dbName, String scriptName)
			throws SQLException, IOException;

}
