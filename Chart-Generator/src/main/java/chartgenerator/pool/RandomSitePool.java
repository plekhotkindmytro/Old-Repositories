package chartgenerator.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public final class RandomSitePool {

	private static final double MAX_PERCENTS = 100.0;

	private RandomSitePool() {
		throw new UnsupportedOperationException();
	}

	public static List<String> createPool(
			final Map<String, Integer> siteFrequencyMap, final int poolSize) {
		final List<String> pool = new ArrayList<String>();
		fillPool(pool, siteFrequencyMap, poolSize);
		shufflePool(pool);
		return pool;
	}

	private static List<String> fillPool(final List<String> pool,
			final Map<String, Integer> siteFrequencyMap, final int poolSize) {
		Map<Double, List<String>> approximationNeedMap = new TreeMap<Double, List<String>>(
				Collections.reverseOrder(new Comparator<Double>() {

					@Override
					public int compare(Double o1, Double o2) {

						return o1.compareTo(o2);
					}
				}));

		for (Entry<String, Integer> entry : siteFrequencyMap.entrySet()) {
			final String siteName = entry.getKey();
			final int frequency = entry.getValue();

			final double exactElementsNumber = frequency * poolSize
					/ MAX_PERCENTS;
			final int integerElementsNumber = (int) Math
					.floor(exactElementsNumber);

			for (int i = 0; i < integerElementsNumber; i++) {
				pool.add(siteName);
			}

			final double approximationNeed = exactElementsNumber
					- integerElementsNumber;
			if (approximationNeed > 0) {
				final List<String> siteNameList = approximationNeedMap
						.containsKey(approximationNeed) ? approximationNeedMap
						.get(approximationNeed) : new ArrayList<String>();
				siteNameList.add(siteName);
				approximationNeedMap.put(approximationNeed, siteNameList);
			}
		}

		if (pool.size() < poolSize) {
			improvePool(pool, approximationNeedMap, poolSize);
		}
		return pool;
	}

	private static void improvePool(final List<String> pool,
			final Map<Double, List<String>> approximationNeedMap,
			final int poolSize) {

		for (List<String> aproximationList : approximationNeedMap.values()) {
			if (pool.size() < poolSize) {
				for (String siteName : aproximationList) {
					if (pool.size() < poolSize) {
						pool.add(siteName);
					}
				}
			}
		}
	}

	private static void shufflePool(List<String> pool) {
		Collections.shuffle(pool);
	}
}
