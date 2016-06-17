package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Čita jedan cijeli broj i zapisuje ga na danu lokaciju.
 * 
 * @author Domagoj Tokić
 *
 */
public class InstrIinput implements Instruction {

	/**
	 * Argument instrukcije koji pokazuje na lokaciju spremanja
	 */
	InstructionArgument storageAddress;

	/**
	 * Stvori instancu iinput instrukcije.
	 *  
	 * @param arguments Lista koja sadrži lokaciju spremanja
	 * @throws IllegalArgumentException ako je dani argument String
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size() != 1 || arguments.get(0).isString()) {
			throw new IllegalArgumentException(
					"Integer input instruction must accept only one non-String argument!");
		}

		storageAddress = arguments.get(0);
	}

	@Override
	public boolean execute(Computer computer) {
		Integer number;

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String integerString;
		try {
			integerString = reader.readLine();
			number = Integer.parseInt(integerString);
			computer.getRegisters().setFlag(false);

			if (storageAddress.isNumber()) {
				Integer directLocation = (Integer) storageAddress.getValue();
				computer.getMemory().setLocation(directLocation, number);
			} else if (storageAddress.isRegister()
					&& !RegisterUtil.isIndirect((Integer) storageAddress.getValue())) {
				Integer registerIndex = RegisterUtil
						.getRegisterIndex((Integer) storageAddress.getValue());
				computer.getRegisters().setRegisterValue(registerIndex, number);
			} else {
				Integer indirectLocation = 0;
				try {
					indirectLocation += (Integer) computer.getRegisters().getRegisterValue(
							RegisterUtil.getRegisterIndex((Integer) storageAddress.getValue()));
					indirectLocation += RegisterUtil.getRegisterOffset((Integer) storageAddress.getValue());
				} catch (ClassCastException e) {
					throw new IllegalArgumentException("Unable to use given register in integer input for indirect location!");
				}	
				computer.getMemory().setLocation(indirectLocation, number);
			}
		} catch (NumberFormatException e) {
			computer.getRegisters().setFlag(true);
		} catch (IOException e) {
			System.err.println("Unable to read from input!");
			System.exit(1);
		}
		return false;
	}

}
