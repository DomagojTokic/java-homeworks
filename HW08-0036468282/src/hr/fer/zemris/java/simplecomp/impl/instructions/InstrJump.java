package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija za skakanje s trenutne lokacije na drugu.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrJump implements Instruction {

	/**
	 * Adresa nove lokacije
	 */
	int adresa;
	
	/**
	 * Stvori novu jump instrukciju
	 * @param arguments Lista koja sadrži novu adresu
	 * @throws IllegalArgumentException ako argument nije jedna adresa.
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument!");
		}

		adresa = (Integer) arguments.get(0).getValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(adresa - 1);
		return false;
	}
	
}
