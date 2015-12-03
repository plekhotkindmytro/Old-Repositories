class EscapeSequencesFun {

	private static int maxRowLength = 0;
	public static void startTimer(int seconds, int minutes, int hours, int days, int month, int years) {
		Time time = new Time(seconds, minutes, hours, days, month, years);
		while (time.timeRemains()) {
			printCurrentTime(time);
			time.tick();
		}
	}

	private static void printCurrentTime(Time time) {
		// HH:mm:ss, x day(s) y month(s) z year(s)
		final String timeString = time.toString();
		System.out.print(timeString);
		clearRow(timeString.length());
	}

	public static void startTimer(int seconds, int minutes, int hours, int days, int month) {
		startTimer(seconds, minutes, hours, days, month, 0);
	}

	public static void startTimer(int seconds, int minutes, int hours, int days) {
		startTimer(seconds, minutes, hours, days, 0, 0);
	}

	public static void startTimer(int seconds, int minutes, int hours) {
		startTimer(seconds, minutes, hours, 0, 0, 0);
	}

	public static void startTimer(int seconds, int minutes) {
		startTimer(seconds, minutes, 0, 0, 0);
	}

	public static void startTimer(int seconds) {
		startTimer(seconds, 0, 0, 0, 0);
	}


	private static int getDigitCount(int number) {
		int count = 0;
		
		do {
			number = number / 10;
			count++;
		}
		while (number != 0);
		
		return count;
	}

	private static void clearRow(int rowLength) {
		for (int i = 0; i < rowLength; i++) {
			System.out.print(" ");
		}
		System.out.print("\r");
	}

	public static void main(String [] args) {
		System.out.println("Start loading...");
		
		int argsCount = args.length;
		switch(argsCount) {
			case 0:
				System.out.println("No args!");
				break;

			case 1:
				startTimer(new Integer(args[0]));	
				break;
			case 2:

				startTimer(new Integer(args[0]), new Integer(args[1]));	
				break;
			case 3:
				startTimer(new Integer(args[0]), new Integer(args[1]), new Integer(args[2]));	
				break;
			case 4:
				startTimer(new Integer(args[0]), new Integer(args[1]), new Integer(args[2]), new Integer(args[3]));	
				break;

			case 5:
				startTimer(new Integer(args[0]), new Integer(args[1]), new Integer(args[2]), new Integer(args[3]), new Integer(args[4]));	
				break;

			case 6:
				startTimer(new Integer(args[0]), new Integer(args[1]), new Integer(args[2]), new Integer(args[3]), new Integer(args[4]), new Integer(args[5]));	
				break;
			default:
				System.out.println("Args count has to between 0 and 6.");
				break;
		}
		System.out.println("...loading ended.");
	}
}