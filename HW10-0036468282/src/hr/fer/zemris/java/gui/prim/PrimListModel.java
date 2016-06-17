package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This class is an implementation of ListModel interface and its main function
 * is to store and generate prime numbers and notify it's observers about
 * it.
 * 
 * @author Domagoj
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/** List of prime numbers */
	private ArrayList<Integer> primes;

	/** Model listeners */
	private ArrayList<ListDataListener> listeners;
	
	/** Listeners which are ready for removal */
	private ArrayList<ListDataListener> listenersForRemoval;

	/** Creates new instance of <code>PrimListModel</code> */
	public PrimListModel() {
		primes = new ArrayList<>();
		listeners = new ArrayList<>();
		listenersForRemoval = new ArrayList<>();
	}

	/**
	 * Adds next primary number to primary number list and notifies listeners.
	 */
	public void next() {
		int currentPrime = 0;

		if (primes.isEmpty()) {
			currentPrime = 1;
		} else {
			currentPrime = primes.get(primes.size() - 1);
		}

		while (true) {
			currentPrime++;
			boolean isPrime = true;
			for (int i = 2; i <= Math.sqrt(currentPrime); i++) {
				if (currentPrime % i == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				break;
			}
		}

		primes.add(currentPrime);

		for(ListDataListener listener : listenersForRemoval) {
			listeners.remove(listener);
		}
		
		for (ListDataListener listener : listeners) {
			listener.contentsChanged(
					new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, primes.size() - 1));
		}
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listenersForRemoval.add(l);
	}

}
