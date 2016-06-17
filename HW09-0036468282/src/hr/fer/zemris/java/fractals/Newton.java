package hr.fer.zemris.java.fractals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;

/**
 * Program for drawing fractals derived from Newton-Raphson iteration. For
 * defining fractal image, complex polynomial functions are used. To define
 * polynomial functions, user is required to enter at least 2 complex roots and
 * confirm it with END_INPUT_WORD. If END_INPUT_WORD is written before 2 roots,
 * program ends without drawing image.
 * 
 * @author Domagoj
 *
 */
public class Newton {

	/** Minimum number of roots that user must input */
	private static final int MIN_ROOTS = 2;

	/** End input word */
	private static final String END_INPUT_WORD = "done";

	/**
	 * Entrance into {@link Newton}
	 * 
	 * @param args No arguments
	 */
	public static void main(String[] args) {
		ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(requestRoots());
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new FractalProducer(polynomial));
	}

	/**
	 * Requests input of complex roots from command line in format a+ib. Input
	 * is finished by writing word defined in END_INPUT_WORD.
	 * 
	 * @return array of {@link Complex} number roots
	 */
	private static Complex[] requestRoots() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		List<Complex> rootList = new ArrayList<>();

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		while (!line.trim().equals(END_INPUT_WORD)) {
			System.out.print("Root " + (rootList.size() + 1) + "> ");
			line = readLine(reader);

			if (line.equals(END_INPUT_WORD)) {
				if (rootList.size() < MIN_ROOTS) {
					System.out.println(
							"Exiting program without execution because there aren't enough roots!");
					System.exit(1);
				} else {
					break;
				}
			}

			Complex root = Complex.fromString(line);
			if (root == null) {
				System.out.println("Please write valid complex number (a+ib)!");
				continue;
			} else {
				rootList.add(root);
			}

		}
		Complex[] roots = new Complex[rootList.size()];
		for (int i = 0; i < rootList.size(); i++) {
			roots[i] = rootList.get(i);
		}

		return roots;
	}

	/**
	 * Reads line from input stream
	 * 
	 * @param reader Buffered reader
	 * @return line
	 * @throws IOException if reader is unable to read from input stream
	 */
	private static String readLine(BufferedReader reader) {
		try {
			return reader.readLine().trim();
		} catch (IOException e) {
			System.err.println("Unable to read from input!");
		}
		return null;
	}
}
