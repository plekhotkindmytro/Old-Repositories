package chartgenerator.model;

public enum LinkType {
	HOME("Home URLs"), FOCUS("Focus URLs"), SITEWISE("Sitewise URLs"), RANDOM(
			"Random URLs");

	private final String name;

	private LinkType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
