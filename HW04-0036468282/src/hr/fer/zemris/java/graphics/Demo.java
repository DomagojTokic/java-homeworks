package hr.fer.zemris.java.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * Program draws given geometric shapes onto raster. It must get one or two
 * arguments form command line. If program is given two arguments, first one is
 * interpreted as width and second as height. If program receives one argument,
 * it's considered as both width and height. Next, user has to enter number of
 * inputs that he will make. Each input has to be either geometric shape or
 * change in flip mode (on/off). Input of geometric shapes are in form:
 * "SQUARE x y size"
 * "RECTANGLE x y width height"
 * "CIRCLE x y radius"
 * "ELLIPSE x y horisontal_radius vertical_radius"
 * Change in flip mode is made with input:
 * "FLIP"
 * 
 * @author Domagoj Tokić
 *
 */
public class Demo {

	/**
	 * Width of raster
	 */
	private static int rasterWidth;

	/**
	 * Width of raster
	 */
	private static int rasterHeight;

	/**
	 * Number of inputs
	 */
	private static int numberOfInputs;

	/**
	 * List of shapes (inputs) for drawing
	 */
	private static GeometricShape[] shapes;

	/**
	 * Raster
	 */
	private static BWRasterMem raster;

	/**
	 * Entrance into Demo class
	 * 
	 * @param args If two arguments, first is width, second is height. If one
	 *            argument is provided, it's both width and height.
	 */
	public static void main(String[] args) {

		readRasterDimensions(args);
		raster = new BWRasterMem(rasterWidth, rasterHeight);

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		readNumberOfInputs(reader);
		shapes = new GeometricShape[numberOfInputs];
		for (int i = 0; i < numberOfInputs; i++) {
			readShapeInput(reader, i);
		}
		for (int i = 0; i < numberOfInputs; i++) {
			process(i);
		}
		RasterView view = new SimpleRasterView();
		view.produce(raster);
	}

	/**
	 * Reads command line arguments which are dimensions of raster.
	 * 
	 * @param args Command line arguments
	 */
	private static void readRasterDimensions(String[] args) {
		if (args.length == 2) {
			try {
				rasterWidth = Integer.parseInt(args[0]);
				rasterHeight = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.err.println("Input is in invalid format!");
				System.exit(1);
			}
		} else if (args.length == 1) {
			try {
				rasterWidth = Integer.parseInt(args[0]);
				rasterHeight = rasterWidth;
			} catch (NumberFormatException e) {
				System.err.println("Input is in invalid format!");
				System.exit(1);
			}
		} else {
			System.err.println("You must put one or two arguments");
			System.exit(1);
		}
	}

	/**
	 * Reads first input from user which is number of inputs for drawing.
	 * 
	 * @param reader Reader of standard input
	 * @throws IOException if input stream unexpectedly closes
	 */
	private static void readNumberOfInputs(BufferedReader reader) {
		try {
			String numberOfInputsString = reader.readLine();
			numberOfInputs = Integer.parseInt(numberOfInputsString);
		} catch (IOException e) {
			System.err.println("Something went wrong with input.");
			System.exit(1);
		} catch (NumberFormatException e) {
			System.err.println("Input is in invalid format!");
			System.exit(1);
		}
	}

	/**
	 * Reads shape input. Input of geometric shapes are in form:
	 * "SQUARE x y size"
	 * "RECTANGLE x y width height"
	 * "CIRCLE x y radius"
	 * "ELLIPSE x y horisontal_radius vertical_radius"
	 * Change in flip mode is made with input:
	 * "FLIP"
	 * 
	 * @param reader Reader of standard input
	 * @param index Index of shape in class shape array
	 */
	private static void readShapeInput(BufferedReader reader, int index) {
		String line;
		try {
			line = reader.readLine();
			line = line.trim().replaceAll("\\s+", " ");
			String[] elements = line.split(" ");

			switch (elements[0]) {
			case "FLIP":
				shapes[index] = null;
				break;
			case "SQUARE":
				shapes[index] = generateSquare(elements);
				break;
			case "RECTANGLE":
				shapes[index] = generateRectangle(elements);
				break;
			case "CIRCLE":
				shapes[index] = generateCircle(elements);
				break;
			case "ELLIPSE":
				shapes[index] = generateEllipse(elements);
				break;
			default:
				System.err.println("Invalid input");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Something went wrong with input stream!");
			System.exit(1);
		}

	}

	/**
	 * Generates instance of square
	 * 
	 * @param elements Array of strings containing parameters for creation
	 * @return instance of square
	 */
	private static GeometricShape generateSquare(String[] elements) {
		if (elements.length != 4) {
			System.err.println("Square must have 3 parameters");
			System.exit(1);
		}
		try {
			int x = Integer.parseInt(elements[1]);
			int y = Integer.parseInt(elements[2]);
			int size = Integer.parseInt(elements[3]);
			return new Square(x, y, size);
		} catch (NumberFormatException e) {
			System.err.println("Parameters must be whole numbers");
			System.exit(1);
		}
		throw new IllegalAccessError();
	}

	/**
	 * Generates instance of rectangle
	 * 
	 * @param elements Array of strings containing parameters for creation
	 * @return instance of rectangle
	 */
	private static GeometricShape generateRectangle(String[] elements) {
		if (elements.length != 5) {
			System.err.println("Square must have 4 parameters");
			System.exit(1);
		}
		try {
			int x = Integer.parseInt(elements[1]);
			int y = Integer.parseInt(elements[2]);
			int width = Integer.parseInt(elements[3]);
			int height = Integer.parseInt(elements[4]);
			return new Rectangle(x, y, width, height);
		} catch (NumberFormatException e) {
			System.err.println("Parameters must be whole numbers");
			System.exit(1);
		}
		throw new IllegalAccessError();
	}

	/**
	 * Generates instance of circle
	 * 
	 * @param elements Array of strings containing parameters for creation
	 * @return instance of circle
	 */
	private static GeometricShape generateCircle(String[] elements) {
		if (elements.length != 4) {
			System.err.println("Square must have 3 parameters");
			System.exit(1);
		}
		try {
			int x = Integer.parseInt(elements[1]);
			int y = Integer.parseInt(elements[2]);
			int radius = Integer.parseInt(elements[3]);
			return new Circle(x, y, radius);
		} catch (NumberFormatException e) {
			System.err.println("Parameters must be whole numbers");
			System.exit(1);
		}
		throw new IllegalAccessError();
	}

	/**
	 * Generates instance of ellipse
	 * 
	 * @param elements Array of strings containing parameters for creation
	 * @return instance of ellipse
	 */
	private static GeometricShape generateEllipse(String[] elements) {
		if (elements.length != 5) {
			System.err.println("Square must have 4 parameters");
			System.exit(1);
		}
		try {
			int x = Integer.parseInt(elements[1]);
			int y = Integer.parseInt(elements[2]);
			int horisontalRadius = Integer.parseInt(elements[3]);
			int verticalRadius = Integer.parseInt(elements[4]);
			return new Ellipse(x, y, horisontalRadius, verticalRadius);
		} catch (NumberFormatException e) {
			System.err.println("Parameters must be whole numbers");
			System.exit(1);
		}
		throw new IllegalAccessError();
	}

	/**
	 * Processes element of shapes array. If element is instance of geometric
	 * shape, method draws it onto raster. If element is null, switches raster
	 * flip mode.
	 * 
	 * @param index Index of shape array element
	 */
	public static void process(int index) {
		if (shapes[index] == null) {
			if (raster.isFlipMode()) {
				raster.disableFlipMode();
			} else {
				raster.enableFlipMode();
			}
		} else {
			shapes[index].draw(raster);
		}
	}

}
