package chartgenerator.inputs;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

public class TLDWebsiteInputs {
	public final int totalTldWebsitesNumber;
	public final int totalArticlesNumber;

	public final int minArticleCount;
	public final int maxArticleCount;

	public final int minImageCount;
	public final int maxImageCount;

	public final int minVideoCount;
	public final int maxVideoCount;

	public final int minAudioCount;
	public final int maxAudioCount;

	public final Map<String, Integer> platformFrequencies;
	public final Map<String, Integer> tldFrequencies;

	public final LocalDate startDate;
	public final LocalDate endDate;

	public final List<KeywordGroupInputs> keywordGroupInputsList;

	public TLDWebsiteInputs(final int totalTldWebsitesNumber,
			final int totalArticlesNumber, final int minArticleCount,
			final int maxArticleCount, final int minImageCount,
			final int maxImageCount, final int minVideoCount,
			final int maxVideoCount, final int minAudioCount,
			final int maxAudioCount, final Map<String, Integer> tldFrequencies,
			final Map<String, Integer> platformFrequencies,
			final LocalDate startDate, final LocalDate endDate,
			final List<KeywordGroupInputs> keywordGroupInputsList) {

		if (tldFrequencies.size() == 0) {
			throw new IllegalArgumentException(
					"Please add at least one TLD to use! Frequency of single TLD has to be equal to 100.");
		}

		if (platformFrequencies.size() == 0) {
			throw new IllegalArgumentException(
					"Please add at least one platform to use! Frequency of single platform site has to be equal to 100.");
		}

		this.minArticleCount = minArticleCount;
		this.maxArticleCount = maxArticleCount;

		this.minImageCount = minImageCount;
		this.maxImageCount = maxImageCount;

		this.minVideoCount = minVideoCount;
		this.maxVideoCount = maxVideoCount;

		this.minAudioCount = minAudioCount;
		this.maxAudioCount = maxAudioCount;

		this.totalTldWebsitesNumber = totalTldWebsitesNumber;
		this.totalArticlesNumber = totalArticlesNumber;

		this.tldFrequencies = tldFrequencies;
		this.platformFrequencies = platformFrequencies;

		this.startDate = startDate;
		this.endDate = endDate;

		this.keywordGroupInputsList = keywordGroupInputsList;
	}
}
