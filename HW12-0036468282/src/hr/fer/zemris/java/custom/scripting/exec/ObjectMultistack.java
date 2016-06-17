package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates stack for every different string put in instance of this class.
 * Therefore, every stack has it's name (key).
 * 
 * @author Domagoj Tokić
 *
 */
public class ObjectMultistack {

	/**
	 * Entry class for {@link ObjectMultistack}
	 * 
	 * @author Domagoj Tokić
	 *
	 */
	private static class MultistackEntry {

		/**
		 * Value of entry
		 */
		private ValueWrapper value;

		/**
		 * Next entry in stack
		 */
		private MultistackEntry next;

		/**
		 * Creates instance of {@link MultistackEntry}
		 * 
		 * @param value Entry value
		 * @param next Next entry in stack
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

	}

	/**
	 * Map of object stacks
	 */
	private Map<String, MultistackEntry> objectMultistack;

	/**
	 * Creates instance of {@link ObjectMultistack}
	 */
	public ObjectMultistack() {
		objectMultistack = new HashMap<>();
	}

	/**
	 * Pushes object onto stack with given name
	 * 
	 * @param name Stack name
	 * @param valueWrapper Value of entry
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry entry = objectMultistack.get(name);
		objectMultistack.put(name, new MultistackEntry(valueWrapper, entry));
	}

	/**
	 * Removes entry from stack with given name and returns it's value.
	 * @param name Stack name
	 * @return value of removed stack entry
	 */
	public ValueWrapper pop(String name) {
		MultistackEntry entry = objectMultistack.get(name);
		if (entry == null) {
			throw new EmptyStackException();
		}
		objectMultistack.put(name, entry.next);
		return entry.value;
	}

	/**
	 * Returns value from top of stack with given name
	 * 
	 * @param name Stack name
	 * @return value from top of stack
	 */
	public ValueWrapper peek(String name) {
		MultistackEntry entry = objectMultistack.get(name);
		if (entry == null) {
			throw new EmptyStackException();
		}
		return entry.value;
	}

	/**
	 * Checks if stack with given name is empty
	 * @param name Stack name
	 * @return true if stack is empty, else false
	 */
	public boolean isEmpty(String name) {
		return objectMultistack.get(name) == null;
	}

}
