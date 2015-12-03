public class Loader {
	public static void main (String [] args) {
		final String [] parts = {"\\", "|", "/", "-"};

		while(true) {
			for (int i = 0; i < parts.length; i++) {
				System.out.print(parts[i] + "\b");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}