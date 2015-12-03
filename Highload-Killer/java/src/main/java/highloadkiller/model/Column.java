package highloadkiller.model;

public final class Column {
	public final String name;
	public final int type;
	public final int size;

	public Column(final String name, final int type, final int size) {
		this.name = name;
		this.type = type;
		this.size = size;
	}

}
