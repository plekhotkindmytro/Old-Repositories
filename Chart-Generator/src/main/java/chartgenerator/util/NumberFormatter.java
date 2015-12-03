package chartgenerator.util;

public final class NumberFormatter {

	private NumberFormatter() {
		throw new UnsupportedOperationException();
	}

	public static String format(int digitsNumber, int numberToFormat) {
		return String.format("%0" + digitsNumber + "d", numberToFormat);
	}
}
