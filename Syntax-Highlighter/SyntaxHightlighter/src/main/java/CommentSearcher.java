import java.util.ArrayList;
import java.util.List;

// TODO: separate javadoc comments
public class CommentSearcher {

	private static char QUOTATION_MARK = '"';
	private static final char SLASH = '/';
	private static final char BACKSLASH = '\\';
	private static final char ASTERISK = '*';

	// occlusive
	private boolean inQuote;
	private boolean maybeComment;
	private boolean inOneLineComment;
	private boolean inMultilineComment;
	private boolean maybeOcclusiveCommentString;

	private boolean escaping;
	private boolean locked;

	private StringBuilder currentQuote;
	private StringBuilder currentComment;

	private List<String> commentList;
	private List<String> quoteList;

	public CommentSearcher(String text) {
		currentQuote = new StringBuilder();
		currentComment = new StringBuilder();
		commentList = new ArrayList<>();
		quoteList = new ArrayList<>();

		for (char symbol : text.toCharArray()) {

			if (!locked) {

				tryToLock(symbol);

			} else {
				tryToUnlock(symbol);
			}
		}

	}

	private void tryToLock(char symbol) {
		tryLockQuote(symbol);

		if (!locked) {
			tryToLockComment(symbol);
		}

	}

	private void tryToLockComment(char symbol) {
		if (maybeComment) {
			maybeComment = false;
			if (symbol == SLASH) {
				inOneLineComment = true;
				locked = true;
			} else if (symbol == ASTERISK) {
				inMultilineComment = true;
				locked = true;
			}

			if (locked) {
				currentComment.append(SLASH);
				currentComment.append(symbol);
			}
		} else if (symbol == SLASH) {
			maybeComment = true;
		}
	}

	private void tryLockQuote(char symbol) {
		if (symbol == QUOTATION_MARK) {
			inQuote = true;
			locked = true;
			currentQuote.append(symbol);
		}
	}

	private void tryToUnlock(char symbol) {

		if (inQuote) {
			tryToCloseQuote(symbol);

		} else if (inOneLineComment) {
			tryToCloseOneLineComment(symbol);
		} else if (inMultilineComment) {
			tryToCloseMultiLineComment(symbol);

		}

	}

	private void tryToCloseMultiLineComment(char symbol) {
		currentComment.append(symbol);
		if (maybeOcclusiveCommentString) {
			maybeOcclusiveCommentString = false;
			if (symbol == SLASH) {
				locked = false;
				inMultilineComment = false;
				saveComment();

			}
		}

		if (symbol == ASTERISK) {
			maybeOcclusiveCommentString = true;
		}

	}

	private void saveComment() {
		commentList.add(currentComment.toString());
		currentComment = new StringBuilder();
	}

	// TODO: add support for Windows (\r\n)
	private void tryToCloseOneLineComment(char symbol) {
		if (symbol == '\n') {
			locked = false;
			inOneLineComment = false;
			saveComment();
		} else {
			currentComment.append(symbol);
		}

	}

	private void tryToCloseQuote(char symbol) {
		currentQuote.append(symbol);
		if (escaping) {
			escaping = false;
		} else {
			if (symbol == BACKSLASH) {
				escaping = true;
			} else if (symbol == QUOTATION_MARK) {
				locked = false;
				inQuote = false;
				saveQuote();
			}
		}

	}

	private void saveQuote() {
		quoteList.add(currentQuote.toString());
		currentQuote = new StringBuilder();

	}

	public List<String> getComments() {
		return commentList;
	}

	public List<String> getQuotes() {
		return quoteList;
	}

}
