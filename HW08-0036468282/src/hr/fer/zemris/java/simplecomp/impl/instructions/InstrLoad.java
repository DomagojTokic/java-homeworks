package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Učita podatak s memorijske lokacije i spremi ga u registar
 * 
 * @author Domagoj
 *
 */
public class InstrLoad implements Instruction {

	/**
	 * Indeks registra u kojeg se učitava
	 */
	private int indexRegistra;

	/**
	 * Vrijednost objekta koji se učitava
	 */
	private Object value;

	/**
	 * Stvori instancu load instrukcije
	 * 
	 * @param arguments Argumenti instrukcije
	 * @throws IllegalArgumentException ako broj argumenta ili njihov tip nije
	 *             valjan
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()
				|| RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {
			throw new IllegalArgumentException("First argument for load must be regular register!");
		}
		if (!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Second argument for load must be memory location!");
		}

		indexRegistra = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		value = arguments.get(1).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object memValue = computer.getMemory().getLocation((Integer) value);
		computer.getRegisters().setRegisterValue(indexRegistra, memValue);
		return false;
	}

}
