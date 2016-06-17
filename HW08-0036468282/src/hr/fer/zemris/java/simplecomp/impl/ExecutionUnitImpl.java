package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * Implementacija{@link ExecutionUnit} sučelja
 * 
 * @author Domagoj Tokić
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {

		computer.getRegisters().setProgramCounter(0);
		Instruction instruction;

		do {
			instruction = (Instruction) computer.getMemory()
					.getLocation(computer.getRegisters().getProgramCounter());

			if (instruction instanceof Instruction) {
				instruction.execute(computer);
				computer.getRegisters().incrementProgramCounter();
			} else {
				throw new ClassCastException(
						"Invalid end of program. Before " + instruction + " program must end");
			}
		} while (instruction != null);

		return true;
	}

}
