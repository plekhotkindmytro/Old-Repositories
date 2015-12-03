package dmytro.javacases.annotation;

public class Program {
	private static final long BIG_NUMBER = 10000;

	public static void main(String[] args) {
		goodWork();
		badWork();
	}

	private static void badWork() {
		System.out.println("Start doing bad work.");
		String builder = new String();
		for (int i = 0; i < BIG_NUMBER; i++) {
			builder += i;
		}
		System.out.println(builder);
		System.out.println("End doing bad work.");
	}

	@Bottleneck
	private static void goodWork() {
		System.out.println("Start doing good work.");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < BIG_NUMBER; i++) {
			builder.append(i);
		}
		System.out.println(builder);
		System.out.println("End doing good work.");
	}
}
