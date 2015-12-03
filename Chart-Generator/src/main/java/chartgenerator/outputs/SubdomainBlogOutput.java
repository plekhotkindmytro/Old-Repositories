package chartgenerator.outputs;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import chartgenerator.model.Status;

public final class SubdomainBlogOutput implements Output {

	private final LocalDate creationDate;
	private final String id;
	private final String site;
	private final List<ArticleOutput> articleOutputList;

	public SubdomainBlogOutput(final LocalDate creationDate, final String id,
			final String site, final int articleCount,
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
		this.site = site;
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

		final StringBuilder blogStringBuilder = new StringBuilder();
		if (status.equals(Status.Creation)
				|| (status.equals(Status.Editing) && articleCountForDate != 0)) {
			blogStringBuilder
					.append("ID,Subdomain Blog Site,Status,Article Amount:");
			blogStringBuilder.append(System.lineSeparator());
			blogStringBuilder.append(id).append(",");
			blogStringBuilder.append(site).append(",");
			blogStringBuilder.append(status).append(",");
			blogStringBuilder.append(articleCountForDate);
			blogStringBuilder.append(System.lineSeparator());
			blogStringBuilder.append(articleStringsBuilder.toString());
		}

		return blogStringBuilder.toString();
	}
}
