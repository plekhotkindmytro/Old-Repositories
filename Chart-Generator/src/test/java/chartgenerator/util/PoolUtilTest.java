package chartgenerator.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PoolUtilTest {
	private static List<Double> SECTOR_FREQUENCIES = Arrays.asList(0.5, 0.3,
			0.15, 0.05);

	@Test
	public void testFindElementsCountInSector() {
		assertEquals(Arrays.asList(10, 10, 10, 10),
				PoolUtil.calculateSectorSizeList(40, SECTOR_FREQUENCIES));
		assertEquals(Arrays.asList(11, 11, 11, 8),
				PoolUtil.calculateSectorSizeList(41, SECTOR_FREQUENCIES));
		assertEquals(Arrays.asList(2, 2, 1, 0),
				PoolUtil.calculateSectorSizeList(5, SECTOR_FREQUENCIES));
		assertEquals(Arrays.asList(2, 2, 2, 0),
				PoolUtil.calculateSectorSizeList(6, SECTOR_FREQUENCIES));
		assertEquals(Arrays.asList(2, 2, 2, 1),
				PoolUtil.calculateSectorSizeList(7, SECTOR_FREQUENCIES));

		assertEquals(Arrays.asList(1, 1, 0, 0),
				PoolUtil.calculateSectorSizeList(2, SECTOR_FREQUENCIES));
		assertEquals(Arrays.asList(1, 0, 0, 0),
				PoolUtil.calculateSectorSizeList(1, SECTOR_FREQUENCIES));
		assertEquals(Arrays.asList(0, 0, 0, 0),
				PoolUtil.calculateSectorSizeList(0, SECTOR_FREQUENCIES));

	}

	@Test
	public void testCreateDefaultIntegerPool() {

		assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3,
				3), PoolUtil.createDefaultIntegerPool(0, 3, 100));

		assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2,
				2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
				3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 7,
				7), PoolUtil.createDefaultIntegerPool(0, 7, 100));

		assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				PoolUtil.createDefaultIntegerPool(0, 0, 10));

		assertEquals(Arrays.asList(1, 1, 2, 2, 3, 4, 5, 6, 7, 8),
				PoolUtil.createDefaultIntegerPool(1, 10, 10));
		try {

			PoolUtil.createDefaultIntegerPool(10, 1, 10);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(),
					"Max value has to be greater that min value.");
		}
	}
}
