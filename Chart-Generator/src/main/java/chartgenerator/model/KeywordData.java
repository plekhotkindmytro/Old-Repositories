package chartgenerator.model;

import java.util.List;

public final class KeywordData {

	private final int frequency;
	
	private final List<String> keywordList;

	public KeywordData(final int frequency,
			final List<String> keywordList) {
		this.frequency = frequency;
		this.keywordList = keywordList;
	}

	public int getFrequency() {
		return frequency;
	}

	public List<String> getKeywordList() {
		return keywordList;
	}
}
