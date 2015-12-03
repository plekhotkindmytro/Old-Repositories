public class Wheel {
	private int goalIndex;
	private String[] symbols;
	private int wheelPosition;
	private int currentSymbolIndex;

	private static final int START_POSITION = 0;

	public Wheel (String [] symbols, int wheelPosition) {
		this.symbols = symbols;
		this.wheelPosition = wheelPosition;
	}

	public void spin (Display display) {
			
			currentSymbolIndex++;
			if(currentSymbolIndex == symbols.length) {
				currentSymbolIndex = START_POSITION;
			}

			display.setSymbol(wheelPosition, symbols[currentSymbolIndex]);
	}
}