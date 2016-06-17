package hr.fer.zemris.java.gui.calc;

import javax.swing.JButton;

/**
 * Class extending JButton in a way that it can store operations for
 * {@link Calculator}. Every button is predicted to hold up to two operations,
 * original and inverted operation.
 * 
 * @author Domagoj
 *
 */
public class OperationStorageButton extends JButton {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Main operation.
	 */
	Object mainOperation;

	/**
	 * Inverted operation
	 */
	Object invertedOperation;

	/**
	 * Creates instance of <code>OperationStorageButton</code> with one
	 * operation.
	 * 
	 * @param mainOperation Main operation
	 * @param text Button text
	 */
	public OperationStorageButton(Object mainOperation, String text) {
		super(text);
		this.mainOperation = mainOperation;
	}

	/**
	 * Creates instance of <code>OperationStorageButton</code> with two
	 * operations.
	 * 
	 * @param mainOperation Main operation
	 * @param invertedOperation Inverted operation
	 * @param text Button text
	 */
	public OperationStorageButton(Object mainOperation, Object invertedOperation, String text) {
		super(text);
		this.mainOperation = mainOperation;
		this.invertedOperation = invertedOperation;
	}


	/**
	 * Returns main operation.
	 * @return main operation.
	 */
	public Object getMainOperation() {
		return mainOperation;
	}

	/**
	 * Returns inverted operation.
	 * @return inverted operation.
	 */
	public Object getInvertedOperation() {
		return invertedOperation;
	}

}
