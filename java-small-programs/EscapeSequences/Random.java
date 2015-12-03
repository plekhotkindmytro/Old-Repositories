import java.util.Date;

/** 
 * Linear congruential generator;
 */
public class Random {
	public static int getRandom() {
		generate();
		return random;
	}

	public static Date date = new Date();
	public static int a = date.getDay();
	public static int c = date.getSeconds();
	public static int m = date.getMinutes();

	private static int random = 0;
	private static void generate () {
		random = (a * random + c) % m;
	}

	public static void main(String[] args) {
		for (int i =0;i < 100 ;i++ ) {
			println(""+Random.getRandom());
		}
		
	}

	public static void println(String string) {
		System.out.println(string);
	}


	public static void print(String string) {
		System.out.print(string);
	}
}