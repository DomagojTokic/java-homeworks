package hr.fer.zemris.java.tecaj.hw1;

/**
 * Class for printing set number of prime numbers to standard output.
 * 
 * @author Domagoj Tokić
 *
 */

public class PrimeNumbers {

	/**
	 * Entrance into program
	 * 
	 * @param args Number of prime numbers.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Number of arguments must be 1.");
			System.exit(1);
		}

		int n = Integer.parseInt(args[0]);

		if (n <= 0) {
			System.err.println("Number must be greater than 0.");
			System.exit(1);
		}

		long[] list = new long[n];

		list[0] = 2;
		long number = 2;
		boolean prime;

		for (int i = 1; i < n; i++) {
			while (true) {
				number++;
				prime = true;

				for (int j = 2; j < Math.sqrt(number); j++) {
					if (number % j == 0) {
						prime = false;
					}
				}

				if (prime) {
					list[i] = number;
					break;
				}
			}
		}

		System.out.println("You requested calculation of " + n
				+ " prime numbers. Here they are:");

		for (int i = 0; i < n; i++) {
			System.out.println((i+1) + ". " + list[i]);
		}
	}

}
