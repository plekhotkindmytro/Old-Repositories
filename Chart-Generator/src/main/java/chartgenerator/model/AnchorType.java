package chartgenerator.model;

public enum AnchorType {

	E("EXACT KEYWORDS - E -"), EE("EXACT EXTENDED - EE -"), BN(
			"BRAND/SITE NAME - BN -"), N("NICHE - N -"), URL("URL - URL -"), G(
			"GENERIC - G -"), R("RANDOM - R -"), I("IMAGE - I -");
	private final String name;

	private AnchorType(String name) {
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
