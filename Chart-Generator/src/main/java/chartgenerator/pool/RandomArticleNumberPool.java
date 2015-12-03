package chartgenerator.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomArticleNumberPool {

	public static List<Integer> createPool(int minRandomArticleNumber,
			int maxRandomArticleNumber, int totalArticles, int poolSize) {
		final Random random = new Random();

		final List<Integer> pool = new ArrayList<Integer>();
		int sum = 0;

		for (int sizeIndex = 0; sizeIndex < poolSize; sizeIndex++) {
			pool.add(minRandomArticleNumber);
		}
		sum = poolSize * minRandomArticleNumber;

		for (int sizeIndex = 0, numberIndex = minRandomArticleNumber; sizeIndex < poolSize; sizeIndex++) {
			int oldValue = pool.get(sizeIndex);
			int newValue = numberIndex;

			int newSum = sum + newValue - oldValue;
			if (newSum <= totalArticles) {
				if (newValue - oldValue > 0) {
					sum = newSum;
					pool.set(sizeIndex, numberIndex);
				}
			}

			
			if (sizeIndex == poolSize - 1 && sum != totalArticles) {
				sizeIndex = 0;
			}
			
			numberIndex = random
					.nextInt((maxRandomArticleNumber - minRandomArticleNumber) + 1)
					+ minRandomArticleNumber;

		}

		Collections.shuffle(pool);
		return pool;
	}

	@Deprecated
	public static List<Integer> createPool2(int minRandomArticleNumber,
			int maxRandomArticleNumber, int totalArticles, int poolSize) {
		final Random random = new Random();

		final List<Integer> pool = new ArrayList<Integer>();
		int sum = 0;

		for (int sizeIndex = 0; sizeIndex < poolSize; sizeIndex++) {
			int randomNum = random
					.nextInt((maxRandomArticleNumber - minRandomArticleNumber) + 1)
					+ minRandomArticleNumber;
			pool.add(randomNum);
			sum += randomNum;
		}

		for (int i = 0; i < poolSize; i++) {
			int oldValue = pool.get(i);

			if (sum > totalArticles && oldValue > minRandomArticleNumber) {
				int newValue = oldValue - 1;
				pool.set(i, newValue);
				sum -= 1;
			} else if (sum < totalArticles && oldValue < maxRandomArticleNumber) {
				int newValue = oldValue + 1;
				pool.set(i, newValue);
				sum += 1;
			}

			if (i == poolSize - 1 && sum != totalArticles) {
				i = 0;
			} else {
				break;
			}

		}

		Collections.shuffle(pool);
		return pool;
	}
}
