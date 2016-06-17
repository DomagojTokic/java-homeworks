package hr.fer.zemris.java.complex;

/**
 * Complex (number) polynomial function class which provides basic functions
 * with function roots.
 * 
 * @author Domagoj
 *
 */
public class ComplexRootedPolynomial {

	/** Roots of polynomial function */
	Complex[] roots;

	/**
	 * Creates new instance of {@link ComplexRootedPolynomial}
	 * 
	 * @param roots Roots of polynomial function
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = roots;
	}

	/**
	 * computes polynomial value at given point z
	 * 
	 * @param z Point for calculation
	 * @return value for given point z
	 */
	public Complex apply(Complex z) {
		return toComplexPolynom().apply(z);
	}

	/**
	 * converts this representation to {@link ComplexPolynomial} type
	 * 
	 * @return representation in {@link ComplexPolynomial} type
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = new ComplexPolynomial(Complex.ONE);

		for (Complex root : roots) {
			polynomial = polynomial.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
		}

		return polynomial;
	}

	@Override
	public String toString() {
		String string = "";
		for (Complex root : roots) {
			string += root.toString() + "\n";
		}
		return string;
	}

	/**
	 * Finds index of closest root for given complex number z that is within
	 * treshold.
	 * 
	 * @param z Complex number
	 * @param treshold Maximum distance between root and complex number
	 * @return closest root or -1 if it's outside treshold
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = 0;
		double minDistance = z.sub(roots[0]).module();

		for (int i = 1; i < roots.length; i++) {
			double module = z.sub(roots[i]).module();
			if (module < minDistance) {
				index = i;
				minDistance = module;
			}
		}
		if ((minDistance - roots[index].module()) < treshold) {
			return index + 1;
		}

		return -1;
	}

}
