package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Interface for integer storage observers
 * 
 * @author Domagoj Tokić
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Action to be performed after value of storage has changed
	 * 
	 * @param integerStorage Storage which value has changed
	 */
	public void valueChanged(IntegerStorageChange integerStorage);
	
}
