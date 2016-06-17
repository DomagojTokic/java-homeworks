package hr.fer.zemris.java.tecaj.hw6.observer2;

import hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage;

/**
 * Counter of changes of {@link IntegerStorage}
 * 
 * @author Domagoj Tokić
 * @version 1.1
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
	public void valueChanged(IntegerStorageChange integerStorage) {
		numberOfChanges++;
		System.out.println("Number of changes is " + numberOfChanges);
	}

}
