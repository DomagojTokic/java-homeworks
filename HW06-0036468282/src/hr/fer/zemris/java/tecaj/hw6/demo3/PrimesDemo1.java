package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * Example class for {@link PrimesCollection}
 * 
 * @author Domagoj Tokić
 *
 */
public class PrimesDemo1 {

	/**
	 * Entrance into PrimesDemo1 class
	 * 
	 * @param args No parameters
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}

}
