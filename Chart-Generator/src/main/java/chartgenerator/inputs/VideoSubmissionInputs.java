package chartgenerator.inputs;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

public class VideoSubmissionInputs {
	public final int videoSumbmissionsNumber;

	public final Map<String, Integer> siteFrequencies;
	public final LocalDate startDate;
	public final LocalDate endDate;

	public final List<KeywordGroupInputs> keywordGroupInputsList;

	public VideoSubmissionInputs(final int videoSumbmissionsNumber,
			final Map<String, Integer> siteFrequencies,
			final LocalDate startDate, final LocalDate endDate,
			final List<KeywordGroupInputs> keywordGroupInputsList) {

		if (siteFrequencies.size() == 0) {
			throw new IllegalArgumentException(
					"Please add at least one video submission site to use! Frequency of single video submission site has to be equal to 100.");
		}

		this.videoSumbmissionsNumber = videoSumbmissionsNumber;
		this.siteFrequencies = siteFrequencies;
		this.startDate = startDate;
		this.endDate = endDate;

		this.keywordGroupInputsList = keywordGroupInputsList;
	}
}
