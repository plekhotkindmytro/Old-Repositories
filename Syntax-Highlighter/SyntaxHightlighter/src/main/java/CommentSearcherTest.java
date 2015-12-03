import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CommentSearcherTest {

	public static void main(String[] args) {
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get("testdata.txt"), Charset.defaultCharset())) {

			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[2048];
			while (reader.read(buffer) != -1) {
				builder.append(buffer);
			}
			String text = builder.toString();
			CommentSearcher searcher = new CommentSearcher(text);
			List<String> commentList = searcher.getComments();
			for (String comment : commentList) {
				text = text.replace(comment, "<comment>" + comment
						+ "</comment>");
			}

			List<String> quoteList = searcher.getQuotes();
			for (String quote : quoteList) {
				text = text.replace(quote, "<quote>" + quote + "</quote>");
			}

			System.out.println(text);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
