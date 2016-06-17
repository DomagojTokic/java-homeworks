package hr.fer.zemris.java.hw13.servlets;

/**
 * Storage of degree used in {@link TrigonometricServlet}. It's used in
 * trigonometic.jsp for retrieving sin and cosine values of stored degree.
 * 
 * @author Domagoj Tokić
 *
 */
public class TrigResult {

	/** Degree */
	private int degree;

	/**
	 * Creates new instance of {@link TrigResult}
	 * 
	 * @param degree Degree
	 */
	public TrigResult(int degree) {
		super();
		this.degree = degree;
	}

	/**
	 * Returns stored degree.
	 * 
	 * @return stored degree.
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * Returns sin value of stored degree.
	 * 
	 * @return sin value of stored degree.
	 */
	public double getSin() {
		return Math.sin(Math.toRadians(degree));
	}

	/**
	 * Returns cosine value of stored degree.
	 * 
	 * @return cosine value of stored degree.
	 */
	public double getCos() {
		return Math.cos(Math.toRadians(degree));
	}

}
