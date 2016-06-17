package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage for integer value. Notifies observers on every change of value.
 * 
 * @author Domagoj Tokić
 *
 */
public class IntegerStorage {

	/** Storage value */
	private int value;
	
	/** List of observers */
	private List<IntegerStorageObserver> observers;

	/**
	 * Creates instance of {@link IntegerStorage}
	 * 
	 * @param initialValue Initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Adds observer into list of observers
	 * 
	 * @param observer Observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(!observers.contains(observer)){
			observers.add(observer);	
		}
	}

	/**
	 * Removes observer from observers list
	 * 
	 * @param observer Observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Clears list of observers
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Returns value of storage
	 * 
	 * @return value of storage
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets value of storage and notifies observers if value is different from
	 * current
	 * 
	 * @param value New value of storage
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			// Update current value
			this.value = value;
			// Notify all registered observers
			List<IntegerStorageObserver> observerList = new ArrayList<>(observers);
			if (observers != null) {
				for (IntegerStorageObserver observer : observerList) {
					observer.valueChanged(change);
				}
			}
		}
	}
}
