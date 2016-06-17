package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Counter of changes on {@link IntegerStorage}
 * 
 * @author Domagoj Tokić
 * @version 1.0
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/** Number of changes */
	int numberOfChanges;

	/**
	 * Raises counter of changes by one and prints number of changes to
	 * standard output.
	 */
	@Override
	public void valueChanged(IntegerStorage integerStorage) {
		numberOfChanges++;
		System.out.println("Number of changes is " + numberOfChanges);
	}

}
