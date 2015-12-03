package chartgenerator.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberFormatterTest {

	@Test
	public void testFormat() {
		String test3_123 = NumberFormatter.format(3, 123);
		assertEquals("123", test3_123);
		assertNotEquals("000", test3_123);
		
		String test3_1234 = NumberFormatter.format(3, 1234);
		assertEquals("1234", test3_1234);
		assertNotEquals("123", test3_1234);
		assertNotEquals("234", test3_1234);
		
		String test3_12 = NumberFormatter.format(3, 12);
		assertEquals("012", test3_12);
		assertNotEquals("12", test3_12);
		
		String test3_1 = NumberFormatter.format(3, 1);
		assertEquals("001", test3_1);
		assertNotEquals("1", test3_1);
		
		String test3_0 = NumberFormatter.format(3, 0);
		assertEquals("000", test3_0);
		assertNotEquals("0", test3_0);
		
		
	}

}
