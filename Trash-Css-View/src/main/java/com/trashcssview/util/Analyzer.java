package com.trashcssview.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.trashcssview.CSSParser;
import com.trashcssview.pseudo.Dynamic;

public class Analyzer {

	private static final int DEFAULT_SCAN_DEPTH = 0;

	private String host = null;
	private Map<String, List<String>> unusedSelectorsMap;
	private Map<String, Document> siteMap;

	private static int collectedPagesCount = 0;

	public Analyzer(final String pageUrl) throws IOException {
		this(new HashSet<String>(Arrays.asList(pageUrl)), ScanType.PARTIAL,
				DEFAULT_SCAN_DEPTH);
	}

	public Analyzer(final String pageUrl, final ScanType scanType)
			throws IOException {
		this(new HashSet<String>(Arrays.asList(pageUrl)), scanType);
	}

	public Analyzer(final Set<String> pageUrlSet, final ScanType scanType)
			throws IOException {
		this(pageUrlSet, scanType, DEFAULT_SCAN_DEPTH);
	}

	public Analyzer(final Set<String> pageUrlSet, final ScanType scanType,
			final int scanDepth) throws IOException {
		unusedSelectorsMap = new HashMap<String, List<String>>();
		siteMap = new HashMap<String, Document>();
		System.out.println("Collecting site pages. Please wait...");
		storeAnchorsForAnalyze(pageUrlSet, scanType, scanDepth);
		

	}

	private void storeAnchorsForAnalyze(Set<String> pageUrlSet,
			ScanType scanType, int depth) throws IOException {
		for (String urlString : pageUrlSet) {
			if (host == null) {
				final URL url = new URL(urlString);
				host = url.getHost();
			}
			if (!siteMap.containsKey(urlString)) {
				try {
					final Document doc = Jsoup.connect(urlString).get();
					siteMap.put(urlString, doc);
					System.out.print("\r");
					System.out.print(++collectedPagesCount );
					if (depth != 0 || scanType.equals(ScanType.FULL)) {

						Elements anchors = doc.select("a[href]");

						Set<String> anchorSet = new HashSet<String>();
						for (final Element a : anchors) {
							String href = a.attr("abs:href");
							boolean isSameHost = false;
							try {
								isSameHost = new URL(href).getHost().equals(
										(host));
							} catch (Exception e) {
								// TODO: write to log: bad URL.
							}
							if (isSameHost) {
								anchorSet.add(href);
							}
						}
						switch (scanType) {
						case PARTIAL:
							storeAnchorsForAnalyze(anchorSet, scanType, --depth);

							break;
						case FULL:
							storeAnchorsForAnalyze(anchorSet, scanType,
									DEFAULT_SCAN_DEPTH);
							break;

						default:
							break;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

	public Map<String, List<String>> analyze() throws IOException {
		// how to analyze?
		Document document;
		Elements imports;
		for (Entry<String, Document> page : siteMap.entrySet()) {
			document = page.getValue();
			imports = document.select("link[href][rel=stylesheet]");

			for (Element link : imports) {
				final String linkUrl = link.attr("abs:href");
				List<String> unusedSelectorsList;
				if (unusedSelectorsMap.containsKey(linkUrl)) {
					unusedSelectorsList = getUnusedStyleList(document,
							unusedSelectorsMap.get(linkUrl));

				} else {
					unusedSelectorsList = getUnusedStyleList(document, linkUrl);
				}

				unusedSelectorsMap.put(linkUrl, unusedSelectorsList);
			}
		}

		return unusedSelectorsMap;
	}

	private List<String> getUnusedStyleList(Document document,
			List<String> selectorList) {
		List<String> trashList = new ArrayList<String>();
		for (String selector : selectorList) {

			final String cleanSelector = cleanSuspect(selector);
			// TODO:
			try {
				final Elements elements = document.select(cleanSelector);

				if (elements.size() == 0) {
					trashList.add(selector);
				}
			} catch (Exception e) {
				// TODO: add to output or write to log file..?
				// trashList.add("Bad selector syntax: " + selector);
			}
		}
		return trashList;
	}

	private List<String> getSelectors(Document subject, final String cssUrl)
			throws IOException {
		String cssContent = Connector.get(cssUrl);
		return CSSParser.parse(cssContent);
	}

	private List<String> getUnusedStyleList(Document document,
			final String cssUrl) throws IOException {
		final List<String> selectors = getSelectors(document, cssUrl);
		return getUnusedStyleList(document, selectors);
	}

	private String cleanSuspect(String selector) {
		return selector.replaceAll(Dynamic.CLASSES.REG_EXP, "");
	}

}
