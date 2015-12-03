package chartgenerator.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PoolUtil {

	private static List<Double> SECTOR_FREQUENCIES = Arrays.asList(0.5, 0.3,
			0.15, 0.05);

	private PoolUtil() {
		throw new UnsupportedOperationException();
	}

	public static List<Integer> calculateSectorSizeList(
			final int elementsCount, final List<Double> sectorFrequencyList) {

		int averageSectorSize = (int) Math.ceil(elementsCount
				/ (double) sectorFrequencyList.size());
		List<Integer> sectorSizeList = new ArrayList<Integer>();

		int notAddedElements = elementsCount;
		for (int i = 0; i < sectorFrequencyList.size(); i++) {
			if (notAddedElements > averageSectorSize) {
				sectorSizeList.add(averageSectorSize);
				notAddedElements -= averageSectorSize;
			} else if (notAddedElements < 0) {
				sectorSizeList.add(0);
			} else {
				sectorSizeList.add(notAddedElements);
				notAddedElements -= averageSectorSize;
			}

		}
		return sectorSizeList;
	}

	public static List<Integer> createDefaultIntegerPool(int min, int max,
			int poolSize) {
		if (max < min) {
			throw new IllegalArgumentException(
					"Max value has to be greater that min value.");
		}

		int elementsCount = max - min + 1;
		List<Integer> sectorSizeList = calculateSectorSizeList(elementsCount,
				SECTOR_FREQUENCIES);

		final List<Integer> pool = new ArrayList<Integer>(poolSize);
		for (int i = min, sectorIndex = 0; i <= max
				|| sectorIndex < SECTOR_FREQUENCIES.size(); i += sectorSizeList
				.get(sectorIndex), sectorIndex++) {

			double sectorFrequency = SECTOR_FREQUENCIES.get(sectorIndex);
			int elementsNumber = (int) Math.ceil(poolSize * sectorFrequency);
			if (elementsNumber + pool.size() > poolSize) {
				elementsNumber = poolSize - pool.size();
			}
			int sectorSize = sectorSizeList.get(sectorIndex);
			if (sectorSize == 0) {
				for (int j = min; j < min + sectorSizeList.get(0); j++) {
					for (int frequencyIndex = 0; frequencyIndex < elementsNumber; frequencyIndex++) {
						pool.add(j);
					}
				}
			} else {
				int frequency = (int) Math.ceil((double) elementsNumber
						/ (double) sectorSize);
				int notAddedElementsNumber = elementsNumber;
				for (int j = i; j < i + sectorSizeList.get(sectorIndex); j++) {

					for (int frequencyIndex = 0; frequencyIndex < frequency
							&& frequencyIndex < notAddedElementsNumber; frequencyIndex++) {
						pool.add(j);
					}
					notAddedElementsNumber -= frequency;

				}
			}
		}

		return pool;
	}
}
