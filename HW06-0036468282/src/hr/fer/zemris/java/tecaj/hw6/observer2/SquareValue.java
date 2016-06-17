package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Prints double value of current and beginning value of {@link IntegerStorage}
 * on every change of value.
 * 
 * @author Domagoj Tokić
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints double value of current and beginning value of
	 * {@link IntegerStorage}
	 */
	@Override
	public void valueChanged(IntegerStorageChange integerStorageChange) {
		System.out.println("Squared value of integer storage is "
				+ Math.pow(integerStorageChange.getCurrentValue(), 2)
				+ " and before it was "
				+ Math.pow(integerStorageChange.getBeginningValue(), 2));
	}

}
