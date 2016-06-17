package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Uspoređuje vrijednosti dvaju registara. Ako su vrijednosti iste, postavlja
 * flag na true (1).
 * 
 * @author Domagoj
 *
 */
public class InstrTestEquals implements Instruction {

	/**
	 * Indeks prvog registra
	 */
	private int indexRegistra1;

	/**
	 * Indeks drugog registra
	 */
	private int indexRegistra2;

	/**
	 * Stvori instancu testEquals instrukcije
	 * 
	 * @param arguments Lista s argumentima
	 * @throws IllegalArgumentException ako arugmenti nisu dva registra
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		for (int i = 0; i < 2; i++) {
			if (!arguments.get(i).isRegister()
					|| RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + i + "!");
			}
		}
		this.indexRegistra1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegistra2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegistra1);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegistra2);

		computer.getRegisters().setFlag(value1.equals(value2));

		return false;
	}

}
