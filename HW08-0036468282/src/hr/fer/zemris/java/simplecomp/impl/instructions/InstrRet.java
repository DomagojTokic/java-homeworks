package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija za vraćanje iz pozvanje procedure. Vraća se na adresu koja je
 * vrijednost spremljena na vrh stoga.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrRet implements Instruction {

	/**
	 * Stvori instancu ret instrukcije.
	 * 
	 * @param arguments Prazna lista
	 * @throws IllegalArgumentException ako lista argumenata nije prazna.
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		if (!arguments.isEmpty()) {
			throw new IllegalArgumentException("Instruction return cannot have arguments!");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		Integer stackTop = (Integer) computer.getRegisters()
				.getRegisterValue(Registers.STACK_REGISTER_INDEX);
		Integer returnAddress = (Integer) computer.getMemory().getLocation(stackTop + 1);
		computer.getRegisters().setProgramCounter(returnAddress);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, stackTop + 1);
		return false;
	}

}
