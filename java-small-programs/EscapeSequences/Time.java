public class Time  {
	private int seconds;
	private int minutes;
	private int hours;
	private int days;
	private int month;
	private int years;

	private long timeSpanInSeconds;

	private static final int SECS_IN_MIN = 60;
	private static final int MINS_IN_HOUR = 60;
	private static final int HOURS_IN_DAY = 24;
	private static final int DAYS_IN_MONTH = 30;
	private static final int MONTH_IN_YEAR  = 12;
	private static final String PLURAL_PART = "s";
	private static final String EMPTY_STRING = "";
	private static final String ZERO_ZERO = "00";

	public Time(int seconds, int minutes, int hours, int days, int month, int years) {
		this.seconds = seconds;
		this.minutes = minutes;
		this.hours = hours; 
		this.days = days;
		this.month = month;
		this.years = years;
	}
		
	public boolean timeRemains() {
			return seconds > 0 || minutes > 0 || hours > 0 || days > 0 || month > 0 || years > 0;
		}

		public void tick() {
			substractASecond();
			try {
				Thread.sleep(1000);	
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		public void substractASecond() {

			if(seconds == 0) {
				substractAMinute();
			} else {
				seconds--;
			}
		}

		public void substractAMinute() {

			if(minutes == 0) {
				substractAnHour();
			} else {
				minutes--;
				seconds = SECS_IN_MIN;
			}
		}

		public void substractAnHour() {

			if(hours == 0) {
				substractADay();
			} else {
				hours--;
				minutes = MINS_IN_HOUR;
			}
		}


		public void substractADay() {
			if(days == 0) {
				substractAMonth();
			} else {
				days--;
				hours = HOURS_IN_DAY;
			}
		}

		public void substractAMonth() {
			if(month == 0) {
				substractAYear();
			} else {
				month--;
				days = DAYS_IN_MONTH;
			}
		}

		public void substractAYear() {
			if(years != 0) {
				years--;
				month = MONTH_IN_YEAR;
			}
		}

		public String toString() {
			String yearsPart = EMPTY_STRING;

		if (years != 0) {
			yearsPart = years + " year";
			if (years != 1) {
				yearsPart += PLURAL_PART;
			} 
		}

		String monthPart = EMPTY_STRING;

		if (month != 0) {
			monthPart = " " + month + " month";
			if (month != 1) {
				monthPart += PLURAL_PART;
			} 
		}

		String daysPart = EMPTY_STRING;
		
		if (days != 0) {
			daysPart = " " + days + " day";
			if (days != 1) {
				daysPart += PLURAL_PART + " ";
			} else {
				daysPart = " ";
			}
		}

		String hoursPart = ZERO_ZERO;

		if (hours != 0) {
			if (hours < 10) {
				hoursPart = "0" + hours;
			} else {
				hoursPart = "" + hours;
			}
		}


		String minutesPart = ZERO_ZERO;

		if (minutes != 0) {
			if (minutes < 10) {
				minutesPart = "0" + minutes;
			} else {
				minutesPart = "" + minutes;
			}
		}


		String secondsPart = ZERO_ZERO;

		if (seconds != 0) {
			if (seconds < 10) {
				secondsPart = "0" + seconds;
			} else {
				secondsPart = "" + seconds;
			}
		}
			return yearsPart + monthPart + daysPart + hoursPart+":"+minutesPart+":"+secondsPart;
		}
}