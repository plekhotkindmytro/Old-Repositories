package chartgenerator.outputs;

import java.util.List;

import org.joda.time.LocalDate;

import chartgenerator.model.Status;

public final class BookmarkSubmissionOutput implements Output {

	private final LocalDate creationDate;
	private final String id;
	private final String site;
	private final List<String> anchorList;
	private final List<String> linkList;

	public BookmarkSubmissionOutput(final LocalDate creationDate,
			final String id, final String site, List<String> anchorList,
			List<String> linkList) {

		this.creationDate = creationDate;
		this.id = id;
		this.site = site;

		this.anchorList = anchorList;
		this.linkList = linkList;

	}

	@Override
	public String getCsvString(LocalDate date) {
		Status status = creationDate.isEqual(date) ? Status.Creation
				: Status.Editing;
		int articleCountForDate = 0;

		final StringBuilder builder = new StringBuilder();
		if (status.equals(Status.Creation)
				|| (status.equals(Status.Editing) && articleCountForDate != 0)) {
			builder.append("ID,Submission Site,");

			for (int i = 1; i <= anchorList.size(); i++) {
				builder.append(",ANCHOR ").append(i);
			}
			for (int i = 1; i <= linkList.size(); i++) {
				builder.append(",LINK ").append(i);
			}

			builder.append(System.lineSeparator());
			builder.append(id).append(",");
			builder.append(site).append(",");

			builder.append(id).append(",");

			for (int i = 0; i < anchorList.size(); i++) {
				if (i < anchorList.size()) {
					builder.append(anchorList.get(i));
				}

				builder.append(",");
			}
			for (int i = 0; i < linkList.size(); i++) {
				if (i < linkList.size()) {
					builder.append(linkList.get(i));
				}

				if (i != linkList.size() - 1) {
					builder.append(",");
				}

			}
			builder.append(System.lineSeparator());
		}

		return builder.toString();
	}
}
