package chartgenerator.inputs;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public final class SubdomainBlogInputs {

	public final int totalBlogsNumber;
	public final int totalArticlesNumber;

	public final int minArticleCount;
	public final int maxArticleCount;

	public final int minImageCount;
	public final int maxImageCount;

	public final int minVideoCount;
	public final int maxVideoCount;

	public final int minAudioCount;
	public final int maxAudioCount;

	public final Map<String, Integer> blogFrequencies;
	public final LocalDate startDate;
	public final LocalDate endDate;

	public final List<KeywordGroupInputs> keywordGroupInputsList;

	public SubdomainBlogInputs(final int totalBlogsNumber,
			final int totalArticlesNumber, final int minArticleCount,
			final int maxArticleCount, final int minImageCount,
			final int maxImageCount, final int minVideoCount,
			final int maxVideoCount, final int minAudioCount,
			final int maxAudioCount,
			final Map<String, Integer> blogFrequencies,
			final LocalDate startDate, final LocalDate endDate,
			final List<KeywordGroupInputs> keywordGroupInputsList) {

		if (blogFrequencies.size() == 0) {
			throw new IllegalArgumentException(
					"Please add at least one blog site to use! Frequency of single blog site has to be equal to 100.");
		}
		this.minArticleCount = minArticleCount;
		this.maxArticleCount = maxArticleCount;

		this.minImageCount = minImageCount;
		this.maxImageCount = maxImageCount;

		this.minVideoCount = minVideoCount;
		this.maxVideoCount = maxVideoCount;

		this.minAudioCount = minAudioCount;
		this.maxAudioCount = maxAudioCount;

		this.totalBlogsNumber = totalBlogsNumber;
		this.totalArticlesNumber = totalArticlesNumber;

		this.blogFrequencies = blogFrequencies;
		this.startDate = startDate;
		this.endDate = endDate;

		this.keywordGroupInputsList = keywordGroupInputsList;
	}
}
