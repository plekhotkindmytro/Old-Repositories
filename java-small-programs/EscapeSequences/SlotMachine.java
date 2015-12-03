public class SlotMachine {
	private Wheel[] wheels;
	private static final String[] symbols = { "❤",  "€", "✪", "❼", "8", "$", "@", "0"};
	private static final int WHEELS_COUNT = 3;
	private Display display;

	public SlotMachine() {
		for (int i = 0; i < symbols.length ; i++) {

			System.out.print(" "+ i + ", "+symbols[i] );
			System.out.println();
		}
		String [] startSymbols = {symbols[0],symbols[0],symbols[0]};
		display = new Display(startSymbols);

		wheels = new Wheel[WHEELS_COUNT];
		for (int i = 0 ; i < WHEELS_COUNT; i++) {
			wheels[i] = new Wheel(symbols, i);
		}


	}

	public void spin() {
		final int wheelsCount = wheels.length;
		
		String [] currentSymbols = new String[wheelsCount];

		for (int i = 0 ; i < wheelsCount; i++) {
			WheelThread thread  = new WheelThread(wheels[i]);
			thread.start();
		}

		while(true) {
			try { 
				Thread.sleep(10);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			display.refresh();	
		}
	}

	public static void main(String []args) {
		SlotMachine slotMachine = new SlotMachine();

		slotMachine.spin();
		
	}


	private class WheelThread extends Thread {

		Wheel wheel;
		
		public WheelThread(Wheel wheel) {
			this.wheel = wheel;
		}
		public void run() {
			int randomNumber = getRandomInt(10, 50);
			int randomSpeed = getRandomInt(50, 300);

			for (int i = randomNumber; i > 0; i--) {
				// System.out.println(i +"; randomNumber: "+ randomNumber +", randomSpeed: "+ randomSpeed);

				wheel.spin(display);
				try { 
					Thread.sleep(randomSpeed);	
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public int getRandomInt (int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
}