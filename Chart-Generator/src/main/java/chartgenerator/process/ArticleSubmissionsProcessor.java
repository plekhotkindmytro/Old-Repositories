package chartgenerator.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import chartgenerator.inputs.ArticleSubmissionInputs;
import chartgenerator.inputs.GlobalSettings;
import chartgenerator.inputs.KeywordGroupInputs;
import chartgenerator.model.KeywordData;
import chartgenerator.outputs.ArticleSubmissionOutput;
import chartgenerator.pool.RandomSitePool;
import chartgenerator.util.NumberFormatter;

public class ArticleSubmissionsProcessor {

	private static final int MAX_PERCENTS = 100;

	private final ArticleSubmissionInputs inputs;
	private final GlobalSettings globalSettings;
	private final List<ArticleSubmissionOutput> outputs;

	private final List<Integer> anchorCountPool;

	private final Random RANDOM = new Random();
	private final Random RANDOM_LINK = new Random();
	private final Random RANDOM_ANCHOR = new Random();

	private final Random RANDOM_ANCHOR_COUNT = new Random();

	private final Random RANDOM_KEYWORD_GROUP = new Random();

	private final Random RANDOM_DATE = new Random();

	private LocalDate getRandomDateTime(LocalDate startDate, LocalDate endDate) {
		int daysCount = Days.daysBetween(startDate, endDate).getDays();
		LocalDate randomDate = startDate.plusDays(RANDOM_DATE
				.nextInt(daysCount));
		return randomDate;
	}

	public ArticleSubmissionsProcessor(final ArticleSubmissionInputs inputs,
			final GlobalSettings globalSettings) {
		this.inputs = inputs;
		this.globalSettings = globalSettings;

		anchorCountPool = new ArrayList<Integer>();
		for (int anchorIndex = 0; anchorIndex < globalSettings.anchorFrequencies
				.size(); anchorIndex++) {
			for (int i = 0; i < globalSettings.anchorFrequencies
					.get(anchorIndex); i++) {
				anchorCountPool.add(anchorIndex);
			}
		}
		outputs = generateOutputs();
	}

	// TODO: function is too long. Update it!
	private List<ArticleSubmissionOutput> generateOutputs() {
		final List<String> keywordGroupPool = new ArrayList<String>(
				MAX_PERCENTS);
		for (KeywordGroupInputs keywordGroupInputs : inputs.keywordGroupInputsList) {
			for (int i = 0; i < keywordGroupInputs.frequency; i++) {
				keywordGroupPool.add(keywordGroupInputs.name);
			}
		}

		final List<String> sitePool = RandomSitePool.createPool(
				inputs.siteFrequencies, inputs.articleSubmissionsNumber);

		final int digitsNumber = Integer.toString(
				inputs.articleSubmissionsNumber).length();

		List<ArticleSubmissionOutput> outputList = new ArrayList<ArticleSubmissionOutput>();
		for (int i = 1; i <= inputs.articleSubmissionsNumber; i++) {
			// KG name
			String keywordGroupName = keywordGroupPool.get(RANDOM_KEYWORD_GROUP
					.nextInt(MAX_PERCENTS));
			KeywordGroupInputs randomKeywordGroupInputs = null;
			for (KeywordGroupInputs keywordGroupInputs : inputs.keywordGroupInputsList) {
				if (keywordGroupInputs.name.equals(keywordGroupName)) {
					randomKeywordGroupInputs = keywordGroupInputs;
					break;
				}
			}

			if (randomKeywordGroupInputs != null) {
				// ID number
				String idNumber = NumberFormatter.format(digitsNumber, i);

				StringBuilder idBuilder = new StringBuilder();
				idBuilder.append(keywordGroupName).append("SB")
						.append(globalSettings.projectName).append(idNumber);

				// Site

				String site = sitePool.remove(sitePool.size() - 1);
				LocalDate randomDate = getRandomDateTime(inputs.startDate,
						inputs.endDate);

				List<List<String>> anchorDataPool = new ArrayList<List<String>>(
						MAX_PERCENTS);
				for (KeywordData anchor : randomKeywordGroupInputs.anchorData) {
					for (int j = 0; j < anchor.getFrequency(); j++) {
						anchorDataPool.add(anchor.getKeywordList());
					}
				}

				List<List<String>> linkDataPool = new ArrayList<List<String>>(
						MAX_PERCENTS);
				for (KeywordData link : randomKeywordGroupInputs.linkData) {
					for (int j = 0; j < link.getFrequency(); j++) {
						linkDataPool.add(link.getKeywordList());
					}
				}

				List<String> anchorList = new ArrayList<String>();
				List<String> linkList = new ArrayList<String>();

				int anchorNumber = anchorCountPool.get(RANDOM_ANCHOR_COUNT
						.nextInt(MAX_PERCENTS));
				for (int j = 0; j < anchorNumber; j++) {
					List<String> anchor = anchorDataPool.get(RANDOM_ANCHOR
							.nextInt(anchorDataPool.size()));
					anchorList.add(anchor.get(RANDOM.nextInt(anchor.size())));

					List<String> link = linkDataPool.get(RANDOM_LINK
							.nextInt(linkDataPool.size()));
					linkList.add(link.get(RANDOM.nextInt(link.size())));
				}

				ArticleSubmissionOutput output = new ArticleSubmissionOutput(
						randomDate, idBuilder.toString(), site, anchorList,
						linkList);

				outputList.add(output);
			}

		}

		return outputList;
	}

	public List<ArticleSubmissionOutput> getOutputs() {
		return outputs;
	}
}
