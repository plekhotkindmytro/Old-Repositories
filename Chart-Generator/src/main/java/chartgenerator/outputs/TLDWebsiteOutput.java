package chartgenerator.outputs;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import chartgenerator.model.Status;

public final class TLDWebsiteOutput implements Output {

	private final LocalDate creationDate;
	private final String id;
	private final String platform;
	private final String tld;
	private final List<ArticleOutput> articleOutputList;

	public TLDWebsiteOutput(final LocalDate creationDate, final String id,
			final String platform, String tld, final int articleCount,
			final List<ArticleOutput> articleOutputList) {

		if (articleCount > 0 && articleOutputList.size() == 0) {
			throw new IllegalArgumentException(
					"Article list has to be not null if article count not greater than 0.");
		} else if (articleCount == 0 && articleOutputList.size() != 0) {
			throw new IllegalArgumentException(
					"Article list has to be null if article count equals 0.");
		}

		this.creationDate = creationDate;
		this.id = id;
		this.platform = platform;
		this.tld = tld;
		this.articleOutputList = articleOutputList;

	}

	@Override
	public String getCsvString(LocalDate date) {
		Status status = creationDate.isEqual(date) ? Status.Creation
				: Status.Editing;
		int articleCountForDate = 0;

		final StringBuilder articleStringsBuilder = new StringBuilder();
		for (ArticleOutput articleOutput : articleOutputList) {
			String articleCsvString = articleOutput.getCsvString(date);
			if (!articleCsvString.isEmpty()) {
				articleStringsBuilder.append(articleCsvString);
				articleCountForDate++;
			}
		}

		final StringBuilder csvStringBuilder = new StringBuilder();
		if (status.equals(Status.Creation)
				|| (status.equals(Status.Editing) && articleCountForDate != 0)) {
			csvStringBuilder.append("ID,Platform,TLD,Status,Article Amount:");
			csvStringBuilder.append(System.lineSeparator());
			csvStringBuilder.append(id).append(",");
			csvStringBuilder.append(platform).append(",");
			csvStringBuilder.append(tld).append(",");
			csvStringBuilder.append(status).append(",");
			csvStringBuilder.append(articleCountForDate);
			csvStringBuilder.append(System.lineSeparator());
			csvStringBuilder.append(articleStringsBuilder.toString());
		}

		return csvStringBuilder.toString();
	}
}
