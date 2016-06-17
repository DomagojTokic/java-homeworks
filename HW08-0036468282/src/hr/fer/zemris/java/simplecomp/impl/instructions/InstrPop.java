package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Skine vrijednost s vrha stoga, spremi ga u registar te poveća adresu stoga za
 * 1.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrPop implements Instruction {

	/**
	 * Indeks registra u kojeg se sprema podatak sa stoga
	 */
	Integer indexRegistra;

	/**
	 * Stvori instancu pop instrukcije
	 * 
	 * @param arguments Lista s argumentom instrukcije
	 * @throws IllegalArgumentException ako argument nije registar ili ima više
	 *             od jednog argumenta
	 */
	public InstrPop(List<InstructionArgument> arguments) {
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
		Object value = computer.getMemory().getLocation(stackTop + 1);
		computer.getRegisters().setRegisterValue(indexRegistra, value);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, stackTop + 1);
		return false;
	}

}
