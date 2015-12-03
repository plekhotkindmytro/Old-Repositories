package chartgenerator.pool;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomSitePoolTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RandomSitePoolTest.class);

	@Test
	public void testGetPool() {
		Map<String, Integer> siteFrequencyMap = new HashMap<String, Integer>();
		siteFrequencyMap.put("blogger", 50);
		siteFrequencyMap.put("wordpress", 50);

		List<String> pool = RandomSitePool.createPool(siteFrequencyMap, 10);
		assertEquals(10, pool.size());
		assertEquals(5, Collections.frequency(pool, "blogger"));
		assertEquals(5, Collections.frequency(pool, "wordpress"));

		siteFrequencyMap = new HashMap<String, Integer>();
		siteFrequencyMap.put("0", 8);
		siteFrequencyMap.put("1", 4);
		siteFrequencyMap.put("2", 4);
		siteFrequencyMap.put("3", 4);
		siteFrequencyMap.put("4", 4);
		siteFrequencyMap.put("5", 4);
		siteFrequencyMap.put("6", 4);
		siteFrequencyMap.put("7", 4);
		siteFrequencyMap.put("8", 4);
		siteFrequencyMap.put("9", 4);
		siteFrequencyMap.put("10", 4);
		siteFrequencyMap.put("11", 4);
		siteFrequencyMap.put("12", 4);
		siteFrequencyMap.put("13", 4);
		siteFrequencyMap.put("14", 4);
		siteFrequencyMap.put("15", 4);
		siteFrequencyMap.put("16", 4);
		siteFrequencyMap.put("17", 4);
		siteFrequencyMap.put("18", 4);
		siteFrequencyMap.put("19", 4);
		siteFrequencyMap.put("20", 4);
		siteFrequencyMap.put("21", 4);
		siteFrequencyMap.put("22", 4);
		siteFrequencyMap.put("23", 2);
		siteFrequencyMap.put("24", 2);

		pool = RandomSitePool.createPool(siteFrequencyMap, 100);
		assertEquals(100, pool.size());

		assertEquals(8, Collections.frequency(pool, "0"));
		assertEquals(4, Collections.frequency(pool, "1"));
		assertEquals(4, Collections.frequency(pool, "2"));
		assertEquals(4, Collections.frequency(pool, "3"));
		assertEquals(4, Collections.frequency(pool, "4"));
		assertEquals(4, Collections.frequency(pool, "5"));
		assertEquals(4, Collections.frequency(pool, "6"));
		assertEquals(4, Collections.frequency(pool, "7"));
		assertEquals(4, Collections.frequency(pool, "8"));
		assertEquals(4, Collections.frequency(pool, "9"));
		assertEquals(4, Collections.frequency(pool, "10"));
		assertEquals(4, Collections.frequency(pool, "11"));
		assertEquals(4, Collections.frequency(pool, "12"));
		assertEquals(4, Collections.frequency(pool, "13"));
		assertEquals(4, Collections.frequency(pool, "14"));
		assertEquals(4, Collections.frequency(pool, "15"));
		assertEquals(4, Collections.frequency(pool, "16"));
		assertEquals(4, Collections.frequency(pool, "17"));
		assertEquals(4, Collections.frequency(pool, "18"));
		assertEquals(4, Collections.frequency(pool, "19"));
		assertEquals(4, Collections.frequency(pool, "20"));
		assertEquals(4, Collections.frequency(pool, "21"));
		assertEquals(4, Collections.frequency(pool, "22"));
		assertEquals(2, Collections.frequency(pool, "23"));
		assertEquals(2, Collections.frequency(pool, "24"));

		siteFrequencyMap = new HashMap<String, Integer>();
		siteFrequencyMap.put("1", 36);
		siteFrequencyMap.put("2", 24);
		siteFrequencyMap.put("3", 28);
		siteFrequencyMap.put("4", 12);
		pool = RandomSitePool.createPool(siteFrequencyMap, 10);
		assertEquals(10, pool.size());
		assertEquals(4, Collections.frequency(pool, "1"));
		assertEquals(2, Collections.frequency(pool, "2"));
		assertEquals(3, Collections.frequency(pool, "3"));
		assertEquals(1, Collections.frequency(pool, "4"));

	}
}
