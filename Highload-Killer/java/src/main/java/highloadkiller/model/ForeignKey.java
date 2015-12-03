package highloadkiller.model;

public final class ForeignKey {

	public final String pkTableName;
	public final String pkColumnName;
	public final String fkColumnName;

	public ForeignKey(final String pkTableName, final String pkColumnName,
			final String fkColumnName) {
		this.pkTableName = pkTableName;
		this.pkColumnName = pkColumnName;
		this.fkColumnName = fkColumnName;
	}

}
