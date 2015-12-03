import java.util.ArrayList;
import java.util.List;

public class OneLineComment {

	
	public static final String BEGIN = "//";
	public static final String END = System.lineSeparator();

	
	public static final String QUOTES = "\"";
	
	public static final String MULTILINE_COMMENT_BEGIN = "/*";
	public static final String MULTILINE_COMMENT_END = "*/";
	
	public static List<Integer> getLineNumbers(String text) {
		List<Integer> lineNumbers = new ArrayList<Integer>();
		
		boolean isComment = false;
		int index = 0;
		
		String commentString = "/";
		
		
		
		return lineNumbers;
	}
}
