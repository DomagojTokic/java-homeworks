package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Stavi vrijednost registra na vrh stoga te smanji adresu registra za 1.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrPush implements Instruction {

	/**
	 * Indeks registra čiji se sadržaj stavlja na stog
	 */
	int indexRegistra;

	/**
	 * Stvori instancu push instrukcije
	 * 
	 * @param arguments Lista s registrom čiji se sadržaj stavlja na stog.
	 * @throws IllegalArgumentException ako argument nije registar ili ima više
	 *             od jednog argumenta
	 */
	public InstrPush(List<InstructionArgument> arguments) {
		if (arguments.size() != 1 || !arguments.get(0).isRegister()
				|| RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {
			throw new IllegalArgumentException("Argument of push must be register!");
		}

		indexRegistra = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Integer stackTop = (Integer) computer.getRegisters()
				.getRegisterValue(Registers.STACK_REGISTER_INDEX);
		Object value = computer.getRegisters().getRegisterValue(indexRegistra);
		computer.getMemory().setLocation(stackTop, value);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, stackTop - 1);
		return false;
	}

}
