public class ProgressBar {
	public static void main (String [] args) {
		// final String [] parts = {"|// // // //|", "| // // // /|" };
		// final String [] parts = {"|/ / / / / |", "| / / / / /|" };
		 final String [] parts = {"E[:|||||||:]3", " E[:|||||:]3 ", "  E[:|||:]3  ", "   E[:|:]3   ", "  E[:|||:]3  ", " E[:|||||:]3 "};


		System.out.println("   (\\__/)");
		System.out.println("   (='.'=)");

		while(true) {

			for (int i = 0; i < parts.length; i++) {
				System.out.print(parts[i] + "\r");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}