package highloadkiller.dao;

import highloadkiller.model.Column;
import highloadkiller.model.ForeignKey;
import highloadkiller.model.Table;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao {
	public void constructDatabase(String dbName, String scriptName)
			throws SQLException, IOException {
		DbUtil.replaceDatabaseIfExist(dbName);
		DbUtil.executeScript(dbName, scriptName);
	}

	@Override
	public List<Object> getForeignKeyValues(String dbName, ForeignKey foreignKey)
			throws SQLException {
		final Connection connection = DbConnector.getConnection(dbName);
		final Statement statement = connection.createStatement();

		final ResultSet resultSet = statement.executeQuery("select "
				+ foreignKey.pkColumnName + " from " + foreignKey.pkTableName);

		final List<Object> foreignKeyValues = new ArrayList<Object>();

		while (resultSet.next()) {
			Object value = resultSet.getObject(foreignKey.pkColumnName);
			final int type = resultSet.getMetaData().getColumnType(1);
			switch (type) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				// TODO: create random string
				value = "\"" + value + "\"";
				break;
			default:
				break;
			}
			foreignKeyValues.add(value);
		}

		DbConnector.closeConnection(connection);
		return foreignKeyValues;
	}

	@Override
	public void executeInsert(String dbName, String query) throws SQLException {
		final Connection connection = DbConnector.getConnection(dbName);
		final Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		DbConnector.closeConnection(connection);

	}

	public List<Table> getTableList(String dbName) throws SQLException {
		Connection connection = DbConnector.getConnection(dbName);
		DatabaseMetaData metadata = connection.getMetaData();

		List<Table> tableList = new ArrayList<Table>();

		ResultSet tables = metadata.getTables(null, null, "%", null);
		while (tables.next()) {
			String tableName = tables.getString(3);

			List<ForeignKey> foreignKeys = getForeinKeysByTableName(connection,
					tableName);
			List<Column> columnList = getColumnsByTableName(connection,
					tableName);

			Table table = new Table(tableName, columnList, foreignKeys);
			tableList.add(table);
		}

		DbConnector.closeConnection(connection);
		return tableList;

	}

	private List<Column> getColumnsByTableName(Connection connection,
			String tableName) throws SQLException {
		List<Column> columnList = new ArrayList<Column>();

		Statement statement = connection.createStatement();
		ResultSet columns = statement
				.executeQuery("select * from " + tableName);

		ResultSetMetaData columnsMetadata = columns.getMetaData();
		for (int i = 1; i <= columnsMetadata.getColumnCount(); i++) {
			int columnSize = columnsMetadata.getColumnDisplaySize(i);
			String columnName = columnsMetadata.getColumnName(i);
			int dataType = columnsMetadata.getColumnType(i);

			Column column = new Column(columnName, dataType, columnSize);
			columnList.add(column);

		}
		return columnList;
	}

	private List<ForeignKey> getForeinKeysByTableName(Connection connection,
			String tableName) throws SQLException {
		List<ForeignKey> fkList = new ArrayList<ForeignKey>();
		DatabaseMetaData metadata = connection.getMetaData();
		ResultSet importedKeys = metadata.getImportedKeys(
				connection.getCatalog(), null, tableName);
		while (importedKeys.next()) {

			final String pkTableName = importedKeys.getString("PKTABLE_NAME");
			final String pkColumnName = importedKeys.getString("PKCOLUMN_NAME");
			final String fkColumnName = importedKeys.getString("FKCOLUMN_NAME");

			final ForeignKey foreignKey = new ForeignKey(pkTableName,
					pkColumnName, fkColumnName);
			fkList.add(foreignKey);
		}

		return fkList;
	}
}
