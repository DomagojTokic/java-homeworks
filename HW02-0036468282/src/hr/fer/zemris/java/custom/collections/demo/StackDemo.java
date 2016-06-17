package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Command-line application which accepts a single command-line argument:
 * expression which should be evaluated. Expression must be in post-fix
 * representation. Application replaces two numbers behind operation and
 * operation with result of operation. Supported operators are: +, -, *, / and
 * %. If application ends with one number, it writes it.
 * Example 1: "8 2 /" means apply / on 8 and 2, so 8/2=4.
 * Example 2: "-1 8 2 / +" means apply / on 8 and 2, so 8/2=4, then apply + on
 * -1 and 4, so the result is 3.
 * @author Domagoj Tokić
 * @version 1.0
 */
public class StackDemo {

	/**
	 * Entrance into stack testing class
	 * @param args
	 */
	
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Number of arguments must be 1.");
			System.exit(1);
		}

		ObjectStack stack = new ObjectStack();
		String input = args[0];

		input = input.trim();
		input = input.replaceAll("\\s+", " ");
		String[] literals = input.split(" ");

		for (int i = 0; i < literals.length; i++) {

			if (isInteger(literals[i])) {
				int number = Integer.parseInt(literals[i]);
				stack.push(number);
			} else {
				try {
					int secondOperand = (int) stack.pop();
					int firstOperand = (int) stack.pop();

					stack.push(operate(firstOperand, secondOperand, literals[i]));
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
					System.exit(1);
				} catch (EmptyStackException e) {
					System.err.println("Invalid number of literals");
					System.exit(1);
				}
			}
		}

		if (stack.size() != 1) {
			System.err.println("Invalid number of literals");
			System.exit(1);
		}

		System.out.println("Expression evaluates to " + stack.pop().toString()
				+ ".");

	}

	/**
	 * Tests if given string could be turned into integer.
	 * @param s Potential integer String
	 * @return True if String can be turned into Integer, else false.
	 */
	public static boolean isInteger(String s) {
		if (s.isEmpty()) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1) {
					return false;
				} else {
					continue;
				}
			}
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Does operation over two operands depending on given operation. Operand is
	 * String and current version supports operation arguments "+", "-", "*",
	 * "/" and "%". Method throws IllegalArgumentException if given operation
	 * string is not supported or division by zero occurred.
	 * @param firstOperand
	 * @param secondOperand
	 * @param operation
	 * @return Result of operation
	 */
	public static int operate(int firstOperand, int secondOperand,
			String operation) {

		switch (operation) {
		case "+":
			return firstOperand + secondOperand;
		case "-":
			return firstOperand - secondOperand;
		case "*":
			return firstOperand * secondOperand;
		case "/":
			if (secondOperand == 0) {
				throw new IllegalArgumentException("Cannot divide by zero");
			} else {
				return firstOperand / secondOperand;
			}
		case "%":
			return firstOperand % secondOperand;
		default:
			throw new IllegalArgumentException("Given operation " + operation
					+ "isn't supported");
		}
	}
}
