package highloadkiller.service;

import highloadkiller.dao.Dao;
import highloadkiller.dao.DaoImpl;
import highloadkiller.model.Column;
import highloadkiller.model.ForeignKey;
import highloadkiller.model.Table;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class DataGeneratorServiceImpl implements DataGeneratorService {

	private static final int RANDOM_DATA_COUNT = 100;

	private Dao dao;

	public DataGeneratorServiceImpl() {
		dao = new DaoImpl();
	}

	public void generateDBForTesting(String dbName, String scriptName)
			throws SQLException, IOException {
		dao.constructDatabase(dbName, scriptName);
		fillDB(dbName);
	}

	private void fillDB(String dbName) throws SQLException {
		final List<Table> tableList = dao.getTableList(dbName);
		for (Table table : tableList) {
			fillTable(dbName, table, tableList);
		}
	}

	private void fillTable(String dbName, final Table table,
			final List<Table> tableList) throws SQLException {
		if (!table.isFilled()) {
			for (ForeignKey foreignKey : table.foreignKeys) {
				final Table pkTable = findTableByName(foreignKey.pkTableName,
						tableList);
				if (!pkTable.isFilled()) {
					fillTable(dbName, pkTable, tableList);
				}
			}

			buildInsertQuery(dbName, table);
		}
	}

	private void buildInsertQuery(final String dbName, final Table table)
			throws SQLException {
		final Random random = new Random();
		final StringBuilder insertBuilder = new StringBuilder();

		insertBuilder.append("insert into ");
		insertBuilder.append(table.name);
		insertBuilder.append(" value");

		final List<Column> columns = table.columns;
		final List<ForeignKey> foreignKeys = table.foreignKeys;
		final Map<String, List<Object>> possibleValuesMap = new HashMap<String, List<Object>>();
		for (ForeignKey foreignKey : foreignKeys) {
			List<Object> foreignKeyValues = dao.getForeignKeyValues(dbName,
					foreignKey);
			possibleValuesMap.put(foreignKey.fkColumnName, foreignKeyValues);
		}

		for (int i = 0; i < RANDOM_DATA_COUNT; i++) {
			if (i > 0) {
				insertBuilder.append(",");
			}
			insertBuilder.append("(");
			for (int j = 0; j < columns.size(); j++) {
				if (j > 0) {
					insertBuilder.append(",");
				}

				boolean isForeignKey = false;
				for (ForeignKey foreignKey : foreignKeys) {
					if (foreignKey.fkColumnName.equals(columns.get(j).name)) {
						isForeignKey = true;
						break;
					}
				}

				Object value = null;
				if (!isForeignKey) {
					switch (columns.get(j).type) {
					case Types.CHAR:
					case Types.VARCHAR:
					case Types.LONGVARCHAR:
						// TODO: create random string
						value = "\"" + UUID.randomUUID().toString() + "\"";
						break;
					case Types.NUMERIC:
					case Types.DECIMAL:
						value = new BigDecimal(random.nextDouble());
						break;
					case Types.BIT:
						value = random.nextBoolean();
					case Types.TINYINT:
					case Types.SMALLINT:
						// byte
						value = Math.round(random.nextDouble()
								* Short.MAX_VALUE);
						break;
					case Types.INTEGER:
						value = Math.round(random.nextDouble()
								* Integer.MAX_VALUE);
						break;
					case Types.BIGINT:
						value = Math
								.round(random.nextDouble() * Long.MAX_VALUE);

					case Types.REAL:
						value = Math.round(random.nextDouble()
								* Float.MAX_VALUE);
					case Types.FLOAT:
					case Types.DOUBLE:
						value = Math.round(random.nextDouble()
								* Float.MAX_VALUE);
					case Types.BINARY:
						value = Math.round(random.nextDouble()
								* Integer.MAX_VALUE);
						// case Types.VARBINARY:
						// case Types.LONGVARBINARY:
						// // byte[]
						 //case Types.DATE:
							 //java.sql.Date
						// case Types.TIME:
						// // java.sql.Time
						// case Types.TIMESTAMP:
						// // java.sql.Timestamp
						// case Types.CLOB:
						// // Clob
						// case Types.BLOB:
						// // Blob
						// case Types.ARRAY:
						// // Array
					default:
						value = "";
						break;
					}

					String valueString = value.toString();
					int columnSize = columns.get(j).size;

					if (valueString.toCharArray().length > columnSize) {
						if (columnSize == 0) {
							columnSize = 1;
						}
						if (valueString.endsWith("\"")) {
							valueString = valueString.substring(0,
									columnSize - 1) + "\"";
						} else {
							valueString = valueString.substring(0, columnSize);
						}
					}

					value = valueString;
				} else {
					for (ForeignKey foreignKey : foreignKeys) {
						if (foreignKey.fkColumnName.equals(columns.get(j).name)) {
							List<Object> possibleValues = possibleValuesMap
									.get(foreignKey.fkColumnName);
							int valueIndex = (int) (random.nextDouble() * possibleValues
									.size());
							value = possibleValues.remove(valueIndex);

							break;
						}
					}
				}

				insertBuilder.append(value);
			}
			insertBuilder.append(")");

		}

		dao.executeInsert(dbName, insertBuilder.toString());
		table.setFilled(true);
	}

	private static Table findTableByName(String pkTableName,
			List<Table> tableList) {
		for (Table table : tableList) {
			if (table.name.equals(pkTableName)) {
				return table;
			}
		}
		return null;
	}

}
