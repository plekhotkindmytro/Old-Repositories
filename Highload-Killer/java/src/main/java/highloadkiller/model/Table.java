package highloadkiller.model;

import java.util.Collections;
import java.util.List;

public final class Table {
	public final String name;
	public final List<ForeignKey> foreignKeys;
	public final List<Column> columns;
	private boolean filled;

	public Table(final String name, final List<Column> columns,
			final List<ForeignKey> foreignKeys) {
		this.name = name;
		this.foreignKeys = Collections.unmodifiableList(foreignKeys);
		this.columns = Collections.unmodifiableList(columns);

	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

}
