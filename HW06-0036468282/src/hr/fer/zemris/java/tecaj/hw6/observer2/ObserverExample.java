package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Example of usage of observer design pattern
 * 
 * @author Domagoj Tokić
 *
 */
public class ObserverExample {

	/**
	 * Entrance into example class
	 * 
	 * @param args No arguments
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		IntegerStorageObserver observer2 = new ChangeCounter();
		istorage.addObserver(observer2);
		IntegerStorageObserver observer3 = new DoubleValue(4);
		istorage.addObserver(observer3);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}

}
