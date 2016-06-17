package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementacija {@link Registers} sučelja
 * 
 * @author Domagoj Tokić
 *
 */
public class RegistersImpl implements Registers {

	/**
	 * Registri simuliranog računala
	 */
	Object[] register;

	/**
	 * Brojač instrukcija
	 */
	int programCounter;

	/**
	 * Višenamjenska zastavica
	 */
	boolean flag;

	/**
	 * Stvori instancu registara simuliranog računala.
	 * 
	 * @param regsLen Broj registara
	 * @throws IllegalArgumentException ako je broj registara manji od 16
	 */
	public RegistersImpl(int regsLen) {
		if (regsLen < 16) {
			throw new IllegalArgumentException("Minimum number of registers is 16");
		}
		register = new Object[regsLen];
		programCounter = 0;
	}

	@Override
	public Object getRegisterValue(int index) {
		return register[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		register[index] = value;
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		programCounter = value;
	}

	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

}
