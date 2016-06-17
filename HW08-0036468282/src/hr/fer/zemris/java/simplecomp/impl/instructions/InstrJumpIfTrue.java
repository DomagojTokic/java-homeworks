package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija za skakanje na novu lokaciju ako je postavljen flag.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrJumpIfTrue implements Instruction {

	/**
	 * Nova adresa (adresa na koju se skače)
	 */
	int adresa;

	/**
	 * Stvori novu instancu jumpIfTrue instrukcije.
	 * 
	 * @param arguments Lista koja sadrži adresu na koju se potencijalno skače
	 * @throws IllegalArgumentException ako argument nije adresa
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
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
		if (computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(adresa - 1);
		}
		return false;
	}

}
