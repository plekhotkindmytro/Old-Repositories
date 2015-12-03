package com.trashcssview;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.helper.Validate;

import com.trashcssview.util.Analyzer;
import com.trashcssview.util.ScanType;

public class DataVisualizer {

	public static void main(final String[] args) throws IOException {
		Validate.isTrue(args.length >= 1, "usage: supply url to fetch");

		ScanType scanType = ScanType.PARTIAL;
		int scanDepth = 0;
		Set<String> pageUrlSet = new HashSet<String>();

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-t")) {
				if (i != args.length - 1) {
					try {
						switch (Integer.parseInt(args[i + 1])) {
						case 1:
							scanType = ScanType.FULL;
							break;
						case 0:
						default:
							scanType = ScanType.PARTIAL;
							break;
						}
						i++;
					} catch (NumberFormatException e) {
						print("Scan type: 0 - partial(default), 1 - entire site.");
						return;
					}
				} else {
					print("Parameter -t (scan type) has no value.");
					return;
				}
			} else if (args[i].equals("-d")) {
				if (i != args.length - 1) {
					try {
						scanDepth = Integer.parseInt(args[i + 1]);
						i++;
					} catch (NumberFormatException e) {
						print("Scan depth has to have integer value.");
						return;
					}
				} else {
					print("Parameter -d (scan depth) has no value.");
					return;
				}
			} else {
				pageUrlSet.add(args[i]);

			}
		}

		final Analyzer analyzer = new Analyzer(pageUrlSet, scanType, scanDepth);

		final Map<String, List<String>> unusedSelectors = analyzer.analyze();

		printResult(unusedSelectors);

	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static void printResult(Map<String, List<String>> unusedSelectors) {
		print(">>>");
		for (Entry<String, List<String>> entry : unusedSelectors.entrySet()) {
			List<String> selectorList = entry.getValue();
			if (selectorList.size() != 0) {
				print("\n=====================");
				print(entry.getKey() + "\n");
				for (String selector : selectorList) {
					print(selector);
				}
			}
		}
		print("\n<<<");
	}

	public void printTrash(String trashHolderUrl, List<String> trashList) {

	}

}
