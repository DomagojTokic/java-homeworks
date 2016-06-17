package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Implementacija {@link Memory} sučelja
 * 
 * @author Domagoj Tokić
 *
 */
public class MemoryImpl implements Memory {

	/**
	 * Memorija simuliranog računala
	 */
	Object[] memory;

	/**
	 * Stvori instancu simulirane računalne memorije.
	 * 
	 * @param size Veličina memorije
	 * @throws IllegalArgumentException ako je veličina memorije manja od 1
	 */
	public MemoryImpl(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Size of memory must be greater than zero!");
		}
		memory = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		memory[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		return memory[location];
	}

}
