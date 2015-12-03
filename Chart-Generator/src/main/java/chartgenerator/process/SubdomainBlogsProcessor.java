package chartgenerator.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import chartgenerator.inputs.GlobalSettings;
import chartgenerator.inputs.KeywordGroupInputs;
import chartgenerator.inputs.SubdomainBlogInputs;
import chartgenerator.model.KeywordData;
import chartgenerator.model.Links;
import chartgenerator.outputs.ArticleOutput;
import chartgenerator.outputs.SubdomainBlogOutput;
import chartgenerator.pool.RandomSitePool;
import chartgenerator.util.NumberFormatter;
import chartgenerator.util.PoolUtil;

public class SubdomainBlogsProcessor {

	private static final int MAX_PERCENTS = 100;

	private final SubdomainBlogInputs inputs;
	private final GlobalSettings globalSettings;
	private final List<SubdomainBlogOutput> outputs;

	private final List<Integer> imagesCountPool;
	private final List<Integer> videosCountPool;
	private final List<Integer> audiosCountPool;
	private final List<Integer> anchorCountPool;

	private final Random RANDOM = new Random();
	private final Random RANDOM_LINK = new Random();
	private final Random RANDOM_ANCHOR = new Random();
	private final Random RANDOM_IMAGE = new Random();
	private final Random RANDOM_VIDEO = new Random();
	private final Random RANDOM_AUDIO = new Random();
	private final Random RANDOM_ANCHOR_COUNT = new Random();

	private final Random RANDOM_KEYWORD_GROUP = new Random();

	private final Random RANDOM_DATE = new Random();

	private static long getRandomTimeBetweenTwoDates(long startTime,
			long endTime) {
		long diff = endTime - startTime + 1;
		return startTime + (long) (Math.random() * diff);
	}

	private LocalDate getRandomDateTime(LocalDate startDate, LocalDate endDate) {
		int daysCount = Days.daysBetween(startDate, endDate).getDays();
		LocalDate randomDate = startDate.plusDays(RANDOM_DATE.nextInt(daysCount));
		return randomDate;
	}

	public SubdomainBlogsProcessor(final SubdomainBlogInputs inputs,
			final GlobalSettings globalSettings) {
		this.inputs = inputs;
		this.globalSettings = globalSettings;

		imagesCountPool = PoolUtil.createDefaultIntegerPool(
				inputs.minImageCount, inputs.maxImageCount, MAX_PERCENTS);
		Collections.shuffle(imagesCountPool);
		videosCountPool = PoolUtil.createDefaultIntegerPool(
				inputs.minVideoCount, inputs.maxVideoCount, MAX_PERCENTS);
		Collections.shuffle(videosCountPool);
		audiosCountPool = PoolUtil.createDefaultIntegerPool(
				inputs.minAudioCount, inputs.maxAudioCount, MAX_PERCENTS);
		Collections.shuffle(audiosCountPool);
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
	private List<SubdomainBlogOutput> generateOutputs() {
		final List<String> keywordGroupPool = new ArrayList<String>(
				MAX_PERCENTS);
		for (KeywordGroupInputs keywordGroupInputs : inputs.keywordGroupInputsList) {
			for (int i = 0; i < keywordGroupInputs.frequency; i++) {
				keywordGroupPool.add(keywordGroupInputs.name);
			}
		}

		final List<String> sitePool = RandomSitePool.createPool(
				inputs.blogFrequencies, inputs.totalBlogsNumber);

		final int digitsNumber = Integer.toString(inputs.totalBlogsNumber)
				.length();

		final List<Integer> articleCountPool = PoolUtil
				.createDefaultIntegerPool(inputs.minArticleCount,
						inputs.maxArticleCount, inputs.totalBlogsNumber);
		Collections.shuffle(articleCountPool);

		List<SubdomainBlogOutput> outputList = new ArrayList<SubdomainBlogOutput>();
		for (int i = 1; i <= inputs.totalBlogsNumber; i++) {
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

				// Article count
				int articleCount = articleCountPool.remove(articleCountPool
						.size() - 1);

				String site = sitePool.remove(sitePool.size() - 1);
				LocalDate randomDate = getRandomDateTime(inputs.startDate,
						inputs.endDate);
				List<ArticleOutput> articleOutputList = createArticleOutputList(
						randomDate, idBuilder.toString(), articleCount,
						randomKeywordGroupInputs);

				SubdomainBlogOutput output = new SubdomainBlogOutput(
						randomDate, idBuilder.toString(), site, articleCount,
						articleOutputList);

				outputList.add(output);
			}

		}

		return outputList;
	}

	private List<ArticleOutput> createArticleOutputList(LocalDate startDate,
			String blogId, int articleCount,
			final KeywordGroupInputs keywordGroupInputs) {
		List<List<String>> anchorDataPool = new ArrayList<List<String>>(
				MAX_PERCENTS);
		for (KeywordData anchor : keywordGroupInputs.anchorData) {
			for (int i = 0; i < anchor.getFrequency(); i++) {
				anchorDataPool.add(anchor.getKeywordList());
			}
		}

		List<List<String>> linkDataPool = new ArrayList<List<String>>(
				MAX_PERCENTS);
		for (KeywordData link : keywordGroupInputs.linkData) {
			for (int i = 0; i < link.getFrequency(); i++) {
				linkDataPool.add(link.getKeywordList());
			}
		}

		List<ArticleOutput> outputList = new ArrayList<ArticleOutput>();

		for (int i = 1; i <= articleCount; i++) {

			final String id = blogId + "-A" + i;

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

			int imageCount = imagesCountPool.get(RANDOM_IMAGE
					.nextInt(imagesCountPool.size()));
			int videoCount = videosCountPool.get(RANDOM_VIDEO
					.nextInt(videosCountPool.size()));
			int audioCount = audiosCountPool.get(RANDOM_AUDIO
					.nextInt(audiosCountPool.size()));

			LocalDate randomDate = getRandomDateTime(startDate, inputs.endDate);

			ArticleOutput output = new ArticleOutput(randomDate, id,
					anchorNumber == 0 ? Links.NO : Links.YES, anchorList,
					linkList, globalSettings.anchorFrequencies.size() - 1,
					imageCount, videoCount, audioCount);
			outputList.add(output);
		}
		return outputList;
	}

	public List<SubdomainBlogOutput> getOutputs() {
		return outputs;
	}
}
