package chartgenerator.inputs;

import java.util.List;

import org.joda.time.LocalDate;

public class BlogCommentInputs {
	public final int blogCommentsNumber;
	public final LocalDate startDate;
	public final LocalDate endDate;

	public final List<KeywordGroupInputs> keywordGroupInputsList;

	public BlogCommentInputs(final int blogCommentsNumber,

	final LocalDate startDate, final LocalDate endDate,
			final List<KeywordGroupInputs> keywordGroupInputsList) {

		this.blogCommentsNumber = blogCommentsNumber;
		this.startDate = startDate;
		this.endDate = endDate;

		this.keywordGroupInputsList = keywordGroupInputsList;
	}
}
