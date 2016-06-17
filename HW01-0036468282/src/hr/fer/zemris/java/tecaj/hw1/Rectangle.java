package hr.fer.zemris.java.tecaj.hw1;

import java.io.*;

/**
 * Simple class which reads rectangle width and height from standard input and
 * calculates its area and perimeter.
 * 
 * @author Domagoj Tokić
 * 
 */

public class Rectangle {

	/**
	 * Entrance into class function.
	 * 
	 * @param args
	 * @throws IOException if program is unable to read form standard input.
	 */

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(System.in)));

		String request = "Write rectangle width: ";
		double width = requestPosNumInput(request, reader);

		request = "Write rectange height: ";
		double height = requestPosNumInput(request, reader);

		System.out.printf(
				"You've defined rectanle with width %.1f and height %.1f. It's area is %.1f"
						+ " and perimeter is %.1f",
				width, height, width * height, 2 * width + 2 * height);

		reader.close();
	}

	/**
	 * Method for reading one number input.
	 * 
	 * @param request Text of request.
	 * @param reader Input reader.
	 * @return Read number.
	 * @throws IOException if reader is unable to read from input.
	 */
	public static double requestPosNumInput(String request,
			BufferedReader reader) throws IOException {

		while (true) {
			System.out.println(request);
			String line = reader.readLine();
			line = line.trim();

			if (line.isEmpty()) {
				System.out.println("Empty line written, please enter number:");
				continue;
			}

			double number = Double.parseDouble(line);

			if (number < 0) {
				System.out.println("Number must be positive!");
				continue;
			}

			return number;
		}

	}

}
