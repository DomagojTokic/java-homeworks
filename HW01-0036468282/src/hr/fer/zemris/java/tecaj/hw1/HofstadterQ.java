package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program which writes n-th number of Hofstadter Q series to standard output.
 * 
 * @author Domagoj TokiĆ
 * 
 */

public class HofstadterQ {

	/**
	 * Entrance into HofstadterQ program.
	 * 
	 * @param args Ordinal number of Hofstadter Q series
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Program mora primiti 1 argument.");
			System.exit(1);
		}

		long i = Long.parseLong(args[0]);

		if (i <= 0) {
			System.out.println("Ulazni argument mora biti ve�i od 0");
			System.exit(1);
		}

		long number = getHofstadterQ(i);

		System.out.println("You requested calculation of " + i + ". "
				+ "number of Hofstadter's Q-sequence. The requested number is "
				+ number + ".");
	}

	/**
	 * Returns n-th number of Hofstadter Q series by formula:
	 * Q(1) = Q(2) = 1
	 * Q(n) = Q(n - Q(n-1)) + Q(n - Q(n-2))
	 * 
	 * @param n Ordinal number
	 * @return n-th number of Hofstadter Q series
	 */
	private static long getHofstadterQ(long n) {
		if (n == 1 || n == 2) {
			return 1;
		} else {
			return getHofstadterQ(n - getHofstadterQ(n - 1))
					+ getHofstadterQ(n - getHofstadterQ(n - 2));
		}
	}

}
