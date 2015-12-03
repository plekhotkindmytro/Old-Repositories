package chartgenerator.inputs;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

public class ArticleSubmissionInputs {
	public final int articleSubmissionsNumber;

	public final Map<String, Integer> siteFrequencies;
	public final LocalDate startDate;
	public final LocalDate endDate;

	public final List<KeywordGroupInputs> keywordGroupInputsList;

	public ArticleSubmissionInputs(final int articleSubmissionsNumber,
			final Map<String, Integer> siteFrequencies,
			final LocalDate startDate, final LocalDate endDate,
			final List<KeywordGroupInputs> keywordGroupInputsList) {

		if (siteFrequencies.size() == 0) {
			throw new IllegalArgumentException(
					"Please add at least one article submission site to use! Frequency of single article submission site has to be equal to 100.");
		}

		this.articleSubmissionsNumber = articleSubmissionsNumber;
		this.siteFrequencies = siteFrequencies;
		this.startDate = startDate;
		this.endDate = endDate;

		this.keywordGroupInputsList = keywordGroupInputsList;
	}
}
