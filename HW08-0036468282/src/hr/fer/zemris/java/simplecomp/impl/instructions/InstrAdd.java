package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Zbroji vrijednosti iz dvaju registara i spremi dobivenu vrijednost u
 * registar:
 * add rX, rY, rZ
 * rX <- rY + rZ
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrAdd implements Instruction {

	/**
	 * Indeks prvog registra
	 */
	private int indexRegistra1;

	/**
	 * Indeks drugog registra
	 */
	private int indexRegistra2;

	/**
	 * Indeks trećeg registra
	 */
	private int indexRegistra3;

	/**
	 * Stvori instancu add instrukcije
	 * 
	 * @param arguments Lista rgumentat instrukcije
	 * @throws IllegalArgumentException ako broj argumenta ili njihov tip nije
	 *             valjan
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister()
					|| RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + i + "!");
			}
		}
		this.indexRegistra1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegistra2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.indexRegistra3 = RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegistra3);

		computer.getRegisters().setRegisterValue(indexRegistra1,
				Integer.valueOf((Integer) value1 + (Integer) value2));
		return false;

	}

}
