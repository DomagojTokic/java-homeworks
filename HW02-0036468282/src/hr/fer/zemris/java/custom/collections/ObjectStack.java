package hr.fer.zemris.java.custom.collections;

/**
 * Adapter for collections which implements behavior of stack.
 * @author Domagoj Tokić
 * @version 1.0
 */
public class ObjectStack {

	/**
	 * Object storage
	 */
	private ArrayIndexedCollection storage;

	/**
	 * Default constructor
	 */
	public ObjectStack() {
		storage = new ArrayIndexedCollection();
	}

	/**
	 * Checks if stack is empty.
	 * @return State of emptiness
	 */
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	/**
	 * Returns size of stack
	 * @return Size of stack
	 */
	public int size() {
		return storage.size();
	}

	/**
	 * Pushes object on top of stack.
	 * @param value Object to be pushed on stack
	 * @throws IllegalArgumentException id given object is null
	 */
	public void push(Object value) {

		if (value == null) {
			throw new IllegalArgumentException(
					"Cannot add null object onto stack");
		}

		storage.add(value);
	}

	/**
	 * Returns value from top of the stack and removes it from stack.
	 * @return Value on top of the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object pop() {

		if (storage.isEmpty()) {
			throw new EmptyStackException("Stack is empty!");
		}

		Object value = storage.get(storage.size() - 1);
		storage.remove(storage.size() - 1);
		return value;
	}

	/**
	 * Returns value from top of the stack (without removing it).
	 * @return Value on top of the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object peek() {

		if (storage.isEmpty()) {
			throw new EmptyStackException("Stack is empty!");
		}

		return storage.get(storage.size() - 1);
	}

	/**
	 * Removes all elements from stack
	 */
	public void clear() {
		storage.clear();
	}

}
