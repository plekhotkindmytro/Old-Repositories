class Tag {
	private String name;
	private int count;

	public Tag(String tagName, int tagCount) {
		this.name = tagName;
		this.count = tagCount;
	}

	public String getName() {
		return name;
	}

	public void setName(final String tagName) {
		this.name = tagName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(final int tagCount) {
		this.count = tagCount;
	}
	
	public void addCount(final int tagCount) {
		this.count += tagCount;
	}

	public static void main(String [] args) {
		Tag tag = new Tag("script", 5);
		tag.addCount(5);
		if (tag.getCount() != 10) {
			throw new RuntimeException("tag count = "+tag.getCount() + ". Must be 10.");
		}

		tag.setCount(8);
		if (tag.getCount() != 8) {
			throw new RuntimeException("tag count = "+tag.getCount() + ". Must be 8.");
		}

	}
}
