package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Ispiše vrijednost registra na standardni izlaz.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrEcho implements Instruction {

	/**
	 * Indeks registra za ispis
	 */
	int indeksRegistra;

	/**
	 * Stvori instancu echo instrukcije
	 * 
	 * @param arguments Lista s argumentom
	 * @throws IllegalArgumentException ako nije predan valjan argument
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 register argument!");
		}
		if (!arguments.get(0).isRegister() && !RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {
			throw new IllegalArgumentException("Echo argument must be register!");
		}
		
		indeksRegistra = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object regValue = computer.getRegisters()
				.getRegisterValue(RegisterUtil.getRegisterIndex(indeksRegistra));

		if (RegisterUtil.isIndirect(indeksRegistra)) {			
			if(regValue instanceof Integer) {
				int adresa = (Integer) regValue + RegisterUtil.getRegisterOffset(indeksRegistra);
				System.out.print(computer.getMemory().getLocation(adresa));
			} else {
				throw new IllegalArgumentException("Sadržaj registra mora biti cijeli broj za indirektno adresiranje!");
			}
		} else {
			System.out.print(regValue);
		}

		return false;
	}

}
