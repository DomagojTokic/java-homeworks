package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija za završavanje izvođenja programa
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrHalt implements Instruction {

	/**
	 * Stvori instancu halt instrukcije
	 * @param arguments Prazna lista
	 * @throws IllegalArgumentException ako dana lista argumenata nije prazna
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if(!arguments.isEmpty()) {
			throw new IllegalArgumentException("Instruction halt can't accept arguments");
		}
	}
	
	@Override
	public boolean execute(Computer computer) {
		System.exit(0);
		return false;
	}

}
