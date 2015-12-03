package chartgenerator.util;

import java.util.List;
import java.util.Random;

public class RandomPool<T> {

	private final Random random;
	private final List<T> elements;

	public RandomPool(List<T> elements) {
		random = new Random();
		this.elements = elements;
	}

}
