package com.trashcssview;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSSParser {

	public static final String SELECTOR_BODY_REGEXP = "\\s*(\\{[^\\{\\}]*\\})\\s*";
	public static final String COMMENT_REGEXP = "/\\*[^\\*]*\\*+([^/\\*][^\\*]*\\*+)*/";
	private static final Pattern AT_MEDIA_PATTERN = Pattern
			.compile(
					"@media([a-zA-Z, ]+)\\{((([^\\{\\}]*)\\{([^\\{\\}]*)\\})*)(\\s*)\\}",
					Pattern.CASE_INSENSITIVE);

	public static List<String> parse(String cssContent) throws IOException {
		// replace comments
		cssContent = cssContent.replaceAll(CSSParser.COMMENT_REGEXP, "");
		cssContent = replaceMediaBlocks(cssContent);
		final String[] styles = cssContent.split(SELECTOR_BODY_REGEXP);
		return Arrays.asList(styles);
	}

	private static String replaceMediaBlocks(String cssContent) {
		Matcher m = AT_MEDIA_PATTERN.matcher(cssContent);
		while (m.find()) {
			String mediaBlock = m.group();
			cssContent = cssContent.replace(mediaBlock, "");
		}
		return cssContent;
	}
}