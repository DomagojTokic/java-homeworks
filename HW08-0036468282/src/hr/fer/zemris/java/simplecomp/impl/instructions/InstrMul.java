package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Množi vrijednosti dvaju registara i spremaju rezultat u registar.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrMul implements Instruction {

	/**
	 * Indeks prvog registra
	 */
	private int indexRegistra1;
	
	/**
	 * Indeks drugof registra
	 */
	private int indexRegistra2;
	
	/**
	 * Indeks trećeg registra
	 */
	private int indexRegistra3;

	/**
	 * Stvori instancu mul instrukcije
	 * @param arguments Lista koja sadrži registre s kojima se računa
	 * @throws IllegalArgumentException ako argumenti nisu registri
	 */
	public InstrMul(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister()
					|| RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + i + "!");
			}
		}
		this.indexRegistra1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegistra2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.indexRegistra3 = RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegistra3);

		computer.getRegisters().setRegisterValue(indexRegistra1,
				Integer.valueOf((Integer) value1 * (Integer) value2));
		return false;

	}
}
