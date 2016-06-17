package hr.fer.zemris.java.tecaj.hw1;

/**
 * Class which writes decomposition of whole number to factors to standard
 * output.
 * 
 * @author Domagoj Tokić
 *
 */
public class NumberDecomposition {

	/**
	 * Entrance into program.
	 * 
	 * @param args Number for decomposition
	 */

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Program needs 1 argument.");
			System.exit(1);
		}

		long number = Long.parseLong(args[0]);

		if (number <= 1) {
			System.err.println("Number must be greater than 1.");
			System.exit(1);
		}

		printDecomposition(number);
	}

	/**
	 * Prints decomposition of number to factors to standard output.
	 * 
	 * @param number Number for decomposition.
	 */
	private static void printDecomposition(long number) {
		int factor = 2;
		int i = 1;

		System.out.println("You requested decomposition of number " + number
				+ " onto prime factors. Here they are:");

		while (true) {
			if (number == 1) {
				break;
			} else if (number % factor == 0) {
				System.out.println(i + ". " + factor);
				number = number / factor;
				i++;
			} else {
				factor++;
			}
		}
	}

}
