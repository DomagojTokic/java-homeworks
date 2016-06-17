package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Premjesti podatak s jedne kolacije na drugu. Lokacija može biti registar ili
 * memorijska lokacija.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrMove implements Instruction {

	/**
	 * Odredišna lokacija
	 */
	Integer dest;
	
	/**
	 * Izvorišna lokacija
	 */
	InstructionArgument src;

	/**
	 * Stvori instancu move instrukcije.
	 * 
	 * @param arguments Lista kojia sadrži dva argumenta
	 * @throws IllegalArgumentException ako argumenti nisu ispravni
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"First argument of move must be register or indirect adress!");
		}
		if (arguments.get(1).isString()) {
			throw new IllegalArgumentException("Second argument can't be String!");
		}

		dest = (Integer) arguments.get(0).getValue();
		src = arguments.get(1);
	}

	@Override
	public boolean execute(Computer computer) {
		Object value;
		// Extractiong value object from source
		if (src.isRegister()) {
			if (RegisterUtil.isIndirect((Integer) src.getValue())) {
				int srcAddress = getIndirectAdress(computer, (Integer) src.getValue());
				value = computer.getMemory().getLocation(srcAddress);
			} else {
				value = computer.getRegisters()
						.getRegisterValue(RegisterUtil.getRegisterIndex((Integer) src.getValue()));
			}
		} else {
			value = src.getValue();
		}

		if (RegisterUtil.isIndirect(dest)) {
			int destAddress = getIndirectAdress(computer, dest);
			computer.getMemory().setLocation(destAddress, value);
		} else {
			computer.getRegisters().setRegisterValue(RegisterUtil.getRegisterIndex(dest), value);
		}

		return false;
	}

	/**
	 * Računa adresu iz argumenta koji koristi indirektno adresiranje.
	 * 
	 * @param computer Computer
	 * @param value Vrijednost argumenta
	 * @return Memorijska lokacija (adresa)
	 */
	private static int getIndirectAdress(Computer computer, Integer value) {
		Object regValue = computer.getRegisters()
				.getRegisterValue(RegisterUtil.getRegisterIndex(value));
		if (!(regValue instanceof Integer)) {
			throw new ClassCastException(
					"Unable to use indirect adressing with register value " + regValue);
		}
		Integer regOffset = (Integer) regValue;
		int offset = RegisterUtil.getRegisterOffset(value);
		return offset + regOffset;
	}

}
