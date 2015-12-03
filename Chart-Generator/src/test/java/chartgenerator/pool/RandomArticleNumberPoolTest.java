package chartgenerator.pool;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomArticleNumberPoolTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RandomArticleNumberPoolTest.class);

	@Test
	public void test() {
		List<Integer> pool = RandomArticleNumberPool.createPool(1, 5, 20, 10);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(20, sum);
		assertEquals(10, pool.size());
	}

	@Test
	public void test2() {
		List<Integer> pool = RandomArticleNumberPool.createPool(1, 10, 20, 10);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(20, sum);
		assertEquals(10, pool.size());
	}

	@Test
	public void test3() {
		List<Integer> pool = RandomArticleNumberPool.createPool(1, 100, 20, 10);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(20, sum);
		assertEquals(10, pool.size());

	}

	@Test
	public void test4() {
		List<Integer>

		pool = RandomArticleNumberPool.createPool(1, 5, 21, 10);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(21, sum);
		assertEquals(10, pool.size());

	}

	@Test
	public void test5() {
		List<Integer> pool = RandomArticleNumberPool.createPool(1, 4, 20, 10);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(20, sum);
		assertEquals(10, pool.size());

	}

	@Test
	public void test6() {
		List<Integer> pool = RandomArticleNumberPool.createPool(1, 4, 21, 10);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(21, sum);
		assertEquals(10, pool.size());

	}

	@Test
	public void test7() {
		List<Integer> pool = RandomArticleNumberPool.createPool(2, 4, 21, 10);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(21, sum);
		assertEquals(10, pool.size());
	}

	@Test
	public void test8() {
		List<Integer> pool = RandomArticleNumberPool.createPool(1, 5, 150, 60);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(150, sum);
		assertEquals(60, pool.size());
	}

	@Test
	public void test9() {
		List<Integer> pool = RandomArticleNumberPool.createPool(1, 5, 160, 50);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(160, sum);
		assertEquals(50, pool.size());
	}

	@Test
	public void test10() {
		List<Integer> pool = RandomArticleNumberPool.createPool(1, 4, 160, 50);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(160, sum);
		assertEquals(50, pool.size());
	}

	@Test
	@Ignore
	public void test11() {
		List<Integer> pool = RandomArticleNumberPool.createPool(1, 3, 160, 50);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(160, sum);
		assertEquals(50, pool.size());
	}

	@Test
	@Ignore
	public void test12() {
		List<Integer> pool = RandomArticleNumberPool.createPool(2, 3, 160, 50);
		int sum = 0;
		LOGGER.debug("Pool elements:");
		for (Integer element : pool) {
			LOGGER.debug("Element: {}", element);
			sum += element;
		}
		assertEquals(160, sum);
		assertEquals(50, pool.size());
	}
}
