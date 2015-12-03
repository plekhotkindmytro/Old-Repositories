class Display {
	private String[] symbols;

	public Display(String[] symbols) {
		this.symbols= symbols;
	}
	

	public void setSymbols(String[] symbols) {
		this.symbols = symbols;
	}

	public String[] getSymbols() {
		return symbols;
	}

	public void setSymbol(int position, String value) {
		if(validPosition(position)) {
			symbols[position] = value;
		}
	}

	private boolean validPosition(int position ) {
		return position >= 0 && position < symbols.length;
	}

	public void refresh() {
	
		String displayString = "";
		for (int i = 0; i < symbols.length ; i++) {

			displayString += "|"+symbols[i]+"|";
		}


		print(displayString);
	}

	private void print(String s) {
		System.out.print("\r");
		System.out.print(s);
		System.out.print("  Symbols length: " + symbols.length);
		for (int i = 0; i < symbols.length ; i++) {

			System.out.print(" i: "+ i + ", "+symbols[i] );
		}
	}
}