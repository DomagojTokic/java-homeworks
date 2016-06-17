package hr.fer.zemris.java.fractals;

import java.util.concurrent.Callable;

import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexPolynomial;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;

/**
 * Job for calculation fractals in set span of y (vertical) coordinate and
 * storing them in array.
 * 
 * @author Domagoj
 *
 */
public class FractalJob implements Callable<Void> {

	/** Minimal real part */
	private double reMin;

	/** Maximal real part */
	private double reMax;

	/** Minimal imaginary part */
	private double imMin;

	/** Maximal imaginary part */
	private double imMax;

	/** Frame width */
	private int width;

	/** Frame height */
	private int height;

	/** Storage of calculated fractals */
	private short[] data;

	/** Starting y coordinate for calculation */
	private int yStart;

	/** Ending y coordinate for calculation */
	private int yEnd;

	/** Polynomial in rooted form */
	private ComplexRootedPolynomial rootedPolynomial;

	/** Maximum number of iterations for fractal point calculation */
	private static final int NUM_OF_ITERATIONS = 256;

	/** Minimum distance of complex number from root */
	private static final double TRESHOLD = 0.002;

	/** Minimum value of |zn1-zn| */
	private static final double CONVERGENCE_TRESHOLD = 0.001;

	/**
	 * Creates new instance of {@link FractalJob}
	 * 
	 * @param reMin Minimal real part
	 * @param reMax Maximal real part
	 * @param imMin Minimal imaginary part
	 * @param imMax Maximal imaginary part
	 * @param width Frame width
	 * @param height Frame height
	 * @param data Storage of calculated fractals
	 * @param yMin Starting y coordinate for calculation
	 * @param yMax Ending y coordinate for calculation
	 * @param rootedPolynomial Polynomial in rooted form
	 */
	public FractalJob(double reMin, double reMax, double imMin, double imMax, int width, int height,
			short[] data, int yMin, int yMax, ComplexRootedPolynomial rootedPolynomial) {
		super();
		this.reMin = reMin;
		this.reMax = reMax;
		this.imMin = imMin;
		this.imMax = imMax;
		this.width = width;
		this.height = height;
		this.data = data;
		this.yStart = yMin;
		this.yEnd = yMax;
		this.rootedPolynomial = rootedPolynomial;
	}

	@Override
	public Void call() throws Exception {
		int offset = yStart * width;
		ComplexPolynomial polynominal = rootedPolynomial.toComplexPolynom();
		double u;
		double v;

		for (int y = yStart; y <= yEnd; y++) {
			for (int x = 0; x < width; x++) {
				u = x / (width - 1.0) * (reMax - reMin) + reMin;
				v = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;

				Complex zn = new Complex(u, v);
				Complex zn1 = Complex.ZERO;

				double module = 0;
				int iter = 0;
				do {
					zn1 = nextFractal(zn, polynominal);
					module = zn1.sub(zn).module();
					zn = zn1;
					iter++;
				} while (module > CONVERGENCE_TRESHOLD && iter < NUM_OF_ITERATIONS);
				data[offset++] = (short) rootedPolynomial.indexOfClosestRootFor(zn1, TRESHOLD);
			}
		}
		return null;
	}

	/**
	 * Returns fractal after zn based on given polynomial function
	 * 
	 * @param zn Fractal
	 * @param polynominal Complex polynomial function
	 * @return newt fractal
	 */
	private static Complex nextFractal(Complex zn, ComplexPolynomial polynominal) {
		Complex numerator = polynominal.apply(zn);
		Complex denominator = polynominal.derive().apply(zn);
		Complex fraction = numerator.divide(denominator);
		Complex zn1 = zn.sub(fraction);
		return zn1;
	}

}
