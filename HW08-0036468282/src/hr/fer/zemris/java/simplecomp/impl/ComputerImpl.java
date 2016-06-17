package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementacija {@link Computer} sučelja
 * 
 * @author Domagoj Tokić
 *
 */
public class ComputerImpl implements Computer {

	/**
	 * Memorija simuliranog računala
	 */
	Memory memory;

	/**
	 * Registri simuliranog računala
	 */
	Registers registers;

	/**
	 * Stvori instancu simuliranog računala.
	 * 
	 * @param size Veličina memorije
	 * @param regLen Broj registara
	 */
	public ComputerImpl(int size, int regLen) {
		memory = new MemoryImpl(size);
		registers = new RegistersImpl(regLen);
	}

	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

}
