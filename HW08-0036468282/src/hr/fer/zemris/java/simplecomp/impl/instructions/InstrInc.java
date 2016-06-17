package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Poveća vrijednost registra za 1.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrInc implements Instruction {

	/**
	 * Indeks regista za povećavanje
	 */
	private int indexRegistra;

	/**
	 * Stvori instancu increment instrukcije.
	 * @param arguments Lista koja sadrži registar za povećavanje.
	 * @throws IllegalArgumentException ako argument nije jedan registar
	 */
	public InstrInc(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if (!arguments.get(0).isRegister()
				|| RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {
			throw new IllegalArgumentException("Type mismatch for argument!");
		}

		this.indexRegistra = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(indexRegistra,
				(Integer) computer.getRegisters().getRegisterValue(indexRegistra) + 1);
		return false;
	}

}
