package highloadkiller.dao;

import highloadkiller.model.ForeignKey;
import highloadkiller.model.Table;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Dao {

	public void constructDatabase(String dbName, String scriptName)
			throws SQLException, IOException;

	public List<Table> getTableList(String dbName) throws SQLException;

	public List<Object> getForeignKeyValues(String dbName, ForeignKey foreignKey)
			throws SQLException;

	public void executeInsert(String dbName, String string) throws SQLException;
}
