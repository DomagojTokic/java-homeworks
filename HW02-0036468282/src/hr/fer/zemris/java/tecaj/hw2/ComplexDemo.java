package hr.fer.zemris.java.tecaj.hw2;

/**
 * Demo class for testing ComplexNumber class.
 * @author Domagoj Tokić
 *
 */
public class ComplexDemo {

	/**
	 * Entrance into demo class method
	 * @param args No arguments
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c4 = ComplexNumber.parse("8+2i");
        System.out.println(c4.toString());
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
				.div(c2).power(3).root(2)[1];

		System.out.println(c3);

	}

}
