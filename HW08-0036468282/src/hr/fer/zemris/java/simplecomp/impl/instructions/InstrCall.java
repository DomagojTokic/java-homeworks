package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Pozove proceduru na danoj adresi. Trenutna adresa se pohranjuje na stog.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrCall implements Instruction {

	/**
	 * Adresa pozvane funkcije
	 */
	Integer adresa;

	/**
	 * Stvori instancu call instrukcije
	 * 
	 * @param arguments Lista s argumentom
	 * @throws IllegalArgumentException ako argument nije adresa procedure
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		if (arguments.size() != 1 && !arguments.get(0).isNumber()) {
			throw new IllegalArgumentException(
					"Instruction call must get procedure address for argument!");
		}

		adresa = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Integer stackTop = (Integer) computer.getRegisters()
				.getRegisterValue(Registers.STACK_REGISTER_INDEX);
		computer.getMemory().setLocation(stackTop, computer.getRegisters().getProgramCounter());
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, stackTop - 1);
		computer.getRegisters().setProgramCounter(adresa - 1);
		return false;
	}

}
